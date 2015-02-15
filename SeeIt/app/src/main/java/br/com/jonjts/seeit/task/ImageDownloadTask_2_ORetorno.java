package br.com.jonjts.seeit.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.jonjts.seeit.IUpdateGrid;
import br.com.jonjts.seeit.MyImage;

/**
 * Created by Jonas on 20/01/2015.
 */
public class ImageDownloadTask_2_ORetorno extends AsyncTask<Object, Void, MyImage> {

    private Context context;
    private IUpdateGrid iUpdateGrid;
    private String link;

    public ImageDownloadTask_2_ORetorno(Context context, IUpdateGrid iUpdateGrid, String link) {
        this.context = context;
        this.iUpdateGrid = iUpdateGrid;
        this.link = link;
    }

    @Override
    protected MyImage doInBackground(Object... params) {
        try {
            try {
                Thread.sleep(4 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            ImageView imageView = new ImageView(context);
            imageView.setImageBitmap(resizeImage(myBitmap));

            return new MyImage(imageView);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("getBmpFromUrl error: ", e.getMessage().toString());
            return null;
        }
    }

    private Bitmap resizeImage(Bitmap bmpOriginal) {
        Bitmap novoBmp = null;

        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();

        int novoW = 300;
        int novoH = 600;

        //Calcula escala em porcentagem do tamanho original para o novo tamanho
        float scalaW = ((float) novoW) / w;
        float scalaH = ((float) novoH) / h;

        // Criando uma matrix para manipulação da imagem BitMap
        Matrix matrix = new Matrix();

        // Definindo a proporção da escala para o matrix
        matrix.postScale(scalaW, scalaH);

        //criando o novo BitMap com o novo tamanho
        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);

        return novoBmp;
    }

    @Override
    protected void onPostExecute(MyImage imageView) {
        iUpdateGrid.updateGrid(imageView);
    }
}
