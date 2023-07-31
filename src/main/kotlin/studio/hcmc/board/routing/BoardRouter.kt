package studio.hcmc.board.routing

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SortOrder
import studio.hcmc.board.dto.BoardDTO
import studio.hcmc.board.dto.ErrorDTO
import studio.hcmc.board.service.BoardService
import studio.hcmc.kotlin.protocol.toValueObjects
import studio.hcmc.ktor.routing.respondArray
import studio.hcmc.ktor.routing.respondObject

fun Routing.boardRouter() {
    @Resource("/boards")
    class Post

    @Resource("/boards/{id}")
    data class Put(val id: Long)

    @Resource("/boards/{id}")
    data class Get(val id: Long)

    @Resource("/boards")
    data class GetByName(val name: String)

    @Resource("/boards")
    data class ListAll(val size: Int, val offset: Long, val sortOrder: SortOrder)

    @Resource("/boards/count")
    class CountAll

    post<Post, BoardDTO.Post> { _, dto ->
        val entity = BoardService.add(dto)
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.Created, vo)
    }

    put<Put, BoardDTO.Put> { (id), dto ->
        val entity = BoardService.set(id, dto) ?: throw ErrorDTO.BoardNotFound
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.OK, vo)
    }

    get<Get> { (id) ->
        val entity = BoardService.get(id) ?: throw ErrorDTO.BoardNotFound
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.OK, vo)
    }

    get<GetByName> { (name) ->
        val entity = BoardService.getByName(name) ?: throw ErrorDTO.BoardNotFound
        val vo = entity.toValueObject()
        call.respondObject(HttpStatusCode.OK, vo)
    }

    get<ListAll> { (size, offset, sortOrder) ->
        val entities = BoardService.listAll(offset, size, sortOrder)
        val vos = entities.toValueObjects()
        call.respondArray(HttpStatusCode.OK, vos)
    }

    get<CountAll> {
        val count = BoardService.countAll()
        call.respondObject(HttpStatusCode.OK, count)
    }
}