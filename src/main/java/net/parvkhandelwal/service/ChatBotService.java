package net.parvkhandelwal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.dto.ChatRequest;
import net.parvkhandelwal.dto.ChatResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatBotService {

    @Value("${whatsapp.api.token}")
    private String apiToken;
    @Value("${whatsapp.phone.number.id}")
    private String phoneNumberId;


    public ChatResponse processMessage(ChatRequest request) {
        String sender = request.getSenderPhone();
        String messageText = request.getMessageText();
        log.info("incoming message from [{}]: {}",sender,messageText);
        String incomingText = messageText!= null ? messageText.trim().toLowerCase() : "";
        String replyText= switch (incomingText) {
            case "hi" -> "Hello";
            case "bye" -> "Goodbye";

            default -> "I am a simple simulation bot. I only understand 'Hi' and 'Bye'.";
        };
        log.info("🤖 Bot replied to [{}]: {}",sender, replyText);
        sendReplyToMeta(sender, replyText);
        return new ChatResponse(replyText);
    }
    private void sendReplyToMeta(String recipientPhone, String messageText) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://graph.facebook.com/v20.0/" + phoneNumberId + "/messages";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiToken);


            String payload = "{ \"messaging_product\": \"whatsapp\", \"to\": \"" + recipientPhone + "\", \"type\": \"text\", \"text\": { \"body\": \"" + messageText + "\" } }";

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);

            log.info("Sending outbound HTTP request to Meta API...");
            restTemplate.postForEntity(url, entity, String.class);
            log.info("✅ Successfully delivered reply to Meta!");

        } catch (Exception e) {
            log.error("Failed to send outbound message: {}", e.getMessage());
        }
    }
}
