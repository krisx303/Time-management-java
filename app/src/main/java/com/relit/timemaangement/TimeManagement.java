package com.relit.timemaangement;

import android.app.Application;

import androidx.annotation.Nullable;

import com.maltaisn.icondialog.pack.IconPack;
import com.maltaisn.icondialog.pack.IconPackLoader;
import com.maltaisn.iconpack.defaultpack.IconPackDefault;
import com.relit.timemaangement.database.CategoryDatabase;
import com.relit.timemaangement.ui.category.CategoryDatabaseHelper;

public class TimeManagement extends Application {

    private static CategoryDatabaseHelper categoryDatabase;
    private static CategoryDatabase catDb;

    @Nullable
    private IconPack iconPack;

    public static CategoryDatabase getCatDb() {
        return catDb;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Load the icon pack on application start.
        loadIconPack();

        categoryDatabase = new CategoryDatabaseHelper(this);
        catDb = new CategoryDatabase(this);
    }

    @Nullable
    public IconPack getIconPack() {
        return iconPack != null ? iconPack : loadIconPack();
    }

    private IconPack loadIconPack() {
        // Create an icon pack loader with application context.
        IconPackLoader loader = new IconPackLoader(this);

        // Create an icon pack and load all drawables.
        iconPack = IconPackDefault.createDefaultIconPack(loader);
        iconPack.loadDrawables(loader.getDrawableLoader());

        return iconPack;
    }

    public static CategoryDatabaseHelper getCategoryDatabase() {
        return categoryDatabase;
    }
}