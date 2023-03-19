package com.relit.timemaangement.ui.abcalendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.relit.timemaangement.R;
import com.relit.timemaangement.domain.semester.Semester;

import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder> {
    private final List<Semester> semesters;
    private final int currentSemesterID;
   // private final OnCategoryClickListener listener;

    public SemesterAdapter(List<Semester> semesters, int currentSemesterID) {
        this.semesters = semesters;
        this.currentSemesterID = currentSemesterID;
    }

    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.semester_item, parent, false);

        return new SemesterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        Semester semester = semesters.get(position);
        holder.bind(semester, semester.getID() == currentSemesterID);
    }

    @Override
    public int getItemCount() {
        return semesters.size();
    }

    protected static class SemesterViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView period;
        private final MaterialCardView card;

        public SemesterViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.semester_name);
            period = itemView.findViewById(R.id.semester_period);
            card = itemView.findViewById(R.id.material_card);
        }

        public void bind(Semester semester, boolean isFlag) {
            name.setText(semester.getName());
            String text = semester.getStartDateAsString() + " - " + semester.getEndDateAsString();
            period.setText(text);
            if(isFlag)
                card.setCardBackgroundColor(Color.parseColor("#2b2670"));
        }
    }
}