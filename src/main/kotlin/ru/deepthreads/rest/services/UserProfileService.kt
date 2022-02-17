package ru.deepthreads.rest.services

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import ru.deepthreads.rest.exceptions.other.NotFound
import ru.deepthreads.rest.models.Account
import ru.deepthreads.rest.models.entity.UserProfile
import ru.deepthreads.rest.mongoTemplate

@Service
object UserProfileService {

    fun getUser(userId: String): UserProfile {
        val query = Query(Criteria.where("userProfile.objectId").`is`(userId))
        val user = mongoTemplate.findOne(query, Account::class.java, "accounts")
        return user?.userProfile ?: throw NotFound()
    }
    fun getUserList(uidList: List<String>): List<UserProfile> {
        val total = mutableListOf<UserProfile>()
        uidList.forEach { id ->
            total += getUser(id)
        }
        return total
    }
}
