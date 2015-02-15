package br.com.jonjts.seeit;

import android.widget.ImageView;

/**
 * Created by Jonas on 20/01/2015.
 */
public class MyImage {

    private ImageView imageView;
    private boolean visible;

    public MyImage(ImageView imageView) {
        this.imageView = imageView;
        visible = false;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
