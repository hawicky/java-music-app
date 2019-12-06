//recycle view for home_frag displaying all the saved songs
package rgu.ac.uk.recordbox.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import rgu.ac.uk.recordbox.OurData.OurData2;
import rgu.ac.uk.recordbox.R;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerViewAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return OurData2.albumT.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        CircleImageView image;
        TextView mAlbumText;
        TextView mArtistNameText;
        ImageButton mImageButton;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.artist_img);
            mAlbumText = itemView.findViewById(R.id.album_name);
            mArtistNameText = itemView.findViewById(R.id.artist_name);
            mImageButton = itemView.findViewById(R.id.imageButton6);
            //adding song from search to savedSongs
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String song = mAlbumText.getText().toString();
                    String artist = mArtistNameText.getText().toString();
                    Log.d("Song", song);
                    Log.d("Artist", artist);
                    int position = OurData2.getPosition(song, artist);
                    if(position !=-1){
                        OurData2.albumT.remove(position);
                        OurData2.artistN.remove(position);
                        Log.d("remove", "Remove successful");
                        
                    }
                }
            });
        }

        public void bindView(int position){
            //mAlbumText.setText(OurData3.albumTitle[position]);
            //mArtistNameText.setText(OurData3.artistName[position]);
            mAlbumText.setText(OurData2.albumT.get(position));
            mArtistNameText.setText(OurData2.artistN.get(position));
        }


    }

}
