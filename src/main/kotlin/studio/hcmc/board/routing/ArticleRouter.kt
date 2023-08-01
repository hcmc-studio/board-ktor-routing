package studio.hcmc.board.routing

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SortOrder
import studio.hcmc.board.dto.ArticleDTO
import studio.hcmc.board.dto.ErrorDTO
import studio.hcmc.board.service.ArticleService
import studio.hcmc.kotlin.protocol.toValueObjects
import studio.hcmc.ktor.routing.respondArray
import studio.hcmc.ktor.routing.respondEmpty
import studio.hcmc.ktor.routing.respondObject

fun Routing.articleRouter() {
    @Resource("/boards/{boardId}/articles")
    data class Post(val boardId: Long)

    @Resource("/boards/articles/{id}")
    data class Put(val id: Long)

    @Resource("/boards/articles/{id}")
    data class Delete(val id: Long)

    @Resource("/boards/articles/{id}")
    data class Get(val id: Long)

    @Resource("/boards/articles")
    data class ListAll(val size: Int, val offset: Long, val sortOrder: SortOrder)

    @Resource("/boards/{boardId}/articles")
    data class ListByBoardId(val boardId: Long, val size: Int, val offset: Long, val sortOrder: SortOrder)

    @Resource("/boards/{boardId}/articles/count")
    data class CountByBoardId(val boardId: Long)

    post<Post, ArticleDTO.Post> { (boardId), dto ->
        val entity = ArticleService.add(boardId, call.request.origin.remoteAddress, dto)
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.Created, vo)
    }

    put<Put, ArticleDTO.Put> { (id), dto ->
        val entity = ArticleService.set(id, dto) ?: throw ErrorDTO.ArticleNotFound
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.OK, vo)
    }

    delete<Delete> { (id) ->
        ArticleService.remove(id)
        call.respondEmpty(HttpStatusCode.OK)
    }

    get<Get> { (id) ->
        val entity = ArticleService.get(id) ?: throw ErrorDTO.ArticleNotFound
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.OK, vo)
    }

    get<ListAll> { (size, offset, sortOrder) ->
        val entities = ArticleService.listAll(offset, size, sortOrder)
        val vos = entities.toValueObjects()
        call.respondArray(HttpStatusCode.OK, vos)
    }

    get<ListByBoardId> { (boardId, size, offset, sortOrder) ->
        val entities = ArticleService.listByBoardId(boardId, offset, size, sortOrder)
        val vos = entities.toValueObjects()
        call.respondArray(HttpStatusCode.OK, vos)
    }

    get<CountByBoardId> { (boardId) ->
        val count = ArticleService.countByBoardId(boardId)
        call.respondObject(HttpStatusCode.OK, count)
    }
}