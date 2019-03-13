package com.example.swimtracker;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private TeamFragment teamFragment;
    private LibraryFragment libraryFragment;
    private SwimFragment swimFragment;
    private NoteFragment noteFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);

        teamFragment = new TeamFragment();
        libraryFragment = new LibraryFragment();
        swimFragment = new SwimFragment();
        noteFragment = new NoteFragment();
        settingFragment = new SettingFragment();

        setFragment(teamFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_team:
                        setFragment(teamFragment);
                        return true;

                    case R.id.nav_library:
                        setFragment(libraryFragment);
                        return true;

                    case R.id.nav_swim:
                        setFragment(swimFragment);
                        return true;

                    case R.id.nav_note:
                        setFragment(noteFragment);
                        return true;

                    case R.id.nav_setting:
                        setFragment(settingFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
