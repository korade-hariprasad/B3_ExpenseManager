package sumago.androidipt.b3expensemanagement.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.helpers.DbHelper;

public class MoreFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnDeleteAllExpenses).setOnClickListener(v->{
            if(new DbHelper(view.getContext()).deleteAllExpenses()){
                Toast.makeText(view.getContext(), "All Expenses deleted", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(view.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();;
        });
    }
}