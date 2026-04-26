package net.parvkhandelwal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRequest {

    @JsonProperty("entry")
    private List<Entry> entry;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Entry {
        @JsonProperty("changes")
        private List<Change> changes;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Change {
        @JsonProperty("value")
        private Value value;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Value {
        @JsonProperty("messages")
        private List<Message> messages;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        @JsonProperty("from")
        private String from;

        @JsonProperty("text")
        private Text text;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Text {
        @JsonProperty("body")
        private String body;
    }

    public String getSenderPhone() {
        try {
            return entry.get(0).getChanges().get(0).getValue()
                    .getMessages().get(0).getFrom();
        } catch (Exception e) {
            return null;
        }
    }

    public String getMessageText() {
        try {
            return entry.get(0).getChanges().get(0).getValue()
                    .getMessages().get(0).getText().getBody();
        } catch (Exception e) {
            return null;
        }
    }
}