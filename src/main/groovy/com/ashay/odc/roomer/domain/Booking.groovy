package com.ashay.odc.roomer.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Booking {

    @Id
    public String id

    public String roomName

    public String teamName

    public Date startTime

    public Date endTime

    String getRoomName() {
        return roomName
    }

    void setRoomName(String roomName) {
        this.roomName = roomName
    }

    String getTeamName() {
        return teamName
    }

    void setTeamName(String teamName) {
        this.teamName = teamName
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
