package com.example.gergo.companyx;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class LoadScreen {

    private android.app.ProgressDialog progress;
    private Context context;
    private String message, title;
    private int runTime;

    public void progressDialog(Context context, String message, String title){
        progress = new android.app.ProgressDialog(context);
        progress.setMax(100);
        progress.setMessage(message);
        progress.setTitle(title);
        progress.setProgressStyle(android.app.ProgressDialog.STYLE_HORIZONTAL);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(progress.getProgress() <= progress.getMax()){
                        Thread.sleep(15);
                        handler.sendMessage(handler.obtainMessage());
                        if(progress.getProgress() == progress.getMax()){
                            progress.dismiss();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress.incrementProgressBy(1);
        }
    };
}
