package rgu.ac.uk.recordbox.OurData;

import java.util.ArrayList;
import java.util.Arrays;

public class OurData2 {


    //arraylists for home_frag display
    public static ArrayList<String> albumT = new ArrayList<>(Arrays.asList("Baby", "Get Lucky", "No Freedom"));
    public static ArrayList<String> artistN = new ArrayList<>(Arrays.asList("Justin Bieber", "Daft Punk", "Dido"));

    public static int getPosition(String album, String artist){
        for(int i=0; i<albumT.size();i++){
            if(albumT.get(i).equals(album)){
                if(artistN.get(i).equals(artist)){
                    return i;
                }
            }
        }
        return -1;
    }


}