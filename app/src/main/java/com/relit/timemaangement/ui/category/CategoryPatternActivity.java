package com.relit.timemaangement.ui.category;

import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;
import com.maltaisn.icondialog.data.Icon;
import com.maltaisn.icondialog.filter.DefaultIconFilter;
import com.maltaisn.icondialog.pack.IconPack;
import com.relit.timemaangement.R;
import com.relit.timemaangement.TimeManagement;
import com.relit.timemaangement.ToolbarActivity;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorListener;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

import java.util.List;
import java.util.Random;

public abstract class CategoryPatternActivity extends ToolbarActivity implements IconDialog.Callback {
    protected static final String ICON_DIALOG_TAG = "icon-dialog";
    protected ImageView icon;
    protected EditText editName, editShortcut;
    protected int color, iconID, noIcons;
    protected Random random;
    protected IconDialog iconDialog;
    protected ColorPickerView colorPickerView;
    protected BrightnessSlideBar slideBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = new Random();
        findViews();
        prepareIconDialog();
        assert getIconDialogIconPack() != null;
        noIcons = getIconDialogIconPack().getAllIcons().size();
        colorPickerView.attachBrightnessSlider(slideBar);
        colorPickerView.setColorListener((ColorListener) this::onColorChosen);
        icon.setOnClickListener(this::onIconClicked);
        findViewById(R.id.confirm_button).setOnClickListener(this::onConfirmed);
        findViewById(R.id.random_icon).setOnClickListener(this::onRandomClicked);
    }

    protected abstract void onConfirmed(View view);

    private void findViews() {
        colorPickerView = findViewById(R.id.colorPickerView);
        slideBar = findViewById(R.id.brightnessSlide);
        icon = findViewById(R.id.category_icon);
        editName = findViewById(R.id.edit_semester_name);
        editShortcut = findViewById(R.id.edit_category_shortcut);
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
        setIconColor(selectedColor);
    }

    private void setIconColor(int color){
        icon.setColorFilter(new LightingColorFilter(icon.getSolidColor(), color));
        this.color = color;
    }

    private void onIconClicked(View view) {
        iconDialog.show(getSupportFragmentManager(), ICON_DIALOG_TAG);
    }

    @Override
    public void onIconDialogCancelled() { }

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

    protected void setIconByID(int id) {
        Icon iconSrc;
        if (getIconDialogIconPack() != null && (iconSrc = getIconDialogIconPack().getIcon(id)) != null)
            icon.setImageDrawable(iconSrc.getDrawable());
        iconID = id;
    }

    protected void onRandomClicked(View view) {
        int randomID = random.nextInt(noIcons);
        setIconByID(randomID);
    }
}
