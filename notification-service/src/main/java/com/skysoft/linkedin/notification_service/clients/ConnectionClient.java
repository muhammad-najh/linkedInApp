package com.skysoft.linkedin.notification_service.clients;

import com.skysoft.linkedin.notification_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "connections-service",path = "/connections") //path is context-path for properties
public interface ConnectionClient {

    @GetMapping("/core/first-degree")
    List<PersonDto> getFirstConnections(@RequestHeader( "x-user-id") Long userId) ;
}
