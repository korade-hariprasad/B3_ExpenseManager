package sumago.androidipt.b3expensemanagement.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;
import sumago.androidipt.b3expensemanagement.model.Expense;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etName, etDate, etAmount, etNote;
    Button btnAdd;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etAmount = findViewById(R.id.etAmount);
        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etNote = findViewById(R.id.etNote);
        btnAdd = findViewById(R.id.btnAdd);
        dbHelper = new DbHelper(this);

        btnAdd.setOnClickListener(v->{
            //insert data into database
            double amount = Double.parseDouble(etAmount.getText().toString());
            String name = etName.getText().toString(),
                    note = etNote.getText().toString(),
                    date = "20-06-2024";

            long result = dbHelper.insertExpense(new Expense(name, date, note, amount));

            if(result>0)
                Toast.makeText(this, "Expense Inserted Successfully", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        });

    }
}