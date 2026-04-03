package net.parvkhandelwal.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private String message;
    private String sender;
}
