package com.example.todomvvm.tasks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;

import com.example.todomvvm.database.TaskEntry;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {



    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if(position == 0){
             return  null;
        }
        else if(position ==1)
        {
            return null;
        }
        else {
             return null;
        }
    }

    @Override
    public int getCount() {
        return 2 ;
    }
}
