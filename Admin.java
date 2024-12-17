import java.util.Scanner;
public class Admin {
    private static String pass = "0000";

    static Scanner scan = new Scanner(System.in);

    public static void menu(){
        while (true) {
            System.out.println("1. Manage menu");
            System.out.println("2. Manage Orders");
            System.out.println("3. Generate Sales Report");
            System.out.println("0. Logout");
            System.out.print("Your choice --> ");
            int choice = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    manageMenu();
                    break;
                case 2:
                    manageOrders();
                    break;
                case 3:
                    Shop.generateSalesReport();
                    break;
                
                default:
                    return;
            }
        }
        
    }

    public static void login() {

        while (true) {
            System.out.print("Enter password: ");
            String password = scan.nextLine();

            if (Admin.pass.equals(password)) {
                menu();
                return;
            }
            System.out.println("Wrong password");
            System.out.println("1. Try again?\n0. Exit (Press any other key)");
            System.out.print("Your choice --> ");
            int choice = scan.nextInt();
            scan.nextLine();

            if (choice != 1) {
                Main.welcome();
                break;
            }
        }
    }

    public static void manageMenu(){
        while (true) {
            System.out.println("1. View menu");
            System.out.println("2. Add new item");
            System.out.println("3. Update existing item");
            System.out.println("4. Remove item");
            System.out.println("0. Main Menu");
            System.out.print("Your choice --> ");
            int choice = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    Shop.viewAllItems();
                    break;
                case 2:
                    Shop.addItem();
                    break;
                case 3:
                    Shop.updateItem();
                    break;
                case 4:
                    Shop.removeItem();
                    break;
                case 0:
                    return;
            
                default:
                    return;
            }
        }
    }

    public String getPass() {
        return pass;
    }

    public static void manageOrders(){
        while (true) {
            System.out.println("1. View Pending Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Process Refunds");
            System.out.println("0. Main Menu");
            System.out.print("Your choice --> ");
            int choice = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    Shop.viewOrders();
                    break;
                case 2:
                    Shop.updateOrderStatus();
                    break;
                case 3:
                   Shop.processRefunds();
                    break;
                
                default:
                    return;
            }
        }
    }
    
}
