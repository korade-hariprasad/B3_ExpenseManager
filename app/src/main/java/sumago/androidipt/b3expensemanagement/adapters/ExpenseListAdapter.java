package sumago.androidipt.b3expensemanagement.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.activities.UpdateInfoActivity;
import sumago.androidipt.b3expensemanagement.activities.ExpenseInfoActivity;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;
import sumago.androidipt.b3expensemanagement.interfaces.onItemDeleteListener;
import sumago.androidipt.b3expensemanagement.model.Expense;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ViewHolder> {

    ArrayList<Expense> expenseList;
    TextView tvName, tvAmount;
    ImageButton btnInfo, btnEdit, btnDelete;
    DbHelper dbHelper;
    onItemDeleteListener onItemDeleteListener;

    public ExpenseListAdapter(ArrayList<Expense> expenseList, onItemDeleteListener onItemDeleteListener){
        this.expenseList = expenseList;
        this.onItemDeleteListener = onItemDeleteListener;
    }

    @NonNull
    @Override
    public ExpenseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout the ui
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListAdapter.ViewHolder holder, int position) {
        //logic on one item
        tvName.setText(expenseList.get(position).getName());
        tvAmount.setText(String.valueOf(expenseList.get(position).getAmount()));

        btnDelete.setOnClickListener(v -> {
            //code to delete the item
            onItemDeleteListener.deleteExpense(expenseList.get(position).getId());
            /*
            int result = dbHelper.deleteExpense(expenseList.get(position).getId());
            if(result>0) Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            else Toast.makeText(v.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            */
        });

        btnEdit.setOnClickListener(v->{
            //code to edit the item
            Intent i = new Intent(v.getContext(), UpdateInfoActivity.class);
            i.putExtra("id", expenseList.get(position).getId());
            holder.itemView.getContext().startActivity(i);
        });

        btnInfo.setOnClickListener(v->{
            //code to get info of the item
            Intent i = new Intent(v.getContext(), ExpenseInfoActivity.class);
            i.putExtra("id", expenseList.get(position).getId());
            holder.itemView.getContext().startActivity(i);
            //Toast.makeText(v.getContext(), expenseList.get(position).getNote(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnInfo = itemView.findViewById(R.id.btnInfo);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            tvName = itemView.findViewById(R.id.tvExpenseName);
            tvAmount = itemView.findViewById(R.id.tvExpenseAmount);
            dbHelper = new DbHelper(itemView.getContext());
        }
    }
}
