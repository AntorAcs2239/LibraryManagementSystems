package com.example.bookhouse;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PendingBookAdapter extends RecyclerView.Adapter<PendingBookAdapter.viewholder> {
    Context context;
    ArrayList<Pendingbookmodel> list;
    public Onclickliten onclickliten;

    public interface Onclickliten {
        void onclick(Pendingbookmodel booksmode);

        Boolean longpress(int position);
    }

    public void setOnclickliten(Onclickliten onclickliten) {
        this.onclickliten = onclickliten;
    }

    public PendingBookAdapter(Context context, ArrayList<Pendingbookmodel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowforpendingbooks, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Pendingbookmodel pendingbookmodel = list.get(position);
        holder.bookname.setText(pendingbookmodel.getBookname());
        holder.writername.setText(pendingbookmodel.getWritername());
        Glide.with(context).load(pendingbookmodel.getImg()).into(holder.imageView);
//        String t=timeago(pendingbookmodel.getDate());
//        holder.time.setText(pendingbookmodel.getDate());
        String t = (String) DateUtils.getRelativeTimeSpanString(pendingbookmodel.getTimestamp().getSeconds() * 1000);
        holder.time.setText(t);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickliten.onclick(pendingbookmodel);
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

    private String timeago(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            long time = sdf.parse(date).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView bookname, writername, time;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            bookname = itemView.findViewById(R.id.bname);
            writername = itemView.findViewById(R.id.wname);
            time = itemView.findViewById(R.id.time);
        }
    }

    public void filter(ArrayList<Pendingbookmodel> backup) {
        list = backup;
        notifyDataSetChanged();
    }
}
