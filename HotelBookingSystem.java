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

    public List<Room> searchAvailableRooms(Date checkInDate, Date checkOutDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
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

    public void sendConfirmation() {
        System.out.println("Booking confirmed for " + client.getFullName() +
            ". Room " + room.getRoomNumber() + " from " + checkInDate + " to " + checkOutDate + ".");
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

class Admin {
    public void addHotel(HotelBookingSystem system, String name, String address) {
        Hotel hotel = new Hotel(name, address);
        system.addHotel(hotel);
    }

    public void addRoom(Hotel hotel, int roomNumber, String type, double pricePerNight) {
        Room room = new Room(roomNumber, type, pricePerNight);
        hotel.addRoom(room);
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
                booking.sendConfirmation();
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
        Scanner scanner = new Scanner(System.in);
        HotelBookingSystem system = new HotelBookingSystem();
        Admin admin = new Admin();

        // Add initial data
        admin.addHotel(system, "Sunshine Hotel", "123 Sunny Street");
        Hotel hotel = system.hotels.get(0);
        admin.addRoom(hotel, 101, "Single", 50.0);
        admin.addRoom(hotel, 102, "Double", 100.0);

        // Register client
        System.out.println("Enter client details:");
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        Client client = new Client(firstName, lastName, email, phoneNumber);
        system.registerClient(client);

        // Book a room
        try {
            System.out.println("Available rooms in Sunshine Hotel:");
            for (Room room : hotel.searchAvailableRooms(new Date(), new Date())) {
                System.out.println("Room " + room.getRoomNumber() + ", Type: " + room.getType() + ", Price: " + room.getPricePerNight());
            }

            System.out.print("Enter room number to book: ");
            int roomNumber = scanner.nextInt();
            Booking booking = system.bookRoom(client, hotel, roomNumber, new Date(), new Date());
            System.out.println("Booking successful: " + booking);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
