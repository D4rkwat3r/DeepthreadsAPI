package ru.deepthreads.rest.contollers.v1

import org.springframework.web.bind.annotation.*
import ru.deepthreads.rest.models.response.success.PostListResponse
import ru.deepthreads.rest.models.response.success.UserProfileResponse
import ru.deepthreads.rest.services.AccountService
import ru.deepthreads.rest.services.UserProfileService
import ru.deepthreads.rest.services.WallService

@RestController @RequestMapping("/api/v1/users")
class UserController {
    @GetMapping("/{objectId}")
    fun getUser(@PathVariable("objectId") objectId: String): UserProfileResponse {
        val user = UserProfileService.getUser(objectId)
        return UserProfileResponse(userProfile = user)
    }
    @GetMapping("/me")
    fun getMe(@RequestHeader("AuthToken") token: String): UserProfileResponse {
        val user = AccountService.getByToken(token).userProfile
        return UserProfileResponse(userProfile = user)
    }
    @GetMapping("/{objectId}/wall")
    fun getUserPosts(
        @PathVariable("objectId") objectId: String,
        @RequestHeader("AuthToken") token: String,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): PostListResponse {
        val posts = WallService.getUserPosts(
            skip,
            limit,
            AccountService.getByToken(token).userProfile.objectId,
            objectId
        )
        return PostListResponse(postList = posts)
    }
}