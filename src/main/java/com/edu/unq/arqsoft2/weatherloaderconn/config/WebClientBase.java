package com.edu.unq.arqsoft2.weatherloaderconn.config;

import com.edu.unq.arqsoft2.weatherloaderconn.exception.ApiException;
import com.edu.unq.arqsoft2.weatherloaderconn.exception.NotFoundException;
import com.edu.unq.arqsoft2.weatherloaderconn.exception.UnauthorizedException;
import com.edu.unq.arqsoft2.weatherloaderconn.util.TokenInfo;
import org.springframework.http.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public abstract class WebClientBase {
    protected final WebClient webClient;
    protected final AtomicReference<TokenInfo> tokenRef;

    public WebClientBase(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.tokenRef = new AtomicReference<>(null);
    }

    public Mono<TokenInfo> getToken() {
        TokenInfo currentToken = tokenRef.get();
        return Mono.just(currentToken);
    }

    protected Mono<String> get(String uri, Map<String, ?> queryParams) {
        return webClient.get()
                .uri(uriBuilder -> buildUri(uriBuilder, uri, queryParams))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                .bodyToMono(String.class);
    }

    protected Mono<String> post(String uri, Map<String, ?> queryParams, String requestBody) {
        return webClient.post()
                .uri(uriBuilder -> buildUri(uriBuilder, uri, queryParams))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .accept(MediaType.ALL)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                .bodyToMono(String.class);
    }

    protected <T> Mono<T> method(HttpMethod method,
                                 String uri,
                                 Map<String, String> headers,
                                 Map<String, ?> queryParams,
                                 Object body,
                                 Class<T> responseType,
                                 MediaType mediaType) {

        WebClient.RequestBodySpec request = webClient.method(method)
                .uri(uriBuilder -> buildUri(uriBuilder, uri, queryParams))
                .contentType(mediaType);

        if (headers != null) {
            request = request.headers(httpHeaders -> headers.forEach(httpHeaders::set));
        }

        if (body != null) {
            request = (WebClient.RequestBodySpec) request.body(BodyInserters.fromValue(body));
        }

        return request.retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handle5xxError)
                .bodyToMono(responseType);
    }

    protected Mono<? extends Throwable> handle4xxError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    int statusCode = response.statusCode().value();
                    if (statusCode == 401) {
                        return Mono.error(new UnauthorizedException(errorBody));
                    } else if (statusCode == 404) {
                        return Mono.error(new NotFoundException(errorBody));
                    } else {
                        return Mono.error(new ApiException(statusCode, errorBody));
                    }
                });
    }

    protected Mono<? extends Throwable> handle5xxError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(errorBody -> Mono.error(new ApiException(response.statusCode().value(), errorBody)));
    }

    private URI buildUri(UriBuilder uriBuilder, String path, Map<String, ?> queryParams) {
        UriBuilder builder = uriBuilder.path(path);
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        return builder.build();
    }
}
