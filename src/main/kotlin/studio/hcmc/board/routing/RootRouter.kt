package studio.hcmc.board.routing

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import studio.hcmc.ktor.routing.respondObject

fun Routing.rootRouter() {
    @Resource("/")
    data class Echo(val message: String)

    get<Echo> { (message) ->
        call.respondObject(HttpStatusCode.OK, message)
    }
}