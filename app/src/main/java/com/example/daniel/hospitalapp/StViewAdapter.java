package com.example.daniel.hospitalapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Daniel on 18/11/2015.
 */
public class StViewAdapter extends RecyclerView.Adapter<StViewAdapter.MyViewHolder>  {
    private LayoutInflater inflater;
    List<StAvCourseList> pclist;
    private RecyclerClickListner mRecyclerClickListner;

    public StViewAdapter(Context context,List<StAvCourseList> data){
        inflater=LayoutInflater.from(context);
        this.pclist=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.st_row_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Ptvname.setText(pclist.get(position).getCourse_Name());
        holder.tvsd.setText(pclist.get(position).getStart_Date());
        holder.tved.setText(pclist.get(position).getEnd_Date());
        holder.tvpa.setText(pclist.get(position).getProf_Assign());
    }

    @Override
    public int getItemCount() {
        return pclist.size();
    }

    public void setRecyclerClickListner(RecyclerClickListner recyclerClickListner){
        mRecyclerClickListner = recyclerClickListner;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView Ptvname,tvsd,tved,tvpa;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Ptvname=(TextView) itemView.findViewById(R.id.textView36);
            tvsd=(TextView) itemView.findViewById(R.id.textView34);
            tved=(TextView) itemView.findViewById(R.id.textView37);
            tvpa=(TextView) itemView.findViewById(R.id.textView40);
        }

        @Override
        public void onClick(View v) {
            if (mRecyclerClickListner != null) {
                mRecyclerClickListner.itemClick(v, getPosition());

            }
        }
    }
    public interface RecyclerClickListner
    {
        public void itemClick(View view, int position);
    }
}
