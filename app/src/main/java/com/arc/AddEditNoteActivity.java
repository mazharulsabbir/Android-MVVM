package com.arc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.arc.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.arc.EXTRA_TITLE";

    public static final String EXTRA_DESC =
            "com.arc.EXTRA_DESC";

    public static final String EXTRA_PRIORITY =
            "com.arc.EXTRA_PRIORITY";

    private EditText title, description;
    private NumberPicker priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.edit_text_title);
        description = findViewById(R.id.edit_text_description);

        priority = findViewById(R.id.number_picker_priority);
        priority.setMinValue(1);
        priority.setMaxValue(10);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            title.setText(intent.getStringExtra(EXTRA_TITLE));
            description.setText(intent.getStringExtra(EXTRA_TITLE));
            priority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));

        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;

            case android.R.id.home:
                super.onBackPressed();

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void saveNote() {
        String mTitle, mDescription;
        int mPriority;

        mTitle = title.getText().toString().trim();
        mDescription = description.getText().toString().trim();
        mPriority = priority.getValue();

        if (mTitle.isEmpty() | mDescription.isEmpty()) {
            Toast.makeText(this, "All Fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, mTitle);
        data.putExtra(EXTRA_DESC, mDescription);
        data.putExtra(EXTRA_PRIORITY, mPriority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }
}
