package com.example.cakehut;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderRVAdapter extends RecyclerView.Adapter<OrderRVAdapter.ViewHolder> {

    private ArrayList<CakeModel> cakeModelArrayList;
    private Context context;
    int lastPos = -1;
    private OrderClickInterface orderClickInterface;

    public OrderRVAdapter(ArrayList<CakeModel> cakeModelArrayList, Context context, OrderClickInterface orderClickInterface) {
        this.cakeModelArrayList = cakeModelArrayList;
        this.context = context;
        this.orderClickInterface = orderClickInterface;
    }

    @NonNull
    @Override
    public OrderRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.order_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRVAdapter.ViewHolder holder, int position) {

        CakeModel cakeModel = cakeModelArrayList.get(position);
        holder.name.setText(cakeModel.getName());
        holder.weight.setText(cakeModel.getWeight());
        Picasso.get().load(cakeModel.getImage()).into(holder.img);
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderClickInterface.onOrderClick(position);
            }
        });

    }

    private void setAnimation(View itemView, int position){
        if(position>lastPos){

            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }

    }

    @Override
    public int getItemCount() {
        return cakeModelArrayList.size();
    }

    public interface OrderClickInterface{
        void onOrderClick(int position);

    }

    public class ViewHolder extends  RecyclerView.ViewHolder{


        private TextView name,weight;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.idTVname);
            weight = itemView.findViewById(R.id.idTVweight);
            img = itemView.findViewById(R.id.idIvOrder);
        }
    }


}
