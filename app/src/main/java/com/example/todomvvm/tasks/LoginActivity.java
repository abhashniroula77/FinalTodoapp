package com.example.todomvvm.tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todomvvm.R;
import com.example.todomvvm.database.AppDatabase;
import com.example.todomvvm.database.TaskDao;
import com.example.todomvvm.database.User;
import com.example.todomvvm.database.userDao;
import com.example.todomvvm.database.userRepo;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Button signIn;
    private Button signUp;
    private EditText userEmail;
    private EditText userPassword;
    private AppDatabase database;
    private com.example.todomvvm.database.userDao userDao;
    private ProgressDialog progressDialog;
    com.example.todomvvm.database.userRepo userRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Checking user");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        database = AppDatabase.getInstance(this);

        userRepo = new userRepo(database);
        signIn = findViewById(R.id.SignIn);
        signUp = findViewById(R.id.Register);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_Password);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });


    }
    private boolean emptyValidation()
    {
        if (TextUtils.isEmpty(userEmail.getText().toString())||TextUtils.isEmpty(userPassword.getText().toString())) {

            return true;
        }
        else
        {
            return false;
        }
    }

private class AsyncLogin extends AsyncTask<Void,Void,Void>{

    @Override
    protected Void doInBackground(Void... voids) {
        int userId = userRepo.getUser(userEmail.getText().toString(), userPassword.getText().toString());
        if (userId != 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userId", Integer.toString(userId));
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    }
    public void login(View view) {
        new AsyncLogin().execute();
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();

    }
}
