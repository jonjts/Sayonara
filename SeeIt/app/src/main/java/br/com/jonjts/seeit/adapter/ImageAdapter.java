package br.com.jonjts.seeit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import br.com.jonjts.seeit.R;

/**
 * Created by Jonas on 20/01/2015.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<ImageView> image;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, List<ImageView> image) {
        this.context = context;
        this.image = image;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public Object getItem(int position) {
        return image.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout inflate = (LinearLayout) inflater.inflate(R.layout.image, null);
        ImageView i = (ImageView) inflate.findViewById(R.id.img);
        Drawable drawable = image.get(position).getDrawable();
        i.setImageDrawable(drawable);

        float density = context.getResources().getDisplayMetrics().density;
        final int w = (int) (100 * density + 12f);
        inflate.setLayoutParams(new GridView.LayoutParams(300, 600));
        return inflate;
    }
}
