package ru.deepthreads.rest.services

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.Utils
import ru.deepthreads.rest.exceptions.auth.IncorrectPassword
import ru.deepthreads.rest.exceptions.auth.NonexistentAccount
import ru.deepthreads.rest.exceptions.auth.TakenDeepID
import ru.deepthreads.rest.exceptions.auth.Unauthorized
import ru.deepthreads.rest.models.Account
import ru.deepthreads.rest.models.entity.UserProfile
import ru.deepthreads.rest.mongoTemplate
import java.time.Instant

@Service
object AccountService {
    fun validate(deepId: String, password: String): Account {
        val query = Query(Criteria.where("deepId").`is`(deepId))
        val result = mongoTemplate.findOne(query, Account::class.java, "accounts")
        return if (result == null) {
            throw NonexistentAccount()
        } else if (result.password != password) {
            throw IncorrectPassword()
        } else {
            result
        }
    }

    private fun checkDeepId(deepId: String): Boolean {
        val query = Query(Criteria.where("deepId").`is`(deepId))
        return if (mongoTemplate.findOne(query, Account::class.java, "accounts") == null) {
            true
        } else {
            throw TakenDeepID()
        }
    }

    fun create(
        nickname: String,
        deepId: String,
        password: String,
        pictureResource: String
    ): Account {
        checkDeepId(deepId)
        val token = Utils.generateAuthorizationToken()
        val account = Account(
            nickname,
            deepId,
            password,
            token,
            Instant.now().epochSecond,
            UserProfile(
                Utils.generateObjectId(),
                Instant.now().epochSecond,
                Constants.OBJECTSTATUS.NORMAL,
                nickname,
                deepId,
                pictureResource,
            )
        )
        mongoTemplate.save(account, "accounts")
        return account
    }
    fun validateToken(token: String): Boolean {
        val query = Query(Criteria.where("authToken").`is`(token))
        return if (mongoTemplate.findOne(query, Account::class.java, "accounts") == null) {
            throw Unauthorized()
        } else {
            true
        }
    }
    fun getByToken(token: String): Account {
        val query = Query(Criteria.where("authToken").`is`(token))
        return mongoTemplate.findOne(query, Account::class.java, "accounts")!!
    }
    fun getByUserId(userId: String): Account {
        val query = Query(Criteria.where("userProfile.objectId").`is`(userId))
        return mongoTemplate.findOne(query, Account::class.java, "accounts")!!
    }
    fun tokenToUserId(token: String): String {
        return getByToken(token).userProfile.objectId
    }
}