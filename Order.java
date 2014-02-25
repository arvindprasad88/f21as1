import java.util.Comparator;

public class Order {
    private int seqNo;
    private int tableNo;
    private String orderName;
    private int qty;

    public Order(int seqNo, int tableNo, String orderName, int qty) {

        this.seqNo = seqNo;
        this.tableNo = tableNo;
        this.orderName = orderName;
        this.qty = qty;
    }

    // Get Sequence No.
    public int getSeqNo() {
        return seqNo;
    }

    // Get Table No.
    public int getTableNo() {
        return tableNo;
    }

    // Get Order Name
    public String getOrderName() {
        return orderName;
    }

    // Get Quantity
    public int getQty() {
        return qty;
    }

    // get full details of the orders
    public String getOrderDetails() {
        String detail = "\r\n ";
        // detail += String.format("%-5s", this.getTableNo());
        detail += String.format("%-30s", this.getOrderName());
        detail += String.format("%-5s", this.getQty());
        return detail;
    }

    // Sorts the order by table number
    public static class OrderComparatorTableNo implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            String o1TNum = Integer.toString(o1.tableNo);
            String o2TNum = Integer.toString(o2.tableNo);

            // ascending order
            return o1TNum.compareTo(o2TNum);
        }
    }

    // Sorts the order by table number
    public static class OrderComparatorOrderName implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            String o1OName = o1.orderName;
            String o2OName = o2.orderName;

            // ascending order
            return o1OName.compareTo(o2OName);
        }
    }
}
