package com.example.basak.monkeyme;

import android.os.Handler;

import java.util.HashMap;

/**
 * Created by basak on 2015-05-18.
 */
class BackThread2 extends Thread{
    Handler mHandler;
    String g_no;
    String m_no;
    String b_count;
    String mPath;
    HashMap<String, String> InfoTable;

    BackThread2(Handler mHandler,String mPath, HashMap<String, String> InfoTable){
        this.mHandler = mHandler;
        this.mPath = mPath;
        this.InfoTable = InfoTable;
    }

    public void run(){
        Network net = new Network();
        net.downNetwork2(mHandler, 2, mPath, InfoTable);
    }
}
