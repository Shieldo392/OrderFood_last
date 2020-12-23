package payMent;

public class ItemBill {
    private int id;
    private String name;
    private int price;
    private int count;
    private int id_bill;

    public ItemBill(int id, String name, int price, int count, int id_bill) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.id_bill = id_bill;
    }

    public int getId_bill() {
        return id_bill;
    }

    public void setId_bill(int id_bill) {
        this.id_bill = id_bill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int thanhTien(){
        return count*price;
    }
}
