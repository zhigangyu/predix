package com.ge.predix.solsvc.websocket;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebsocketServerConfig {

	/**
	 * @return DataIngestionWebsocket
	 */
	@Bean
    public ServerEndPoint dataIngestionWebsocketEndpoint() {
        return new ServerEndPoint();
    }

    /**
     * @return -
     */
    @Bean
    public ServerEndpointExporter endpointExporter() {
        return new ServerEndpointExporter();
    } 
    
    /**
     * @return -
     */
    @Bean 
    public WebSocketContainer getWebsocketContainer() {
    	 return ContainerProvider.getWebSocketContainer();
    }

}

