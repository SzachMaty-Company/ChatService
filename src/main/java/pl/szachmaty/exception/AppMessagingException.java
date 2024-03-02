package pl.szachmaty.exception;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

public class AppMessagingException extends MessagingException {

    public AppMessagingException(Message<?> message) {
        super(message);
    }

    public AppMessagingException(String description) {
        super(description);
    }

}
