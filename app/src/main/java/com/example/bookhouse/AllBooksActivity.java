package com.example.bookhouse;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookhouse.databinding.ActivityAllBooksBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class AllBooksActivity extends AppCompatActivity {
    ActivityAllBooksBinding binding;
    FirebaseFirestore firestore;
    ArrayList<Booksmode> list;
    FirebaseAuth firebaseAuth;
    BooksAdapter adapter;
    String collection;
    int c = 0;
    int numofreq;
    String phone,regi,department,email,name;
    SharedClass sharedClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);
        binding = ActivityAllBooksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        sharedClass = new SharedClass(AllBooksActivity.this);
        collection = getIntent().getStringExtra("catagory");
        adapter = new BooksAdapter(this, list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, RecyclerView.VERTICAL, false);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(gridLayoutManager);

        HashMap<String, String> map = sharedClass.getuserdetails();
         name = map.get(SharedClass.NAME);
         department = map.get(SharedClass.DEPARTMENT);
         regi = map.get(SharedClass.REG_NUM);
         phone = map.get(SharedClass.PHONE_NUM);
         email = map.get(SharedClass.EMAIL);
        firestore.collection(collection)
                .whereEqualTo("available", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Booksmode booksmode = snapshot.toObject(Booksmode.class);
                            list.add(booksmode);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //booksAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterlist(editable.toString());
            }
        });
        adapter.setOnclickliten(new BooksAdapter.Onclickliten() {
            @Override
            public void onclick(Booksmode booksmode) {
                Intent intent = new Intent(AllBooksActivity.this, BookDetailsActivity.class);
                intent.putExtra("bookid", booksmode.getBookid());
                intent.putExtra("image", booksmode.getImage());
                intent.putExtra("bookname", booksmode.getBookname());
                intent.putExtra("writername", booksmode.getWritername());
                intent.putExtra("numofbook", c);
                intent.putExtra("catagory", collection);
                intent.putExtra("phone", phone);
                intent.putExtra("description",booksmode.getDescription());
                intent.putExtra("registration",regi);
                intent.putExtra("name",name);
                startActivity(intent);
            }
            @Override
            public Boolean longpress(int position) {
                return null;
            }
        });
    }

    private void filterlist(String toString) {
        ArrayList<Booksmode> tem = new ArrayList<>();
        for (Booksmode obj : list) {
            if (obj.getBookname()!=null&&obj.getWritername()!=null) {
                if (obj.getBookname().toLowerCase().contains(toString.toLowerCase()) || obj.getWritername().toLowerCase().contains(toString.toLowerCase())) {
                    tem.add(obj);
                }
            }
        }
        adapter.filter(tem);
        adapter.notifyDataSetChanged();
    }
}