import com.MagdhaPlace.hotel.AppRunner;
import com.MagdhaPlace.hotel.handler.HandleCustomer;
import com.MagdhaPlace.hotel.repo.CustomerRepo;
import com.MagdhaPlace.hotel.repo.impl.CustomerRepoImpl;
import com.MagdhaPlace.hotel.service.CustomerService;
import com.MagdhaPlace.hotel.service.impl.CustomerServiceImpl;

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

        AppRunner appRunner = new AppRunner(handleCustomer);
        appRunner.run();

    }
}