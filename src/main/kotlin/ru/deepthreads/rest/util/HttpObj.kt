package ru.deepthreads.rest.util

import java.net.Authenticator
import java.net.CookieHandler
import java.net.ProxySelector
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLParameters

object HttpObj : HttpClient() {

    private val impl = newHttpClient()

    override fun cookieHandler(): Optional<CookieHandler> {
        return impl.cookieHandler()
    }

    override fun connectTimeout(): Optional<Duration> {
       return impl.connectTimeout()
    }

    override fun followRedirects(): Redirect {
        return impl.followRedirects()
    }

    override fun proxy(): Optional<ProxySelector> {
        return impl.proxy()
    }

    override fun sslContext(): SSLContext {
        return impl.sslContext()
    }

    override fun sslParameters(): SSLParameters {
        return impl.sslParameters()
    }

    override fun authenticator(): Optional<Authenticator> {
        return impl.authenticator()
    }

    override fun version(): Version {
        return impl.version()
    }

    override fun executor(): Optional<Executor> {
        return impl.executor()
    }

    override fun <T : Any?> send(
        request: HttpRequest?,
        responseBodyHandler: HttpResponse.BodyHandler<T>?
    ): HttpResponse<T> {
        return impl.send(request, responseBodyHandler)
    }

    override fun <T : Any?> sendAsync(
        request: HttpRequest?,
        responseBodyHandler: HttpResponse.BodyHandler<T>?
    ): CompletableFuture<HttpResponse<T>> {
        return impl.sendAsync(request, responseBodyHandler)
    }

    override fun <T : Any?> sendAsync(
        request: HttpRequest?,
        responseBodyHandler: HttpResponse.BodyHandler<T>?,
        pushPromiseHandler: HttpResponse.PushPromiseHandler<T>?
    ): CompletableFuture<HttpResponse<T>> {
        return impl.sendAsync(request, responseBodyHandler, pushPromiseHandler)
    }

    fun get(url: String): HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(URI(url))
            .GET()
            .build()
        return send(request, HttpResponse.BodyHandlers.ofString())
    }

}