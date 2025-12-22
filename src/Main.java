import com.MagdhaPlace.hotel.AppRunner;
import com.MagdhaPlace.hotel.handler.*;
import com.MagdhaPlace.hotel.repo.*;
import com.MagdhaPlace.hotel.repo.impl.*;
import com.MagdhaPlace.hotel.service.*;
import com.MagdhaPlace.hotel.service.impl.*;

import java.nio.file.Path;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Path dataDir = Paths.get("D:\\Project\\HotelManagement\\Hotel-management\\Data");

        CustomerRepo customerRepo = new CustomerRepoImpl(dataDir.resolve("customers.csv"));
        CustomerService customerService = new CustomerServiceImpl(customerRepo);
        HandleCustomer handleCustomer = new HandleCustomer(customerService);

        RoomRepo roomRepo = new RoomRepoImpl(dataDir.resolve("rooms.csv"));
        RoomService roomService = new RoomServiceImpl(roomRepo);
        HandleRoom handleRoom = new HandleRoom(roomService);

        InvoiceRepo invoiceRepo = new InvoiceRepoImpl(dataDir.resolve("invoice.csv"));
        InvoiceService invoiceService = new InvoiceServiceImpl(invoiceRepo);

        BookingRepo bookingRepo = new BookingRepoImpl(dataDir.resolve("booking.csv"));
        BookingService bookingService = new BookingServiceImpl(bookingRepo, roomService, invoiceService);
        HandleBooking handleBooking = new HandleBooking(bookingService, roomService, customerService);

        AppRunner appRunner = new AppRunner(handleCustomer, handleRoom, handleBooking);
        appRunner.run();

    }
}