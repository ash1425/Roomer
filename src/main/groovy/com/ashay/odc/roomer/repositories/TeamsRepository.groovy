package com.ashay.odc.roomer.repositories

import com.ashay.odc.roomer.domain.Team
import org.springframework.data.mongodb.repository.MongoRepository

public interface TeamsRepository extends MongoRepository<Team,String> {

    Team findByTeamName(String teamName)
}