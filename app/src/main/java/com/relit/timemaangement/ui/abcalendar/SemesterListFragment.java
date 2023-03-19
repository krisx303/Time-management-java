package com.relit.timemaangement.ui.abcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.relit.timemaangement.R;
import com.relit.timemaangement.TimeManagement;
import com.relit.timemaangement.databinding.FragmentSemesterListBinding;
import com.relit.timemaangement.domain.semester.Semester;
import com.relit.timemaangement.ui.addcategory.AddCategoryActivity;
import com.relit.timemaangement.util.Date;

import java.util.ArrayList;
import java.util.List;

public class SemesterListFragment extends Fragment {

    private FragmentSemesterListBinding binding;
    private List<Semester> semesters = new ArrayList<>();
    private SemesterAdapter adapter;
    private int currentSemesterID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSemesterListBinding.inflate(inflater, container, false);
        FloatingActionButton fab = binding.getRoot().findViewById(R.id.add_new_category);
        fab.setOnClickListener(this::onAddSemesterClicked);
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recycler_view);
        semesters = TimeManagement.getSemesterDatabase().getAllElements();
        currentSemesterID = TimeManagement.getCurrentSemesterID();
        adapter = new SemesterAdapter(semesters, currentSemesterID);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    private void onAddSemesterClicked(View view) {
        Semester semester = TimeManagement.getSemesterDatabase().getLatestSemester();
        if(semester != null){
            if(Date.getCurrentDate().isBefore(semester.getStartDate())){
                Toast.makeText(getContext(), "Moze być tylko jeden zaplanowany semestr!", Toast.LENGTH_LONG).show();
                return;
            }else if(semester.getEndDate().isBefore(Date.getCurrentDate())){
                Toast.makeText(getContext(), "Poprzedni semestr się jeszcze nie skończył!", Toast.LENGTH_LONG).show();
                return;
            }
        }
        Intent intent = new Intent(getActivity(), AddABCalendarActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        semesters.clear();
        semesters.addAll(TimeManagement.getSemesterDatabase().getAllElements());
        adapter.notifyDataSetChanged();
    }
}