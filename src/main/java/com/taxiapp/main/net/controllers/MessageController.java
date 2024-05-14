package com.taxiapp.main.net.controllers;

import com.taxiapp.main.net.requests.NewMessageRequest;
import com.taxiapp.main.net.responses.MessageDataResponse;
import com.taxiapp.main.services.message.MessageService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/new")
    public ResponseEntity<MessageDataResponse> sendMessage(@Valid @RequestBody NewMessageRequest newMessageRequest) {
        return ResponseEntity.ok(messageService.createMessage(newMessageRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MessageDataResponse>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }
}
