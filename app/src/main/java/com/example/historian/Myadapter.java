package com.example.historian;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>
{
    private static final String TAG = "Myadapter";
    private List<Listitem> mdata;
    private Context mcontext;
    LayoutInflater inflater;
    RequestOptions options;


    public Myadapter(Context mcontext, List<Listitem> mdata)
    {
        this.mcontext = mcontext;
        this.mdata = mdata;

        // Request option for Glide

        options = new RequestOptions().centerCrop().placeholder(R.drawable.loadingshape).error(R.drawable.loadingshape);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view;
        final LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.rowitem, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view_conatiner.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(mcontext, Pagereviewlistitem.class);
                i.putExtra("Museum_Name",mdata.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("Museum_Rating",mdata.get(viewHolder.getAdapterPosition()).getRating());
                i.putExtra("Museum_Type",mdata.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("Museum_Image",mdata.get(viewHolder.getAdapterPosition()).getImage_url());
//                i.putExtra("Museum_Data",mdata.get(viewHolder.getAdapterPosition()).getMuseumdata());

                mcontext.startActivity(i);


            }
            public void onClick1(View view1)
            {
                Intent intent = new Intent(mcontext, bookTicket.class);
                intent.putExtra("Museum_Name",mdata.get(viewHolder.getAdapterPosition()).getName());
                intent.putExtra("Museum_Image",mdata.get(viewHolder.getAdapterPosition()).getImage_url());

                mcontext.startActivity(intent);

            }
        });


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position)
    {
        holder.tv_name.setText(mdata.get(position).getName());
        holder.tv_rating.setText(mdata.get(position).getRating());
        holder.tv_type.setText(mdata.get(position).getDescription());
        // load image from internet and  it into Imageview using glide
        Glide.with(mcontext).load(mdata.get(position).getImage_url()).apply(options).into(holder.img_thumbnail);

    }


    @Override
    public int getItemCount()
    {
        return mdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_name;
        TextView tv_rating;
        TextView tv_type;
        ImageView img_thumbnail;
        LinearLayout view_conatiner;



        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.title);
            tv_rating = (TextView)itemView.findViewById(R.id.rating);
            img_thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            tv_type = (TextView) itemView.findViewById(R.id.type);
            view_conatiner = (LinearLayout) itemView.findViewById(R.id.container);


        }
    }
}