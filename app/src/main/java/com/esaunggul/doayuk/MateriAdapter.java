package com.esaunggul.doayuk;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.MyViewHolder> {

    private Context mContext;
    private List<MateriList> materiLists;
    private long mLastClickTime = 0;
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mis-clicking prevention, using threshold of 1000 ms
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                MateriList materi = materiLists.get(position);

                final String mata_pelajaran = materi.getKategori();
                final Integer Thumbnail = materi.getThumbnail();
                final Integer SubKategori = materi.getSubKategori();
                final String Kategori = materi.getKategori();
                final String Judul = materi.getJudul();

                Intent intent = new Intent(mContext, DetailMateriActivity.class);
                Bundle extras = new Bundle();

                extras.putString("PARAM_PELAJARAN", mata_pelajaran);
                extras.putInt("PARAM_THUMBNAIL", Thumbnail);

                extras.putString("PARAM_KATEGORI", Kategori);
                extras.putString("PARAM_JUDUL", Judul);
                extras.putInt("PARAM_SUBKATEGORI", SubKategori);

                intent.putExtras(extras);
                mContext.startActivity(intent);
            }
        };

    public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView judul, kategori, arab, latin, arti;
            public ImageView photo, cover;

            public MyViewHolder(View view) {
                super(view);
                itemView.setTag(this);
                itemView.setOnClickListener(onItemClickListener);
                judul = (TextView) view.findViewById(R.id.judul);
                kategori = (TextView) view.findViewById(R.id.kategori);
                arab = (TextView) view.findViewById(R.id.txtArab);
                latin = (TextView) view.findViewById(R.id.txtLatin);
                arti = (TextView) view.findViewById(R.id.txtArti);
                photo = (ImageView) view.findViewById(R.id.photo);
            }
        }


    public MateriAdapter(Context mContext, List<MateriList> materiLists) {
            this.mContext = mContext;
            this.materiLists = materiLists;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.material_card, parent, false); //materi_card

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            MateriList materi = materiLists.get(position);
            final String mata_pelajaran = materi.getKategori();
            final String kode_materi = materi.getKodeMateri();
            final String judul_materi = materi.getJudul();
            holder.judul.setText(judul_materi);

            holder.kategori.setText(mata_pelajaran);

            // loading album cover using Glide library
            Glide.with(mContext).load(materi.getThumbnail()).into(holder.photo);

            int isZeroCover = materi.getCover();
            //if (isZeroCover == 0){
                //holder.cover.setVisibility(View.INVISIBLE);
            //}
            //else
            //{
                //holder.cover.setVisibility(View.INVISIBLE);
            //}
        }

        @Override
        public int getItemCount() {
            return materiLists.size();
        }
    }

