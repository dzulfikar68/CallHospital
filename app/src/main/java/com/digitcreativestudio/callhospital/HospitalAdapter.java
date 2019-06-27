package com.digitcreativestudio.callhospital;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>{

    private Context context;
    private List<HospitalModel> list;
    RecyclerView mRecyclerView;

    public HospitalAdapter(Context context, List<HospitalModel> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.hospital_adapter, viewGroup,false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HospitalViewHolder itemMarketViewHolder, int i) {
        final HospitalModel model = list.get(i);
        itemMarketViewHolder.name.setText(model.getName());
        itemMarketViewHolder.region.setText(model.getRegion());
        Glide.with(context).load(model.getPhoto()).into(itemMarketViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HospitalViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView name, region;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            region = itemView.findViewById(R.id.region);
            image = itemView.findViewById(R.id.photo);
        }
    }
}
