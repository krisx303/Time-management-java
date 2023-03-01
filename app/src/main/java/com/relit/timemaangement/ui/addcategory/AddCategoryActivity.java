package com.relit.timemaangement.ui.addcategory;

import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;
import com.maltaisn.icondialog.data.Icon;
import com.maltaisn.icondialog.filter.DefaultIconFilter;
import com.maltaisn.icondialog.pack.IconPack;
import com.relit.timemaangement.ui.category.Category;
import com.relit.timemaangement.ui.category.CategoryDatabaseHelper;
import com.relit.timemaangement.R;
import com.relit.timemaangement.TimeManagement;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorListener;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

import java.util.List;
import java.util.Random;

public class AddCategoryActivity extends AppCompatActivity implements IconDialog.Callback {

    private static final String ICON_DIALOG_TAG = "icon-dialog";
    private ImageView icon;
    private EditText editName, editShortcut;
    private int color, iconID, noIcons;
    private Random random;
    private IconDialog iconDialog;
    private ColorPickerView colorPickerView;
    private BrightnessSlideBar slideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        random = new Random();
        findViews();
        prepareToolbar();
        prepareIconDialog();
        colorPickerView.attachBrightnessSlider(slideBar);
        colorPickerView.setColorListener((ColorListener) this::onColorChosen);
        icon.setOnClickListener(this::onoIconClicked);
        findViewById(R.id.confirm_button).setOnClickListener(this::onConfirmed);
        noIcons = getIconDialogIconPack().getAllIcons().size();
        findViewById(R.id.random_icon).setOnClickListener(this::onRandomClicked);
        onRandomClicked(null);
    }

    private void prepareIconDialog() {
        IconDialog dialog = (IconDialog) getSupportFragmentManager().findFragmentByTag(ICON_DIALOG_TAG);
        iconDialog = dialog != null ? dialog
                : IconDialog.newInstance(new IconDialogSettings.Builder().build());
        iconDialog.settings = createDialogSettings();
    }

    private IconDialogSettings createDialogSettings() {
        return new IconDialogSettings(
                new DefaultIconFilter(),
                IconDialog.SearchVisibility.ALWAYS,
                IconDialog.HeadersVisibility.STICKY,
                IconDialog.TitleVisibility.ALWAYS,
                R.string.app_name,
                1, false, false, false);
    }

    private void onColorChosen(int selectedColor, boolean fromUser) {
        icon.setColorFilter(new LightingColorFilter(icon.getSolidColor(), selectedColor));
        color = selectedColor;
    }

    private void findViews() {
        colorPickerView = findViewById(R.id.colorPickerView);
        slideBar = findViewById(R.id.brightnessSlide);
        icon = findViewById(R.id.category_icon);
        editName = findViewById(R.id.edit_category_name);
        editShortcut = findViewById(R.id.edit_category_shortcut);
    }

    private void onoIconClicked(View view) {
        iconDialog.show(getSupportFragmentManager(), ICON_DIALOG_TAG);
    }

    private void prepareToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Stwórz nową kategorię");
    }

    private void setIconByID(int id) {
        Icon iconSrc;
        if (getIconDialogIconPack() != null && (iconSrc = getIconDialogIconPack().getIcon(id)) != null)
            icon.setImageDrawable(iconSrc.getDrawable());
        iconID = id;
    }

    private void onRandomClicked(View view) {
        int randomID = random.nextInt(noIcons);
        setIconByID(randomID);
    }

    private void onConfirmed(View view) {
        String name = editName.getText().toString();
        String shortcut = editShortcut.getText().toString();
        if (name.trim().length() == 0 || shortcut.trim().length() == 0) {
            Toast.makeText(this, "Te pola nie mogą pozostać puste ://", Toast.LENGTH_SHORT).show();
            return;
        }
        Category category = new Category(name, shortcut, iconID, color);
        CategoryDatabaseHelper categoryDatabase = TimeManagement.getCategoryDatabase();
        boolean b = categoryDatabase.addElement(category);
        if (b) {
            Toast.makeText(this, "Kategoria dodana", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @Nullable
    @Override
    public IconPack getIconDialogIconPack() {
        return ((TimeManagement) getApplication()).getIconPack();
    }

    @Override
    public void onIconDialogIconsSelected(@NonNull IconDialog dialog, @NonNull List<Icon> icons) {
        icon.setImageDrawable(icons.get(0).getDrawable());
        iconID = icons.get(0).getId();
    }

    @Override
    public void onIconDialogCancelled() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}