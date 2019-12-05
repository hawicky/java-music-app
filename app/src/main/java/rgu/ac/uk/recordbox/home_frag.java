package rgu.ac.uk.recordbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class home_frag extends Fragment {
    private RecyclerView mRecyclerView;
    public static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<AlbumRecyclerView> exampleList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        //recycler view
        exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "Post Malone", "Stoney"));
        exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "21 Savage", "Issa Album"));
       /* exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "Twenty One Pilots", "Trench"));
        exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "Ed Sheeran", "X"));
        exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "Line 9", "Line 6"));
        exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "Line 5", "Line 6"));
        exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "Line 5", "Line 6"));
        exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "Line 5", "Line 6"));
        exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "Line 5", "Line 6"));
        */
       exampleList.add(new AlbumRecyclerView(R.drawable.ic_album, "Line 5", "Line 6"));

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}
