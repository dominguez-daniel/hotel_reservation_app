package model;

import java.util.Objects;

public class Room implements IRoom {
    private final String roomNumber; // used as id
    private final Double roomPrice;
    private final RoomType roomType;

    public Room(String roomNumber, Double roomPrice, RoomType roomType) {
        super();
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * Determines if the room is free or not.
     */
    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) && Objects.equals(roomPrice, room.roomPrice) && roomType == room.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, roomPrice, roomType);
    }

    @Override
    public String toString() {
        return "roomNumber: " + this.roomNumber
                + ", roomPrice: " + this.roomPrice
                + ", roomType: " + this.roomType;
    }
}
