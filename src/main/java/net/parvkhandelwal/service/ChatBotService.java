package net.parvkhandelwal.service;

import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.dto.ChatRequest;
import net.parvkhandelwal.dto.ChatResponse;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatBotService {
    public ChatResponse processMessage(ChatRequest request) {
        log.info("incoming message from [{}]: {}", request.getSender(), request.getMessage());
        String incomingText = request.getMessage()!= null ? request.getMessage().trim().toLowerCase() : "";
        String replyText= switch (incomingText) {
            case "hi" -> "Hello";
            case "bye" -> "Goodbye";
            default -> "I am a simple simulation bot. I only understand 'Hi' and 'Bye'.";
        };


        log.info("🤖 Bot replied to [{}]: {}", request.getSender(), replyText);
        return new ChatResponse(replyText);
    }
}
