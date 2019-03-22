package com.mobile.azrinurvani.crudbiodata.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.azrinurvani.crudbiodata.EditDataActivity;
import com.mobile.azrinurvani.crudbiodata.R;
import com.mobile.azrinurvani.crudbiodata.model.DataBiodata;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BiodataAdapter extends RecyclerView.Adapter<BiodataAdapter.ViewHolder> {


    List<DataBiodata> dataBiodata;

    public BiodataAdapter(List<DataBiodata> dataBiodata) {
        this.dataBiodata = dataBiodata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_biodata, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textNama.setText(dataBiodata.get(position).getNama());
        holder.textJekel.setText(dataBiodata.get(position).getJekel());
        holder.textHobi.setText(dataBiodata.get(position).getHobi());
        holder.textAlamat.setText(dataBiodata.get(position).getAlamat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                aksi apabila item di klik
                Intent intent = new Intent(holder.itemView.getContext(),EditDataActivity.class);
                intent.putExtra("id",dataBiodata.get(position).getId());
                intent.putExtra("nama",dataBiodata.get(position).getNama());
                intent.putExtra("alamat",dataBiodata.get(position).getAlamat());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBiodata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textNama)
        TextView textNama;
        @BindView(R.id.textJekel)
        TextView textJekel;
        @BindView(R.id.textHobi)
        TextView textHobi;
        @BindView(R.id.textAlamat)
        TextView textAlamat;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
