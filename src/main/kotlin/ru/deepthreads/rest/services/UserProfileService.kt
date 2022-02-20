package ru.deepthreads.rest.services

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import ru.deepthreads.rest.Utils
import ru.deepthreads.rest.exceptions.other.Conflict
import ru.deepthreads.rest.models.entity.Account
import ru.deepthreads.rest.models.views.UserProfileView
import ru.deepthreads.rest.mongoTemplate

@Service
object UserProfileService {

    private fun uidListToUserList(uids: List<String>): List<UserProfileView> {
        return uids.map { Utils.castUserProfile(AccountService.getFully(it).userProfile) }
    }

    fun getUser(userId: String): UserProfileView {
        val account = AccountService.getFully(userId)
        return Utils.castUserProfile(account.userProfile)
    }
    fun getBlocked(userId: String): List<UserProfileView> {
        val accounts = mongoTemplate.findAll(Account::class.java, "accounts")
        return Utils.castUserProfileList(
            accounts
                .map { it.userProfile }
                .filter { it.blockerUidList.contains(userId) }
        )
    }
    fun getBlockers(userId: String): List<UserProfileView> {
        val account = AccountService.getFully(userId)
        return uidListToUserList(account.userProfile.blockerUidList)
    }
    fun getSubscribers(userId: String, skip: Int, limit: Int): List<UserProfileView> {
        val account = AccountService.getFully(userId)
        return PaginationService.paginate(
                uidListToUserList(account.userProfile.subscriberUidList),
                skip,
                limit
            )
    }
    fun getSubscribed(userId: String, skip: Int, limit: Int): List<UserProfileView> {
        val accounts = mongoTemplate.findAll(Account::class.java, "accounts")
        return Utils.castUserProfileList(
            PaginationService.paginate(
                accounts
                    .map { it.userProfile }
                    .filter { it.subscriberUidList.contains(userId) },
                skip,
                limit
            )
        )
    }
    fun block(blockerUserId: String, userId: String) {
        if (AccountService.getFully(userId).userProfile.blockerUidList.contains(blockerUserId)) {
            throw Conflict()
        }
        val query = Query(Criteria.where("userProfile.objectId").`is`(userId))
        val update = Update().addToSet("userProfile.blockerUidList", blockerUserId)
        mongoTemplate.updateFirst(query, update, "accounts")
    }
    fun unblock(blockerUserId: String, userId: String) {
        if (!AccountService.getFully(userId).userProfile.blockerUidList.contains(blockerUserId)) {
            throw Conflict()
        }
        val query = Query(Criteria.where("userProfile.objectId").`is`(userId))
        val update = Update().pull("userProfile.blockerUidList", blockerUserId)
        mongoTemplate.updateFirst(query, update, "accounts")
    }
    fun subscribe(uid: String, targetUid: String) {
        if (AccountService.getFully(targetUid).userProfile.subscriberUidList.contains(uid)) {
            throw Conflict()
        }
        val query = Query(Criteria.where("userProfile.objectId").`is`(targetUid))
        val update = Update().addToSet("userProfile.subscriberUidList", uid)
        mongoTemplate.updateFirst(query, update, "accounts")
    }
    fun unsubscribe(uid: String, targetUid: String) {
        if (!AccountService.getFully(targetUid).userProfile.subscriberUidList.contains(uid)) {
            throw Conflict()
        }
        val query = Query(Criteria.where("userProfile.objectId").`is`(targetUid))
        val update = Update().pull("userProfile.subscriberUidList", uid)
        mongoTemplate.updateFirst(query, update, "accounts")
    }
}
