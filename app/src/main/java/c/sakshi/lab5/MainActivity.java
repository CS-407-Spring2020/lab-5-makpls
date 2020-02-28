package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences shared = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Notes");
        String user = shared.getString("username", "");
        if(user.equals("")) {
            setContentView(R.layout.activity_main);
        } else {
            Intent mainNote = new Intent(this, mainNote.class);
            mainNote.putExtra("USERNAME", user);
            startActivity(mainNote);
        }
    }


    public void loginClick(View view){
        SharedPreferences shared = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        Intent mainNote = new Intent(this, mainNote.class);
        EditText username = findViewById(R.id.username);
        String message = username.getText().toString();
        shared.edit().putString("username", message).apply();
        mainNote.putExtra("USERNAME", message);
        startActivity(mainNote);
    }
}
