package com.relit.timemaangement.ui.category;

import android.graphics.LightingColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maltaisn.icondialog.data.Icon;
import com.relit.timemaangement.R;

import java.util.List;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private final List<Category> categories;
    private final OnCategoryClickListener listener;
    private final Map<Integer, Icon> icons;
    private final boolean isDialogElement;

    public CategoryAdapter(List<Category> categories, OnCategoryClickListener listener, Map<Integer, Icon> icons) {
        this.categories = categories;
        this.listener = listener;
        this.icons = icons;
        this.isDialogElement = false;
    }

    public CategoryAdapter(List<Category> categories, OnCategoryClickListener listener, Map<Integer, Icon> icons, boolean isDialogElement) {
        this.categories = categories;
        this.listener = listener;
        this.icons = icons;
        this.isDialogElement = isDialogElement;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        Icon icon = icons.get(category.getIconID());
        if(icon != null)
        holder.bind(category, listener, icon);
        if(isDialogElement)
            holder.itemView.setOnClickListener(v -> listener.onCategoryClick(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    protected class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView shortcut;
        private final ImageButton edit;
        private final ImageView iconView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.category_name);
            shortcut = itemView.findViewById(R.id.category_shortcut);
            edit = itemView.findViewById(R.id.edit_button);
            iconView = itemView.findViewById(R.id.category_icon);
            if(CategoryAdapter.this.isDialogElement){
                edit.setVisibility(View.INVISIBLE);
            }
        }

        public void bind(Category category, OnCategoryClickListener listener, Icon icon) {
            name.setText(category.getName());
            shortcut.setText(category.getShortcut());
            iconView.setImageDrawable(icon.getDrawable());
            iconView.setColorFilter(new LightingColorFilter(iconView.getSolidColor(), category.getColor()));
            if(!CategoryAdapter.this.isDialogElement){
                edit.setOnClickListener((v) -> listener.onCategoryClick(category));
            }
        }
    }
}