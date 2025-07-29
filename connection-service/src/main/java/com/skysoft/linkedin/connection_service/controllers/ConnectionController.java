package com.skysoft.linkedin.connection_service.controllers;

import com.skysoft.linkedin.connection_service.auth.UserContextHolder;
import com.skysoft.linkedin.connection_service.entity.PersonEntity;
import com.skysoft.linkedin.connection_service.services.impl.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<PersonEntity>> getFirstConnections() {
        return ResponseEntity.ok(connectionService.getFirstConnections());
    }
}
