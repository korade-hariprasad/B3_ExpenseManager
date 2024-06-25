package sumago.androidipt.b3expensemanagement.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.adapters.AnalyticsListAdapter;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;

public class AnalyticsFragment extends Fragment {

    RecyclerView recyclerViewAnalytics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_analytics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewAnalytics = view.findViewById(R.id.recyclerViewAnalytics);
        loadData();
    }

    private void loadData() {
        recyclerViewAnalytics.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewAnalytics.setAdapter(new AnalyticsListAdapter(new DbHelper(getContext()).getAnalytics()));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}