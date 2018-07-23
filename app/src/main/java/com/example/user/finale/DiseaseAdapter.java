package com.example.user.finale;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.ViewHolder> {
    private List<ListDiseases> listDiseases;
    private Context context;
    private DiseaseInfo diseaseInfo;

    private CardView cardView;

    public DiseaseAdapter(List<ListDiseases> listDiseases, Context context, DiseaseInfo diseaseInfo) {
        this.listDiseases = listDiseases;
        this.context = context;
        this.diseaseInfo = diseaseInfo;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_diseases, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListDiseases disease = listDiseases.get(position);
        final String dName = disease.getDiseaseName();
        final String dDesc = disease.getDiseaseDesc();
        String dSymptom = disease.getDiseaseSymptoms();
        String dTreatment = disease.getDiseaseTreatment();

        holder.textViewDname.setText(dName);
        holder.textViewDdesc.setText(dDesc);
        holder.textViewDsymptoms.setText(dSymptom);
        holder.textViewDtreatment.setText(dTreatment);
        holder.cardView_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    diseaseInfo.categoryPopup();
//                    diseaseInfo.showDescriptionOnCard(dName, dDesc);
                } catch (Exception e) {
                    //Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
                    //e.printStackTrace();
                    Log.e("kamaa","this is the error " + e);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDiseases.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDname, textViewDdesc, textViewDsymptoms, textViewDtreatment;
        CardView cardView_d;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewDname = itemView.findViewById(R.id.tv_disease_name);
            textViewDdesc = itemView.findViewById(R.id.tv_disease_description);
            textViewDsymptoms = itemView.findViewById(R.id.tv_disease_symptoms);
            textViewDtreatment = itemView.findViewById(R.id.tv_disease_treatment);
            cardView_d = itemView.findViewById(R.id.card_view_d);
        }
    }

}

