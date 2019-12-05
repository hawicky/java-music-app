package rgu.ac.uk.recordbox.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import rgu.ac.uk.recordbox.Adapter.RecyclerViewAdapter;
import rgu.ac.uk.recordbox.OurActivity.MainActivity;
import rgu.ac.uk.recordbox.R;

public class search_frag extends Fragment {

    ArrayList<String> mAlbumNames;
    ArrayList<String> mArtistNames;

    //input fields
    String searchValue;
    EditText searchInput;
    Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    //WORKING USER INPUT HANDLER
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //intent to access mainactivity
        final Intent intent= new Intent(view.getContext(), MainActivity.class);




        searchInput = (EditText) getActivity().findViewById(R.id.searchKey);
        submitButton = (Button) getActivity().findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Handling users input
                searchValue = (String) searchInput.getText().toString();
                Log.d("submitString", searchValue);
                MainActivity.searchContent = searchValue;
                getActivity().startActivity(intent);
            }
        });
        //attempt to move spotify content
    }





}
