package ru.gorbunov.Spring5WebFlux;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

//@SpringBootApplication
public class Spring5WebFluxApplication {

	public static void main(String[] args) throws InterruptedException {
		//SpringApplication.run(Spring5WebFluxApplication.class, args);

		RouterFunction router = getRouter();

		HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);

		HttpServer
				.create("localhost", 8080)
				.newHandler(new ReactorHttpHandlerAdapter(httpHandler))
				.block();

		Thread.currentThread().join();

	}

	static RouterFunction getRouter() {
		HandlerFunction hello = request -> ok().body(fromObject("Hello"));

		return route(
					GET("/"), hello)
				.andRoute(
					GET("/json"), req -> ok().contentType(APPLICATION_JSON).body(fromObject(new Hello("world"))));
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