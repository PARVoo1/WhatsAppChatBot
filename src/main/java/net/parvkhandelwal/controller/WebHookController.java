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

    @GetMapping
    public ResponseEntity<String> verifyWebhook(@RequestParam(value = "hub.challenge", required = false) String challenge) {
        return ResponseEntity.ok(challenge != null ? challenge : "Webhook Server is UP. Waiting for Meta Handshake...");
    }
    @PostMapping
    public ResponseEntity<ChatResponse> receiveMessage(@RequestBody ChatRequest chatRequest) {
        ChatResponse response=chatBotService.processMessage(chatRequest);
        return ResponseEntity.ok(response);
    }
}
