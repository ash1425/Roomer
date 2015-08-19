package com.ashay.odc.roomer.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Booking {

    @Id
    private String id

    @DBRef
    private Room room

    @DBRef
    private Team team

    private Date startTime

    private Date endTime

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    Room getRoom() {
        return room
    }

    void setRoom(Room room) {
        this.room = room
    }

    Team getTeam() {
        return team
    }

    void setTeam(Team team) {
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
