package com.wohlig.jaipurpinkpanthers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class SlideShowActivity extends AppCompatActivity {
    ViewFlipper vfSlide;
    private float initialXPoint;
    //String[] images;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        Intent intent = this.getIntent();
        int position = Integer.parseInt(intent.getStringExtra("position"));
        images = intent.getStringArrayListExtra("links");
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(SlideShowActivity.this)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP


        vfSlide = (ViewFlipper) findViewById(R.id.vfSlide);

        for (int i = 0; i < images.size(); i++) {
            addImageInFlipper(images.get(i));
        }

        vfSlide.setDisplayedChild(position);
    }

    public void addImageInFlipper(String url){

        LayoutInflater inflator = getLayoutInflater();
        View viewFlipperSingleImage = inflator.inflate(R.layout.layout_single_image_flipper, null, false);

        ImageView ivBig = (ImageView) viewFlipperSingleImage.findViewById(R.id.ivBig); //find the different Views

        imageLoader.displayImage(url, ivBig, options);

        vfSlide.addView(viewFlipperSingleImage);

    }

    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {


            case MotionEvent.ACTION_DOWN:
                initialXPoint = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();


                // Handling left to right screen swap.
                if (initialXPoint < currentX) {


                    // If there aren't any other children, just break.
                    if (vfSlide.getDisplayedChild() == 0)
                        break;


                    // Next screen comes in from left.
                    vfSlide.setInAnimation(this, R.anim.slide_right_out);
                    // Current screen goes out from right.
                    vfSlide.setOutAnimation(this, R.anim.slide_right_in);


                    // Display next screen.
                    vfSlide.showPrevious();
                }


                // Handling right to left screen swap.
                if (initialXPoint > currentX) {


                    // If there is a child (to the left), kust break.
                    if (vfSlide.getDisplayedChild() == vfSlide.getChildCount()-1)
                        break;


                    // Next screen comes in from right.
                    vfSlide.setInAnimation(this, R.anim.slide_left_in);
                    // Current screen goes out from left.
                    vfSlide.setOutAnimation(this, R.anim.slide_left_out);


                    // Display previous screen.
                    vfSlide.showNext();
                }
                break;
        }
        return false;
    }
}
