import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Shop {
    static Scanner scan = new Scanner(System.in);
    private static ArrayList<Item> items = new ArrayList<>();
    private static ArrayList<Order> orders = new ArrayList<>();
    private static ArrayList<Order> completedOrders = new ArrayList<>();
    private static int sales;

    static {
        items.add(new Item("maggi", 30, "Food", true, new ArrayList<>(Arrays.asList("affordable", "noodles", "popular", "maggi", "fast"))));
        items.add(new Item("parantha", 30, "Food", true, new ArrayList<>(Arrays.asList("affordable", "parantha", "popular"))));
        items.add(new Item("cold coffee", 50, "Drink", true, new ArrayList<>(Arrays.asList("coffee", "cold", "fast"))));
    }

    public static void generateSalesReport(){
        int count = 0;
        int amount = 0;
        for (Order order : completedOrders) {
            count++;
            amount = amount + order.getAmount();
        }
        Item popular = getPopularItem();

        System.out.println("--- Sales Report Of The Day ---");
        System.out.println("Total Orders: " + count);
        System.out.println("Total Sales: " + amount);
        System.out.println("Most popular Item: " + popular.getName());
    }

    public static Item getPopularItem(){
        int max_count = 0;
        Item popular = null;
        for (Item item : items) {
            int count = 0;
            for (Order order : completedOrders) {
                for (Item item2: order.getItems()) {
                    if (item == item2) {
                        count++;
                    }
                }
                if (count > max_count) {
                    max_count = count;
                    popular = item;
                }
            }
        }
         return popular;
    }

    
    public static ArrayList<Order> getOrders() {
        return orders;
    }

    public static ArrayList<Order> getCompletedOrders() {
        return completedOrders;
    }

    public static void updateSales(int sales) {
        Shop.sales = Shop.sales + sales;
    }

    public static void menu() {
        while (true) {
            System.out.println("---- Browse Menu ----");
            System.out.println("1. View All Items");
            System.out.println("2. Search Item");
            System.out.println("3. Filter by Category");
            System.out.println("4. Sort by Price");
            System.out.println("0. Back to Main Menu");
            System.out.print("Your choice --> ");
            int choice = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    viewAllItems();
                    break;
                case 2:
                    System.out.print("Enter keyword to search: ");
                    String keyword = scan.nextLine();
                    searchItem(keyword);
                    break;
                case 3:
                    System.out.print("Enter category to filter by: ");
                    String category = scan.nextLine();
                    filterByCategory(category);
                    break;
                case 4:
                    System.out.println("1. Sort by Price (Low to High)");
                    System.out.println("2. Sort by Price (High to Low)");
                    System.out.print("Your choice --> ");
                    int sortOption = scan.nextInt();
                    scan.nextLine();
                    sortByPrice(sortOption);
                    break;
                case 0:
                    return; // Exit the browse menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    

    // public static void viewAllItems(){
    //     if (items.isEmpty()) {
    //     System.out.println("No items on the menu");
    //     System.out.println();
    //     return;
    //     }
    //     System.out.println("---- All Menu Items ----");
    //     for (Item item : items) {
    //         System.out.println(item.getItem());
    //     }
    //     System.out.println();
    // }

    public static void viewAllItems() {
        JFrame frame = new JFrame("Shop Menu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
    
        JPanel panel = new JPanel(new BorderLayout());
    
        JLabel titleLabel = new JLabel("Available Items in the Shop", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
    
        // Table column headers
        String[] columnNames = {"Name", "Price", "Category", "Available"};
    
        // Table data
        Object[][] data = new Object[Shop.getItems().size()][4];
        int row = 0;
        for (Item item : Shop.getItems()) {
            data[row][0] = item.getName();
            data[row][1] = item.getPrice();
            data[row][2] = item.getCategory();
            data[row][3] = item.getAvailable() ? "Yes" : "No";
            row++;
        }
    
        // Create the table
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> frame.dispose());
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        frame.add(panel);
        frame.setVisible(true);
    }
    



    public static ArrayList<Item> getItems() {
        return items;
    }

    public static void searchItem(String keyword){
        if (items.isEmpty()) {
            System.out.println("No items on the menu");
            System.out.println();
            return;
        }
        boolean flag = false;
        keyword = keyword.toLowerCase();
        for (Item item : items) {
            if (item.getName().toLowerCase().equals(keyword)) {
                System.out.println(item.getItem());
                flag = true;
                continue;
            }
            for (String i : item.getKeywords()) {
                if (i.toLowerCase().contains(keyword)) {
                    System.out.println(item.getItem());
                    flag = true;
                }
            }
        }
        if (flag == false) {
            System.out.println("No matches found");
            System.out.println();
        }
        System.out.println();
    }

    public static void filterByCategory(String category){
        if (items.isEmpty()) {
            System.out.println("No items on the menu");
            System.out.println();
            return;
        }
        boolean flag = false;
        category = category.trim().toLowerCase();
        for (Item item : items) {
            if (item.getCategory().toLowerCase().equals(category)) {
                System.out.println(item.getItem());
                flag = true;
            }
        }
        if (flag == false) {
            System.out.println("No items in such category");
        }
        System.out.println();
    }

    public static void sortByPrice(int sortOption) {
        if (items.isEmpty()) {
            System.out.println("No items on the menu");
            return;
        }
        if (sortOption == 1) {
            items.sort(Comparator.comparingInt(Item::getPrice));
            System.out.println("Items sorted by price (Low to High):");
            viewAllItems();
        } else if (sortOption == 2) {
            Collections.sort(items, Comparator.comparingInt(Item::getPrice).reversed());
            System.out.println("Items sorted by price (High to Low):");
            viewAllItems();
        } else {
            System.out.println("Invalid option");
            return;
        }

        for (Item item : items) {
            System.out.println(item.getItem());
        }
    }

    public static Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public static void addItem(){
        System.out.print("Enter Item name: ");
        String name = scan.nextLine();
        System.out.print("Enter Item price: ");
        int price = scan.nextInt();
        scan.nextLine();
        System.out.print("Enter Item category: ");
        String category = scan.nextLine();
        System.out.print("Enter Item keywords separated by spaces: ");
        String words = scan.nextLine();
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(words.split(" ")));
        items.add(new Item(name, price, category, true, keywords));
    }

    public static void updateItem(){
        System.out.print("Enter item name to update: ");
        String name = scan.nextLine();
        Item item = getItem(name);
        if (item == null) {
            System.out.println("Item does not exist");
            System.out.println();
            return;
        }
        System.out.println("1. Update price");
        System.out.println("2. Update availability");
        System.out.println("0. back");
        System.out.print("Your choice --> ");
        int choice = scan.nextInt();
        scan.nextLine();
        System.out.println();

        switch (choice) {
            case 1:
                System.out.print("Enter new price: ");
                int price = scan.nextInt();
                scan.nextLine();
                item.setPrice(price);
                System.out.println("Updated Successfully!");
                break;

            case 2:
                System.out.println("Set availablity (yes/no) : ");
                String ch = scan.nextLine();
                boolean bool = false;
                if (ch.toLowerCase().equals("yes")) {
                    bool = true;
                }
                else if (ch.toLowerCase().equals("no")) {
                    bool = false;
                }
                else{
                    System.out.println("Wrong option");
                    return;
                }
                item.setAvailable(bool);
                System.out.println("Updated Successfully!");

                
                break;
        
            default:
                break;
        }
    }


    public static void removeItem() {
        System.out.print("Enter the name of the item to remove: ");
        String name = scan.nextLine();
        boolean itemFound = false;
        Item itemToRemove = null;
    
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                itemToRemove = item;
                itemFound = true;
                break;
            }
        }
    
        if (itemToRemove != null) {
            items.remove(itemToRemove);
            System.out.println(itemToRemove.getName() + " has been removed from Shop.");
            ArrayList<Order> ordersToDeny = new ArrayList<>();
            for (Order order : orders) {
                for (Item orderItem : order.getItems()) {
                    if (orderItem.equals(itemToRemove)) {
                        order.setStatus("Denied");
                        orderItem.setAvailable(false);
                        ordersToDeny.add(order);
                        Customer customer = Customer.getCustomer(order.getId());
                        customer.getOrderHistory().add(order);
                        customer.saveOrderHistory(order);
                        customer.setOrder(null);
                    }
                }              
            }
            orders.removeAll(ordersToDeny);
        }
        if (!itemFound) {
            System.out.println("Item not found in the shop.");
        }
    }

    
    public static void viewOrders() {
        JFrame frame = new JFrame("Manage Orders");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
    
        JPanel panel = new JPanel(new BorderLayout());
    
        JLabel titleLabel = new JLabel("Pending Orders", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
    
        String[] columnNames = {"Customer ID", "Items", "Total Amount", "Status"};
    
        Object[][] data = new Object[Shop.getOrders().size()][5];
        int row = 0;
        for (Order order : Shop.getOrders()) {
            data[row][0] = order.getId();
            data[row][1] = order.getItems();
            data[row][2] = order.getAmount();
            data[row][3] = order.getStatus();
            row++;
        }
    
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
    
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> frame.dispose());
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        frame.add(panel);
        frame.setVisible(true);
    }
    

    public static void updateOrderStatus(){
        if (orders.isEmpty()) {
            System.out.println("No Pending Orders");
            return;
        }
        System.out.print("Enter the Order ID to update: ");
        String id = scan.nextLine();
        if (checkOrderId(id)) {
            System.out.print("Enter the new status (Preparing/Delivered): ");
            String status = scan.nextLine();
            for (Order order : orders) {
                if (order.getId().equals(id)) {
                    order.setStatus(status);
                    System.out.println("Order Status Updated!");
                    if (status.toLowerCase().equals("delivered")) {
                        updateSales(order.getAmount());
                        completedOrders.add(order);
                        orders.remove(order);
                        Customer customer = Customer.getCustomer(id);
                        customer.getOrderHistory().add(order);
                        customer.saveOrderHistory(order);
                        customer.setOrder(null);
                        return;
                    }
                }
            }
        }
        else{
            System.out.println("Order does not exist");
        }
        
    }

    public static void processRefunds() {
        if (orders.isEmpty()) {
            System.out.println("No Pending Orders");
            return;
        }
        System.out.print("Enter the Order ID to Process Refund: ");
        String id = scan.nextLine();
        if (checkOrderId(id)) {
            for (Order order : orders) {
                if (order.getId().equals(id)) {
                    order.setStatus("Refunded");
                    System.out.println("Refund Processed Successfully!");
                    orders.remove(order);
                    Customer customer = Customer.getCustomer(id);
                    customer.getOrderHistory().add(order);
                    customer.saveOrderHistory(order);
                    customer.setOrder(null);
                    return;
                }
            }
        }
        else{
            System.out.println("Order does not exist");
        }
    }

    public static boolean checkOrderId(String id){
        boolean flag = false;
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }
}
