package com.example.base;

import com.example.base.application.dto.MessageDto;
import com.example.base.service.rabbitmq.RabbitMQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RabbitMQController {
    private final RabbitMQService rabbitMQService;

    @PostMapping("/send/message")
    public ResponseEntity<String> sendMessage(
            @RequestBody MessageDto messageDto
    ) {
        rabbitMQService.sendMessage(messageDto);
        return ResponseEntity.ok("Message sent to RabbitMQ");
    }

}
