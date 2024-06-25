package sumago.androidipt.b3expensemanagement.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.adapters.FilterListAdapter;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;
import sumago.androidipt.b3expensemanagement.interfaces.SetTotalFilterInterface;
import sumago.androidipt.b3expensemanagement.interfaces.onItemDeleteListener;

public class FilterFragment extends Fragment implements onItemDeleteListener, SetTotalFilterInterface {

    Spinner spinnerCategory;
    TextView tvStartDate, tvEndDate, tvSum;
    RecyclerView recyclerViewFilter;
    FloatingActionButton fabFilter;
    DbHelper dbHelper;
    Calendar calendar;
    int day, month, year;
    String selectedCategory, startDate, endDate;
    ArrayList<String> categoryNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        tvStartDate = view.findViewById(R.id.tvStartDate);
        tvEndDate = view.findViewById(R.id.tvEndDate);
        tvSum = view.findViewById(R.id.tvSum);
        recyclerViewFilter = view.findViewById(R.id.recyclerViewFilter);
        fabFilter = view.findViewById(R.id.fabFilter);
        dbHelper = new DbHelper(getContext());
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        categoryNames = dbHelper.getAllCategories();

        startDate = year+"-"+(month+1)+"-"+day;
        endDate = year+"-"+(month+1)+"-"+month;
        tvStartDate.setText(startDate);
        tvEndDate.setText(startDate);

        recyclerViewFilter.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        spinnerCategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, categoryNames));
        selectedCategory = categoryNames.get(0);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoryNames.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvStartDate.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            startDate = year+"-"+(month+1)+"-"+dayOfMonth;
                            tvStartDate.setText(startDate);
                        }
                    }, year, month, day);
            datePickerDialog.show();
        });

        tvEndDate.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            endDate = year+"-"+(month+1)+"-"+dayOfMonth;
                            tvEndDate.setText(endDate);
                        }
                    }, year, month, day);
            datePickerDialog.show();
        });

        fabFilter.setOnClickListener(v->{
            loadData();
        });
    }

    public void loadData(){
        recyclerViewFilter.setAdapter(new FilterListAdapter(dbHelper.getFilteredExpenses(selectedCategory, startDate, endDate), this, this));
    }

    @Override
    public void deleteExpense(int id) {
        int result = dbHelper.deleteExpense(id);
        if(result>0){
            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            loadData();
        }
        else Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTotal(double total) {
        tvSum.setText(String.valueOf(total));
    }
}