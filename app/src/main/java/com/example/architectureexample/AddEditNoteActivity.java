package com.example.architectureexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.architectureexample.databinding.ActivityAddNoteBinding;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE =
            "com.example.architectureexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.architectureexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.architectureexample.EXTRA_PRIORITY";
    public static final String EXTRA_ID = "com.example.architectureexample.ID";


    private ActivityAddNoteBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        mBinding.numberPickerPriority.setMaxValue(10);
        mBinding.numberPickerPriority.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        getNoteFromIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = mBinding.editTextTitle.getText().toString();
        String description = mBinding.editTextDescription.getText().toString();
        int priority = mBinding.numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    private void getNoteFromIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            setTitle(getResources().getString(R.string.edit_note));
            mBinding.editTextTitle.setText(extras.getString(EXTRA_TITLE));
            mBinding.editTextDescription.setText(extras.getString(EXTRA_DESCRIPTION));
            mBinding.numberPickerPriority.setValue(extras.getInt(EXTRA_PRIORITY, 1));
        } else {
            setTitle(getResources().getString(R.string.add_note));
        }
    }


}
