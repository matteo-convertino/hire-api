package com.convertino.hire.security;

import com.convertino.hire.exceptions.websocket.UnauthorizedSubscriptionException;
import com.convertino.hire.model.Interview;
import com.convertino.hire.model.User;
import com.convertino.hire.service.InterviewService;
import com.convertino.hire.utils.routes.WebSocketRoutes;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Interceptor for handling WebSocket subscription requests.
 * <p>
 * Ensures that only authorized users can subscribe to protected topics.
 */
@Component
@AllArgsConstructor
public class SubscriptionInterceptor implements ChannelInterceptor {

    private final InterviewService interviewService;

    private final List<String> protectedTopics = List.of(
            WebSocketRoutes.QUEUE_INTERVIEWS
    );

    private final List<String> publicTopics = List.of(
            WebSocketRoutes.QUEUE_ERRORS
    );

    /**
     * Intercepts the message before it is sent to the channel.
     * Checks if the user has permission to subscribe to the requested topic.
     *
     * @param message the message to be sent
     * @param channel the message channel
     * @return the message if the user has permission, otherwise throws an exception
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        // Access the STOMP headers from the message
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        // Check if the command is a subscription request
        if (accessor != null && StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String destination = accessor.getDestination();

            if (destination != null && accessor.getUser() != null) {
                User user = (User) ((UsernamePasswordAuthenticationToken) accessor.getUser()).getPrincipal();

                destination = destination.replace("/user", "");

                if (publicTopics.contains(destination)) {
                    return message;
                }

                // Iterate over the protected topics to check if the user has access
                for (String topic : protectedTopics) {
                    System.out.println(topic);
                    if (destination.matches(topic + "\\d+")) {
                        String interviewId = destination.replace(topic, "");

                        // Check if the user is the candidate of the interview
                        if (isInterviewCandidate(user.getId(), Long.parseLong(interviewId)))
                            return message;

                        break;
                    }
                }
            }

            throw new UnauthorizedSubscriptionException(destination);
        }


        return message;
    }

    /**
     * Checks if the user has access to specified interview.
     *
     * @param userId      the ID of the user
     * @param interviewId the ID of the interview
     * @return true if the user is the candidate of the interview, false otherwise
     */
    private boolean isInterviewCandidate(long userId, long interviewId) {
        Interview interview = interviewService.findEntityById(interviewId);
        return interview.getUser().getId() == userId;
    }
}
