package com.example.bookhouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookhouse.databinding.ActivityBookDetailsBinding;
import com.example.bookhouse.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class BookDetailsActivity extends AppCompatActivity {
    private ActivityBookDetailsBinding binding;
   private FirebaseFirestore firestore;
   private FirebaseAuth firebaseAuth;
   private String image,writer,bookname,bookid,description;
   private String catagory;
   private int c;
   int numofreq;
   private String studentname,phone,registration;
   private ProgressDialog progressDialog;
   private int numofbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        binding= ActivityBookDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        firestore=FirebaseFirestore.getInstance();
        image=getIntent().getStringExtra("image");
        writer=getIntent().getStringExtra("writername");
        bookname=getIntent().getStringExtra("bookname");
        catagory=getIntent().getStringExtra("catagory");
        bookid=getIntent().getStringExtra("bookid");
        c=getIntent().getIntExtra("numofbook",0);
        phone=getIntent().getStringExtra("phone");
        description=getIntent().getStringExtra("description");
        registration=getIntent().getStringExtra("registration");
        studentname=getIntent().getStringExtra("name");
        Glide.with(this).load(image).into(binding.image);
        binding.detailbookname.setText("Book Name: "+bookname);
        binding.detailwritername.setText("Writer Name: "+writer);
        binding.description.setText("Descripton: \n"+description);
        firestore.collection("RequestQ")
                .whereEqualTo("studentid",firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot snapshot:value.getDocuments())
                        {
                            numofreq++;
                        }
                        Check();
                        progressDialog.dismiss();
                    }
                });
        binding.goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookDetailsActivity.this,MainActivity.class));
            }
        });
    }
    public void Check()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi=connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileinternet=connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if(wifi.isConnected()||mobileinternet.isConnected())
        {

            binding.nointernet.setVisibility(View.GONE);
            if (numofreq>=5)
            {
                binding.requestbtn.setVisibility(View.GONE);
                binding.goback.setVisibility(View.VISIBLE);
                binding.goback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(BookDetailsActivity.this,MainActivity.class));
                    }
                });
            }
            else
            {
                binding.goback.setVisibility(View.GONE);
                binding.requestbtn.setVisibility(View.VISIBLE);
                binding.requestbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(BookDetailsActivity.this,"Request Sent",Toast.LENGTH_SHORT).show();
                        DocumentReference documentReference=firestore.collection("RequestQ").document();
                        RequestModel requestModel=new RequestModel(firebaseAuth.getCurrentUser().getUid(),bookid,documentReference.getId(),image,writer,bookname,catagory,studentname,phone,description,registration);
                        documentReference.set(requestModel);
                        startActivity(new Intent(BookDetailsActivity.this,MainActivity.class));
                        finish();
                    }
                });
            }
        }
        else
        {
            binding.requestbtn.setVisibility(View.GONE);
            binding.nointernet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BookDetailsActivity.this,MainActivity.class));
        super.onBackPressed();
    }
}