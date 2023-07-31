package studio.hcmc.board.routing

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SortOrder
import studio.hcmc.board.dto.CommentDTO
import studio.hcmc.board.dto.ErrorDTO
import studio.hcmc.board.service.CommentService
import studio.hcmc.kotlin.protocol.toValueObjects
import studio.hcmc.ktor.routing.respondArray
import studio.hcmc.ktor.routing.respondObject

fun Routing.commentRouter() {
    @Resource("/boards/articles/{articleId}/comments")
    data class Post(val articleId: Long)

    @Resource("/boards/articles/comments/{id}")
    data class Put(val id: Long)

    @Resource("/boards/articles/comments/{id}")
    data class Get(val id: Long)

    @Resource("/boards/articles/comments")
    data class ListAll(val size: Int, val offset: Long, val sortOrder: SortOrder)

    @Resource("/boards/articles/{articleId}/comments")
    data class ListByArticleId(val articleId: Long, val size: Int, val offset: Long, val sortOrder: SortOrder)

    @Resource("/boards/articles/{articleId}/comments/count")
    data class CountByArticleId(val articleId: Long)

    post<Post, CommentDTO.Post> { (boardId), dto ->
        val entity = CommentService.add(boardId, call.request.origin.remoteAddress, dto)
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.Created, vo)
    }

    put<Put, CommentDTO.Put> { (id), dto ->
        val entity = CommentService.set(id, dto) ?: throw ErrorDTO.ArticleNotFound
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.OK, vo)
    }

    get<Get> { (id) ->
        val entity = CommentService.get(id) ?: throw ErrorDTO.ArticleNotFound
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.OK, vo)
    }

    get<ListAll> { (size, offset, sortOrder) ->
        val entities = CommentService.listAll(offset, size, sortOrder)
        val vos = entities.toValueObjects()
        call.respondArray(HttpStatusCode.OK, vos)
    }

    get<ListByArticleId> { (articleId, size, offset, sortOrder) ->
        val entities = CommentService.listByArticleId(articleId, offset, size, sortOrder)
        val vos = entities.toValueObjects()
        call.respondArray(HttpStatusCode.OK, vos)
    }

    get<CountByArticleId> { (articleId) ->
        val count = CommentService.countByArticleId(articleId)
        call.respondObject(HttpStatusCode.OK, count)
    }
}