package com.example.deviantartgallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

public class GalleryFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    RecyclerView picturesRecyclerView;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new PictureTask().execute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        picturesRecyclerView = v.findViewById(R.id.picture_recycler_view);
        picturesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        return v;
    }


    private class PictureTask extends AsyncTask <Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            new UrlWork().fetchItems();
            return null;
        }
    }
}
