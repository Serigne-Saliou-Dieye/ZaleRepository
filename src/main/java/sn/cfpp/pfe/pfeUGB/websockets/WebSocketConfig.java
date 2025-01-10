package sn.cfpp.pfe.pfeUGB.websockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Définit les préfixes pour envoyer ou recevoir des messages
        config.enableSimpleBroker("/topic"); // Broker pour les notifications
        config.setApplicationDestinationPrefixes("/app"); // Prefix pour le client
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint pour établir la connexion
        registry.addEndpoint("/ws-notifications")
        .setAllowedOrigins("http://localhost:3000", "http://192.168.43.86:8081") // Ajoutez l'URL de votre client React Js
        .withSockJS();
    }

}
