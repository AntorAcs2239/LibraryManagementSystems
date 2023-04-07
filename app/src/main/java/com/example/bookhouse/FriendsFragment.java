package com.example.bookhouse;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookhouse.databinding.FragmentFriendsBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FriendsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendsFragment newInstance(String param1, String param2) {
        FriendsFragment fragment = new FriendsFragment();
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

    FragmentFriendsBinding binding;
    ArrayList<Modelforpdf> list;
    FirebaseFirestore firestore;
    PdfAdapter pdfAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendsBinding.inflate(inflater, container, false);

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        pdfAdapter = new PdfAdapter(list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recforpdf.setLayoutManager(linearLayoutManager);
        binding.recforpdf.setAdapter(pdfAdapter);

        firestore.collection("Pdf")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        list.clear();
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Modelforpdf modelforpdf = snapshot.toObject(Modelforpdf.class);
                            list.add(modelforpdf);
                        }
                        pdfAdapter.notifyDataSetChanged();
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
        pdfAdapter.setOnclickliten(new PdfAdapter.Onclickliten() {
            @Override
            public void onclick(Modelforpdf modelforpdf) {
                Intent intent = new Intent(getContext(), ArticleShowActivity.class);
                intent.putExtra("url", modelforpdf.getUrl());
                startActivity(intent);
            }

            @Override
            public Boolean longpress(int position) {
                return null;
            }
        });
        return binding.getRoot();
    }

    private void filterlist(String toString) {
        ArrayList<Modelforpdf> tem = new ArrayList<>();
        for (Modelforpdf obj : list) {
            if (obj.getPdfname().toLowerCase().contains(toString.toLowerCase())) {
                tem.add(obj);
            }
        }
        pdfAdapter.filter(tem);
        pdfAdapter.notifyDataSetChanged();
    }
}