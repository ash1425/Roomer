package com.ashay.odc.roomer.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
class Room {

    @Id
    public String id

    public String roomName

    String getRoomName() {
        return roomName
    }

    void setRoomName(String roomName) {
        this.roomName = roomName
    }
}
