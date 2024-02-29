package pl.szachmaty.security;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;
import pl.szachmaty.exception.AppMessagingException;

public class CustomStompErrorHandler extends StompSubProtocolErrorHandler {

    public CustomStompErrorHandler() {
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        Throwable throwable = new MessagingException("Internal exception occurred");
        if (ex instanceof MessageDeliveryException messageDeliveryException) {
            if (messageDeliveryException.getRootCause() instanceof BadCredentialsException badCredentialsException) {
                throwable = badCredentialsException;
            }
        }

        if (ex instanceof AppMessagingException appMessagingException) {
            throwable = appMessagingException;
        }

        return super.handleClientMessageProcessingError(clientMessage, throwable);
    }

    @Override
    public Message<byte[]> handleErrorMessageToClient(Message<byte[]> errorMessage) {
        return super.handleErrorMessageToClient(errorMessage);
    }

}
