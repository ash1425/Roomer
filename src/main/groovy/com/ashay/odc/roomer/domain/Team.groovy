package com.ashay.odc.roomer.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Team {

    @Id
    public String id

    public String teamName

    String getTeamName() {
        return teamName
    }

    void setTeamName(String teamName) {
        this.teamName = teamName
    }
}
