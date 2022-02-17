package ru.deepthreads.rest.services

import org.springframework.stereotype.Service
import ru.deepthreads.rest.exceptions.other.InvalidRequest
import ru.deepthreads.rest.models.entity.Chat
import ru.deepthreads.rest.mongoTemplate
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException

@Service
object PaginationService {
    fun <T> paginate(list: List<T>, skip: Int, limit: Int): List<T> {
        return try {
            list.asReversed().subList(skip, limit)
        } catch (e: IndexOutOfBoundsException) {
            return paginate(list, skip, list.size)
        } catch (e: IllegalArgumentException) {
            throw InvalidRequest("Invalid pagination params")
        }
    }
}