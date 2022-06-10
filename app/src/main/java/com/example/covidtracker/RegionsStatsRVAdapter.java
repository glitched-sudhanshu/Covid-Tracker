package com.example.covidtracker;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import java.util.ArrayList;
import java.util.List;

public class RegionsStatsRVAdapter extends RecyclerView.Adapter<RegionsStatsRVAdapter.ViewHolder> {

    private List<Model> stateStats;
    private final Context context;

    public RegionsStatsRVAdapter(List<Model> stateStats, Context context) {
        this.stateStats = stateStats;
        this.context = context;
    }

    @NonNull
    @Override
    public RegionsStatsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rv_list_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull RegionsStatsRVAdapter.ViewHolder holder, int position) {
        Model stateModel = stateStats.get(position);
        holder.stateName.setText(stateModel.getState());
        holder.districtName.setText(stateModel.getDistrict());
        holder.textTotalCases.setText(String.valueOf(stateModel.getCases()));
        holder.textTotalRecovery.setText(String.valueOf(stateModel.getRecovered()));
        holder.textTotalDeaths.setText(String.valueOf(stateModel.getDeaths()));
        holder.textActiveCases.setText(String.valueOf(stateModel.getActive()));
        holder.textMigratedOthers.setText(String.valueOf(stateModel.getMigratedOthers()));

        //hidden drop menu functionality
        holder.dropDown.setOnClickListener(v -> {
            stateModel.setExpanded(true);
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandableLayout.setVisibility(View.VISIBLE);
            holder.dropDown.setVisibility(View.GONE);

        });

        holder.dropUp.setOnClickListener(v -> {
            stateModel.setExpanded(false);
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandableLayout.setVisibility(View.GONE);
            holder.dropDown.setVisibility(View.VISIBLE);
        });

    }

    //setting up state lists
    public void setStateStats(List<Model> setStateStats) {
        this.stateStats = setStateStats;
        notifyDataSetChanged();
    }

    //changing filtered list
    public void filterList(ArrayList<Model> filteredList) {
        stateStats = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return stateStats.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView stateName, textTotalCases, textTotalRecovery, textTotalDeaths, districtName, textActiveCases, textMigratedOthers;

        private final ConstraintLayout expandableLayout;

        private final ImageButton dropDown, dropUp;

        private final CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stateName = itemView.findViewById(R.id.stateName);
            textTotalCases = itemView.findViewById(R.id.textTotalCases);
            textTotalRecovery = itemView.findViewById(R.id.textTotalRecovery);
            textTotalDeaths = itemView.findViewById(R.id.textTotalDeaths);
            districtName = itemView.findViewById(R.id.districtName);
            textActiveCases = itemView.findViewById(R.id.textActiveCases);
            textMigratedOthers = itemView.findViewById(R.id.textMigratedOthers);
            dropDown = itemView.findViewById(R.id.dropDown);
            dropUp = itemView.findViewById(R.id.dropUp);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            parent = itemView.findViewById(R.id.parent);

        }
    }
}
