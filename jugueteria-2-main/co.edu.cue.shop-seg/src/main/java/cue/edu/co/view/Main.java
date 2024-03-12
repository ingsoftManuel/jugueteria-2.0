package cue.edu.co.view;


import cue.edu.co.dtos.CreateUserDTO;
import cue.edu.co.dtos.ToysDTO;
import cue.edu.co.exceptions.SaleException;
import cue.edu.co.model.*;
import cue.edu.co.services.SaleService;
import cue.edu.co.services.ToysService;
import cue.edu.co.services.UserService;
import cue.edu.co.singleton.ServicesSingleton;

import java.text.SimpleDateFormat;
import java.util.*;


public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static ToysService toyService = ServicesSingleton.getInstance().toysService;
    public static UserService userService = ServicesSingleton.getInstance().userService;
    public static SaleService saleService = ServicesSingleton.getInstance().saleService;
    public static void main(String[] args) {

        boolean letters = false;
        while (!letters) {
            System.out.println("\nWhat do you want to do today");
            System.out.println(" 1 to add new toy");
            System.out.println(" 2 to show the toys by type");
            System.out.println(" 3 to show the total count");
            System.out.println(" 4 to show the toys above that price");
            System.out.println(" 5 to decrease the quantity amount of a toy ");
            System.out.println(" 6 to increase the quantity amount of a toy");
            System.out.println(" 7 to show the major quantity toys");
            System.out.println(" 8 to show the less quantity toys.");
            System.out.println(" 9 to filter toys by price min.");
            System.out.println(" 10 to filter toys by price max.");
            System.out.println(" 11 to create an employee");
            System.out.println(" 12 to show all employees");
            System.out.println(" 13 to create a customer");
            System.out.println(" 14 to show all customers");
            System.out.println(" 15 to create a sale");

            System.out.println(" 17 to sort toys by price.");
            System.out.println(" 0 to exit ");

            int interact = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (interact){
                    case 1:
                        System.out.println("Toy Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Toy Type: 0.Male, 1.Female, 2.Unisex");
                        Category type = Category.fromName(Integer.parseInt(scanner.nextLine()));
                        System.out.println("Toy Price: ");
                        double price = scanner.nextDouble();
                        System.out.println("Toy Quantity: ");
                        int quantity = scanner.nextInt();
                        ToysDTO newToy = new ToysDTO(name, type, price, quantity);
                        toyService.addToy(newToy);
                        break;
                    case 2:
                        System.out.println("\nToys by type: ");
                        System.out.println(toyService.showByType());
                        break;
                    case 3:
                        System.out.println("\n Total count: " + toyService.totalToys());
                        break;
                    case 4:
                        System.out.println("Enter the value");
                        double value = scanner.nextDouble();
                        System.out.println("\n Total Value: " + toyService.showToysAbove(value));
                        break;
                    case 5:

                        System.out.println("\n Name of the toy: ");
                        String decreaseName= scanner.nextLine();
                        System.out.println("Quantity to decrease: ");
                        int decrQuantity = scanner.nextInt();
                        toyService.decrease(toyService.search(decreaseName), decrQuantity);
                        break;
                    case 6:
                        System.out.println("\n Name of the toy: ");
                        String incrementName = scanner.nextLine();
                        System.out.println("Quantity to increase: ");
                        int incrQuantity = scanner.nextInt();
                        toyService.increase(toyService.search(incrementName), incrQuantity);
                        break;
                    case 7:
                        System.out.println("\n Major quantity: " + toyService.maxToy());
                        break;
                    case 8:
                        System.out.println("\n Less quantity: " + toyService.minToy());
                        break;
                    case 9:
                        System.out.println("\n Min Price: ");
                        System.out.println(toyService.minToy());
                        break;
                    case 10:
                        System.out.println("\n Max Price: ");
                        System.out.println(toyService.maxToy());
                        break;
                    case 11:
                        System.out.println("Employee Name: ");
                        String employeeName = scanner.nextLine();
                        System.out.println("Employee Email: ");
                        String employeeEmail = scanner.nextLine();
                        System.out.println("Employee Birth Date (dd/mm/yyyy): ");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String birthDate = scanner.nextLine();
                        Date employeeBirthDate = simpleDateFormat.parse(birthDate);
                        CreateUserDTO createUserDTO = new CreateUserDTO(employeeName, employeeEmail, employeeBirthDate, Role.EMPLOYEE);
                        userService.save(createUserDTO);
                        System.out.println("Employee created!");
                        break;
                    case 12:
                        System.out.println("Employees: ");
                        userService.getByRole(Role.EMPLOYEE).forEach(System.out::println);
                        break;
                    case 13:
                        System.out.println("Customer Name: ");
                        String customerName = scanner.nextLine();
                        System.out.println("Customer Email: ");
                        String customerEmail = scanner.nextLine();
                        System.out.println("Customer Birth Date (dd/mm/yyyy): ");
                        SimpleDateFormat simpleDateFormatCustomer = new SimpleDateFormat("dd/MM/yyyy");
                        String customerBirth = scanner.nextLine();
                        Date customerBirthDate = simpleDateFormatCustomer.parse(customerBirth);
                        CreateUserDTO createCustomerDTO = new CreateUserDTO(customerName, customerEmail, customerBirthDate, Role.CUSTOMER);
                        userService.save(createCustomerDTO);
                        System.out.println("Customer created!");
                        break;
                    case 14:
                        System.out.println("Customers: ");
                        userService.getByRole(Role.CUSTOMER).forEach(System.out::println);
                        break;
                    case 15:
                        try{
                            createSale();
                        }catch (SaleException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 0:
                        letters = true;
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error :(( " + e.getMessage());
            }

        }
        scanner.close();
    }

    public static void createSale(){
        Sale sale = new Sale();

        System.out.println("Employee ID: ");
        Long employeeId = scanner.nextLong();
        Optional<User> employee = userService.getById(employeeId);
        if(employee.isEmpty()){
            throw new SaleException("Employee not found");
        }
        sale.setEmployee(employee.get());
        System.out.println("Customer ID: ");
        Long customerId = scanner.nextLong();
        Optional<User> customer = userService.getById(customerId);
        if(customer.isEmpty()){
            throw new SaleException("Customer not found");
        }
        sale.setCustomer(customer.get());
        sale.setDate(new Date());

        List<SaleDetail> saleDetails = new ArrayList<>();
        System.out.println("How many toys do you want to add to the sale?");
        int quantityToys = scanner.nextInt();
        for (int i = 0; i < quantityToys; i++) {
            System.out.println("Toy ID: ");
            Long toyId = scanner.nextLong();
            Optional<Toys> toy = toyService.findById(toyId);
            if(toy.isEmpty()){
                throw new SaleException("Toy not found");
            }
            System.out.println("Quantity: ");
            int quantitySale = scanner.nextInt();
            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setToy(toy.get());
            saleDetail.setQuantity(quantitySale);
            saleDetails.add(saleDetail);
        }
        sale.setDetails(saleDetails);
        saleService.save(sale);
        System.out.println("Sale created!");
    }
}