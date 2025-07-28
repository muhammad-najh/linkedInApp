package com.skysoft.linkedin.connection_service.services.impl;

import com.skysoft.linkedin.connection_service.entity.PersonEntity;
import com.skysoft.linkedin.connection_service.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final PersonRepository personRepository;

    public List<PersonEntity> getFirstConnections(Long userId) {
        log.info("Fetching first connections for userId: {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }
}
