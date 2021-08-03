package model;

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
    public String toString() {
        return "roomNumber: " + this.roomNumber
                + ", roomPrice: " + this.roomPrice
                + ", roomType: " + this.roomType;
    }
}
