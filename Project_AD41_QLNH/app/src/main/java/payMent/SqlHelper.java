package payMent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SqlHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DeltailBill.db";
    private final String DB_TABLE = "Bill";
    private final String DB_TABLE_HIS = "Bill_his";
    private final String DB_TABLE_USER = "User_Detail";

    private static int DB_VERSION = 1;


    private String detail_ID = "id";
    private String detail_Name = "Ten";
    private String detail_Count = "SoLuong";
    private String detail_price = "GiaBan";
    private String detail_Bill_ID = "bill_id";
    private String detail_Date = "date";

    private String user_ID = "id";
    private String user_Name = "name";
    private String user_Addres = "address";
    private String user_birthDay = "birthday";
    private String user_Phone = "phone";


    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public SqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + DB_TABLE + "(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Ten TEXT," +
                "GiaBan INTEGER," +
                "SoLuong INTEGER," +
                "bill_id INTEGER," +
                "date TEXT)";


        String sql_hisBill = "CREATE TABLE " + DB_TABLE_HIS + "(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "Ten TEXT," +
                "GiaBan INTEGER," +
                "SoLuong INTEGER," +
                "bill_id INTEGER," +
                "date TEXT)";

        String sql_user = "CREATE TABLE "+ DB_TABLE_USER +"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "birthday TEXT," +
                "phone TEXT," +
                "address TEXT)";

        db.execSQL(sql_user);
        db.execSQL(sql);
        db.execSQL(sql_hisBill);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        }
    }

    public void insert_bill(ItemBill bill) {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String day = sdf.format(date);

        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(detail_Name, bill.getName());
        contentValues.put(detail_Count, bill.getCount());
        contentValues.put(detail_price, bill.getPrice());
        contentValues.put(detail_Bill_ID, bill.getId_bill());
        contentValues.put(detail_Date, day);


        sqLiteDatabase.insert(DB_TABLE, null, contentValues);
    }

    public void insert_list_bill_his(List<ItemBill> list) {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String day = sdf.format(date);

        for(int i =0; i<list.size(); i++){
            sqLiteDatabase = getWritableDatabase();
            contentValues = new ContentValues();
            ItemBill bill = list.get(i);

            contentValues.put(detail_Name, bill.getName());
            contentValues.put(detail_Count, bill.getCount());
            contentValues.put(detail_price, bill.getPrice());
            contentValues.put(detail_Bill_ID, bill.getId_bill());
            contentValues.put(detail_Date, day);
        }


        sqLiteDatabase.insert(DB_TABLE_HIS, null, contentValues);
    }



    public void update_bill(ItemBill bill) {

        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(detail_Name, bill.getName());
        contentValues.put(detail_Count, bill.getCount());
        contentValues.put(detail_price, bill.getPrice());
        contentValues.put(detail_Bill_ID, bill.getId_bill());

        sqLiteDatabase.update(DB_TABLE, contentValues, "Ten = ?", new String[]{bill.getName()});
    }

    public List<ItemBill> getList() {
        List<ItemBill> list = new ArrayList<ItemBill>();
        sqLiteDatabase = getReadableDatabase();
        String sql = "Select * from " + DB_TABLE;
        cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                int id = cursor.getInt(cursor.getColumnIndex(detail_ID));
                String tenSP = cursor.getString(cursor.getColumnIndex(detail_Name));
                int soLuong = cursor.getInt(cursor.getColumnIndex(detail_Count));
                int gia = cursor.getInt(cursor.getColumnIndex(detail_price));
                int id_bill = cursor.getInt(cursor.getColumnIndex(detail_Bill_ID));

                ItemBill itemBill = new ItemBill(id, tenSP, gia, soLuong, id_bill);
                list.add(itemBill);
            } while (cursor.moveToNext());
        }
        return list;
    }


    public void deleteAll() {
        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DB_TABLE, null, null);
    }

    public List<User_pro> getList_user(){
        List<User_pro> list = new ArrayList<User_pro>();
        sqLiteDatabase = getReadableDatabase();
        String sql = "Select * from " + DB_TABLE_USER;
        cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                int id = cursor.getInt(cursor.getColumnIndex(user_ID));
                String name = cursor.getString(cursor.getColumnIndex(user_Name));
                String birth = cursor.getString(cursor.getColumnIndex(user_birthDay));
                String phone = cursor.getString(cursor.getColumnIndex(user_Phone));
                String addr = cursor.getString(cursor.getColumnIndex(user_Addres));

               User_pro user_pro = new User_pro(id, name, birth, phone, addr);
               list.add(user_pro);
            } while (cursor.moveToNext());
        }
        return list;
    }
    public void update_user(User_pro user) {

        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(user_Name, user.getName());
        contentValues.put(user_Phone, user.getPhone());
        contentValues.put(user_birthDay, user.getBirthday());
        contentValues.put(user_Addres, user.getAddress());

        sqLiteDatabase.update(DB_TABLE_USER, contentValues, "id = ?", new String[]{String.valueOf(user.getId())});
    }
    public void insert_user(User_pro user) {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String day = sdf.format(date);

        sqLiteDatabase = getWritableDatabase();
        contentValues = new ContentValues();

        contentValues.put(user_Name, user.getName());
        contentValues.put(user_Phone, user.getPhone());
        contentValues.put(user_birthDay, user.getBirthday());
        contentValues.put(user_ID, user.getAddress());

        sqLiteDatabase.insert(DB_TABLE_USER, null, contentValues);
    }


}
