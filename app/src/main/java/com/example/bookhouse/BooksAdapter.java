package com.example.bookhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.viewholde> {

    Context context;
    ArrayList<Booksmode> list = new ArrayList<>();
    ArrayList<Booksmode> filterdata = new ArrayList<>();
    public Onclickliten onclickliten;

    public interface Onclickliten {
        void onclick(Booksmode booksmode);

        Boolean longpress(int position);
    }
    public void setOnclickliten(Onclickliten onclickliten) {
        this.onclickliten = onclickliten;
    }

    public BooksAdapter(Context context, ArrayList<Booksmode> list) {
        this.context = context;
        this.list = list;
        this.filterdata = list;
    }
    @NonNull
    @Override
    public viewholde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklistrow, parent, false);
        return new viewholde(view, onclickliten);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholde holder, int position) {
        Booksmode booksModel = list.get(position);
        holder.writename.setText(booksModel.getWritername());
        holder.bookname.setText(booksModel.getBookname());
        Glide.with(context).load(booksModel.getImage()).into(holder.bookimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickliten.onclick(booksModel);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onclickliten.longpress(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholde extends RecyclerView.ViewHolder {
        ImageView bookimg;
        TextView bookname, writename;

        public viewholde(@NonNull View itemView, Onclickliten onclickliten) {
            super(itemView);
            bookimg = itemView.findViewById(R.id.bookimgg);
            bookname = itemView.findViewById(R.id.bookname);
            writename = itemView.findViewById(R.id.writername);
            // itemView.setOnClickListener(v->onclickliten.onclick(getAdapterPosition()));
            //itemView.setOnLongClickListener(v->onclickliten.longpress(getAdapterPosition()));
        }
    }

    public void filter(ArrayList<Booksmode> backup) {
        list = backup;
        notifyDataSetChanged();
    }
}