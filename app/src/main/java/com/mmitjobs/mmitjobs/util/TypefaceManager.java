package com.mmitjobs.mmitjobs.util;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;

public class TypefaceManager {
    private static final String ABEL_REGULAR = "Abel-Regular.ttf";
    private static final String FJALLA_ONE = "FjallaOne-Regular.ttf";
    private static final String ROBOTO_SLAB_REGULAR = "RobotoSlab-Regular.ttf";
    private static final String MYANMAR_SABAE = "MyanmarSabae.ttf";
    private static final String MYANMAR_PYIDAUNGSU = "pyidaungsu.ttf";
    private static final String NOTOSAN_MYANMAR = "notosans.ttf";
    private static final String SMART_ZAWGYI = "SmartZawgyi.ttf";

    private final LruCache<String, Typeface> mCache;
    private final AssetManager mAssetManager;

    public TypefaceManager(AssetManager assetManager) {
        mAssetManager = assetManager;
        mCache = new LruCache<>(3);
    }

    private Typeface getTypeface(final String filename) {
        Typeface typeface = mCache.get(filename);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(mAssetManager, "fonts/" + filename);
            mCache.put(filename, typeface);
        }
        return typeface;
    }

    public Typeface getAbelRegular() {
        return getTypeface(ABEL_REGULAR);
    }

    public Typeface getFjallaOneRegular() {
        return getTypeface(FJALLA_ONE);
    }

    public Typeface getRobotoSlabRegular() {
        return getTypeface(ROBOTO_SLAB_REGULAR);
    }

    public Typeface getMyanmarSabae() {
        return getTypeface(MYANMAR_SABAE);
    }

    public Typeface getPyidaungsu() {
        return getTypeface(MYANMAR_PYIDAUNGSU);
    }

    public Typeface getNotoSans() {
        return getTypeface(NOTOSAN_MYANMAR);
    }

    public Typeface getSmartZawgyi() {
        return getTypeface(SMART_ZAWGYI);
    }
}
