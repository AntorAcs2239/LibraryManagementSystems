package com.example.bookhouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookhouse.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ActivitySignUpBinding binding;
    ProgressDialog progressDialog;
    String department, batch, session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Account...");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        binding.studentlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentname, studentdepartment, studentphone, studentemail, studentpassword, uid, studentclassroll, studentregistration;
                studentname = binding.studentname.getText().toString();
                studentemail = binding.studentEmail.getText().toString();
                studentphone = binding.studentphone.getText().toString();
                studentpassword = binding.studentPassword.getText().toString();
                studentclassroll = binding.studentroll.getText().toString();
                studentregistration = binding.studentregistration.getText().toString();
                SharedClass sharedClass = new SharedClass(SignUpActivity.this);
                if (TextUtils.isEmpty(studentemail)) {
                    binding.studentEmail.setError("Email should fill up");
                    binding.studentEmail.requestFocus();
                } else if (studentpassword.isEmpty()) {
                    binding.studentPassword.setError("Password should fill up");
                    binding.studentPassword.requestFocus();
                } else if (studentphone.isEmpty()) {
                    binding.studentphone.setError("Phone should fill up");
                    binding.studentphone.requestFocus();
                } else if (studentname.isEmpty()) {
                    binding.studentname.setError("Name should fill up");
                    binding.studentname.requestFocus();
                } else if (studentclassroll.isEmpty()) {
                    binding.studentroll.setError("Class Roll should fill up");
                    binding.studentroll.requestFocus();
                }
                else if (department==null)
                {
                    Toast.makeText(SignUpActivity.this,"Select Department",Toast.LENGTH_SHORT).show();
                }
                else if (batch==null)
                {
                    Toast.makeText(SignUpActivity.this,"Select Batch",Toast.LENGTH_SHORT).show();
                }
                else if (session==null)
                {
                    Toast.makeText(SignUpActivity.this,"Select Session",Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(studentemail, studentpassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(SignUpActivity.this, "Verification code is sent", Toast.LENGTH_LONG).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });

                                        sharedClass.createloginsession(studentname, department, studentregistration, studentphone, studentemail, studentpassword, batch, studentclassroll);
                                        String uid = task.getResult().getUser().getUid();
                                        StudentModel studentModel = new StudentModel(studentname, department, studentphone, studentemail, studentpassword, uid, studentclassroll, 0, studentregistration, batch, session);
                                        firebaseFirestore.collection("StudentRecord")
                                                .document(uid)
                                                .set(studentModel)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(SignUpActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                    }
                                                });
                                    } else {
                                        progressDialog.dismiss();
                                        try {
                                            throw task.getException();
                                        }
                                        // if user enters wrong email.
                                        catch (FirebaseAuthWeakPasswordException weakPassword) {


                                            // TODO: take your actions!
                                            Toast.makeText(SignUpActivity.this, weakPassword.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                        // if user enters wrong password.
                                        catch (FirebaseAuthInvalidCredentialsException malformedEmail) {

                                            Toast.makeText(SignUpActivity.this, malformedEmail.toString(), Toast.LENGTH_SHORT).show();
                                            // TODO: Take your action
                                        } catch (FirebaseAuthUserCollisionException existEmail) {


                                            // TODO: Take your action
                                            Toast.makeText(SignUpActivity.this, existEmail.toString(), Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.department, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerfordept.setAdapter(adapter);
        binding.spinnerfordept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                department = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.batch, android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerforbatch.setAdapter(adapter2);
        binding.spinnerforbatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                batch = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.session, android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnersession.setAdapter(adapter3);
        binding.spinnersession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                session = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}