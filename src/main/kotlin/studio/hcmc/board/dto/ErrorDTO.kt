package studio.hcmc.board.dto

import io.ktor.http.*

val ErrorDTO.BoardNotFound.status: HttpStatusCode get() = HttpStatusCode.NotFound

val ErrorDTO.ArticleNotFound.status: HttpStatusCode get() = HttpStatusCode.NotFound

val ErrorDTO.CommentNotFound.status: HttpStatusCode get() = HttpStatusCode.NotFound