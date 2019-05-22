package com.example.mandooe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mandooe.R;
import com.example.mandooe.model.Noticies_model;
import com.example.mandooe.model.Policies_model;

import java.util.Collections;
import java.util.List;

public class NoticiesAdapter extends  RecyclerView.Adapter<NoticiesAdapter.View_Holder> {

    private Context context;
    private List<Noticies_model> list2 = Collections.emptyList();

    public NoticiesAdapter(List<Noticies_model> list2, Context context) {
        this.list2 = list2;
        this.context = context;

    }



    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notices_recycle, viewGroup, false);
        View_Holder view_holder = new View_Holder(view);

        return view_holder;

    }


    public void onBindViewHolder(@NonNull View_Holder view_holder, int i) {
//        view_holder.custname.setText(list.get(position).getCustumer_name());

        view_holder.get_title.setText(list2.get(i).getTitle());
        view_holder.get_description.setText(list2.get(i).getDescription());
        view_holder.get_fromdate.setText(list2.get(i).getFromdate());
        view_holder.get_todate.setText(list2.get(i).getTodate());
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    class View_Holder extends RecyclerView.ViewHolder {

        TextView get_title, get_description, get_fromdate, get_todate;


        View_Holder(@NonNull View itemView) {
            super(itemView);

            get_title = itemView.findViewById(R.id.gettitle);
            get_description = itemView.findViewById(R.id.getdescription);
            get_fromdate = itemView.findViewById(R.id.getfromdate);
            get_todate = itemView.findViewById(R.id.gettodate);

        }
    }

}
