package com.example.deviantartgallery;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

/**
 * Реализация класса для взаимодействия с адаптером из библиотеки Groupie
 */
class PictureItem extends Item<ViewHolder> {

    private final String TAG = this.getClass().getSimpleName();

    private Picture picture;

    PictureItem(Picture picture) {
        Log.d(TAG, "PictureItem: start constructor");
        this.picture = picture;
    }

    /**
     * Заполнение элемента
     * Для загрузки изображения по URL используется библиотека Picasso
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        Log.d(TAG, "bind: viewHolder, picture (" + picture.getTitle() + ")");
        ImageView pictureImageView = viewHolder.itemView.findViewById(R.id.pictureImageView);
        Picasso.get().load(picture.getUrl()).into(pictureImageView);
    }

    /**
     * Привязка макета разметки
     *
     * @return
     */
    @Override
    public int getLayout() {
        return R.layout.picture_item;
    }
}