package ru.deepthreads.rest.services

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.Utils
import ru.deepthreads.rest.exceptions.dts.AccessDenied
import ru.deepthreads.rest.exceptions.other.NotFound
import ru.deepthreads.rest.models.entity.Comment
import ru.deepthreads.rest.models.entity.Like
import ru.deepthreads.rest.models.views.CommentView
import ru.deepthreads.rest.models.views.LikeView
import ru.deepthreads.rest.mongoTemplate
import java.time.Instant

@Service
object CommentService {
    private fun getQuery(
        parentId: String,
        parentType: Int,
        commentId: String
    ): Query {
        return Query(
            Criteria.where("objectId").`is`(commentId).andOperator(
                Criteria.where("parentId").`is`(parentId).andOperator(
                    Criteria.where("parentType").`is`(parentType)
                )
            )
        )
    }
    private fun getModel(
        parentId: String,
        parentType: Int,
        commentId: String
    ): Comment {
        val query = getQuery(parentId, parentType, commentId)
        return mongoTemplate.findOne(query, Comment::class.java, "comments") ?: throw NotFound()
    }
    fun create(
        uid: String,
        content: String,
        parentId: String,
        parentType: Int
    ): CommentView {
        val comment = Comment(
            Utils.generateObjectId(),
            Instant.now().epochSecond,
            Constants.OBJECTSTATUS.NORMAL,
            uid,
            content,
            mutableListOf(),
            parentId,
            parentType
        )
        mongoTemplate.save(comment, "comments")
        return Utils.castComment(comment, uid)
    }
    fun delete(
        uid: String,
        parentId: String,
        parentType: Int,
        commentId: String
    ) {
        val query = getQuery(parentId, parentType, commentId)
        val model = getModel(parentId, parentType, commentId)
        if (model.authorUid != uid) {
            throw AccessDenied("No such permissions")
        }
        mongoTemplate.remove(query, Comment::class.java, "comments")
    }
    fun getComment(
        uid: String,
        parentId: String,
        parentType: Int,
        commentId: String
    ): CommentView {
        return Utils.castComment(
            getModel(parentId, parentType, commentId),
            uid
        )
    }
    fun getComments(
        uid: String,
        parentId: String,
        parentType: Int,
        skip: Int,
        limit: Int
    ): List<CommentView> {
        val query = Query(
            Criteria.where("parentId").`is`(parentId).andOperator(
                Criteria.where("parentType").`is`(parentType)
            )
        )
        val models = mongoTemplate.find(query, Comment::class.java, "comments")
        return PaginationService.paginate(
            Utils.castCommentList(models, uid),
            skip,
            limit
        )
    }
    fun likeComment(
        uid: String,
        parentId: String,
        parentType: Int,
        commentId: String
    ): LikeView {
        val model = getModel(parentId, parentType, commentId)
        if (model.likes.map { it.authorUid }.contains(uid)) {
            throw AccessDenied("Already liked")
        }
        val like = Like(
            Utils.generateObjectId(),
            Instant.now().epochSecond,
            Constants.OBJECTSTATUS.NORMAL,
            uid
        )
        val query = getQuery(parentId, parentType, commentId)
        val update = Update().addToSet("likes", like)
        mongoTemplate.updateFirst(query, update, "comments")
        return Utils.castLike(like)
    }
    fun deleteLike(
        uid: String,
        parentId: String,
        parentType: Int,
        commentId: String
    ) {
        val model = getModel(parentId, parentType, commentId)
        val like = model.likes.find { it.authorUid == uid } ?: throw NotFound("Like not found")
        val query = getQuery(parentId, parentType, commentId)
        val update = Update().pull("likes", like)
        mongoTemplate.updateFirst(query, update, "comments")
    }
    fun getLikes(
        parentId: String,
        parentType: Int,
        commentId: String,
        skip: Int,
        limit: Int
    ): List<LikeView> {
        val model = getModel(parentId, parentType, commentId)
        return Utils.castLikeList(
            PaginationService.paginate(
                model.likes,
                skip,
                limit
            )
        )
    }
    fun getLike(
        parentId: String,
        parentType: Int,
        commentId: String,
        likeId: String
    ): LikeView {
        val model = getModel(parentId, parentType, commentId)
        return Utils.castLike(
            model.likes.find { it.objectId == likeId } ?: throw NotFound()
        )
    }
    fun getCount(
        parentId: String,
        parentType: Int
    ): Int {
        val query = Query(
            Criteria.where("parentId").`is`(parentId).andOperator(
                Criteria.where("parentType").`is`(parentType)
            )
        )
        val models = mongoTemplate.find(query, Comment::class.java, "comments")
        return models.size
    }
}