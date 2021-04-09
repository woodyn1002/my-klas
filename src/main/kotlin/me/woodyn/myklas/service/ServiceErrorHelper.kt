package me.woodyn.myklas.service

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ServiceErrorHelper {

    fun notFound(subject: String) =
        ResponseStatusException(HttpStatus.NOT_FOUND, "$subject was not found")

    fun badRequest(reason: String) =
        ResponseStatusException(HttpStatus.BAD_REQUEST, reason)

}