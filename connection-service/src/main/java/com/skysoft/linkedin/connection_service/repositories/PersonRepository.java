package com.skysoft.linkedin.connection_service.repositories;

import com.skysoft.linkedin.connection_service.entity.PersonEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<PersonEntity, Long> {
    Optional<PersonEntity> findByName(String name);
    @Query("MATCH(personA:Person)-[:CONNECTED_TO]-(personB:Person)" +
            " WHERE personA.userId = $userId" +
            " RETURN personB")
    List<PersonEntity> getFirstDegreeConnections(Long userId);
}
