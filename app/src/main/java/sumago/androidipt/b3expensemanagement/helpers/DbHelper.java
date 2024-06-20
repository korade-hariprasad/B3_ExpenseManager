package sumago.androidipt.b3expensemanagement.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import sumago.androidipt.b3expensemanagement.model.Expense;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenseDb";
    private static final String TABLE_NAME = "expense";
    private static final String COL_NAME = "name";
    private static final String COL_DATE = "date";
    private static final String COL_ID = "id";
    private static final String COL_AMOUNT = "amount";
    private static final String COL_NOTE = "note";

    private static final String CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+ "("+
            COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COL_NAME+" TEXT NOT NULL,"+
            COL_DATE+" TEXT,"+
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
        return db.insert(TABLE_NAME, null, values);
    }
}
