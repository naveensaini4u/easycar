package com.naveen.easycar.console;

import com.naveen.easycar.entity.Car;
import com.naveen.easycar.entity.Customer;
import com.naveen.easycar.entity.Reservation;
import com.naveen.easycar.enums.CarCategory;
import com.naveen.easycar.service.CarService;
import com.naveen.easycar.service.CustomerService;
import com.naveen.easycar.service.ReservationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class DriveEasyConsoleRunner implements CommandLineRunner {

    private final CarService carService;
    private final CustomerService customerService;
    private final ReservationService reservationService;

    private final Scanner scanner = new Scanner(System.in);

    public DriveEasyConsoleRunner(
            CarService carService,
            CustomerService customerService,
            ReservationService reservationService
    ) {
        this.carService = carService;
        this.customerService = customerService;
        this.reservationService = reservationService;
    }

    @Override
    public void run(String... args) {
        //showMenu();
    }

    private void showMenu() {

        while (true) {
            System.out.println("""
            \n===== DriveEasy Rentals =====
            1. Add Car
            2. Register Customer
            3. View Available Cars
            4. Create Reservation
            5. Cancel Reservation
            0. Exit
            """);

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1 -> addCar();
                    case 2 -> registerCustomer();
                    case 3 -> viewCars();
                    case 4 -> createReservation();
                    case 5 -> cancelReservation();
                    case 6 -> getAllReservations();
                    case 0 -> System.exit(0);
                    default -> System.out.println("Invalid option");
                }
            } catch (RuntimeException ex) {
                System.out.println("❌ " + ex.getMessage());
            }
        }
    }

    private void addCar() {
        System.out.print("Registration Number: ");
        String reg = scanner.nextLine();

        System.out.print("Brand: ");
        String brand = scanner.nextLine();

        System.out.print("Model: ");
        String model = scanner.nextLine();

        System.out.print("Category (HATCHBACK/SUV/SEDAN/LUXURY): ");
        CarCategory category = CarCategory.valueOf(scanner.nextLine().toUpperCase());

        System.out.print("Daily Rate: ");
        BigDecimal rate = new BigDecimal(scanner.nextLine());

        Car car = new Car();
        car.setRegistrationNumber(reg);
        car.setBrand(brand);
        car.setModel(model);
        car.setCategory(category);
        car.setDailyRate(rate);

        carService.addCar(car);
        System.out.println("✅ Car added successfully");
    }

    private void registerCustomer() {
        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        Customer customer = new Customer();
        customer.setFullName(name);
        customer.setEmail(email);
        customer.setPhone(phone);

        customerService.registerCustomer(customer);
        System.out.println("✅ Customer registered");
    }

    private void viewCars() {
        List<Car> cars = carService.getAllActiveCars();

        if (cars.isEmpty()) {
            System.out.println("No cars available");
            return;
        }

        cars.forEach(car ->
                System.out.printf(
                        "ID: %d | %s %s | %s | ₹%s/day%n",
                        car.getId(),
                        car.getBrand(),
                        car.getModel(),
                        car.getCategory(),
                        car.getDailyRate()
                )
        );
    }

    private void createReservation() {

        System.out.print("Car ID: ");
        Long carId = Long.parseLong(scanner.nextLine());

        System.out.print("Customer ID: ");
        Long customerId = Long.parseLong(scanner.nextLine());

        System.out.print("Start Date (yyyy-mm-dd): ");
        LocalDate start = LocalDate.parse(scanner.nextLine());

        System.out.print("End Date (yyyy-mm-dd): ");
        LocalDate end = LocalDate.parse(scanner.nextLine());

        Reservation reservation = reservationService.createReservation(
                carId, customerId, start, end
        );

        System.out.println(
                "✅ Reservation created. Total Cost: ₹" +
                        reservation.getTotalCost()
        );
    }

    private void cancelReservation() {

        System.out.print("Reservation ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        reservationService.cancelReservation(id);
        System.out.println("✅ Reservation cancelled");
    }

    private void getAllReservations(){
        List<Reservation> reservations = reservationService.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No reservations found");
            return;
        }

        reservations.forEach(reservation ->
                System.out.printf(
                        "ID: %d | Car: %s %s | Customer: %s | From: %s | To: %s | Status: %s%n",
                        reservation.getId(),
                        reservation.getCar().getBrand(),
                        reservation.getCar().getModel(),
                        reservation.getCustomer().getFullName(),
                        reservation.getStartDate(),
                        reservation.getEndDate(),
                        reservation.getStatus()
                )
        );

    }

}

