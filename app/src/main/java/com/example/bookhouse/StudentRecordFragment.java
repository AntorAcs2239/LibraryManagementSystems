package com.example.bookhouse;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookhouse.databinding.FragmentStudentRecordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentRecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentRecordFragment newInstance(String param1, String param2) {
        StudentRecordFragment fragment = new StudentRecordFragment();
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

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FragmentStudentRecordBinding binding;
    FirebaseFirestore firestore;
    ArrayList<StudentModel> list;
    StudentAdapter studentAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentRecordBinding.inflate(inflater, container, false);

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        studentAdapter = new StudentAdapter(getContext(), list);
        binding.studentrec.setAdapter(studentAdapter);
        binding.studentrec.setLayoutManager(linearLayoutManager);

        firestore.collection("StudentRecord")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        list.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            StudentModel studentModel = snapshot.toObject(StudentModel.class);
                            list.add(studentModel);
                        }
                        studentAdapter.notifyDataSetChanged();
                    }
                });
        studentAdapter.setOnclickliten(new StudentAdapter.Onclickliten() {
            @Override
            public void onclick(StudentModel studentModel) {
                Toast.makeText(getContext(), "Name: " + studentModel.getStudentname(), Toast.LENGTH_LONG).show();
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
        return binding.getRoot();
    }

    private void filterlist(String toString) {
        ArrayList<StudentModel> tem = new ArrayList<>();
        for (StudentModel obj : list) {
            if (obj.getStudentname().toLowerCase().contains(toString.toLowerCase()) || obj.getStudentregistration().toLowerCase().contains(toString.toLowerCase())) {
                tem.add(obj);
            }
        }
        studentAdapter.filter(tem);
        studentAdapter.notifyDataSetChanged();
    }
}