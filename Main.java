import java.util.Scanner;
public class Main{
    static Scanner scan = new Scanner(System.in);

    public static void welcome(){
        while (true) {
            System.out.println("Welcome to Byte Me! Canteen System");
            System.out.println("Please select your role to continue:");
            System.out.println("1. Customer");
            System.out.println("2. Admin");
            System.out.println("0. Exit");
            System.out.print("Your choice --> ");
            int choice = scan.nextInt();
            scan.nextLine();
            if (choice == 1) {
                System.out.println();
                Customer.login();
            }   
            else if (choice == 2){
                System.out.println();
                Admin.login();
            }
            else if (choice == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        welcome();
    }
}
