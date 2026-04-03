package net.parvkhandelwal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.parvkhandelwal.dto.ChatRequest;
import net.parvkhandelwal.dto.ChatResponse;
import net.parvkhandelwal.service.ChatBotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@Slf4j
public class WebHookController {
    private final ChatBotService chatBotService;

    @GetMapping("/webhook")
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(value = "hub.mode", required = false) String mode,
            @RequestParam(value = "hub.verify_token", required = false) String token,
            @RequestParam(value = "hub.challenge", required = false) String challenge
    ) {
        String verifyToken = "test123";

        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            log.info("Webhook verified!");
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(403).body("Forbidden");
    }
    @PostMapping
    public ResponseEntity<ChatResponse> receiveMessage(@RequestBody ChatRequest chatRequest) {
        ChatResponse response=chatBotService.processMessage(chatRequest);
        return ResponseEntity.ok(response);
    }
}
