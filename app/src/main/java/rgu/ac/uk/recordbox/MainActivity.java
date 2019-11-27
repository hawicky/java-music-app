package rgu.ac.uk.recordbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> mAlbumNames;
    ArrayList<String> mArtistNames;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAlbumNames = new ArrayList<>();
        mArtistNames = new ArrayList<>();


        //Logo Text colorSpan
        //select the word box and make it orange
        String app = getApplicationInfo().loadLabel(getPackageManager()).toString();
        SpannableString ss = new SpannableString(app);
        ForegroundColorSpan fcsOrange = new ForegroundColorSpan(getResources().getColor(R.color.colorOrange));
        ss.setSpan(fcsOrange,6,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setTitle(ss);

        //bottom nav
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new home_frag()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;


                    switch (item.getItemId()){
                        case R.id.home_nav:
                            selectedFragment = new home_frag();
                            break;
                        case R.id.search_nav:
                            selectedFragment = new search_frag();
                            break;
                        case R.id.contact_Nav:
                            selectedFragment = new contact_frag();
                            break;
                    }


                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                            selectedFragment).commit();

                    return true;
                }
            };


    private void initAlbum() {
        mAlbumNames.add("One");
        mAlbumNames.add("Two");
        mAlbumNames.add("Three");

        mArtistNames.add("The Killer");
        mArtistNames.add("Arctic Monekys");
        mArtistNames.add("Three Doors Down");

        initRecyclerView();
    }


    private void initRecyclerView(){
//        recyclerView = findViewById(R.id.recyclerView);
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mAlbumNames,mArtistNames);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this  ));
//        recyclerView.setAdapter(adapter);


    }


}
