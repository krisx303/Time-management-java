package com.relit.timemaangement.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maltaisn.icondialog.data.Icon;
import com.relit.timemaangement.R;
import com.relit.timemaangement.TimeManagement;
import com.relit.timemaangement.ui.category.Category;
import com.relit.timemaangement.ui.category.CategoryAdapter;
import com.relit.timemaangement.ui.category.OnCategoryClickListener;

import java.util.List;
import java.util.Map;

public class CategoryDialog extends Dialog {

    private RecyclerView recyclerView;
    private final OnCategoryClickListener listener;
    private final List<Category> categories;
    private final Map<Integer, Icon> icons;

    public CategoryDialog(@NonNull Context context, OnCategoryClickListener listener, Map<Integer, Icon> icons) {
        super(context);
        categories = TimeManagement.getCategoryDatabase().getAllElements();
        this.listener = listener;
        this.icons = icons;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_category);
        recyclerView = findViewById(R.id.recycler_view);
        Button cancelButton = findViewById(R.id.btn_cancel);
        cancelButton.setOnClickListener(v -> dismiss());
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        CategoryAdapter adapter = new CategoryAdapter(categories, this::onCategoryClick, icons, true);
        recyclerView.setAdapter(adapter);
    }

    private void onCategoryClick(Category category) {
        listener.onCategoryClick(category);
        dismiss();
    }
}