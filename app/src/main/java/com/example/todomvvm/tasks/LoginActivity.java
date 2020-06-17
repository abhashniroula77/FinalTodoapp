package com.example.todomvvm.tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Button signIn;
    private Button signUp;
    private EditText userEmail;
    private EditText userPassword;
    private AppDatabase database;
    private TaskDao taskDao;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Checking user");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        database = Room.databaseBuilder(this,AppDatabase.class,"abhash-database.db")
                .allowMainThreadQueries()

                .build();
        taskDao = database.taskDao();
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

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emptyValidation()){
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = taskDao.getUser(userEmail.getText().toString(),userPassword.getText().toString());
                            if(user!=null){
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                i.putExtra("User",user);
                                startActivity(i);
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this,"ID password Error",Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    },1000);
                }


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

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();

    }
}
