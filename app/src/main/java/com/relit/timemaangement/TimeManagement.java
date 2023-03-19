package com.relit.timemaangement;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.maltaisn.icondialog.pack.IconPack;
import com.maltaisn.icondialog.pack.IconPackLoader;
import com.maltaisn.iconpack.defaultpack.IconPackDefault;
import com.relit.timemaangement.domain.category.CategoryDatabase;
import com.relit.timemaangement.domain.semester.Semester;
import com.relit.timemaangement.domain.semester.SemesterDatabase;
import com.relit.timemaangement.util.Date;

public class TimeManagement extends Application {

    private static CategoryDatabase categoryDatabase;
    private static SemesterDatabase semesterDatabase;
    private static int currentSemesterID = -1;

    @Nullable
    private IconPack iconPack;

    @Override
    public void onCreate() {
        super.onCreate();

        // Load the icon pack on application start.
        loadIconPack();
        categoryDatabase = new CategoryDatabase(this);
        semesterDatabase = new SemesterDatabase(this);
        notifySemesterID();
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

    public static SemesterDatabase getSemesterDatabase() {
        return semesterDatabase;
    }

    public static CategoryDatabase getCategoryDatabase() {
        return categoryDatabase;
    }

    public static void notifySemesterID(){
        Semester latestSemester = semesterDatabase.getLatestSemester();
        if(latestSemester == null){
            currentSemesterID = -1;
            return;
        }
        Date currentDate = Date.getCurrentDate();
        if(latestSemester.getStartDate().isBefore(currentDate) && currentDate.isBefore(latestSemester.getEndDate())){
            currentSemesterID = latestSemester.getID();
        }else{
            currentSemesterID = -1;
        }
    }

    public static int getCurrentSemesterID(){
        return currentSemesterID;
    }
}