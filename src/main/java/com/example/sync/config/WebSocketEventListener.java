package com.example.sync.config;

import com.example.sync.chat.ChatMessage;
import com.example.sync.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component // component of springboot, basically just for auto-detect of @SpringBootApplication
@Slf4j // logging annotation
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent sessionDisconnectEvent
    ) {
        var headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        var username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("{} left the chat", username);
            var chatMessage = ChatMessage.builder()
                    .messageType(MessageType.LEAVE)
                    .sender(username)
                    .build();
            simpMessageSendingOperations.convertAndSend("/topic/public", chatMessage);
        }
    }
}
