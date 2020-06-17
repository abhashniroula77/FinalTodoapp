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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.todomvvm.R;
import com.example.todomvvm.database.AppDatabase;
import com.example.todomvvm.database.TaskDao;
import com.example.todomvvm.database.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText password;

    private Button registerButton;
    private Button cancelButton;

    private TaskDao taskDaoO;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        name = findViewById(R.id.register_name);
        lastName = findViewById(R.id.register_lastName);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_user);
        cancelButton = findViewById(R.id.cancel_user);

        taskDaoO = Room.databaseBuilder(this, AppDatabase.class, "abhash-database.db")
                .allowMainThreadQueries()
                .build()
                .taskDao();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEmpty())
                {
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(name.getText().toString(),lastName.getText().toString(),email.getText().toString(),password.getText().toString());
                           taskDaoO.insert(user);
                            progressDialog.dismiss();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        }
                    },1000);
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Empty Fields",Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
    private boolean isEmpty()
    {
        if(TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(lastName.getText().toString())||TextUtils.isEmpty(email.getText().toString())||TextUtils.isEmpty(password.getText().toString())){
            return  true;
        }
        else
        {
            return  false;
        }
    }
}
