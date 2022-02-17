package ru.deepthreads.rest.contollers.v1

import org.springframework.web.bind.annotation.*
import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.exceptions.other.InvalidRequest
import ru.deepthreads.rest.mapper
import ru.deepthreads.rest.models.requests.MessageSendingRequest
import ru.deepthreads.rest.models.requests.PrivateChatCreatingRequest
import ru.deepthreads.rest.models.requests.PublicChatCreatingRequest
import ru.deepthreads.rest.models.response.success.*
import ru.deepthreads.rest.models.views.MessageView
import ru.deepthreads.rest.services.AccountService
import ru.deepthreads.rest.services.ChatService
import ru.deepthreads.rest.websockets.ChatWebSocket

@RestController @RequestMapping("/api/v1/chats")
class ChatController {

    @GetMapping
    fun chatList(
        @RequestHeader("AuthToken") token: String,
        @RequestParam("type") type: Int,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): ChatListResponse {
        return when (type) {
            1 -> ChatListResponse(chatList = ChatService.getPublicChats(skip, limit, AccountService.tokenToUserId(token)))
            2 -> ChatListResponse(chatList = ChatService.getUserChats(AccountService.tokenToUserId(token), skip, limit))
            3 -> ChatListResponse(chatList = ChatService.getAvailableChats(AccountService.tokenToUserId(token), skip, limit))
            else -> throw InvalidRequest("Unknown type")
        }
    }

    @PostMapping("/public")
    fun createPublic(
        @RequestHeader("AuthToken") token: String,
        @RequestBody body: String
    ): ChatResponse {
        val data = mapper.readValue(body, PublicChatCreatingRequest::class.java)
        val chat = ChatService.create(
            data.title,
            data.iconResource,
            data.backgroundResource,
            Constants.CHATTYPE.PUBLIC,
            AccountService.tokenToUserId(token),
            null,
            null,
            AccountService.tokenToUserId(token)
        )
        return ChatResponse(chat = chat)
    }
    @PostMapping("/private")
    fun createPrivate(
        @RequestHeader("AuthToken") token: String,
        @RequestBody body: String
    ): ChatResponse {
        val data = mapper.readValue(body, PrivateChatCreatingRequest::class.java)
        val chat = ChatService.create(
            null,
            null,
            null,
            Constants.CHATTYPE.PRIVATE,
            AccountService.tokenToUserId(token),
            data.initialMessage,
            data.invitedUsersIds,
            AccountService.tokenToUserId(token)
        )
        return ChatResponse(chat = chat)
    }
    @GetMapping("/{objectId}")
    fun getChat(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String
    ): ChatResponse {
        val chat = ChatService.getChat(objectId, AccountService.tokenToUserId(token))
        return ChatResponse(chat = chat)
    }
    @PostMapping("/{objectId}/message")
    fun addMessage(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String,
        @RequestBody body: String
    ): MessageResponse {
        val data = mapper.readValue(body, MessageSendingRequest::class.java)
        val message = ChatService.addChatMessage(
            AccountService.tokenToUserId(token),
            data.content,
            objectId,
            data.type
        )
        ChatWebSocket.broadcastNewMessage(message, objectId)
        return MessageResponse(message = message)
    }
    @GetMapping("/{objectId}/messages")
    fun getMessages(
        @PathVariable("objectId") objectId: String,
        @RequestParam("skip") skip: Int,
        @RequestParam("limit") limit: Int
    ): MessageListResponse {
        val messages = ChatService.getMessages(objectId, skip, limit)
        return MessageListResponse(messageList = messages)
    }
    @GetMapping("/{objectId}/messages/{messageId}")
    fun getMessage(
        @PathVariable("objectId") objectId: String,
        @PathVariable("messageId") messageId: String
    ): MessageResponse {
        val message = ChatService.getMessage(objectId, messageId)
        return MessageResponse(message = message)
    }
    @PostMapping("/{objectId}/messages/{messageId}/reply")
    fun replyOnMessage(
        @RequestHeader("AuthToken") token: String,
        @PathVariable("objectId") objectId: String,
        @PathVariable("messageId") messageId: String,
        @RequestBody body: String
    ): MessageResponse {
        val data = mapper.readValue(body, MessageSendingRequest::class.java)
        val message = ChatService.addChatMessage(
            AccountService.tokenToUserId(token),
            data.content,
            objectId,
            data.type,
            messageId
        )
        ChatWebSocket.broadcastNewMessage(message, objectId)
        return MessageResponse(message = message)
    }
    @PostMapping("/{objectId}/me")
    fun addMember(
        @PathVariable("objectId") objectId: String,
        @RequestHeader("AuthToken") token: String
    ): EmptyOKResponse {
        val message = ChatService.addMember(
            objectId,
            AccountService.tokenToUserId(token),
            true
        )
        ChatWebSocket.broadcastNewMessage(message!!, objectId)
        return EmptyOKResponse()
    }
    @DeleteMapping("/{objectId}/me")
    fun deleteMember(
        @PathVariable("objectId") objectId: String,
        @RequestHeader("AuthToken") token: String
    ): EmptyOKResponse {
        val message = ChatService.deleteMember(
            objectId,
            AccountService.tokenToUserId(token),
            true
        )
        ChatWebSocket.broadcastNewMessage(message!!, objectId)
        return EmptyOKResponse()
    }
}