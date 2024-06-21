package sumago.androidipt.b3expensemanagement.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import sumago.androidipt.b3expensemanagement.activities.AddExpenseActivity;
import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.adapters.ExpenseListAdapter;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;
import sumago.androidipt.b3expensemanagement.interfaces.onItemDeleteListener;
import sumago.androidipt.b3expensemanagement.model.Expense;

public class HomeFragment extends Fragment implements onItemDeleteListener {

    RecyclerView recyclerView;
    TextView tvTotal;
    ExpenseListAdapter expenseListAdapter;
    DbHelper dbHelper;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvTotal = view.findViewById(R.id.tvTotal);
        context = view.getContext();
        dbHelper = new DbHelper(context);
        loadData(context);
    }

    private void loadData(Context context) {
        tvTotal.setText("Total: "+dbHelper.getSumAmount());
        expenseListAdapter = new ExpenseListAdapter(dbHelper.getAllExpenses(), this);
        recyclerView.setAdapter(expenseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(context);
    }

    @Override
    public void deleteExpense(int id) {
        int result = dbHelper.deleteExpense(id);
        if(result>0){
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            loadData(context);
        }
        else Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
    }
}