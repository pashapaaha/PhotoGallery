package com.example.deviantartgallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.ViewHolder;

import java.util.List;


public class GalleryFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    RecyclerView picturesRecyclerView;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    /**
     * Создание фрагмента
     * Происходит запуск отдельного потока для считывания данных с API
     * Чтение происходит один раз
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: start");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new PictureTask().execute();
    }

    /**
     * Инициализация представления фрагмента
     * Создание RecyclerVie, назначение ему layoutManager'a и адаптера
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: start");
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        picturesRecyclerView = v.findViewById(R.id.picture_recycler_view);
        picturesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));
        setAdapter();
        return v;
    }

    /**
     * Назначение адаптера для RecyclerView
     * Назначение происходит только в случае подключения фрагмента к активити
     * Реализация адаптера предоставлена библиотекой Groupie
     * Данная библиотека позволяет сократить шаблонного кода, а также некоторые встроенные функции
     */
    private void setAdapter() {
        if (isAdded()) {
            GroupAdapter<ViewHolder> adapter = new GroupAdapter<>();
            for (Picture picture : PictureList.get().getList()) {
                adapter.add(new PictureItem(picture));
            }
            picturesRecyclerView.setAdapter(adapter);
        }
    }

    /**
     * Реализация AsyncTask для получения данных с API
     */
    private class PictureTask extends AsyncTask<Void, Void, List<Picture>> {

        /**
         * Получение данных с API
         *
         * @param voids
         * @return - список структур типа Picture, содержащих назавние, url и id изображения
         */
        @Override
        protected List<Picture> doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: start");
            return new UrlWork().getPicturesList();
        }

        /**
         * Сохранение полученного списка в синглтон-классе
         * Назначение адаптера
         *
         * @param pictures
         */
        @Override
        protected void onPostExecute(List<Picture> pictures) {
            Log.d(TAG, "onPostExecute: start");
            super.onPostExecute(pictures);
            PictureList.get().add(pictures);
            setAdapter();
        }
    }

}
