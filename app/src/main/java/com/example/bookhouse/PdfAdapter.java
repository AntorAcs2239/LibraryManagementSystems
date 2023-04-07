package com.example.bookhouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.viewholder> {
    ArrayList<Modelforpdf> list;
    Context context;
    public Onclickliten onclickliten;

    public interface Onclickliten {
        void onclick(Modelforpdf modelforpdf);

        Boolean longpress(int position);
    }

    public void setOnclickliten(Onclickliten onclickliten) {
        this.onclickliten = onclickliten;
    }

    public PdfAdapter(ArrayList<Modelforpdf> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowforpdf, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Modelforpdf modelforpdf = list.get(position);
        holder.writername.setText("written by: " + modelforpdf.getWritername());
        holder.pdfname.setText("Article Title: " + modelforpdf.getPdfname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickliten.onclick(modelforpdf);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView pdfname, writername;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            pdfname = itemView.findViewById(R.id.pdfname);
            writername = itemView.findViewById(R.id.writernamepdf);
        }
    }

    public void filter(ArrayList<Modelforpdf> backup) {
        list = backup;
        notifyDataSetChanged();
    }
}
