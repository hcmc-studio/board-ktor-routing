package studio.hcmc.board.dto

import io.ktor.http.*

val ErrorDTO.BoardNotFound.status: HttpStatusCode get() = HttpStatusCode.NotFound

val ErrorDTO.ArticleNotFound.status: HttpStatusCode get() = HttpStatusCode.NotFound

val ErrorDTO.ArticlePasswordMismatch.status: HttpStatusCode get() = HttpStatusCode.Forbidden

val ErrorDTO.CommentNotFound.status: HttpStatusCode get() = HttpStatusCode.NotFound

val ErrorDTO.CommentPasswordMismatch.status: HttpStatusCode get() = HttpStatusCode.Forbidden