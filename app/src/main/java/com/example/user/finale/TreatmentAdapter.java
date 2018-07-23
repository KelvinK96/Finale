package com.example.user.finale;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.ViewHolder> {
    private List<ListDiseases> listDiseases;
    private Context context;

    Treatment treatment;

    public TreatmentAdapter(List<ListDiseases> listDiseases, Context context, Treatment treatment) {
        this.listDiseases = listDiseases;
        this.context = context;
        this.treatment = treatment;
    }

    @Override
    public TreatmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_treatment_disease, parent, false);
        return new TreatmentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TreatmentAdapter.ViewHolder holder, int position) {
        ListDiseases disease = listDiseases.get(position);
        final String dName = disease.getDiseaseName();
        final String dTreatment = disease.getDiseaseTreatment();

        holder.textViewDname.setText(dName);
        holder.textViewDtreatment.setText(dTreatment);
        holder.cardView_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    treatment.showTreatmentOnCard(dName, dTreatment);
                } catch (Exception e) {
                    Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDiseases.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDname, textViewDtreatment;
        public CardView cardView_t;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewDname = itemView.findViewById(R.id.tv_disease_name_t);
            textViewDtreatment = itemView.findViewById(R.id.tv_disease_treatment_t);
            cardView_t = itemView.findViewById(R.id.card_view_t);
        }
    }
}
