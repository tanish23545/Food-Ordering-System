import java.util.ArrayList;

public class Order {
    private String id;
    private int amount;
    private ArrayList<Item> items;
    private String status;

    public Order(String id, ArrayList<Item> items, int amount) {
        this.id = id;
        this.items = new ArrayList<>(items);
        this.amount = amount;
        this.status = "Order Recieved";
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Total Amount: $" + amount + ", Status: " + status;
    }

    public String orderDetails() {
        return id + "," + items.toString() + "," + amount;
    }

    public static Order parseOrder(String line) {
        try {
            int firstCommaIndex = line.indexOf(",");
            String id = line.substring(0, firstCommaIndex).trim();
    
            int startBracket = line.indexOf("[");
            int endBracket = line.indexOf("]");
            String itemsStr = line.substring(startBracket + 1, endBracket).trim();
            ArrayList<Item> items = new ArrayList<>();
            String[] itemNames = itemsStr.split(", ");
            for (String itemName : itemNames) {
                Item item = Shop.getItem(itemName.trim());
                if (item != null) {
                    items.add(item);
                }
            }
    
            int amountStartIndex = endBracket + 2;
            int amount = Integer.parseInt(line.substring(amountStartIndex).trim());
    
            return new Order(id, items, amount);
    
        } catch (Exception e) {
            System.out.println("Error parsing order: " + line);
            e.printStackTrace();
            return null;
        }
    }
    
    
    


    public void printItems(){
        for (Item item : items) {
            System.out.println(item.getItem());
        }
        System.out.println("Total Amount: " + amount + ", Status: " + status);
    }

    public String getId() {
        return id;
    }
}