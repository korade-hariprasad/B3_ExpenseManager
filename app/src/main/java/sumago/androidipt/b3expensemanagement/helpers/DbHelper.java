package sumago.androidipt.b3expensemanagement.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
}
