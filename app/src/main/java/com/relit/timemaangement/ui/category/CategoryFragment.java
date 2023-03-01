package com.relit.timemaangement.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltaisn.icondialog.data.Icon;
import com.relit.timemaangement.R;
import com.relit.timemaangement.TimeManagement;
import com.relit.timemaangement.databinding.FragmentCategoryBinding;
import com.relit.timemaangement.ui.addcategory.AddCategoryActivity;
import com.relit.timemaangement.ui.editcategory.EditCategoryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private List<Category> categories = new ArrayList<>();
    private CategoryAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        FloatingActionButton fab = binding.getRoot().findViewById(R.id.add_new_category);
        fab.setOnClickListener(this::onAddCategoryClicked);
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recycler_view);
        categories = TimeManagement.getCategoryDatabase().getAllElements();
        categories.forEach(System.out::println);
        adapter = new CategoryAdapter(categories, this::onCategoryClick, getIcons());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    private void onCategoryClick(Category category) {
        Intent intent = new Intent(getActivity(), EditCategoryActivity.class);
        intent.putExtra(CategoryDatabase.CATEGORY_ID, category.getId());
        startActivity(intent);
    }


    @Nullable
    public Map<Integer, Icon> getIcons() {
        return ((TimeManagement) getActivity().getApplication()).getIconPack().getIcons();
    }

    private void onAddCategoryClicked(View view) {
        Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
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
        categories.clear();
        categories.addAll(TimeManagement.getCategoryDatabase().getAllElements());
        adapter.notifyDataSetChanged();
    }
}