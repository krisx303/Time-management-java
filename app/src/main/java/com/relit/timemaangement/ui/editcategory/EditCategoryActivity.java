package com.relit.timemaangement.ui.editcategory;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.relit.timemaangement.R;
import com.relit.timemaangement.TimeManagement;
import com.relit.timemaangement.ui.category.CategoryPatternActivity;
import com.relit.timemaangement.ui.category.Category;
import com.relit.timemaangement.ui.category.CategoryDatabase;

public class EditCategoryActivity extends CategoryPatternActivity {

    private int categoryID;
    private Category previousCategory;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_category);
        super.onCreate(savedInstanceState);
        prepareToolbar("Edytuj kategorię");
        categoryID = getIntent().getIntExtra(CategoryDatabase.CATEGORY_ID, 1);
        Category category = TimeManagement.getCategoryDatabase().getCategoryByID(categoryID);
        editName.setText(category.getName());
        editShortcut.setText(category.getShortcut());
        setIconByID(category.getIconID());
        previousCategory = category;
        final View contentView = findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(this::onPostCreate);
        findViewById(R.id.delete_button).setOnClickListener(this::onDeleteClick);
    }

    private void onDeleteClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.confirmation);
        builder.setMessage("Czy na pewno chcesz usunąć kategorię " + previousCategory.getName() + "?\nSpowoduje to usunięcie wszystkich powiązanych z nią rzeczy!!!");

        builder.setPositiveButton("TAK", (dialog, which) -> {
            //TODO DELETE CATEGORY AND ALL DEPENDING STUFF
        });

        builder.setNegativeButton("Nie", (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onPostCreate() {
        colorPickerView.setPureColor(previousCategory.getColor());
        colorPickerView.fireColorListener(previousCategory.getColor(), false);
    }

    @Override
    protected void onConfirmed(View view) {
        String name = editName.getText().toString();
        String shortcut = editShortcut.getText().toString();
        if (name.trim().length() == 0 || shortcut.trim().length() == 0) {
            Toast.makeText(this, "Te pola nie mogą pozostać puste ://", Toast.LENGTH_SHORT).show();
            return;
        }
        Category category = new Category(categoryID, name, shortcut, iconID, color);
        CategoryDatabase categoryDatabase = TimeManagement.getCategoryDatabase();
        categoryDatabase.updateCategory(category);
        Toast.makeText(this, "Kategoria zaktualizowana", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}