package ru.gorbunov.spring5webflux;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class Spring5WebFluxApplication {

    public static void main(String[] args) throws InterruptedException {

        RouterFunction<ServerResponse> router = getRouter();

        HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);

        HttpServer.create()
                .host("0.0.0.0")
                .port(8080)
                .handle(new ReactorHttpHandlerAdapter(httpHandler))
                .bind()
                .block();

        Thread.currentThread().join();
    }

    static RouterFunction<ServerResponse> getRouter() {
        HandlerFunction<ServerResponse> hello = request -> ok().body(fromValue("Hello"));

        return route(GET("/"), hello)
                .andRoute(GET("/json"),
                        req -> ok().contentType(APPLICATION_JSON).body(fromValue(new Hello("world"))));
    }

}

class Hello {
    private final String name;

    Hello(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}