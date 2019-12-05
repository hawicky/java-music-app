package rgu.ac.uk.recordbox.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import rgu.ac.uk.recordbox.OurData.OurData;
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
        return OurData.albumT.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        CircleImageView image;
        TextView mAlbumText;
        TextView mArtistNameText;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.artist_img);
            mAlbumText = itemView.findViewById(R.id.album_name);
            mArtistNameText = itemView.findViewById(R.id.artist_name);
        }

        public void bindView(int position){
            //mAlbumText.setText(OurData.albumTitle[position]);
            //mArtistNameText.setText(OurData.artistName[position]);
            mAlbumText.setText(OurData.albumT.get(position));
            mArtistNameText.setText(OurData.artistN.get(position));
        }


    }

}