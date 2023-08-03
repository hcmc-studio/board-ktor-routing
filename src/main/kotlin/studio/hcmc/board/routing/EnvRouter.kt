package studio.hcmc.board.routing

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import studio.hcmc.ktor.routing.respondObject

fun Routing.envRouter() {
    @Resource("/env/address")
    class GetAddress

    get<GetAddress> {
        call.respondObject(HttpStatusCode.OK, call.request.origin.remoteAddress)
    }
}