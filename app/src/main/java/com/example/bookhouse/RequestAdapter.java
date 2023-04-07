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

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.viewholde> {

    Context context;
    ArrayList<RequestModel> list;
    ArrayList<RequestModel> filterdata;
    public Onclickliten onclickliten;

    public interface Onclickliten {
        void onclick(RequestModel booksmode);

        Boolean longpress(int position);
    }

    public void setOnclickliten(Onclickliten onclickliten) {
        this.onclickliten = onclickliten;
    }

    public RequestAdapter(Context context, ArrayList<RequestModel> list) {
        this.context = context;
        this.list = list;
        this.filterdata = list;
    }

    //    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String key=charSequence.toString();
//                if(key.isEmpty())
//                {
//                    list=filterdata;
//                }
//                else
//                {
//                    ArrayList<Booksmode>tem=new ArrayList<>();
//                    for (Booksmode booksmode:list)
//                    {
//                        if (booksmode.getBookname().toLowerCase().contains(key.toLowerCase()))
//                        {
//                            tem.add(booksmode);
//                        }
//                    }
//                    filterdata=tem;
//                }
//                FilterResults filterResults=new FilterResults();
//                filterResults.values=filterdata;
//                list=filterdata;
//                return  filterResults;
//            }
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                list.clear();
//                list=(ArrayList<Booksmode>)filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
    @NonNull
    @Override
    public viewholde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklistrow, parent, false);
        return new viewholde(view, onclickliten);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholde holder, int position) {
        RequestModel requestModel = list.get(position);
        holder.bookname.setText(requestModel.getBookname());
        holder.writename.setText(requestModel.getWritername());
        Glide.with(context).load(requestModel.getBoookimg()).into(holder.bookimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickliten.onclick(requestModel);
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

    public void filter(ArrayList<RequestModel> backup) {
        list = backup;
        notifyDataSetChanged();
    }
}
