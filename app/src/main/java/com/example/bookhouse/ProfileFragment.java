package com.example.bookhouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookhouse.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    BooksAdapter booksAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ArrayList<RequestModel> listreq;
    ArrayList<Pendingbookmodel> list2 = new ArrayList<>();
    FragmentProfileBinding binding;
    String dept, name, roll;
    RequestAdapter requestAdapter, pendingadapter;
    PendingBookAdapter pendingBookAdapter;
    ProgressDialog progressDialog;
    private SharedClass sharedClass;
    private BooksAdapter.Onclickliten onclickliten;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        sharedClass = new SharedClass(getContext());
        listreq = new ArrayList<>();
        requestAdapter = new RequestAdapter(getContext(), listreq);
        pendingBookAdapter = new PendingBookAdapter(getContext(), list2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.pendingrquest.setAdapter(requestAdapter);
        binding.pendingrquest.setLayoutManager(linearLayoutManager);
        binding.currentbook.setAdapter(pendingBookAdapter);
        binding.currentbook.setLayoutManager(linearLayoutManager2);




        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedClass.logout();
                startActivity(new Intent(getContext(),LoginActivity.class));
            }
        });



        if (sharedClass.getuserauth() == true) {
            HashMap<String, String> map = sharedClass.getuserdetails();
            String name = map.get(SharedClass.NAME);
            String department = map.get(SharedClass.DEPARTMENT);
            String Reg = map.get(SharedClass.REG_NUM);
            String phone = map.get(SharedClass.PHONE_NUM);
            String email = map.get(SharedClass.EMAIL);

            binding.myroll.setText("Reg_Num: " + Reg);
            binding.myname.setText("Name: " + name);
            binding.mydepartment.setText("Department: " + department);
        } else {
            firebaseFirestore.collection("StudentRecord")
                    .document(firebaseAuth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            StudentModel studentModel = documentSnapshot.toObject(StudentModel.class);
                            String sname, department, registration, phone, email, password, batch, roll;
                            sname = studentModel.getStudentname();
                            department = studentModel.getStudentdepartment();
                            registration = studentModel.getStudentregistration();
                            phone = studentModel.getStudentphone();
                            email = studentModel.getStudentemail();
                            password = studentModel.getStudentpassword();
                            batch = studentModel.getStudentbatch();
                            roll = studentModel.getStudentclassroll();
                            sharedClass.createloginsession(sname, department, registration, phone, email, password, batch, roll);

                            binding.myname.setText("Name: " + sname);
                            binding.myroll.setText("Reg_Num: " + registration);
                            binding.mydepartment.setText("Department: " + department);
                        }
                    });
        }
        firebaseFirestore.collection("RequestQ")
                .whereEqualTo("studentid", firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        listreq.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            RequestModel requestModel = snapshot.toObject(RequestModel.class);
                            listreq.add(requestModel);
                        }
                        requestAdapter.notifyDataSetChanged();
                    }
                });
        firebaseFirestore.collection("PendingBook")
                .whereEqualTo("studentid", firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        list2.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Pendingbookmodel requestModel = snapshot.toObject(Pendingbookmodel.class);
                            list2.add(requestModel);
                        }
                        pendingBookAdapter.notifyDataSetChanged();
                    }
                });
//        firebaseFirestore.collection("StudentRecord")
//                .document(firebaseAuth.getCurrentUser().getUid())
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        StudentModel studentModel=documentSnapshot.toObject(StudentModel.class);
//                        name=studentModel.getStudentname();
//                        roll=studentModel.getStudentclassroll();
//                        dept=studentModel.getStudentdepartment();
//                        binding.myroll.setText("Class Roll: "+roll);
//                        binding.myname.setText("Name: "+name);
//                        binding.mydepartment.setText("Department: "+dept);
//                        progressDialog.dismiss();
//                    }
//                });
        requestAdapter.setOnclickliten(new RequestAdapter.Onclickliten() {
            @Override
            public void onclick(RequestModel booksmode) {
                Toast.makeText(getContext(), "Your selected Book", Toast.LENGTH_SHORT).show();
            }

            @Override
            public Boolean longpress(int position) {
                return true;
            }
        });
        pendingBookAdapter.setOnclickliten(new PendingBookAdapter.Onclickliten() {
            @Override
            public void onclick(Pendingbookmodel booksmode) {
                Toast.makeText(getContext(), "Your Current Book", Toast.LENGTH_SHORT).show();
            }

            @Override
            public Boolean longpress(int position) {
                return null;
            }
        });
        return binding.getRoot();
    }
}