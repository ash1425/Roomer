package com.ashay.odc.roomer.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Booking {

    @Id
    private String id

    private String room

    private String team

    private Date startTime

    private Date endTime

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getRoom() {
        return room
    }

    void setRoom(String room) {
        this.room = room
    }

    String getTeam() {
        return team
    }

    void setTeam(String team) {
        this.team = team
    }

    Date getStartTime() {
        return startTime
    }

    void setStartTime(Date startTime) {
        this.startTime = startTime
    }

    Date getEndTime() {
        return endTime
    }

    void setEndTime(Date endTime) {
        this.endTime = endTime
    }
}
