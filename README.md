# WhatsApp ChatBot

A simple yet functional WhatsApp chatbot built with Spring Boot and integrated with Meta's WhatsApp Business API. This project demonstrates how to create a webhook server that receives messages from WhatsApp users and sends automated responses.

## What's This About?

Ever wanted to build a bot that responds to WhatsApp messages automatically? That's exactly what this project does. It acts as a webhook server that listens for incoming WhatsApp messages through Meta's Graph API, processes them, and sends back intelligent (or at least humorous) responses.

Currently, the bot understands basic commands like "hi" and "bye", but the foundation is solid enough to extend it to do more complex tasks.

## Tech Stack

- **Java 21** - Because we like modern features and records
- **Spring Boot 4.0.5** - The framework that makes everything easier
- **Lombok** - To keep our code DRY and less verbose
- **Maven** - Dependency management and build automation
- **Meta WhatsApp Business API** - For actual WhatsApp integration

## Project Structure

```
src/main/java/net/parvkhandelwal/
├── WhatsAppChatBotApplication.java    # Main Spring Boot entry point
├── controller/
│   └── WebHookController.java          # REST endpoints for webhook
├── service/
│   └── ChatBotService.java             # Business logic and message processing
└── dto/
    ├── ChatRequest.java                # Incoming message payload
    └── ChatResponse.java               # Outgoing message response
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- WhatsApp Business Account with API credentials (more on this below)
- A webhook URL that Meta can reach (use something like ngrok for local testing)

### Installation

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd WhatsAppChatBot
   ```

2. **Configure your credentials**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   whatsapp.api.token=your_meta_api_token_here
   whatsapp.phone.number.id=your_phone_number_id_here
   ```

3. **Build the project**
   ```bash
   mvn clean package
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   
   The server will start on `http://localhost:8080`

### Docker Support

If you prefer containerization, a `Dockerfile` is included:
```bash
docker build -t whatsapp-chatbot .
docker run -p 8080:8080 -e whatsapp.api.token=YOUR_TOKEN -e whatsapp.phone.number.id=YOUR_ID whatsapp-chatbot
```

## How It Works

### Webhook Verification

When you set up a webhook with Meta, it sends a verification request with a challenge parameter. Our endpoint handles this:

```
GET /webhook?hub.challenge=abc123xyz
```

### Receiving Messages

Once verified, WhatsApp sends POST requests to your webhook whenever a user sends a message:

```
POST /webhook
Content-Type: application/json

{
  "senderPhone": "1234567890",
  "messageText": "hi"
}
```

### Processing & Responding

The `ChatBotService` processes incoming messages:
- Logs the incoming message
- Matches it against known patterns ("hi" → "Hello", "bye" → "Goodbye")
- Falls back to a default response for unknown messages
- Sends the reply back to the user via Meta's Graph API

## API Endpoints

### GET /webhook
Verifies the webhook with Meta. Required for initial setup.

**Query Parameters:**
- `hub.challenge` - Challenge string from Meta

**Response:**
```
200 OK
{challenge value or "Webhook Server is UP..."}
```

### POST /webhook
Receives incoming messages from WhatsApp.

**Request body:**
```json
{
  "senderPhone": "1234567890",
  "messageText": "hi"
}
```

**Response:**
```json
{
  "replyText": "Hello"
}
```

## Configuration

Update `application.properties` with your Meta credentials:

```properties
# WhatsApp API configuration
whatsapp.api.token=your_bearer_token_from_meta
whatsapp.phone.number.id=your_phone_number_id

# Server configuration (optional)
server.port=8080
server.servlet.context-path=/
```

You can find these credentials in the [Meta Developer Dashboard](https://developers.facebook.com/).

## Setting Up with Meta WhatsApp Business API

1. Create a Meta Developer account
2. Create a WhatsApp Business App
3. Set up a phone number (either test or production)
4. Generate an API token with `messages` permission
5. Configure your webhook URL in the Meta dashboard to point to `/webhook`
6. Subscribe to `messages` webhook events

## Extending the Bot

The logic for handling messages is in `ChatBotService.processMessage()`. Currently it's a simple switch statement, but you can:

- Connect to a database for persistence
- Integrate with external APIs (weather, news, etc.)
- Add natural language processing (NLP)
- Implement multi-turn conversations
- Add user authentication and session management

Example of adding a new command:
```java
String replyText = switch (incomingText) {
    case "hi" -> "Hello";
    case "bye" -> "Goodbye";
    case "weather" -> getWeatherInfo();  // New feature
    default -> "I am a simple simulation bot. I only understand 'Hi' and 'Bye'.";
};
```

## Running Tests

```bash
mvn test
```

Basic unit tests are included in `src/test/java/net/parvkhandelwal/WhatsAppChatBotApplicationTests.java`.

## Logging

The application uses SLF4J with Lombok's `@Slf4j` annotation. Logs include:
- Incoming messages from users
- Bot responses
- API communication with Meta
- Any errors during message processing

## Known Limitations

- Currently handles simple text-based commands only
- No database persistence (perfect for stateless design though!)
- No support for media messages (images, videos, etc.)
- Simple pattern matching instead of NLP
- Single-threaded message processing

## Troubleshooting

**Webhook not connecting?**
- Make sure your URL is publicly accessible
- Check your firewall and port forwarding settings
- Verify the challenge parameter is being returned correctly

**Messages not being sent?**
- Double-check your API token and phone number ID
- Check the logs for detailed error messages
- Ensure the recipient phone number is in the correct format

**Build issues?**
- Make sure you're using Java 21+
- Try `mvn clean` before rebuilding
- Check that Lombok is properly installed in your IDE

## License

This project is open source. Feel free to use it, modify it, and share it.

## Contributing

Want to make it better? Go ahead! Fork, modify, and send a pull request. All contributions are welcome.

## Author

Created with ☕ and probably some 🤦 moments along the way.

---

**Need help?** Check the logs, they usually tell you what's wrong. If not, that's what debugging is for!

