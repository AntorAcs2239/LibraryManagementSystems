package com.example.bookhouse;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.bookhouse.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ActivityLoginBinding binding;
    SharedPreferences sharepef;
    private static Boolean CHECK;
    SharedClass sharedClass;
    FirebaseFirestore firestore;
    String first;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        sharepef = getPreferences(MODE_PRIVATE);
        sharedClass = new SharedClass(LoginActivity.this);
        first=SharedClass.FIRST;
        CHECK = sharepef.getBoolean("check", false);
        if (sharedClass.getuserauth()==true) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        FirebaseUser user;
        String email = binding.studentemail.getText().toString();
        String pass = binding.studentpassword.getText().toString();
        binding.forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentemail = binding.studentemail.getText().toString();
                if (studentemail.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Give your Email", Toast.LENGTH_LONG).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(studentemail)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LoginActivity.this, "Email is sent for reset password", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            firebaseUser.reload();
            binding.studentlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name, studentemail, studentpassword, phone, uid;
                    studentemail = binding.studentemail.getText().toString();
                    studentpassword = binding.studentpassword.getText().toString();
                    firebaseUser.reload();
                    if (TextUtils.isEmpty(studentemail)) {
                        binding.studentemail.setError("Email should fill up");
                        binding.studentemail.requestFocus();
                    } else if (studentpassword.isEmpty()) {
                        binding.studentpassword.setError("Password should fill up");
                        binding.studentpassword.requestFocus();
                    } else {
                        firebaseUser.reload();
                        if (firebaseUser.isEmailVerified()) {
                            CHECK = true;
                            saveme(CHECK);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }
                }
            });
        } else {
            binding.studentlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String useremail, password;
                    useremail = binding.studentemail.getText().toString();
                    password = binding.studentpassword.getText().toString();
                    if (TextUtils.isEmpty(useremail)) {
                        binding.studentemail.setError("Email should fill up");
                        binding.studentemail.requestFocus();
                    } else if (password.isEmpty()) {
                        binding.studentpassword.setError("Password should fill up");
                        binding.studentpassword.requestFocus();
                    }
                    firebaseAuth.signInWithEmailAndPassword(useremail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                CHECK = true;
                                saveme(CHECK);
//                                firestore.collection("StudentRecord")
//                                        .document(firebaseAuth.getCurrentUser().getUid())
//                                        .get()
//                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                              Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_LONG).show();
//                                            }
//                                        });
                                firestore.collection("StudentRecord")
                                        .document(firebaseAuth.getCurrentUser().getUid())
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                StudentModel studentModel=documentSnapshot.toObject(StudentModel.class);
                                                sharedClass.createloginsession(studentModel.getStudentname(),studentModel.getStudentdepartment(),studentModel.getStudentregistration(),studentModel.getStudentphone(),studentModel.getStudentemail(),studentModel.getStudentpassword(),studentModel.getStudentbatch(),studentModel.getStudentclassroll());
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                finish();
                                            }
                                        });
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
        binding.needaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public void saveme(Boolean name) {
        SharedPreferences.Editor editor = sharepef.edit();
        editor.putBoolean("check", name);
        editor.apply();
    }
}