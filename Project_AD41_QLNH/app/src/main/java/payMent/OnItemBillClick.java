package payMent;

import Bill.ItemBill;

public interface OnItemBillClick {
    void onButtonAddClick(ItemBill bill);
    void onButtonMinusClick(ItemBill bill);
}
