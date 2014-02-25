import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DishList {

    private ArrayList<Dish> dishList;
    // create an empty ArrayList
    public DishList() {
        //dishList = new ArrayList<Dish>();
    	dishList = new ArrayList<Dish>();
    }

    public void readDishFile(String filename) {
        try {
            File f = new File(filename);
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                // read first line and process it
                String inputLine = scanner.nextLine();
                if (inputLine.length() != 0) 
                { // ignored if blank line
                    processLine(inputLine);
                }
            }
        }

        // if the file is not found, stop with system exit
        catch (FileNotFoundException fnf) {
            System.out.println(filename + " not found ");
            System.exit(0);
        }
    }

    /*
     * Processes line, extracts data, creates dish object and adds to list
     * Checks for non-numeric and missing items Will still crash if name entered
     * without a space
     *
     * @param line the line to be processed
     */
    private void processLine(String line) {
        try {
            String parts[] = line.split(",");
            String category = parts[0];
            String dishName = parts[1];
            double price = Integer.parseInt(parts[2]);
            Dish d = new Dish(category, dishName, price);

            boolean ok = this.add(d);
            if (!ok) {
                System.out.println("Duplicate entry " + d.getDishName());
            }
        }
        // this catches trying to convert a String to an integer
        catch (NumberFormatException nfe) {
            String error = "Number conversion error in '" + line + "'  - "
                    + nfe.getMessage();
            System.out.println(error);
        }

        // this catches missing items if only one or two items
        // other omissions will result in other errors
        catch (ArrayIndexOutOfBoundsException air) {
            String error = "Not enough items in  : '" + line
                    + "' index position : " + air.getMessage();
            System.out.println(error);
        }

    }

    // adding a dish into the list and checking for duplicity
    public boolean add(Dish d) {
        String name = d.getDishName();
        Dish inList = this.findDishName(name);
        if (inList == null) {
            dishList.add(d);
            return true;
        }
        return false;
    }

    /*
     * returns the dish object with a specified name demonstrates searching
     * through the array and stopping by returning when a match is found
     */
    public Dish findDishName(String name) {
        for (Dish d : dishList) {
            if (d.getDishName().equals(name)) {
                return d;
            }
        }
        return null;
    }

    public String getDishNotOrdered(OrderList olist) {
        String report = "\r\n\r\nDISHES NOT ORDERED: \r\n";
        report += "--------------------- \r\n";
        for (Dish d : dishList) {
            if (olist.findOrderName(d.getDishName()) == null) {
                report += "\r\n" + d.getDishName().toUpperCase() + "\r\n";
            }
        }
        return report;
    }

    public String getMenuList() {
        String report = "MENU\n";
        report += "=========\n";

        Collections.sort(dishList, new Dish.DishComparatorCategory());
        String category = "";

        for (Dish d : dishList) 
        {
            if (!category.equals(d.getCategory()))
            {
                category = d.getCategory();
                report += "\n" + category + "\n";
            }
            report += d.getDishDetails();
        }
        return report;
    }

    public String getMostExpDish() {
        double MaxPrice = 0;
        for (Dish d : dishList) {
            if (d.getDishPrice() > MaxPrice)
                MaxPrice = d.getDishPrice();
        }
        String expDish = " ";
        for (Dish d : dishList) {
            if (d.getDishPrice() == MaxPrice) {
                expDish += d.getDishName().toUpperCase() + "  - " + MaxPrice
                        + "\r\n";
            }
        }
        return expDish;
    }

    public String getCheapestDish() {
        double MinPrice = 100;
        for (Dish d : dishList) {
            if (d.getCategory() != "Drinks") {
                if (d.getDishPrice() < MinPrice)
                    MinPrice = d.getDishPrice();
            }
        }
        String cDish = " ";
        for (Dish d : dishList) {
            if (d.getDishPrice() == MinPrice) {
                cDish += d.getDishName().toUpperCase() + "  - " + MinPrice
                        + "\r\n";
            }
        }
        return cDish;

    }

    public void writeToFile(String filename, String report) {
        FileWriter fw;
        try {
            fw = new FileWriter(filename);
            fw.write(report);
            fw.close();
        }
        // message and stop if file not found
        catch (FileNotFoundException fnf) {
            System.out.println(filename + " not found ");
            System.exit(0);
        }
        // stack trace here because we don't expect to come here
        catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isEquals(String category1) {
        for (Dish d : dishList) {
            if (d.getCategory() == category1)
                ;
            return true;
        }
        return false;
    }

    public double getTotalPrice(int qty, String name) {
        double totalprice = 0.0;
        for (Dish d : dishList) {
            if (d.getDishName().equals(name)) {
                totalprice = qty * d.getDishPrice();
            }
        }

        return totalprice;
    }

    public double findDishAmount(String name) {
        double amount = 0.0;
        for (Dish d : dishList) {
            if (d.getDishName().equals(name)) {
                amount = d.getDishPrice();
            }
        }
        return amount;
    }

}
