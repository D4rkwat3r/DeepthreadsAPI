package ru.deepthreads.rest.services

import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import ru.deepthreads.rest.Constants
import ru.deepthreads.rest.Utils
import ru.deepthreads.rest.exceptions.dts.AccessDenied
import ru.deepthreads.rest.exceptions.other.NotFound
import ru.deepthreads.rest.models.entity.Like
import ru.deepthreads.rest.models.entity.Post
import ru.deepthreads.rest.models.views.LikeView
import ru.deepthreads.rest.models.views.PostView
import ru.deepthreads.rest.mongoTemplate
import java.time.Instant

@Service
object WallService {

    private fun getModel(postId: String): Post {
        return mongoTemplate
            .findOne(Utils.idQuery(postId), Post::class.java, "wall")
            ?: throw NotFound()
    }

    fun getPosts(
        skip: Int,
        limit: Int,
        userId: String
    ): List<PostView> {
        val posts = mongoTemplate.findAll(Post::class.java, "wall")
        return PaginationService.paginate(Utils.castPostList(posts, userId), skip, limit)
    }

    fun getUserPosts(
        skip: Int,
        limit: Int,
        userId: String,
        target: String
    ): List<PostView> {
        val posts = mongoTemplate.findAll(Post::class.java, "wall")
            .filter { it.authorUid == target }
        return PaginationService.paginate(Utils.castPostList(posts, userId), skip, limit)
    }

    fun create(
        authorUid: String,
        title: String,
        content: String,
        coverUrl: String,
        backgroundUrl: String
    ): PostView {
        val post = Post(
            Utils.generateObjectId(),
            Instant.now().epochSecond,
            Constants.OBJECTSTATUS.NORMAL,
            authorUid,
            title,
            content,
            coverUrl,
            backgroundUrl,
            mutableListOf()
        )
        mongoTemplate.save(post, "wall")
        return Utils.castPost(post, authorUid)
    }
    fun getPost(
        postId: String,
        userId: String
    ): PostView {
        val post = getModel(postId)
        return Utils.castPost(post, userId)
    }
    fun delete(
        postId: String,
        userId: String
    ) {
        val post = getModel(postId)
        if (post.authorUid != userId) {
            throw AccessDenied("No such permissions")
        }
        mongoTemplate.remove(Utils.idQuery(postId), Post::class.java, "wall")
    }
    fun getLike(
        postId: String,
        likeId: String
    ): LikeView {
        val post = getModel(postId)
        return Utils.castLike(
            post.likes
                    .find { it.objectId == likeId }
                    ?: throw NotFound()
        )
    }
    fun getLikes(
        postId: String,
        skip: Int,
        limit: Int
    ): List<LikeView> {
        val post = getModel(postId)
        return Utils.castLikeList(
            PaginationService.paginate(post.likes, skip, limit)
        )
    }
    fun addLike(
        postId: String,
        userId: String
    ): LikeView {
        val post = getModel(postId)
        if (post.likes
            .map { it.authorUid }
            .contains(userId)) {
            throw AccessDenied("Already liked")
        }
        val like = Like(
            Utils.generateObjectId(),
            Instant.now().epochSecond,
            Constants.OBJECTSTATUS.NORMAL,
            userId
        )
        val update = Update().addToSet("likes", like)
        mongoTemplate.updateFirst(Utils.idQuery(postId), update, "wall")
        return Utils.castLike(like)
    }
    fun deleteLike(
        postId: String,
        userId: String
    ) {
        val post = getModel(postId)
        if (!post.likes
                .map { it.authorUid }
                .contains(userId)) {
            throw NotFound("Like not found")
        }
        val like = post.likes.find { it.authorUid == userId }
        val update = Update().pull("likes", like)
        mongoTemplate.updateFirst(Utils.idQuery(postId), update, "wall")
    }
}