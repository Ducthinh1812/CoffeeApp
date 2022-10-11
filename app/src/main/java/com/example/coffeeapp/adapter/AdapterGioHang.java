package com.example.coffeeapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeapp.Model.GioHang;
import com.example.coffeeapp.Model.SanPham;
import com.example.coffeeapp.R;
import com.example.coffeeapp.activity.GioHangActivity;
import com.example.coffeeapp.fragment.HomeFragment;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterGioHang extends BaseAdapter {
    Context context;
    ArrayList<GioHang> GioHangArrayList;
    ViewHolderGH viewHolders;

    public AdapterGioHang(Context context, ArrayList<GioHang> GioHangArrayList) {
        this.context = context;
        this.GioHangArrayList = GioHangArrayList;
    }

    @Override
    public int getCount() {
        return GioHangArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return GioHangArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        viewHolders = null;
        if (view == null) {
            viewHolders = new ViewHolderGH();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_gio_hang, null);
            viewHolders.txttengiohang = view.findViewById(R.id.tvtenGH);
            viewHolders.txtgiagiohang = view.findViewById(R.id.tvGiaGH);
            viewHolders.imggiohang = view.findViewById(R.id.imgGH);
            viewHolders.btnGiaTri = view.findViewById(R.id.tvSoluongGH);
            viewHolders.btncong = view.findViewById(R.id.buttoncong);
            viewHolders.btntru = view.findViewById(R.id.buttontru);
            view.setTag(viewHolders);
        } else {
            viewHolders = (ViewHolderGH) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolders.txttengiohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolders.txtgiagiohang.setText("Giá: " + decimalFormat.format(gioHang.getGiasp()) + " VND");
        Picasso.get().load(gioHang.getHinhsp())
                .into(viewHolders.imggiohang);
        viewHolders.btnGiaTri.setText(Integer.toString(gioHang.getSoluongsp()));
        int sl = Integer.parseInt(viewHolders.btnGiaTri.getText().toString());
        viewHolders.btncong.setOnClickListener(v -> {
            int slmoi = Integer.parseInt(viewHolders.btnGiaTri.getText().toString()) + 1;
            int slht = HomeFragment.giohanglist.get(i).getSoluongsp();
            long giaht = HomeFragment.giohanglist.get(i).getGiasp();
            HomeFragment.giohanglist.get(i).setSoluongsp(slmoi);
            long giamoi = (giaht * slmoi) / slht;
            HomeFragment.giohanglist.get(i).setGiasp(giamoi);
            viewHolders.txtgiagiohang.setText("Giá: " + decimalFormat.format(giamoi) + " VND");
            GioHangActivity.UpdateTongTien();
            viewHolders.btnGiaTri.setText(Integer.toString(slmoi));
        });
        viewHolders.btntru.setOnClickListener(v -> {
            int slmoi = Integer.parseInt(viewHolders.btnGiaTri.getText().toString()) - 1;
            int slht = HomeFragment.giohanglist.get(i).getSoluongsp();
            long giaht = HomeFragment.giohanglist.get(i).getGiasp();
            long giamoi = (giaht * slmoi) / slht;
            if (slht == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomeFragment.giohanglist.remove(i);
                        GioHangActivity.loadlistview();
                        GioHangActivity.UpdateTongTien();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GioHangActivity.UpdateTongTien();
                        GioHangActivity.loadlistview();
                    }
                });
                builder.show();
            } else {
                HomeFragment.giohanglist.get(i).setSoluongsp(slmoi);
                HomeFragment.giohanglist.get(i).setGiasp(giamoi);
                viewHolders.txtgiagiohang.setText("Giá: " + decimalFormat.format(giamoi) + " VND");
                GioHangActivity.UpdateTongTien();
                viewHolders.btnGiaTri.setText(Integer.toString(slmoi));
            }

        });
        return view;
    }

    public class ViewHolderGH {
        TextView txttengiohang, txtgiagiohang, btnGiaTri;
        ImageView imggiohang, XoaSanPham;
        Button btncong, btntru;
    }
}
