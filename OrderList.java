import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class OrderList {
    String report = "";
    private ArrayList<Order> orderList;

    // Create an empty arrayList
    public OrderList() {
        orderList = new ArrayList<Order>();
    }

    Concordance concord = new Concordance();

    // Populate the array list with Order details from a text file
    public void readOrderFile(String file) 
    {

        try {
            File f1 = new File(file);
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(f1);
            while (scanner.hasNextLine()) {
                // Read line and process it
                String inputLine = scanner.nextLine();
                if (inputLine.length() != 0) {
                    processLine(inputLine);
                }
            }
        }
        // Catch exception if file not found
        catch (FileNotFoundException fnf) {
            System.out.println(file + " not found ");
        }
    }

    /**
     * Process line, extract data, create Order object and add to list
     *
     * @param line
     *            string
     */
    private void processLine(String line) {
        try {
            String parts[] = line.split(",");
            int seqNo = Integer.parseInt(parts[0]);
            int tableNo = Integer.parseInt(parts[1]);
            String orderName = parts[2];
            int qty = Integer.parseInt(parts[3]);

            Order o = new Order(seqNo, tableNo, orderName, qty);
            // Throw error if duplicate entry found.
            boolean ok = this.add(o);
            if (!ok) {
                System.out.println("Duplicate entry " + o.getSeqNo());
            }
        }

        // This catches trying to convert a String to an integer
        catch (NumberFormatException e) {
            String error = "Number conversion error in '" + line + "'  - "
                    + e.getMessage();
            System.out.println(error);
        }
    }

    // return the table summary of all tables with the bill amount
    public String getTableSummary(DishList dlist) {
        String report = "";
        Collections.sort(orderList, new Order.OrderComparatorTableNo());
        for (int i = 1; i <= this.getTotaltables(); i++) {
            report += getTableSummaryGUI(dlist, i);
        }

        return report;
    }
    public String getHighestbillamt(DishList dlist)
    {
    	double bill =0.0;
    	int tableno = 0;
    	for (int i = 1; i <= this.getTotaltables(); i++)
    	{
    		double billamt = 0.0, billamt1=0.0;
    		for (Order o : orderList) 
            {
                if (o.getTableNo() == i) 
                {
                	billamt+= dlist.getTotalPrice(o.getQty(), o.getOrderName());
                	billamt1= billamt - this.getDiscount(billamt);
                    if( billamt1 >bill)
                    {
                    	bill= billamt1;
                    	tableno = o.getTableNo();
                    }
                }
            }
    	} return report+= "HIGHEST BILL AMOUNT IS FOR THE DAY " + bill + " TABLE NO : " + tableno;
    }
    
    public double getTotalRevenueOneDay(DishList dlist)
    {
    	double total = 0.0;
    	for (int i = 1; i <= this.getTotaltables(); i++)
    	{
    		double total1 = 0.0, billamt=0.0;
	    	for (Order o : orderList) 
	        {
                if (o.getTableNo() == i) 
                {
                    billamt += dlist.getTotalPrice(o.getQty(), o.getOrderName());   
                } 
	        }
	    	total1 = billamt - this.getDiscount(billamt);
	    	total += total1;
    	}
    	return total;
    }

    public String getTableSummaryGUI(DishList dlist, int no) {
        double billamt = 0.0;
        boolean tableFound = false;
        String report = "\r\n\r\n TABLE SUMMARY FOR TABLE NO. " + no + ":"
                + "\r\n";
        for (Order o : orderList) 
        {
            if (o.getTableNo() == no) 
            {
                billamt += dlist.getTotalPrice(o.getQty(), o.getOrderName());
                report += o.getOrderDetails()
                        + "   "
                        + String.format("%-4s", o.getQty())
                        + " * "
                        + String.format("%-5s",
                                dlist.findDishAmount(o.getOrderName()))
                        + "  =  "
                        + dlist.getTotalPrice(o.getQty(), o.getOrderName());
                tableFound = true;
            }
        }
        if (tableFound) {
            report += "\r\n =======";
            report += "\r\n Total bill amount = " + billamt;
            report += "\r\n Discount = " + getDiscount(billamt);
            report += "\r\n Discounted total = "
                    + (billamt - getDiscount(billamt));
            report += "\r\n --------------------------------------------------------------";
        } else {
            report = "Table Not Found, Please enter the number between 1 to "
                    + this.getTotaltables();
        }

        return report;
    }

    // returns the no of times a particular dish was ordered
    public String getDishFrequency() {
        String report = "";
        report += "\n\r" + concord.process("files/orderlist.csv");
        return report;
    }

    // returns the total number of tables in the restaurant
    public int getTotaltables() {
        int totaltables = 1;
        for (Order o : orderList) {
            if (o.getTableNo() > totaltables) {
                totaltables = o.getTableNo();

            }
        }
        return totaltables;
    }

    // returns the names of the dish that were not ordered
    public String getDishNotOrdered(DishList dlist) {
        String report = "DISHES NOT ORDERED: \r\n";
        report += "--------------------- \n";
        for (Order o : orderList) {
            if (dlist.findDishName(o.getOrderName()) == null) {
                report += o.getOrderName().toUpperCase() + "\r\n";
            }
        }
        return report;
    }

    /*
     * returns the dish object with a specified name demonstrates searching
     * through the array and stopping by returning when a match is found
     */
    public Order findOrderName(String name) {
        for (Order o : orderList) {
            if (o.getOrderName().equals(name)) {
                return o;
            }
        }
        return null;
    }

    // returns few statistics of the restaurant
    public String getStatistics(DishList dl) {
        report += "\r\n\r\nSTATISTICS OF RESTAURANT \r\n";
        report += "---------------------------\r\n";
        report += "THE MOST EXPENSIVE DISH IS" + dl.getMostExpDish();
        report += "THE CHEAPEST DISH IS" + dl.getCheapestDish();
        report += this.getHighestbillamt(dl)+ "\r\n";
        report += "TOTAL REVENUE FOR THE DAY : "+ this.getTotalRevenueOneDay(dl);
        return report;
    }

    // return a discount if the bill is above 150AED
    public double getDiscount(double bill) {
        double disc = 0.0;
        if (bill >= 80 && bill < 120) {
            disc = 0.05 * bill;
        } else if (bill >= 120 && bill < 150) {
            disc = 0.1 * bill;
        } else if (bill >= 150) {
            disc = 0.12 * bill;
        }
        return disc;
    }

    // returns the order with a specified sequence no.
    public Order findBySeqNo(int seqNo) {
        for (Order o : orderList) {
            if (Integer.toString(o.getSeqNo()).equals(Integer.toString(seqNo))) {
                return o;
            }
        }
        return null;
    }

    public boolean add(Order o) {
        int seqNo = o.getSeqNo();
        Order inList = this.findBySeqNo(seqNo);
        if (inList == null) {
            orderList.add(o);
            return true;
        }
        return false;
    }
}
