package br.com.jonjts.seeit.task;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import br.com.jonjts.seeit.R;

/**
 * Created by Jonas on 19/01/2015.
 */
public class PlayTask extends AsyncTask<Object, Void, Boolean> {

    private Context context;

    public PlayTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.vila_la_vida);

        mediaPlayer.start();
        return null;
    }
}
