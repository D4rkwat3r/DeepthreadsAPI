package ru.deepthreads.rest.contollers.v1

import org.springframework.web.bind.annotation.*
import ru.deepthreads.rest.models.response.success.EmptyOKResponse
import ru.deepthreads.rest.models.response.success.PostListResponse
import ru.deepthreads.rest.models.response.success.UserProfileListResponse
import ru.deepthreads.rest.models.response.success.UserProfileResponse
import ru.deepthreads.rest.services.AccountService
import ru.deepthreads.rest.services.UserProfileService
import ru.deepthreads.rest.services.WallService

@RestController @RequestMapping("/api/v1")
class UserController {
    @GetMapping("/users/{objectId}")
    fun getUser(
        @PathVariable("objectId") objectId: String,
        @RequestHeader("AuthToken") token: String
    ): UserProfileResponse {
        val user = if (objectId == "me") {
            AccountService.getByToken(token).userProfile
        } else {
            UserProfileService.getUser(objectId)
        }
        return UserProfileResponse(userProfile = user)
    }
    @GetMapping("/users/{objectId}/wall")
    fun getUserPosts(
        @PathVariable("objectId") objectId: String,
        @RequestHeader("AuthToken") token: String,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): PostListResponse {
        val posts = if (objectId == "me") {
            WallService.getUserPosts(
                skip,
                limit,
                AccountService.getByToken(token).userProfile.objectId,
                AccountService.getByToken(token).userProfile.objectId
            )
        } else {
            WallService.getUserPosts(
                skip,
                limit,
                AccountService.getByToken(token).userProfile.objectId,
                objectId
            )
        }
        return PostListResponse(postList = posts)
    }
    @GetMapping("/users/{objectId}/subscribers")
    fun getSubscribers(
        @PathVariable("objectId") objectId: String,
        @RequestHeader("AuthToken") token: String,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): UserProfileListResponse {
        val subscribers = if (objectId == "me") {
            UserProfileService.getSubscribers(
                AccountService.getByToken(token).userProfile.objectId,
                skip,
                limit
            )
        } else {
            UserProfileService.getSubscribers(objectId, skip, limit)
        }
        return UserProfileListResponse(userProfileList = subscribers)
    }
    @GetMapping("/users/{objectId}/subscribed")
    fun getSubscribed(
        @PathVariable("objectId") objectId: String,
        @RequestHeader("AuthToken") token: String,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): UserProfileListResponse {
        val subscribed = if (objectId == "me") {
            UserProfileService.getSubscribed(
                AccountService.getByToken(token).userProfile.objectId,
                skip,
                limit
            )
        } else {
            UserProfileService.getSubscribed(objectId, skip, limit)
        }
        return UserProfileListResponse(userProfileList = subscribed)
    }
    @GetMapping("/blocked-full-list")
    fun getBlocked(
        @RequestHeader("AuthToken") token: String
    ): UserProfileListResponse {
        val blocked = UserProfileService.getBlocked(
            AccountService.getByToken(token).userProfile.objectId
        )
        return UserProfileListResponse(userProfileList = blocked)
    }
    @GetMapping("/blockers-full-list")
    fun getBlockers(
        @RequestHeader("AuthToken") token: String
    ): UserProfileListResponse {
        val blockers = UserProfileService.getBlockers(
            AccountService.tokenToUserId(token)
        )
        return UserProfileListResponse(userProfileList = blockers)
    }
    @PostMapping("/users/{objectId}/block")
    fun block(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String
    ): EmptyOKResponse {
        UserProfileService.block(
            AccountService.tokenToUserId(token),
            objectId
        )
        return EmptyOKResponse()
    }
    @DeleteMapping("/users/{objectId}/block")
    fun unblock(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String
    ): EmptyOKResponse {
        UserProfileService.unblock(
            AccountService.tokenToUserId(token),
            objectId
        )
        return EmptyOKResponse()
    }
    @PostMapping("/{objectId}/subscription")
    fun subscribe(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String
    ): EmptyOKResponse {
        UserProfileService.subscribe(
            AccountService.tokenToUserId(token),
            objectId
        )
        return EmptyOKResponse()
    }
    @DeleteMapping("/{objectId}/subscription")
    fun unsubscribe(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String
    ): EmptyOKResponse {
        UserProfileService.unsubscribe(
            AccountService.tokenToUserId(token),
            objectId
        )
        return EmptyOKResponse()
    }
}