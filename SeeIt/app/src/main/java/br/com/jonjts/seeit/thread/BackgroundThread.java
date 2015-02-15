package br.com.jonjts.seeit.thread;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;

import java.util.List;

import br.com.jonjts.seeit.IBackground;

/**
 * Created by Jonas on 16/01/2015.
 */
public class BackgroundThread extends Thread {

    private Activity iBackground;
    private List<Integer> colors;


    public BackgroundThread(Activity iBackground, List colors){
        this.iBackground = iBackground;
        this.colors = colors;
    }

    @Override
    public void run() {
        try {
            int count = 0;
            while (true) {
                sleep(3 * 1000);
                final int finalCount = count;
                iBackground.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Integer colorFrom = colors.get(finalCount);

                            int colorT = finalCount + 1;
                            if(colorT == colors.size()){
                                colorT = 0;
                            }
                            Integer colorTo = colors.get(colorT);
                            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                                @Override
                                public void onAnimationUpdate(ValueAnimator animator) {
                                    ((IBackground) iBackground).change((Integer) animator.getAnimatedValue());
                                }

                            });
                            colorAnimation.start();
                        }catch (Exception e){

                        }
                    }
                });
                count++;
                if(count == colors.size()){
                    count = 0;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
