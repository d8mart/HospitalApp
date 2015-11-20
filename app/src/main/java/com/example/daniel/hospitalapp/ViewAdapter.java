package com.example.daniel.hospitalapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Daniel on 17/11/2015.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {//viewadapter con profcourselist y custom row
    private LayoutInflater inflater;
    List<ProfCoursesList> pclist;
    private RecyclerClickListner mRecyclerClickListner;

    public ViewAdapter(Context context,List<ProfCoursesList> data){
        inflater=LayoutInflater.from(context);
        this.pclist=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_item,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        viewHolder.Ptvname.setText(pclist.get(i).getCourse_Name());
        viewHolder.tvsd.setText(pclist.get(i).getStart_Date());
        viewHolder.tved.setText(pclist.get(i).getEnd_Date());
    }

    @Override
    public int getItemCount() {
        return pclist.size();
    }

    public void setRecyclerClickListner(RecyclerClickListner recyclerClickListner){
        mRecyclerClickListner = recyclerClickListner;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView Ptvname,tvsd,tved;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Ptvname=(TextView) itemView.findViewById(R.id.textView28);
            tvsd=(TextView) itemView.findViewById(R.id.textView30);
            tved=(TextView) itemView.findViewById(R.id.textView32);
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
