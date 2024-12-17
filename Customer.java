import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Customer implements User{
    static Scanner scan = new Scanner(System.in);

    private static ArrayList<Customer> customers = new ArrayList<>();
    private String name;
    private String id;
    private String pass;
    private ArrayList<Item> cart = new ArrayList<>();
    private Order order;
    private ArrayList<Order> orderHistory = new ArrayList<>();
    private boolean isVIP;

    static{
        new Customer("Tanish", "001", "0000",false);
        new Customer("Vansh", "002", "0000",false);
        new Customer("Saksham", "003", "0000",true);
    }
    
        public Customer(String _name, String _id, String _pass, boolean vip){
            this.name = _name;
            this.id = _id;
            this.pass = _pass;
            this.isVIP = vip;
            customers.add(this);
        }



        public void loadCart() {
            String fileName = "cart_" + this.id + ".txt";
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("No existing cart data found for user: " + this.id);
                return;
            }
    
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Item item = Shop.getItem(line.trim());
                    if (item != null) {
                        cart.add(item);
                    }
                }
                System.out.println("Cart loaded successfully for user: " + this.id);
            } catch (IOException e) {
                System.out.println("Error loading cart for user: " + this.id);
            }
        }
    
        private void saveCart() {
            String fileName = "cart_" + this.id + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
                for (Item item : cart) {
                    writer.write(item.getName());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error saving cart for user: " + this.id);
            }
        }

    public void saveOrderHistory(Order order) {
        String fileName = "order_" + this.id + ".txt";
        File file = new File(fileName);

        try {

            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Order history file created for user: " + this.id);
            }


            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(order.orderDetails()); // Assuming Order has a valid toString method
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving order history for user: " + this.id);
        }
    }

    public void loadOrderHistory() {
        String fileName = "order_" + this.id + ".txt";
        File file = new File(fileName);
    
        if (!file.exists()) {
            return;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Order order = Order.parseOrder(line);
                order.setStatus("Delivered");
                if (order != null) {
                    orderHistory.add(order);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading order history for user: " + this.id);
        }
    }
    
    
        public void menu(){
            while (true) {
                if (isVIP) {
                    System.out.println("Welcome "+this.getName()+"! - (VIP CUSTOMER)");
                }
                else{
                    System.out.println("Welcome "+this.getName()+"!");
                }
                System.out.println("1. Browse menu");
                System.out.println("2. Cart Operations");
                System.out.println("3. Track order");
                System.out.println("4. Review Items");
                System.out.println("0. Logout");
                System.out.print("Your choice --> ");
                int choice = scan.nextInt();
                scan.nextLine();
                System.out.println();
    
    
                if (choice == 1) {
                    Shop.menu();
                }
    
                else if (choice == 2) {
                    cartOperations();
    
                }
    
                else if (choice == 3) {
                    orderMenu();
                }
    
                else if (choice == 4){
                    reviewMenu();
                }
    
                else if (choice == 0) {
                    break;
                }
            }
        }

        public boolean isVIP() {
            return isVIP;
        }
    
        public static void login() {

            while (true) {
                System.out.print("Enter id: ");
                String id = scan.nextLine();
                System.out.print("Enter password: ");
                String password = scan.nextLine();
                System.out.println();
    
                for (Customer x : customers) {
                    if (x.getId().equals(id) && x.getPass().equals(password)) {
                        x.loadOrderHistory();
                        x.loadCart();
                        x.menu();
                        return;
                    }
                }
                System.out.println("Customer not found");
                System.out.println("1. Try again?\n0. Exit (Press any other key)");
                System.out.print("Your choice --> ");
                int choice = scan.nextInt();
                System.out.println();
                scan.nextLine();
    
    
                if (choice != 1) {
                    Main.welcome();
                    break;
                }
            }
        }

        public static Customer getCustomer(String id){
            for (Customer customer : customers) {
                if (customer.getId().equals(id)) {
                    return customer;
                }
            }
            return null;
        }

        public void cartOperations() {
            while (true) {
                System.out.println("--- Cart Operations ---");
                System.out.println("1. Add Item to Cart");
                System.out.println("2. View Cart");
                System.out.println("3. Remove Item from Cart");
                System.out.println("4. Checkout");
                System.out.println("0. Back to Main Menu");
                System.out.print("Your choice --> ");
                int choice = scan.nextInt();
                scan.nextLine();
                System.out.println();
    
                switch (choice) {
                    case 1:
                        addItemToCart();
                        break;
                    case 2:
                        viewCart();
                        break;
                    case 3:
                        removeItem();
                        break;
                    case 4:
                        checkout();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    
    
        public void addItemToCart() {
            System.out.print("Enter the name of the item to add: ");
            String name = scan.nextLine();
            Item item = Shop.getItem(name); 
            if (item != null ) {
                if (!item.getAvailable()) {
                    System.out.println(item.getName()+" is currently not available ");
                    return;
                }
                cart.add(item);
                saveCart();
                System.out.println(item.getName() + " has been added to your cart.");
                System.out.println();

            } else {
                System.out.println("Item not found in the shop.");
                System.out.println();

            }
        }
    
        public void viewCart(){
            if (cart.isEmpty()) {
                System.out.println("Cart is Empty");
                System.out.println();

                return;
            }
            int price = 0;
            System.out.println("--- cart ---");
            for (Item item : cart) {
                price = price + item.getPrice();
                System.out.println(item.getItem());
            }
            System.out.println("Total amount: "+price);
            System.out.println();
        }

        public int cartAmount(){
            int price = 0;
            for (Item item : cart) {
                price = price + item.getPrice();
            }
            return price;
        }
    
        public void removeItem() {
            System.out.print("Enter the name of the item to remove: ");
            String name = scan.nextLine();
            boolean itemFound = false;
            for (Item item : cart) {
                if (item.getName().equalsIgnoreCase(name)) {
                    cart.remove(item);
                    saveCart();
                    System.out.println(item.getName() + " has been removed from your cart.");
                    System.out.println();

                    itemFound = true;
                    break;
                }
            }
            if (!itemFound) {
                System.out.println("Item not found in your cart.");
                System.out.println();

            }
        }
    
        public void checkout(){
            if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            System.out.println();

            return;
            } 
            viewCart();
            System.out.println("1. Proceed to Checkout");
            System.out.println("0. Back to menu");
            System.out.print("Your choice --> ");
            int choice = scan.nextInt();
            scan.nextLine();
            if (choice == 1) {
                order = new Order(this.id, cart, cartAmount());
                Shop.getOrders().add(order);
                System.out.println("Order Placed Successfully!");
                System.out.println();
                cart.clear();
                String fileName = "cart_" + this.id + ".txt";
                File file = new File(fileName);
                if (file.exists()) {
                    file.delete();
                } 
            }
        }

    public void orderMenu() {
        while (true) {
            System.out.println("\n--- Order Menu ---");
            System.out.println("1. Order Status");
            System.out.println("2. Cancel Order");
            System.out.println("3. Order History");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice --> ");
            int choice = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    orderStatus();
                    break;
                case 2:
                    cancelOrder();
                    break;
                case 3:
                    viewOrderHistory();
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println();
            }
        }
    }

    public void orderStatus(){
        if (order == null) {
            System.out.println("No Order is placed");
            System.out.println();
            return;
        }
        order.printItems();
    }

    public void cancelOrder(){
        if (order == null) {
            System.out.println("No orders placed");
            System.out.println();
            return;
        }
        order.setStatus("Cancelled");
        orderHistory.add(order);
        Shop.getOrders().remove(order);
        order = null;
        System.out.println("Order Cancelled Successfully!");
        System.out.println();
    }
    
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void viewOrderHistory(){
        if (orderHistory.isEmpty()) {
            System.out.println("No past orders");
            System.out.println();
        }
        System.out.println("-------- Order History ----------");

        for (Order order : orderHistory) {
            order.printItems();
            System.out.println("---------------------------------");
        } 
        
    }

    public void reviewMenu(){
        System.out.println("1. Give review");
        System.out.println("2. View reviews");
        System.out.println("0. Back to Main Menu");
        System.out.print("Your choice --> ");
        int choice = scan.nextInt();
        scan.nextLine();
        System.out.println();

        if (choice == 1) {
            giveReview();
        }
        if (choice == 2) {
            showReviews();
        }
    }

    public void giveReview(){
        System.out.print("Enter item name: ");
        String name = scan.nextLine();
        Item item = Shop.getItem(name); 
        if (item != null) {
            System.out.print("Your review --> ");
            String review = scan.nextLine();
            item.getReviews().add(review);
            System.out.println("Review added Successfully!");
            System.out.println();
        } else {
            System.out.println("Item not found");
            System.out.println();
        }
    }

    public void showReviews(){
        System.out.print("Enter item name: ");
        String name = scan.nextLine();
        Item item = Shop.getItem(name); 
        if (item != null) {
            if (item.getReviews().isEmpty()) {  
                System.out.println("No Reviews available");
                System.out.println();
                return;
            }
            for (String review : item.getReviews()) {
                System.out.println(review);
            }
            System.out.println();
        }
        else {
            System.out.println("Item not found");
            System.out.println();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }
    
}