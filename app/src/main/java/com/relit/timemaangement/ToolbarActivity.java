package com.relit.timemaangement;

import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class ToolbarActivity extends AppCompatActivity {
    private ActionBar actionBar;

    protected void prepareToolbar(String title) {
        prepare();
        setToolbarTitle(title);
    }

    protected void prepareToolbar(int refTitle) {
        prepare();
        setToolbarTitle(refTitle);
    }

    private void prepare(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    protected void setToolbarTitle(String title){
        actionBar.setTitle(title);
    }

    protected void setToolbarTitle(int refTitle){
        actionBar.setTitle(refTitle);
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
