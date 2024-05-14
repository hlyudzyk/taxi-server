package com.taxiapp.main.services.message;

import com.taxiapp.main.net.requests.NewMessageRequest;
import com.taxiapp.main.net.responses.MessageDataResponse;
import com.taxiapp.main.persistance.entities.Message;
import com.taxiapp.main.persistance.repositories.MessagesRepository;
import com.taxiapp.main.security.user.Role;
import com.taxiapp.main.security.user.User;
import com.taxiapp.main.security.user.UserRepository;
import com.taxiapp.main.services.exceptions.NoContentException;
import com.taxiapp.main.services.exceptions.UnauthorizedException;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
@Validated
public class MessageService {
    private final MessagesRepository messagesRepository;
    private final UserRepository userRepository;
    private final AuditorAware<UUID> auditorAware;

    public MessageDataResponse createMessage(@Valid NewMessageRequest newMessageRequest) {
        List<String> stringIds = Objects.requireNonNullElse(newMessageRequest.getRecipientsIds(),new ArrayList<>());

        List<UUID> recipientsIds = stringIds
            .stream()
            .map(UUID::fromString)
            .toList();

        List<Role> targetRoles = Objects.requireNonNullElse(newMessageRequest.getTargetRoles(),new ArrayList<>());

        Set<User> recipients = new HashSet<>(findUsersByRole(targetRoles));
        recipients.addAll(findUsersByIds(recipientsIds));

        User sender = findUserById(auditorAware.getCurrentAuditor()
            .orElseThrow(()->new UnauthorizedException("Cannot authenticate user"))
        );

        if (recipients.isEmpty()) {
            //TODO throw an exception
        }

        Message message = Message.builder()
            .subject(newMessageRequest.getSubject())
            .content(newMessageRequest.getContent())
            .sender(sender)
            .recipients(recipients)
            .build();

        return new MessageDataResponse(messagesRepository.save(message));
    }

    public List<MessageDataResponse> getAllMessages() {
        return messagesRepository.findAll().stream().map(m->new MessageDataResponse(m)).toList();
    }

    private List<User> findUsersByIds(List<UUID> userIds) {
        return userRepository.findAllById(userIds);
    }

    private List<User> findUsersByRole(List<Role> roles) {
        return roles.stream()
            .flatMap(role -> userRepository.findAllByRole(role).stream())
            .toList();
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new NoContentException("Cannot find user with given ID: " + userId));
    }


}
