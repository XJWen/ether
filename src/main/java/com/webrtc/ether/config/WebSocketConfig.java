package com.webrtc.ether.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/chatWithRTC").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/chatWithMessage");
    }
    /**
     * 配置WebSocket 消息代理(MessageBroker)
     **/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //点对点配置(/queue)，广播式配置一个(/topic)，群发(/mass)，单聊(/alone)
        registry.enableSimpleBroker("/queue","/topic","/mass","/alone");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
