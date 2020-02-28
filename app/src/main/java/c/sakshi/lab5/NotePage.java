package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotePage extends AppCompatActivity {
    int noteid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_page);
        setTitle("Notes");
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid",-1);
        if (noteid != -1) {
            Note note = mainNote.notes.get(noteid);
            String noteContent = note.getContent();
            EditText field = findViewById(R.id.notefield);
            field.setText(noteContent);
        }

    }
    public void onSave(View view) {
        EditText field = findViewById(R.id.notefield);
        String content = field.getText().toString();
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences shared = getSharedPreferences(getPackageName(),Context.MODE_PRIVATE);
        String username = shared.getString("username", "");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());
        String title;

        if(noteid == -1) {
            title = "NOTE_" + (mainNote.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid+1);
            dbHelper.updateNote(title, date, content);
        }
        Intent intent = new Intent(this, mainNote.class);
        intent.putExtra("USERNAME", username);
        startActivity(intent);
    }
}
