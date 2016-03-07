package com.ge.predix.solsvc.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableWebSocket
public class PredixWebsocketServerApplication extends SpringBootServletInitializer implements WebSocketConfigurer{
    public static void main(String[] args) {
        SpringApplication.run(PredixWebsocketServerApplication.class, args);
    }
 @Override
protected SpringApplicationBuilder configure(
		SpringApplicationBuilder builder) {
	// TODO Auto-generated method stub
	return builder.sources(PredixWebsocketServerApplication.class);
}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
