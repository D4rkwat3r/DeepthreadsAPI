package ru.deepthreads.rest.services

import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.Utils
import ru.deepthreads.rest.exceptions.dts.AccessDenied
import ru.deepthreads.rest.exceptions.chat.AlreadyAMember
import ru.deepthreads.rest.exceptions.chat.NotAMember
import ru.deepthreads.rest.exceptions.chat.YouAreBlocked
import ru.deepthreads.rest.exceptions.other.NotFound
import ru.deepthreads.rest.models.entity.Chat
import ru.deepthreads.rest.models.entity.Message
import ru.deepthreads.rest.models.views.ChatView
import ru.deepthreads.rest.models.views.MessageView
import ru.deepthreads.rest.mongoTemplate
import java.time.Instant

@Service
object ChatService {

    private fun getModel(chatId: String): Chat {
        return mongoTemplate.findOne(Utils.idQuery(chatId), Chat::class.java, "chats") ?: throw NotFound()
    }

    private fun getMessageModel(chatId: String, messageId: String): Message {
        return getModel(chatId)
            .messages
            .find { it.objectId == messageId }
            ?: throw NotFound()
    }

    fun create(
        title: String?,
        iconUrl: String?,
        backgroundUrl: String?,
        type: Int,
        ownerUid: String,
        initialMessage: String?,
        uidPreset: List<String>?,
        userId: String
    ): ChatView {
        val chat = Chat(
            Utils.generateObjectId(),
            Instant.now().epochSecond,
            Constants.OBJECTSTATUS.NORMAL,
            title,
            iconUrl,
            backgroundUrl,
            type,
            listOf(ownerUid),
            ownerUid,
            listOf()
        )
        mongoTemplate.save(chat, "chats")
        if (initialMessage != null) {
            addChatMessage(ownerUid, initialMessage, chat.objectId, Constants.MESSAGETYPE.NORMAL)
        }
        uidPreset?.forEach { uid ->
            if (UserProfileService.getBlocked(uid).map { it.objectId }.contains(ownerUid)) {
                throw YouAreBlocked(
                    UserProfileService.getUser(uid).nickname
                )
            }
            addMember(chat.objectId, uid)
        }
        return Utils.castChat(chat, userId)
    }

    fun getPublicChats(
        skip: Int,
        limit: Int,
        userId: String
    ): List<ChatView> {
        return Utils.castChatList(
            PaginationService.paginate(
                mongoTemplate.findAll(Chat::class.java, "chats")
                    .filter { it.type != Constants.CHATTYPE.PRIVATE },
                skip,
                limit
            ),
            userId
        )
    }
    fun getUserChats(
        userId: String,
        skip: Int,
        limit: Int
    ): List<ChatView> {
        return Utils.castChatList(
            PaginationService.paginate(
                mongoTemplate.findAll(Chat::class.java, "chats")
                    .filter { it.membersUidList.contains(userId) },
                skip,
                limit
            ),
            userId
        )
    }

    fun getAvailableChats(
        userId: String,
        skip: Int,
        limit: Int
    ): List<ChatView> {
        return Utils.castChatList(
            PaginationService.paginate(
                mongoTemplate.findAll(Chat::class.java, "chats")
                    .filter { it.type != Constants.CHATTYPE.PRIVATE || it.membersUidList.contains(userId) },
                skip,
                limit
            ),
            userId
        )
    }

    fun getChat(chatId: String, userId: String): ChatView {
        return Utils.castChat(
            getModel(chatId),
            userId
        )
    }

    fun addMember(chatId: String, userId: String, notify: Boolean = false): MessageView? {
        if (
            getModel(chatId)
                .membersUidList
                .contains(userId)
        ) {
            throw AlreadyAMember()
        }
        UserProfileService.getUser(userId)
        val update = Update().addToSet("membersUidList", userId)
        mongoTemplate.updateFirst(Utils.idQuery(chatId), update, "chats")
        return if (notify) addChatMessage(userId, null, chatId, Constants.MESSAGETYPE.SYSTEM_USER_JOIN) else null
    }

    fun deleteMember(chatId: String, userId: String, notify: Boolean = false): MessageView? {
        if (!getModel(chatId).membersUidList.contains(userId)) {
            throw NotAMember()
        }
        UserProfileService.getUser(userId)
        val update = Update().pull("membersUidList", userId)
        val message = if (notify) addChatMessage(userId, null, chatId, Constants.MESSAGETYPE.SYSTEM_USER_LEAVE) else null
        mongoTemplate.updateFirst(Utils.idQuery(chatId), update, "chats")
        return message
    }

    fun addChatMessage(senderUid: String, content: String?, chatId: String, type: Int, replyMessageId: String? = null): MessageView {
        if (
            !getModel(chatId)
                .membersUidList
                .contains(senderUid)
        ) {
            throw NotAMember()
        }
        val message = Message(
            Utils.generateObjectId(),
            Instant.now().epochSecond,
            Constants.OBJECTSTATUS.NORMAL,
            content,
            type,
            senderUid,
            if (replyMessageId != null) {
                getMessageModel(chatId, replyMessageId)
            } else {
                null
            }
        )
        val update = Update().addToSet("messages", message)
        mongoTemplate.updateFirst(Utils.idQuery(chatId), update, "chats")
        return Utils.castMessage(message)
    }

    fun getMessages(chatId: String, skip: Int, limit: Int): List<MessageView> {
        val chat = getModel(chatId)
        return Utils.castMessageList(
            PaginationService.paginate(
                chat.messages,
                skip,
                limit
            )
        )
    }

    fun getMessage(chatId: String, messageId: String): MessageView {
        return Utils.castMessage(
            getModel(chatId)
                .messages
                .find { it.objectId == messageId }
                ?: throw NotFound()
        )
    }

    fun checkUserInChat(chatId: String, userId: String): Boolean {
        return getModel(chatId).membersUidList.contains(userId)
    }

}