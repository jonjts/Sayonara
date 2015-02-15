package br.com.jonjts.seeit.thread;

import android.content.Context;
import android.media.MediaPlayer;

import br.com.jonjts.seeit.R;

/**
 * Created by Jonas on 16/01/2015.
 */
public class MusicThread extends Thread {

    private Context context;

    public MusicThread(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.vila_la_vida);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
}
