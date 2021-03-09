package com.nanoapps.to_do;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private Context context;
    private List<recyclerData> cartList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView description, firstLetter, time, done, deleted;
        public ImageView thumbnail, img_done, img_del;
        public CardView viewBackground, viewForeground;
        public RelativeLayout baccc;

        public MyViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.time);
            img_done = view.findViewById(R.id.image_done);
            img_del = view.findViewById(R.id.image_delete);
            deleted = view.findViewById(R.id.deleted);
            done = view.findViewById(R.id.done);
            baccc = view.findViewById(R.id.baccccc);
            description = view.findViewById(R.id.Descrip);
            firstLetter = view.findViewById(R.id.firstLetters);
            thumbnail = view.findViewById(R.id.imageView);
            viewBackground = view.findViewById(R.id.backGround);
            viewForeground = view.findViewById(R.id.foreGround);
        }
    }


    public recyclerAdapter(Context context, List<recyclerData> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final recyclerData item = cartList.get(position);
        holder.description.setText(item.getDescription());
        holder.firstLetter.setText(item.getFirstLetters());
        holder.thumbnail.setImageResource(item.getGradient());
        holder.time.setText(item.getTime());

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position) {
        cartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(recyclerData item, int position) {
        cartList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public void addItem(String str1, String str2, String time, int grd){
        cartList.add(new recyclerData(str1, str2, time, grd));
        notifyDataSetChanged();
    }
}