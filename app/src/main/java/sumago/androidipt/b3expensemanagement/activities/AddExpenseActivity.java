package sumago.androidipt.b3expensemanagement.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;
import sumago.androidipt.b3expensemanagement.model.Expense;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etName, etAmount, etNote, etCategory;
    TextView tvDate;
    Button btnAdd;
    DbHelper dbHelper;
    Calendar calendar;
    int day, month, year;
    String selectedDate, selectedCategory;
    Spinner spCategory;
    ArrayList<String> categoryNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etAmount = findViewById(R.id.etAmount);
        etName = findViewById(R.id.etName);
        tvDate = findViewById(R.id.etDate);
        etNote = findViewById(R.id.etNote);
        etCategory = findViewById(R.id.etCategory);
        spCategory = findViewById(R.id.spinnerCategory);
        btnAdd = findViewById(R.id.btnAdd);
        dbHelper = new DbHelper(this);
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        categoryNames = dbHelper.getAllCategories();
        categoryNames.add("Other");
        selectedDate = year+"-"+(month+1)+"-"+day;
        tvDate.setText(selectedDate);

        /*
        ArrayList<String> categoryNames = dbHelper.getAllCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoryNames);
        spCategory.setAdapter(adapter);
        */

        spCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryNames));
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(categoryNames.get(position).equals("Other")){
                    etCategory.setVisibility(View.VISIBLE);
                }
                else selectedCategory = categoryNames.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvDate.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            selectedDate = year+"-"+(month+1)+"-"+dayOfMonth;
                            tvDate.setText(selectedDate);
                        }
                    }, year, month, day);
            datePickerDialog.show();
        });

        btnAdd.setOnClickListener(v->{
            //insert data into database
            if(etCategory.getVisibility()==View.VISIBLE){   selectedCategory = etCategory.getText().toString(); }
            double amount = Double.parseDouble(etAmount.getText().toString());
            String name = etName.getText().toString(), note = etNote.getText().toString();

            long result = dbHelper.insertExpense(new Expense(name, selectedDate, note, amount, selectedCategory));

            if(result>0) {
                Toast.makeText(this, "Expense Inserted Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
            else Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        });

    }
}