package com.example.bookhouse;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookhouse.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;
    SharedPreferences sharepef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharepef = getPreferences(MODE_PRIVATE);
        FragmentTransaction transactio = getSupportFragmentManager().beginTransaction();
        transactio.replace(R.id.frame, new BookListFragment());
        firebaseAuth = FirebaseAuth.getInstance();
        transactio.commit();
        saveme("Created By Antor Sarker");
        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        transaction.replace(R.id.frame, new BookListFragment());
                        break;
                    case 1:
                        transaction.replace(R.id.frame, new ProfileFragment());
                        break;
                    case 2:
                        transaction.replace(R.id.frame, new StudentRecordFragment());
                        break;
                    case 3:
                        transaction.replace(R.id.frame, new FriendsFragment());
                        break;
                }
                transaction.commit();
                return true;
            }
        });
    }

    public void saveme(String name) {
        SharedPreferences.Editor editor = sharepef.edit();
        editor.putString("check", name);
        editor.apply();
    }
}