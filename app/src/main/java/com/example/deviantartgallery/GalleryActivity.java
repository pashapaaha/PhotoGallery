package com.example.deviantartgallery;

import android.support.v4.app.Fragment;

/**
 * Главное окно приложения
 */
public class GalleryActivity extends SingleFragmentActivity {

    /**
     * Указание фрагмента для SingleFragmentActivity
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return GalleryFragment.newInstance();
    }
}
