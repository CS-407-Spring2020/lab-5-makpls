package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class mainNote extends AppCompatActivity {
    public static ArrayList<Note> notes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note);
        setTitle("Notes");
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        String welcomeMessage = "Welcome, " + username + "!";
        TextView welcome = findViewById(R.id.welcome);
        welcome.setText(welcomeMessage);
        // welcome message done
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper helper = new DBHelper(sqLiteDatabase);
        notes = helper.readNotes(username);
        ArrayList<String> displayNotes = new ArrayList<>();

        for (Note note: notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,displayNotes);
        ListView listView = findViewById(R.id.notes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), NotePage.class);
                intent.putExtra("noteid",position);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logout:
                Intent back = new Intent(this,MainActivity.class);
                SharedPreferences shared = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                shared.edit().remove("username").apply();
                startActivity(back);
                break;
            case R.id.add:
                Intent newNote = new Intent(this, NotePage.class);
                startActivity(newNote);
                break;

        }
        return true;
    }
}
