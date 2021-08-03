package model;

public class FreeRoom extends Room {

    FreeRoom(String roomNumber, Double roomPrice, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public String toString() {
        return "roomNumber: " + super.getRoomNumber()
                + "roomPrice: " + super.getRoomPrice()
                + "roomType: " + super.getRoomType();
    }
}
