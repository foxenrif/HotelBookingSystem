import java.util.*;

class Hotel {
    private String name;
    private String address;
    private List<Room> rooms;

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;
        this.rooms = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }
}

class Room {
    private int roomNumber;
    private String type;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, String type, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}

class Client {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public Client(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

class Booking {
    private Client client;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;

    public Booking(Client client, Room room, Date checkInDate, Date checkOutDate) {
        this.client = client;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        room.setAvailable(false);
    }

    public void cancel() {
        room.setAvailable(true);
    }

    @Override
    public String toString() {
        return "Booking[" +
               "client=" + client.getFullName() +
               ", room=" + room.getRoomNumber() +
               ", checkInDate=" + checkInDate +
               ", checkOutDate=" + checkOutDate +
               ']';
    }
}

public class HotelBookingSystem {
    private List<Hotel> hotels;
    private List<Client> clients;
    private List<Booking> bookings;

    public HotelBookingSystem() {
        hotels = new ArrayList<>();
        clients = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public void registerClient(Client client) {
        clients.add(client);
    }

    public Booking bookRoom(Client client, Hotel hotel, int roomNumber, Date checkInDate, Date checkOutDate) {
        for (Room room : hotel.getRooms()) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                Booking booking = new Booking(client, room, checkInDate, checkOutDate);
                bookings.add(booking);
                return booking;
            }
        }
        throw new IllegalArgumentException("Room not available.");
    }

    public void cancelBooking(Booking booking) {
        booking.cancel();
        bookings.remove(booking);
    }

    public static void main(String[] args) {
        HotelBookingSystem system = new HotelBookingSystem();

        Hotel hotel = new Hotel("Sunshine Hotel", "123 Sunny Street");
        hotel.addRoom(new Room(101, "Single", 50.0));
        hotel.addRoom(new Room(102, "Double", 100.0));
        system.addHotel(hotel);

        Client client = new Client("John", "Doe", "john.doe@example.com", "1234567890");
        system.registerClient(client);

        try {
            Booking booking = system.bookRoom(client, hotel, 102, new Date(), new Date());
            System.out.println("Booking successful: " + booking);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
