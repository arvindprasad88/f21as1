import javax.swing.JOptionPane;


public class Manager 
{
	/* dishlist and orderlist are instantiating and text files are read 
		and report of the dish details and order details are written
			in a text file*/
	public void run() 
	{
		DishList dlist = new DishList();
        OrderList olist = new OrderList();
        olist.readOrderFile("files/orderlist.csv");
        dlist.readDishFile("files/dishlist.csv");
        String ordersReport = olist.getTableSummary(dlist);
        ordersReport += olist.getDishFrequency() + dlist.getDishNotOrdered(olist)
                		+ olist.getStatistics(dlist);
        String report = dlist.getMenuList();
        String finalReport = ordersReport + "\n\n" + report;
        dlist.writeToFile("files/FinalReport.txt", finalReport);

      // GUI for entering table number to get the order details and bill amount
        int no = 0;
        for (int i = 0; i < 3; i++) {
            String input = JOptionPane.showInputDialog(null,
                    "Please enter table no to view order.");
            boolean success = true;
            try {
                no = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                success = false;
                JOptionPane.showMessageDialog(null,
                        "Incorrect value. Please enter the integer value.");
            }
            if (success) {
                String tableSummary = olist.getTableSummaryGUI(dlist, no);
                JOptionPane.showMessageDialog(null, tableSummary, "Bill",
                        JOptionPane.PLAIN_MESSAGE);
                break;
            }
        }
    }
}
