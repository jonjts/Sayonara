package br.com.jonjts.seeit.task;

import android.os.AsyncTask;

import br.com.jonjts.seeit.IUpdateGrid;

/**
 * Created by Jonas on 19/01/2015.
 */
public class MensagemTask extends AsyncTask<Object, Void, Boolean> {

    private IUpdateGrid iUpdateGrid;

    public MensagemTask(IUpdateGrid iUpdateGrid) {
        this.iUpdateGrid = iUpdateGrid;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        iUpdateGrid.changeMsg();
    }
}
