package sumago.androidipt.b3expensemanagement.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.sql.SQLData;
import java.util.ArrayList;

import sumago.androidipt.b3expensemanagement.model.AnalyticsRecord;
import sumago.androidipt.b3expensemanagement.model.Expense;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenseDb";
    private static final String TABLE_NAME = "expense";
    private static final String COL_NAME = "name";
    private static final String COL_DATE = "date";
    private static final String COL_ID = "id";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_CATEGORY = "category";
    private static final String COL_NOTE = "note";

    private static final String CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+ "("+
            COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COL_NAME+" TEXT NOT NULL,"+
            COL_DATE+" TEXT,"+
            COL_CATEGORY+" TEXT,"+
            COL_AMOUNT+" REAL,"+
            COL_NOTE+" TEXT" +
            ");";

    /*
    * CREATE TABLE expenseDb(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, date TEXT, amount REAL, note TEXT);
    * */


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertExpense(Expense expense){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, expense.getName());
        values.put(COL_AMOUNT, expense.getAmount());
        values.put(COL_DATE, expense.getDate());
        values.put(COL_NOTE, expense.getNote());
        values.put(COL_CATEGORY, expense.getCategory());
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    public ArrayList<Expense> getAllExpenses(){
        ArrayList<Expense> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        //SELECT * FROM expense
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        /*
        * true/false
        * 0     1   2       3           4       5
        * id, name, date,   note,       catgory, amount
        * 1, hari, 21-6-24, somebnote, default, 123546
        * 2, pari,
        * */
        if(cursor.moveToNext()){
            do{
                Expense expense = new Expense();
                expense.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                expense.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)));
                expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE)));
                expense.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)));
                expense.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT)));
                list.add(expense);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public String getSumAmount() {
        String result = null;
        SQLiteDatabase db = getReadableDatabase();
        //SELECT SUM(COL_NAME) FROM TABLE_NAME
        /*
        * 0
        * 12356
        * */
        Cursor cursor = db.rawQuery("SELECT SUM("+COL_AMOUNT+") FROM "+TABLE_NAME, null);
        if(cursor.moveToFirst()){
            result = String.valueOf(cursor.getDouble(0));
        }
        cursor.close();
        db.close();
        return result;
    }

    public int deleteExpense(int id){
        SQLiteDatabase db = getWritableDatabase();
        /*
        * DELETE FROM TABLE_NAME WHERE COL_ID = ? AND COL_NAME = ?
        * new String[]{ String.valueOf(id), name }
        * */
        int result = db.delete(TABLE_NAME, COL_ID+"=?", new String[]{ String.valueOf(id) });
        db.close();
        return  result;
    }
    
    public boolean deleteAllExpenses(){
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(TABLE_NAME, null, null);
        db.close();
        return (result>0);
    }

    public Expense getExpenseById(int id){
        Expense expense = new Expense();
        SQLiteDatabase db = getReadableDatabase();
        /*
        * SELECT * FROM TABLE_NAME WHERE COL_ID = id
        * */
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL_ID+"=?", new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            expense.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
            expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
            expense.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)));
            expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE)));
            expense.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)));
            expense.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT)));
        }
        cursor.close();
        db.close();
        return expense;
    }

    public long updateExpense(int id, Expense expense) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, expense.getName());
        values.put(COL_AMOUNT, expense.getAmount());
        values.put(COL_DATE, expense.getDate());
        values.put(COL_NOTE, expense.getNote());
        values.put(COL_CATEGORY, expense.getCategory());
        /*
        * UPDATE TABLE_NAME SET COL_NAME = 'new_name' WHERE COL_ID=id
        * */
        long result = db.update(TABLE_NAME, values, COL_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }

    public ArrayList<String> getAllCategories() {
        //Set
        ArrayList<String> categoryNames = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT DISTINCT "+COL_CATEGORY+" FROM "+TABLE_NAME, null);
        if(cr.moveToFirst()){
            do{
                categoryNames.add(cr.getString(0));
            }while (cr.moveToNext());
        }
        cr.close();
        db.close();
        return categoryNames;
    }

    public ArrayList<AnalyticsRecord> getAnalytics() {
        /*
        * SELECT category, SUM(amount), AVG(amount), MIN(amount), MAX(amount) FROM expense ORDER BY category;
        * food,     300, 150, 20, 130
        * clothes, 1200, 150, 20, 130
        * */
        ArrayList<AnalyticsRecord> recordList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT category, SUM(amount), AVG(amount), MIN(amount), MAX(amount) FROM expense GROUP BY category;", null);
        if(cr.moveToFirst()){
            do{
                AnalyticsRecord record = new AnalyticsRecord();
                record.setName(cr.getString(0));
                record.setTotal(cr.getDouble(1));
                record.setAvg(cr.getDouble(2));
                record.setMin(cr.getDouble(3));
                record.setMax(cr.getDouble(4));
                recordList.add(record);
            }while (cr.moveToNext());
        }
        cr.close();
        db.close();
        return recordList;
    }

    public ArrayList<Expense> getFilteredExpenses(String selectedCategory, String startDate, String endDate) {
        ArrayList<Expense> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        //SELECT * FROM expense WHERE category=? AND date BETWEEN ? AND ?;
        Cursor cursor = db.rawQuery("SELECT * FROM expense WHERE category=? AND date BETWEEN ? AND ?;", new String[]{selectedCategory, startDate, endDate});
        if(cursor.moveToNext()){
            do{
                Expense expense = new Expense();
                expense.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                expense.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                expense.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)));
                expense.setNote(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTE)));
                expense.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)));
                expense.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_AMOUNT)));
                list.add(expense);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public double getTotalAmount(String selectedCategory, String startDate, String endDate) {
        double totalAmount = 0;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(amount) AS total FROM expense WHERE category=? AND date BETWEEN ? AND ?;",
                new String[]{selectedCategory, startDate, endDate});

        if(cursor.moveToFirst()){
            totalAmount = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));
        }
        cursor.close();
        db.close();
        return totalAmount;
    }

}
