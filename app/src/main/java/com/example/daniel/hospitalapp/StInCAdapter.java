package com.example.daniel.hospitalapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Daniel on 19/11/2015.
 */
public class StInCAdapter extends RecyclerView.Adapter<StInCAdapter.MyViewHolder>  {
        private LayoutInflater inflater;
        List<StInCList> pclist;
        private RecyclerClickListner mRecyclerClickListner;

       public StInCAdapter(Context context,List<StInCList> data){
        inflater=LayoutInflater.from(context);
        this.pclist=data;
        }

      @Override
       public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.stincourse_row_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
        }

       @Override
       public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.Ptvname.setText(pclist.get(position).getStudent_Name());
        holder.ph.setImageBitmap(BitmapFactory.decodeByteArray(pclist.get(position).getPhoto(),0,pclist.get(position).getPhoto().length));
        }

        @Override
        public int getItemCount() {
        return pclist.size();
        }

        public void setRecyclerClickListner(RecyclerClickListner recyclerClickListner){
         mRecyclerClickListner = recyclerClickListner;
        }

   class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView Ptvname; ImageView ph;

    public MyViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        ph=(ImageView) itemView.findViewById(R.id.imageView5);
        Ptvname=(TextView) itemView.findViewById(R.id.textView29);

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
