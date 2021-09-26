package com.example.cliente.adapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import  androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cliente.Model.HomeModel;
import com.example.cliente.R;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    private List<HomeModel> list;
    Context context;

    public HomeAdapter(List<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false);

        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {

        holder.userNameTv.setText(list.get(position).getUserName());
        holder.titleTv.setText(list.get(position).getPostTitle());
        holder.descriptionTv.setText(list.get(position).getPostDescription());

        int count = list.get(position).getLikeCount();
        if(count==0){
            holder.likeCountTv.setVisibility(View.INVISIBLE);
        }else{
            holder.likeCountTv.setText(count + "");
        }
        int count_1 = list.get(position).getDislikeCount();
        if(count_1==0){
            holder.dislikeCountTv.setVisibility(View.INVISIBLE);
        }else{
            holder.dislikeCountTv.setText(count + "");
        }
        int count_2 = list.get(position).getViewCount();
        if(count_2==0){
            holder.viewCount.setVisibility(View.INVISIBLE);
        }else{
            holder.viewCount.setText(count + "");
        }
        Random random = new Random();


        int color = Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));


        Glide.with(context.getApplicationContext())
                .load(list.get(position).getPostImage())
                .placeholder(R.drawable.ic_persona)
                .timeout(6500)
                .into(holder.profileImage);
        Glide.with(context.getApplicationContext())
                .load(list.get(position).getPostImage())
                .placeholder(new ColorDrawable(color))
                .timeout(7000)
                .into(holder.imageView_1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class HomeHolder extends RecyclerView.ViewHolder{

        private CircleImageView profileImage;
        private TextView userNameTv, titleTv, descriptionTv, likeCountTv, dislikeCountTv, viewCount;
        private ImageView imageView_1,imageView_2 ;
        private ImageButton likeBtn, dislikeBtn;

        public HomeHolder(@NonNull View itemView){
            super(itemView);

            profileImage = itemView.findViewById(R.id.profileImage);
            userNameTv = itemView.findViewById(R.id.userName);
            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.tvDetail);
            likeCountTv = itemView.findViewById(R.id.TvcountLike);
            dislikeCountTv = itemView.findViewById(R.id.TvCountDislike);
            viewCount = itemView.findViewById(R.id.TvCountView);
            imageView_1 = itemView.findViewById(R.id.imageView8);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            dislikeBtn = itemView.findViewById(R.id.dislikeBtn);
            imageView_2=itemView.findViewById(R.id.imageView6);


        }
    }
}
