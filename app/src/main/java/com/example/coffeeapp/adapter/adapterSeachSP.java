package com.example.coffeeapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.activity.ChiTietSanPham;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class adapterSeachSP extends RecyclerView.Adapter<adapterSeachSP.SeachSPHolder> {
    Context context;
    ArrayList<SanPham> SanPhamArrayList;

    public adapterSeachSP(Context context, ArrayList<SanPham> SanPhamArrayList) {
        this.context = context;
        this.SanPhamArrayList = SanPhamArrayList;
    }

    @NonNull
    @Override
    public SeachSPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);
        return new SeachSPHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterSeachSP.SeachSPHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham timKiem = SanPhamArrayList.get(position);

        holder.tvtenSP.setText(timKiem.getTenSP());
        Picasso.get().load(timKiem.getHinhanhSP())
                .into(holder.imgSP);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvgiaban.setText("Giá: " + decimalFormat.format(timKiem.getGiaBan()) + " đ");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPham.class);
                intent.putExtra("data", SanPhamArrayList.get(position));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return SanPhamArrayList.size();
    }

    public class SeachSPHolder extends RecyclerView.ViewHolder {
        ImageView imgSP;
        TextView tvtenSP, tvgiaban;
        CardView cardView;

        public SeachSPHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CarviewSP);
            imgSP = (ImageView) itemView.findViewById(R.id.imgSP);
            tvtenSP = (TextView) itemView.findViewById(R.id.tvtenSP);
            tvgiaban = (TextView) itemView.findViewById(R.id.tvGiaSP);
        }
    }
}