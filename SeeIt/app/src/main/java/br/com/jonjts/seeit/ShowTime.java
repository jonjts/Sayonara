package br.com.jonjts.seeit;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.com.jonjts.seeit.adapter.ImageAdapter;
import br.com.jonjts.seeit.task.ImageDownloadTask;
import br.com.jonjts.seeit.task.ImageDownloadTask_2_ORetorno;
import br.com.jonjts.seeit.task.MensagemTask;


public class ShowTime extends Fragment implements IUpdateGrid{

    private ProgressDialog progressDialog;
    private Long albumsId;
    private List<String> photos;
    private List<ImageView> imagesDone;
    private GridView gridView;
    private int count = 4;
    private int countMsg = 0;
    private List<String> mensagens;
    private TextView msg;
    private MediaPlayer mediaPlayer;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_time, container, false);

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.vila_la_vida);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


        gridView = (GridView) view.findViewById(R.id.grid);
        gridView.setNumColumns(4);

        msg = (TextView) view.findViewById(R.id.msg);

        mensagens = new ArrayList<>();
        mensagens.add("Estava pensando em fazer algo diferente...");
        mensagens.add("Eu não sou bom com desing...");
        mensagens.add("Mas vou colocar umas imagens aqui...");
        mensagens.add("Não sei bem o que te falar :s ");
        mensagens.add("Só quero te dizer...");
        mensagens.add("Que me sinto bem com você ;)");
        mensagens.add("Feliz Aninersário! =]");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Aguarde um instante...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        photos = new ArrayList<>();
        imagesDone = new ArrayList<>();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(getActivity());

        getAlbums();
    }

    private void getAlbums(){
        new Request(
                Session.getActiveSession(),
                "/me/albums",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
                        GraphObject graphObject = response.getGraphObject();
                        Map<String, Object> stringObjectMap = graphObject.asMap();
                        JSONArray data = (JSONArray) stringObjectMap.get("data");
                        for (int i = 0; i < data.length(); i++) {
                            try {
                                JSONObject jsonObject = data.getJSONObject(i);
                                String perfil = "Profile Pictures";
                                String timeLine = "Timeline Photos";
                                String instagram = "Instagram Photos";
                                if(jsonObject.getString("name").equals(timeLine)){
                                   albumsId = jsonObject.getLong("id");
                                    break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loadPhotos(albumsId.longValue());
                    }
                }
        ).executeAsync();
    }


    private void loadPhotos(long album){
        new Request(
                Session.getActiveSession(),
                album+"/photos",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
                        GraphObject graphObject = response.getGraphObject();
                        JSONObject stringObjectMap = graphObject.getInnerJSONObject();
                        try {
                            JSONArray data =  stringObjectMap.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject jsonObject = data.getJSONObject(i);
                                    if(jsonObject.has("images")){
                                        JSONArray images = jsonObject.getJSONArray("images");
                                        JSONObject imageObject = images.getJSONObject(1);
                                        photos.add(imageObject.getString("source"));
                                    }
                                    if(photos.size() == 20){
                                        break;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            MensagemTask mensagemTask = new MensagemTask(ShowTime.this);
                            mensagemTask.execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();
    }

    private void startDownloadImages(){
        ImageDownloadTask imageDownloadTask;
        for(int i = 0; i <= 3; i++){
            imageDownloadTask = new ImageDownloadTask(getActivity(), this, photos.get(i));
            imageDownloadTask.execute();
        }
    }

    private int getCount(){
        if(count == imagesDone.size()-1){
            count = 0;
            return count;
        }else{
            return count++;
        }
    }


    @Override
    public void changeMsg() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        msg.setText(mensagens.get(countMsg++));
        msg.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in));
        if(countMsg == 3){
            startDownloadImages();
        }
        if(countMsg >= 7){
            msg.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_out));
        } else{
            MensagemTask mensagemTask = new MensagemTask(this);
            mensagemTask.execute();
        }

    }

    @Override
    public void AddImage(MyImage myImage) {
        if(myImage != null){
            imagesDone.add(myImage.getImageView());
            if(imagesDone.size() == 4){
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                ImageAdapter imageAdapter = new ImageAdapter(getActivity(),imagesDone);
                gridView.setAdapter(imageAdapter);
                ImageDownloadTask_2_ORetorno oRetorno= new ImageDownloadTask_2_ORetorno(getActivity(), this, photos.get(getCount()));
                oRetorno.execute();
            }
        }
    }

    @Override
    public void updateGrid(MyImage myImage){
        if(myImage != null) {
            imagesDone.add(myImage.getImageView());
            List<ImageView> myImages = new ArrayList<>();
            for (int i = 0; i <= 3; i++) {
                int position = new Random().nextInt(imagesDone.size());
                myImages.add(imagesDone.get(position));
            }
            gridView.setAdapter(new ImageAdapter(getActivity(), myImages));
            gridView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.fade));
            ImageDownloadTask_2_ORetorno oRetorno= new ImageDownloadTask_2_ORetorno(getActivity(), this, photos.get(getCount()));
            oRetorno.execute();
        }
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.release();
        }
    }

    private int getPosition(){
        int maximum = imagesDone.size()-1;
        int minimum = 0;
        Random rn = new Random();
        int n = maximum - minimum + 1;
        int i = rn.nextInt() % n;

        return i;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}