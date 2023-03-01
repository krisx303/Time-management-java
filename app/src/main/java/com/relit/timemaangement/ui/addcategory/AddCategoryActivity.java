package com.relit.timemaangement.ui.addcategory;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.relit.timemaangement.R;
import com.relit.timemaangement.TimeManagement;
import com.relit.timemaangement.ui.category.CategoryPatternActivity;
import com.relit.timemaangement.ui.category.Category;
import com.relit.timemaangement.ui.category.CategoryDatabase;

public class AddCategoryActivity extends CategoryPatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_category);
        super.onCreate(savedInstanceState);
        prepareToolbar("Stwórz nową kategorię");
        onRandomClicked(null);
    }

    @Override
    protected void onConfirmed(View view) {
        String name = editName.getText().toString();
        String shortcut = editShortcut.getText().toString();
        if (name.trim().length() == 0 || shortcut.trim().length() == 0) {
            Toast.makeText(this, "Te pola nie mogą pozostać puste ://", Toast.LENGTH_SHORT).show();
            return;
        }
        Category category = new Category(name, shortcut, iconID, color);
        CategoryDatabase database = TimeManagement.getCategoryDatabase();
        boolean b = database.addElement(category);
        if (b) {
            Toast.makeText(this, "Kategoria dodana", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}