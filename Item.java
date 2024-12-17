import java.util.ArrayList;
public class Item {
    private String name;
    private int price;
    private String category;
    private boolean available;
    private ArrayList<String> keywords = new ArrayList<>();
    private ArrayList<String> reviews = new ArrayList<>();


    public Item(String name, int price, String category, boolean available, ArrayList<String> keywords) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
    
    public boolean getAvailable() {
        return available;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    @Override
    public String toString(){
        return name;
    }

    public String getItem() {
        return "-"+name+", Price: "+price+", Available: "+available;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
