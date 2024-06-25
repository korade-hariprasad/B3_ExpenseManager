package sumago.androidipt.b3expensemanagement.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.model.AnalyticsRecord;

public class AnalyticsListAdapter extends RecyclerView.Adapter<AnalyticsListAdapter.ViewHolder> {

    ArrayList<AnalyticsRecord> list;
    TextView tvCategoryName, tvCategoryMin, tvCategoryMax, tvCategoryAvg, tvCategoryTotal;

    public AnalyticsListAdapter(ArrayList<AnalyticsRecord> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AnalyticsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.analytics_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnalyticsListAdapter.ViewHolder holder, int position) {
        tvCategoryName.setText(list.get(position).getName());
        tvCategoryMin.setText(String.valueOf(list.get(position).getMin()));
        tvCategoryMax.setText(String.valueOf(list.get(position).getMax()));
        tvCategoryAvg.setText(String.valueOf(list.get(position).getAvg()));
        tvCategoryTotal.setText(String.valueOf(list.get(position).getTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvCategoryMin = itemView.findViewById(R.id.tvCategoryMin);
            tvCategoryMax = itemView.findViewById(R.id.tvCategoryMax);
            tvCategoryAvg = itemView.findViewById(R.id.tvCategoryAvg);
            tvCategoryTotal = itemView.findViewById(R.id.tvCategoryTotal);
        }
    }
}