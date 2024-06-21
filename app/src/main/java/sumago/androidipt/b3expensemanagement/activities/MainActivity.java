package sumago.androidipt.b3expensemanagement.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import sumago.androidipt.b3expensemanagement.R;
import sumago.androidipt.b3expensemanagement.fragments.AnalyticsFragment;
import sumago.androidipt.b3expensemanagement.fragments.HomeFragment;
import sumago.androidipt.b3expensemanagement.fragments.MoreFragment;
import sumago.androidipt.b3expensemanagement.fragments.OptionsFragment;

public class MainActivity extends AppCompatActivity {

    FrameLayout fragmentContainer;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.fragmentContainer);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        loadFragment(new HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_home) loadFragment(new HomeFragment());
                if(item.getItemId() == R.id.action_analytics) loadFragment(new AnalyticsFragment());
                if(item.getItemId() == R.id.action_options) loadFragment(new OptionsFragment());
                if(item.getItemId() == R.id.action_more) loadFragment(new MoreFragment());
                return true;
            }
        });

        btnAdd = findViewById(R.id.btnToAddExpense);
        btnAdd.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, AddExpenseActivity.class));
        });
    }

    private void loadFragment(Fragment fragment) {
        //to load fragments inside the container
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

}