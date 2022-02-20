package ru.deepthreads.rest

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import ru.deepthreads.rest.models.entity.*
import ru.deepthreads.rest.models.views.*
import ru.deepthreads.rest.services.CommentService
import ru.deepthreads.rest.services.UserProfileService
import java.util.*

object Utils {
    fun randomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
    fun generateAuthorizationToken(): String {
        val buffer = ByteArray(30)
        Random().nextBytes(buffer)
        return Base64.getUrlEncoder().encodeToString(buffer)
    }
    fun generateObjectId(): String {
        return "${randomString(7)}-${randomString(7)}-${randomString(7)}-${randomString(7)}"
    }
    fun fileName(type: String): String {
        return "${randomString(15)}.$type"
    }
    fun idQuery(objectId: String): Query {
        return Query(Criteria.where("objectId").`is`(objectId))
    }
    fun safeDeepId(deepId: String): String {
        return deepId
            .replace(" ", "_")
            .replace("?", "")
            .replace("\"", "")
            .replace("'", "")
            .replace("!", "")
            .replace("$", "")
            .replace(";", "")
            .replace("%", "")
            .replace("^", "")
            .replace(":", "")
            .replace("&", "")
            .replace("*", "")
            .replace("(", "")
            .replace(")", "")
            .replace("-", "_")
            .replace("+", "")
            .replace("=", "")
            .replace("/", "")
            .replace("\\", "")
            .replace("`", "")
            .replace("¬", "")
            .replace("<", "")
            .replace(">", "")
            .replace("{", "")
            .replace("[", "")
            .replace("}", "")
            .replace("]", "")
            .replace("|", "")
            .replace(".", "_")
    }

    fun withoutScheme(url: String): String {
        return url
            .replace("https://", "")
            .replace("http://", "")
    }

    fun castAccount(account: Account): AccountView {
        return AccountView(
            account.objectId,
            account.createdTime,
            account.status,
            account.nickname,
            account.deepId,
            account.password,
            account.authToken,
            castUserProfile(account.userProfile)
        )
    }

    fun castAccountList(accounts: List<Account>): List<AccountView> {
        return accounts
            .map { castAccount(it) }
    }

    fun castUserProfile(userProfile: UserProfile): UserProfileView {
        return UserProfileView(
            userProfile.objectId,
            userProfile.createdTime,
            userProfile.status,
            userProfile.nickname,
            userProfile.deepId,
            userProfile.pictureUrl,
            userProfile.subscriberUidList.size,
            CommentService.getCount(userProfile.objectId, Constants.COMMENTPARENT.USER_PROFILE),
            userProfile.role
        )
    }

    fun castUserProfileList(userProfiles: List<UserProfile>): List<UserProfileView> {
        return userProfiles
            .map { castUserProfile(it) }
    }

    fun castChat(chat: Chat, userId: String): ChatView {
        return ChatView(
            chat.objectId,
            chat.status,
            chat.createdTime,
            chat.title,
            chat.iconUrl,
            chat.backgroundUrl,
            chat.type,
            UserProfileService.getUser(chat.ownerUid),
            if (chat.messages.isEmpty()) {
                null
            } else {
                castMessage(chat.messages.last())
            },
            chat.membersUidList.size,
            chat.membersUidList.contains(userId)
        )
    }
    fun castChatList(chats: List<Chat>, userId: String): List<ChatView> {
        return chats
            .map { castChat(it, userId) }
            .sortedByDescending { it.lastMessage?.createdTime }
    }
    fun castComment(comment: Comment, userId: String): CommentView {
        return CommentView(
            comment.objectId,
            comment.status,
            comment.createdTime,
            UserProfileService.getUser(comment.authorUid),
            comment.content,
            comment.likes.size,
            comment.likes.map { it.authorUid }.contains(userId)
        )
    }
    fun castCommentList(comments: List<Comment>, userId: String): List<CommentView> {
        return comments.map { castComment(it, userId) }
    }
    fun castLike(like: Like): LikeView {
        return LikeView(
            like.objectId,
            like.status,
            like.createdTime,
            UserProfileService.getUser(like.authorUid)
        )
    }
    fun castLikeList(likes: List<Like>): List<LikeView> {
        return likes.map { castLike(it) }
    }
    fun castMessage(message: Message): MessageView {
        return MessageView(
            message.objectId,
            message.status,
            message.createdTime,
            message.content,
            message.type,
            UserProfileService.getUser(message.senderUid),
            if (message.replyMessage != null) {
                castMessage(message.replyMessage)
            } else {
                null
            }
        )
    }
    fun castMessageList(messages: List<Message>): List<MessageView> {
        return messages.map { castMessage(it) }
    }
    fun castPost(post: Post, userId: String): PostView {
        return PostView(
            post.objectId,
            post.status,
            post.createdTime,
            UserProfileService.getUser(post.authorUid),
            post.title,
            post.content,
            post.coverUrl,
            post.backgroundUrl,
            post.likes.size,
            CommentService.getCount(post.objectId, Constants.COMMENTPARENT.WALL_ITEM),
            post.likes.map { it.authorUid }.contains(userId)
        )
    }
    fun castPostList(posts: List<Post>, userId: String): List<PostView> {
        return posts.map { castPost(it, userId) }
    }
}