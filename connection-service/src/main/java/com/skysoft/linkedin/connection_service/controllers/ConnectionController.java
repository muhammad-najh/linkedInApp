package com.skysoft.linkedin.connection_service.controllers;

import com.skysoft.linkedin.connection_service.entity.PersonEntity;
import com.skysoft.linkedin.connection_service.services.impl.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<PersonEntity>> getFirstConnections(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(connectionService.getFirstConnections(userId));
    }
}
