package com.example.todomvvm.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todomvvm.addedittask.AddEditTaskActivity;
import com.example.todomvvm.R;
import com.example.todomvvm.database.AppDatabase;
import com.example.todomvvm.database.Repository;
import com.example.todomvvm.database.TaskEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener {



    //Initializing variables



    // Constant for logging
    private static final String TAG = MainActivity.class.getSimpleName();
    // Member variables for the adapter and RecyclerView
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private String userTaskId;


    MainActivityViewModel viewModel;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);

        MenuItem searchitem = menu.findItem(R.id.search_btn);
        SearchView searchView= (SearchView) searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
                case R.id.item:
                    viewModel.deleteAllNotes();
                    Toast.makeText(MainActivity.this,"All Notes Deleted",Toast.LENGTH_SHORT).show();
                return  true;
                case R.id.item2:
                Toast.makeText(this,"item 2 Selected",Toast.LENGTH_SHORT).show();
                return true;
                case R.id.byDate:
//                    Intent i = new Intent(MainActivity.this,ActivityUser.class);
//
//                    startActivity(i);
//                    finish();
                Toast.makeText(this,"Sort by date",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.byp:
                Toast.makeText(this,"Sort by p",Toast.LENGTH_SHORT).show();
                return true;

            case R.id.logout:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));


                Toast.makeText(this,"Logout completed",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.priorityCheck:

                    return true;




            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        userTaskId = getIntent().getStringExtra("userId");
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);





        // Set the RecyclerView to its corresponding view

        mRecyclerView = findViewById(R.id.recyclerViewTasks);




        // Set the layout for the RecyclerView to be a linear layout, which measures and
        // positions items within a RecyclerView into a linear list
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete

                int position = viewHolder.getAdapterPosition();
                List<TaskEntry> todoList = mAdapter.getTasks();
                viewModel.deleteTask(todoList.get(position));
                Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);

        /*
         Set the Floating Action Button (FAB) to its corresponding View.
         Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        FloatingActionButton fabButton = findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddEditTaskActivity.class);
                addTaskIntent.putExtra("userId",userTaskId);
                startActivity(addTaskIntent);
            }
        });

        viewModel.getTasks(Integer.parseInt(userTaskId)).observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(List<TaskEntry> taskEntries) {
                mAdapter.setTasks(taskEntries);
            }
        });


    }


    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskActivity.EXTRA_TASK_ID, itemId);
        intent.putExtra("userId",userTaskId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));

    }
}
