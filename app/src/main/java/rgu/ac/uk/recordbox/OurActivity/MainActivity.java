package rgu.ac.uk.recordbox.OurActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rgu.ac.uk.recordbox.Fragment.home_frag;
import rgu.ac.uk.recordbox.Fragment.search_frag;
import rgu.ac.uk.recordbox.OurData.OurData3;
import rgu.ac.uk.recordbox.R;
import rgu.ac.uk.recordbox.jsonObjects.Items;

//volley
//spotify
//JSON
//own java files



public class MainActivity extends AppCompatActivity {
    ArrayList<String> mAlbumNames;
    ArrayList<String> mArtistNames;

    //arraylist for json object
    private ArrayList<Items> allItemsData = new ArrayList<>();

    // Spotify fields
    private static final String CLIENT_ID = "550008f745f044b7a3a4897a11dd0958";
    private static final String REDIRECT_URI = "http://localhost:4002/";
    private static final int REQUEST_CODE = 1337;

    public static String searchContent = "Justin Bieber"; //string to be used by search bar

    // Local Storage
    private SharedPreferences.Editor editor;

    // Request output
    JSONObject output = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intent to open search_activity
        BottomNavigationView navigationInt = (BottomNavigationView) findViewById(R.id.bottom_nav);
        View view = navigationInt.findViewById(R.id.contact_Nav);
        //view.performClick();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity();
            }
        });





        mAlbumNames = new ArrayList<>();
        mArtistNames = new ArrayList<>();

        // Get Token from Spotify
        getTokenFromSpotify();

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

    public void OpenActivity (){
        Intent kampIntent = new Intent(this, ContactActivity.class);
        startActivity(kampIntent);
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
                    }


                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // And we will finish off here.
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    Toast.makeText(getApplicationContext(),"Successfully logged into Spotify",Toast.LENGTH_SHORT).show();
                    editor = getSharedPreferences("SPOTIFY", 0).edit();
                    editor.putString("token", response.getAccessToken());
                    Log.d("STARTING", "GOT AUTH TOKEN");
                    Log.d("STARTING", response.getAccessToken());
                    editor.apply();

                    //url with request
                    searchContent = searchContent.replaceAll("\\s+", "+");
                    if(searchContent.equals("+")){
                        searchContent = "song";
                    }
                    Log.d("REPLACEMENT", searchContent);


                    //request url
                    String url = "https://api.spotify.com/v1/search?q=" + searchContent + "&type=track&limit=5";

                    //ACTION LISTENER SHOULD BE IMPLEMENTED IN THE SEARCH ACTIVITY FOR FOLLOWING CODE
                    JsonObjectRequest getRequest = new JsonObjectRequest(
                            Request.Method.GET,
                            url,
                            null,
                            new Response.Listener<JSONObject>(){

                                @Override
                                public void onResponse(JSONObject requestResponse) {
                                    Log.d("REQUEST", "WE DID IT");
                                    Log.d("REQUEST", requestResponse.toString());
                                    output = requestResponse;

                                    //handling the output starts here
                                    //TODO saving the output to memory

                                    try {
                                        JSONArray outputarray = requestResponse.getJSONObject("tracks").getJSONArray("items");
                                        allItemsData.clear();

                                        //adding items to arraylist
                                        for(int i=0 ; i <outputarray.length() ; i++){
                                            JSONObject data = outputarray.getJSONObject(i);
                                            Items item = new Items(data);
                                            //ArrayList of Items objects
                                            allItemsData.add(item);
                                        }

                                    }catch (JSONException e){
                                        e.toString();
                                    }
                                    //test output
                                    String songName = allItemsData.get(0).name;
                                    Log.d("REQUEST2", songName);
                                    String artistName = allItemsData.get(0).allArtistName.get(0).name;
                                    Log.d("REQUEST3", artistName);

                                    //automated output
                                    //clearing display
                                    OurData3.albumT.clear();
                                    OurData3.artistN.clear();
                                    for(Items it: allItemsData){
                                        String songN = it.name;
                                        String artistN = it.allArtistName.get(0).name;
                                        String result = "Song: " + songN + " Artist: " + artistN;
                                        Log.d("FINAL", result);
                                        //OurData3.artistN.clear();
                                        OurData3.artistN.add(artistN);
                                        OurData3.albumT.add(songN);

                                        //iterating through all artists (optional for future implementation)
                                        //for(artistName artName : it.allArtistName){
                                            //String songN = it.name;
                                            //String artistN = artName.name;
                                            //String result = "Song: " + songN + " Artist: " + artistN;
                                            //Log.d("FINAL", result);
                                        //}
                                    }



                                    //handling the output ends here
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    Log.d("REQUEST", "WE FAILED");
                                    Log.e("REQUEST", error.toString());
                                }
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders(){
                            Map<String, String>  params = new HashMap<>();
                            params.put("Authorization", "Bearer " + response.getAccessToken());

                            return params;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(this);
                    queue.add(getRequest);
                    queue.start();

                    Log.d("SPERMA", "sperma");
                    Log.d("OUTPUT", output.toString());

                    break;

                // Auth flow returned an error
                case ERROR:
                    // Try Again
                    getTokenFromSpotify();
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

    private void getTokenFromSpotify(){

        // Request code will be used to verify if result comes from the login activity. Can be set to any integer.
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.contact_Nav:
//                    Intent kampIntent = new Intent(MainActivity.this, ContactActivity.class);
//                    startActivity(kampIntent);
//                    break;
//            }
//            return false;
//        }
//    };

}
