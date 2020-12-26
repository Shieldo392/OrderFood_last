package payMent;

import java.util.List;

public class His_bill {
    private String maHD;
    private String ngayNhap;
    private List<ItemBill> bills;

    public His_bill(String maHD, String ngayNhap, List<ItemBill> bills) {
        this.maHD = maHD;
        this.ngayNhap = ngayNhap;
        this.bills = bills;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public List<ItemBill> getBills() {
        return bills;
    }

    public void setBills(List<ItemBill> bills) {
        this.bills = bills;
    }
    public void add_itemBill(ItemBill itemBill){
        this.bills.add(itemBill);
    }
    public int TongTien(){
        int tongTien = 0;
        for(int i = 0; i<bills.size(); i++){
            tongTien += bills.get(i).thanhTien();
        }
        return tongTien;
    }
}
