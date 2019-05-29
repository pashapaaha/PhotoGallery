package com.example.deviantartgallery;

import java.util.ArrayList;
import java.util.List;

public class PictureList {

    private static List<Picture> pictures = new ArrayList<>();

    private static PictureList pictureList;
    private PictureList(){

    }

    public static PictureList get(){
        if (pictureList == null) {
            pictureList = new PictureList();
        }
        return pictureList;
    }

    public void add(Picture picture) {
        if(!pictures.contains(picture)) {
            pictures.add(picture);
        }
    }

    public void add(List<Picture> pictures1) {
        pictures.addAll(pictures1);
    }


    public List<Picture> getList(){
        return new ArrayList<>(pictures);
    }
}
