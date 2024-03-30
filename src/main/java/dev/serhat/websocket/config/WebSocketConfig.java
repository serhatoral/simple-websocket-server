package dev.serhat.websocket.config;


import dev.serhat.websocket.BasicWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(basicWebSocketHandler(), "/websocket").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler basicWebSocketHandler() {
        return new BasicWebSocketHandler();
    }
}
