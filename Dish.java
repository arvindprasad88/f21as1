	import java.util.Comparator;
	
public class Dish  {

    // instance variable for swimmer's name
    private String category;
    private String dishName;
    private double price;

    /*
     * Creates a Dish object with values specified in the parameters
     *
     * @param category Category in which the dish falls under
     *
     * @param price price of the dish
     *
     * @param dishName Name of the dish
     */

    // constructor to create object with first, middle and last name
    public Dish(String category, String dishName, double price) {

        this.category = category;
        this.dishName = dishName;
        this.price = price;
    }

    // returns the category of the dish
    public String getCategory() {
        return category;
    }

    // returns the name of the dish
    public String getDishName() {
        return dishName;
    }

    // returns the price of the Dish
    public double getDishPrice() {
        return price;
    }

    // get full details of the menu
    public String getDishDetails() {
        String detail = "";
        // detail = String.format("\r\n%-10s", this.getCategory());
        detail += String.format("\t%-35s", this.getDishName());
        detail += String.format("%-15s", this.getDishPrice());
        detail += "\n";
        return detail;
    }

    // sorts the dish by category
    public static class DishComparatorCategory implements Comparator<Dish> {
        @Override
        public int compare(Dish d1, Dish d2) {
            String d1cat = d1.category.toUpperCase();
            String d2cat = d2.category.toUpperCase();

            // ascending order
            int compare = d1cat.compareTo(d2cat);
            if(compare == 0)
            {
                // ascending order    
                compare = d1.dishName.toUpperCase().compareTo(
                		d2.dishName.toUpperCase());
            }
            
            return compare;

        }
    }

    // sorts the dish by category
    public static class DishComparatorName implements Comparator<Dish>
    {
        @Override
        public int compare(Dish d1, Dish d2) {
            String d1name = d1.dishName.toUpperCase();
            String d2name = d2.dishName.toUpperCase();
            // if(d1.getCategory() == d2.getCategory())
            // ascending order
            
            return d1name.compareTo(d2name);
            
            // else return -1;
        }
    }
}
