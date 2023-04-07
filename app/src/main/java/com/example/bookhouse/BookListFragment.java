package com.example.bookhouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookhouse.databinding.FragmentBookListBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance(String param1, String param2) {
        BookListFragment fragment = new BookListFragment();
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

    private BooksAdapter booksAdapter1, booksAdapter2, booksAdapter3, booksAdapter4, booksAdapter5, booksAdapter6, booksAdapter7, booksAdapter8;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Booksmode> list1;
    private ArrayList<Booksmode> list2;
    private ArrayList<Booksmode> list3;
    private ArrayList<Booksmode> list4;
    private ArrayList<Booksmode> list5;
    private ArrayList<Booksmode> list6;
    private ArrayList<Booksmode> list7;
    private FragmentBookListBinding binding;
    private FirebaseFirestore firestore;
    private String dept, phone, registrationnum, name;
    private int c = 0;
    private int numreq;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private BooksAdapter.Onclickliten onclickliten;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookListBinding.inflate(inflater, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Wait...");
        progressDialog.show();

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        list7 = new ArrayList<>();
        booksAdapter1 = new BooksAdapter(getContext(), list1);
        booksAdapter2 = new BooksAdapter(getContext(), list2);
        booksAdapter3 = new BooksAdapter(getContext(), list3);
        booksAdapter4 = new BooksAdapter(getContext(), list4);
        booksAdapter5 = new BooksAdapter(getContext(), list5);
        booksAdapter6 = new BooksAdapter(getContext(), list6);
        booksAdapter7 = new BooksAdapter(getContext(), list7);

        setrecyclerview(booksAdapter1, binding.reccse);
        setrecyclerview(booksAdapter2, binding.receee);
        setrecyclerview(booksAdapter3, binding.reccv);
        setrecyclerview(booksAdapter4, binding.recgeneral);
        setrecyclerview(booksAdapter5, binding.recforbanga);
        setrecyclerview(booksAdapter6, binding.recforthesis);
        setrecyclerview(booksAdapter7, binding.recforothers);

        Bookcollectfromfirebase("CSE", list1, booksAdapter1);
        Bookcollectfromfirebase("EEE", list2, booksAdapter2);
        Bookcollectfromfirebase("Civil", list3, booksAdapter3);
        Bookcollectfromfirebase("General", list4, booksAdapter4);
        Bookcollectfromfirebase("Bangabondho", list5, booksAdapter5);
        Bookcollectfromfirebase("Thesis", list6, booksAdapter6);
        Bookcollectfromfirebase("Others", list7, booksAdapter7);

        firebaseFirestore.collection("StudentRecord")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        StudentModel studentModel = documentSnapshot.toObject(StudentModel.class);
                        c = studentModel.getNumofbook();
                        phone = studentModel.getStudentphone();
                        dept = studentModel.getStudentdepartment();
                        registrationnum = studentModel.getStudentregistration();
                        name = studentModel.getStudentname();
                        progressDialog.dismiss();
                    }
                });
        binding.cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllBooksActivity.class);
                intent.putExtra("catagory", "CSE");
                startActivity(intent);
            }
        });
        binding.eee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllBooksActivity.class);
                intent.putExtra("catagory", "EEE");
                startActivity(intent);
            }
        });
        binding.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllBooksActivity.class);
                intent.putExtra("catagory", "Civil");
                startActivity(intent);
            }
        });
        binding.general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllBooksActivity.class);
                intent.putExtra("catagory", "General");
                startActivity(intent);
            }
        });
        binding.banga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllBooksActivity.class);
                intent.putExtra("catagory", "Bangabondho");
                startActivity(intent);
            }
        });
        binding.thesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllBooksActivity.class);
                intent.putExtra("catagory", "Thesis");
                startActivity(intent);
            }
        });
        binding.others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AllBooksActivity.class);
                intent.putExtra("catagory", "Others");
                startActivity(intent);
            }
        });
        booksAdapter1.setOnclickliten(new BooksAdapter.Onclickliten() {
            @Override
            public void onclick(Booksmode booksmode) {
                gotorequest(booksmode);
            }

            @Override
            public Boolean longpress(int position) {
                return null;
            }
        });
        booksAdapter2.setOnclickliten(new BooksAdapter.Onclickliten() {
            @Override
            public void onclick(Booksmode booksmode) {
                gotorequest(booksmode);
            }

            @Override
            public Boolean longpress(int position) {
                return true;
            }
        });
        booksAdapter3.setOnclickliten(new BooksAdapter.Onclickliten() {
            @Override
            public void onclick(Booksmode booksmode) {
                gotorequest(booksmode);
            }

            @Override
            public Boolean longpress(int position) {
                return null;
            }
        });
        booksAdapter4.setOnclickliten(new BooksAdapter.Onclickliten() {
            @Override
            public void onclick(Booksmode booksmode) {
                gotorequest(booksmode);
            }

            @Override
            public Boolean longpress(int position) {
                return null;
            }
        });
        booksAdapter5.setOnclickliten(new BooksAdapter.Onclickliten() {
            @Override
            public void onclick(Booksmode booksmode) {
                gotorequest(booksmode);
            }

            @Override
            public Boolean longpress(int position) {
                return null;
            }
        });
        booksAdapter6.setOnclickliten(new BooksAdapter.Onclickliten() {
            @Override
            public void onclick(Booksmode booksmode) {
                gotorequest(booksmode);
            }

            @Override
            public Boolean longpress(int position) {
                return null;
            }
        });
        booksAdapter7.setOnclickliten(new BooksAdapter.Onclickliten() {
            @Override
            public void onclick(Booksmode booksmode) {
                gotorequest(booksmode);
            }

            @Override
            public Boolean longpress(int position) {
                return null;
            }
        });
        return binding.getRoot();
    }

    public void setrecyclerview(BooksAdapter booksAdapter, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setAdapter(booksAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void Bookcollectfromfirebase(String catagory, ArrayList<Booksmode> list, BooksAdapter booksAdapter) {
        firebaseFirestore.collection(catagory)
                .whereEqualTo("available", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        list.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Booksmode booksmode = snapshot.toObject(Booksmode.class);
                            booksmode.setBookid(snapshot.getId());
                            list.add(booksmode);
                        }
                        //Collections.sort(list,Booksmode.booksmodecomparator);
                        booksAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void gotorequest(Booksmode booksmode) {
        Intent intent = new Intent(getContext(), BookDetailsActivity.class);
        intent.putExtra("image", booksmode.getImage());
        intent.putExtra("bookname", booksmode.getBookname());
        intent.putExtra("writername", booksmode.getWritername());
        intent.putExtra("description", booksmode.getDescription());
        intent.putExtra("numofreq", numreq);
        intent.putExtra("catagory", booksmode.getCatagory());
        intent.putExtra("bookid", booksmode.getBookid());
        intent.putExtra("phone", phone);
        intent.putExtra("numofbook", c);
        intent.putExtra("registration", registrationnum);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}