package com.example.basak.listtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by basak on 2015-03-30.
 */
public class Test extends Activity {
    ListView MyList;
    ArrayList<ListItem> arItem;
    MultiAdapter MyAdapter;
    HashMap<String, ArrayList<HashMap<String, String>>> table;
    Intent intent;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_home);

        arItem = new ArrayList<ListItem>();
        arItem.add(new ListItem(3, "게임버튼"));
        arItem.add(new ListItem(1, "내 턴"));
        MyAdapter = new MultiAdapter(this, arItem, mHandler);

        BackThread thread = new BackThread(1, mHandler);
        thread.setDaemon(true);
        thread.start();



    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Log.i("tag", "getMSG!");

                table = (HashMap<String, ArrayList<HashMap<String, String>>>) msg.obj;
                setProfile(table.get("state"));
                if(table.get("myturn").size() !=0){
                Log.i("tag", "table ok!");
                setList(table.get("myturn"), -1);
                Log.i("tag", "test1!");}

                arItem.add(new ListItem(2, "친구턴"));

                if(table.get("friendsturn").size() != 0) {

                    Log.i("tag", "test2!");
                    setList(table.get("friendsturn"), 0);
                }
                MyList = (ListView) findViewById(R.id.HomeList);
                MyList.setAdapter(MyAdapter);
                MyList.setOnItemClickListener(mItemClickListener);
            }
           /** else if(msg.what == 1){
                Intent intent = new Intent(getBaseContext(), SelectFriendActivity.class);
                startActivity(intent);
                finish();
            }**/


        }
    };

    public void setProfile(ArrayList<HashMap<String, String>> downlist){
        TextView setting = (TextView)findViewById(R.id.EditTxt);
        setting.setText(downlist.get(0).get("name"));
        setting = (TextView)findViewById(R.id.LevelTxt);
        setting.setText("Level "+downlist.get(0).get("level"));
        setting = (TextView)findViewById(R.id.EnergyTxt);
        setting.setText(downlist.get(0).get("light"));
        setting = (TextView)findViewById(R.id.BananaTxt);
        setting.setText(downlist.get(0).get("banana"));
        setting = (TextView)findViewById(R.id.LeafTxt);
        setting.setText(downlist.get(0).get("leaf"));
        Button ProfileBtn= (Button)findViewById(R.id.ProfileBtn);
        ProfileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        final ImageView picture = (ImageView)findViewById(R.id.ProfileView);

        Handler imgHandler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what == 1){
                    Log.i("tag", "getIMG!");
                    picture.setImageBitmap((Bitmap)msg.obj);
                }

            }
        };

        ImageThread imgThread = new ImageThread(downlist.get(0).get("profile"), imgHandler, 1);
        imgThread.setDaemon(true);
        imgThread.start();

    }

    public void setList(ArrayList<HashMap<String, String>> downlist, int type) {
        for (int i = 0; i < downlist.size(); i++) {
            arItem.add(new ListItem(type, downlist.get(i).get("name"), downlist.get(i).get("level"), downlist.get(i).get("round"), downlist.get(i).get("profile"), downlist.get(i).get("g_no"), downlist.get(i).get("isSolved")));
        }
    }



    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @SuppressWarnings("unchecked")
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Log.i("position", String.valueOf(position));
            Log.i("type", String.valueOf(arItem.get(position).Type));


            switch (arItem.get(position).Type) {
                case -1:
                    Log.i("tag", "myturn selected");
                    if(arItem.get(position).IsSolved.equals("0")){
                    intent = new Intent(getBaseContext(), GuessActivity.class);
                    intent.putExtra("InfoList", table.get("myturn").get(position-2));
                    startActivity(intent);
                    } else{
                        intent = new Intent(getBaseContext(), SelectKeyWord.class);
                        intent.putExtra("InfoList", table.get("myturn").get(position-2));
                        startActivity(intent);
                        finish();
                    }
                    break;
                case 0:
                    Log.i("tag", "friendsturn selected");
                    break;
            }
        }
    };



}

class BackThread extends Thread{
    int type;   //1:메인업데이트, 2:게임맞춤, 3:키워드 받아오기, 4:몽키대전 친구목록, 5:퍼즐몽키, 6:퍼즐모드 맞춤
    Handler mHandler;
    String g_no;    //g_no or rnd_no(in puzzleMonkey)
    String m_no;
    String b_count;

    BackThread(int type, Handler mHandler){
        this.type = type;
        this.mHandler = mHandler;
        this.g_no = null;
        this.m_no = null;
        this.b_count = null;
    }

    BackThread(Handler mHandler, int type, String g_no, String m_no, String b_count){
        this.type = type;
        this.mHandler = mHandler;
        this.g_no = g_no;
        this.m_no = m_no;
        this.b_count = b_count;
    }
    public void run(){
        Network net = new Network();
        net.downNetwork(mHandler, type, this.g_no, this.m_no, this.b_count);
    }
}


class ImageThread extends Thread{
    private String imgUrl = "";
    private Bitmap image;
    private Handler imgHandler;
    private int what;
    ImageThread(String imgUrl, Handler imgHandler, int what){
        this.imgUrl = imgUrl;
        this.imgHandler = imgHandler;
        this.what = what;
    }

    public void run(){
        downImage();
    }

    private void downImage(){
        try{
            Log.i("log", "test");
            InputStream istream = new URL(imgUrl).openStream();
            Log.i("log", "test1");
            image = BitmapFactory.decodeStream(istream);
            Log.i("log", "test2");
            if(image == null){
                Log.e("Error", "null image!");
            } else{
                Log.i("tag", "imgsend");
                Message msg = new Message();
                msg.what = this.what;
                msg.obj = image;
                imgHandler.sendMessage(msg);
            }
            istream.close();
        } catch(Exception e){
            Log.e("error", "Image Error!");
        }
    }
}



class Network {
    public String downNetwork(Handler mHandler, int type, String g_no, String m_no, String level){  //추후에 어플리케이션으로 구현해서 더함수화 하자
        try{
            URL url = new URL("http://175.211.100.229/monkeyme/monkeyme_server/RequestHandleServer.php");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("content=type", "application/x-www-form=urlencoded");

                StringBuffer buffer = new StringBuffer();
                if(type == 1){
                    buffer.append("command=updateMain&memberNumber=4");
                    OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                    out.write(buffer.toString().getBytes());
                    out.flush();
                    parser(conn.getInputStream(), mHandler);
                    conn.disconnect();
                    Log.i("dis", "connected");
                    return "success";
                } else if(type == 2){
                    buffer.append("command=solveTheMonkey&g_no=").append(g_no).append("&memberNumber=").append(m_no).append("&b_count=").append(level);

                    Log.i("g_no", g_no);
                    Log.i("m_no", m_no);
                    Log.i("b_no", level);
                    Log.i("test", buffer.toString());
                    OutputStream outStream = new BufferedOutputStream(conn.getOutputStream());
                    outStream.write(buffer.toString().getBytes());
                    outStream.flush();
                    Log.i("test", "call");
                    InputStream input = conn.getInputStream();
                    parser(input, mHandler);
                    Log.i("test", "call3");
                    conn.disconnect();
                } else if(type == 3){
                    buffer.append("command=wordList");
                    OutputStream outStream = new BufferedOutputStream(conn.getOutputStream());
                    outStream.write(buffer.toString().getBytes());
                    outStream.flush();
                    Log.i("test", "call");
                    InputStream input = conn.getInputStream();
                    parser2(input, mHandler);
                    Log.i("test", "call3");
                    conn.disconnect();
                } else if(type ==4){
                    buffer.append("command=friendlist_monkey&memberNumber=").append("4");
                    OutputStream outStream = new BufferedOutputStream(conn.getOutputStream());
                    outStream.write(buffer.toString().getBytes());
                    outStream.flush();
                    Log.i("test", "call");
                    /**
                    BufferedReader rd = null;
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line = null;
                    while ((line = rd.readLine()) != null) {
                        Log.i("Lifeclue", line);
                    }**/
                    InputStream input = conn.getInputStream();
                    parser3(input, mHandler);
                    Log.i("test", "call3");
                    conn.disconnect();
                } else if(type==5){ //퍼즐몽키 받아오기
                    buffer.append("command=randomItem&memberNumber=4");
                    OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                    out.write(buffer.toString().getBytes());
                    out.flush();

                    parser4(conn.getInputStream(), mHandler);
                    conn.disconnect();
                    Log.i("dis", "connected");
                    return "success";
                } else if(type==6){ //퍼즐몽키 맞춤
                    buffer.append("command=solveTheRandom&rnd_no=").append(g_no).append("&memberNumber=4");
                    //buffer.append("command=replyList&g_no=1_1429111200&0&sort=1");
                    OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                    out.write(buffer.toString().getBytes());
                    out.flush();
                    /*
                    BufferedReader rd = null;

                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line = null;
                    while ((line = rd.readLine()) != null) {
                        Log.i("Lifeclue", line);
                    }*/

                    parser(conn.getInputStream(), mHandler);
                    conn.disconnect();
                    Log.i("dis", "connected");
                    return "success";
                } else if(type == 7){
                    //buffer.append("command=replyList&g_no=").append(g_no).append("&0&sort=1");
                    buffer.append("command=topReply&g_no=1_1429111200&3&sort=1");
                    OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                    out.write(buffer.toString().getBytes());
                    out.flush();
                    /*
                    BufferedReader rd = null;

                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line = null;
                    while ((line = rd.readLine()) != null) {
                        Log.i("Lifeclue", line);
                    }
                    */
                    parser5(conn.getInputStream(), mHandler);
                    conn.disconnect();
                    Log.i("dis", "connected");
                    return "success";
                }
            }
            return "0";
        } catch(NetworkOnMainThreadException e){
            return "Error : 메인 스레드 네트워크 작업 에러 - "+ e.getMessage();
        }catch(Exception e){
            Log.e("tag", "error");
            return "Error : " + e.getMessage();
        }
    }

    public String downNetwork2(Handler mHandler, int type, String mPath, HashMap<String, String> InfoTable){  //추후에 어플리케이션으로 구현해서 더함수화 하자
        try{
            URL url = new URL("http://175.211.100.229/monkeyme/monkeyme_server/RequestHandleServer.php");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            String lineEnd = "\r\n";
            String twoHypens = "--";
            String boundary = "3FFS4PKI7YH9S";

            byte[] image;
            String stringData1;
            String stringData2;
            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("content-type", "multipart/form-data;boundary=" + boundary);


                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                Log.i("test", "network in");
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                //dos.writeBytes("Content-Disposition: form-data; name=\"g_no\";filename=\""+fileName+"\""+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"command\""+lineEnd+lineEnd+"uploadGameData");
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"g_no\""+lineEnd+lineEnd+InfoTable.get("g_no"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"memberNumber\"" + lineEnd + lineEnd + "4");    //내번호
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"targetNumber\"" + lineEnd + lineEnd + InfoTable.get("m_no")); //상대방번호
                Log.i("m_no", InfoTable.get("m_no"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");//URLEncoder.encode(InfoTable.get("keyword"), "utf-8")
                dos.writeBytes("Content-Disposition: form-data; name=\"keyword\"" + lineEnd + lineEnd + URLEncoder.encode(InfoTable.get("keyword"), "UTF-8"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"hint\"" + lineEnd + lineEnd + URLEncoder.encode(InfoTable.get("hint"), "UTF-8"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"b_count\""+lineEnd+lineEnd+InfoTable.get("b_count"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"round\""+lineEnd+lineEnd+"2");
                dos.writeBytes(lineEnd + "--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"image.jpeg\"\r\n");
                dos.writeBytes("Content-Type: application/octet-stream\r\n\r\n");
                FileInputStream fileInputStream = new FileInputStream(mPath);
                int bytesAvailable = fileInputStream.available();
                int maxBufferSize = 1000;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0)
                {
                    // Upload file part(s)
                    //DataOutputStream dataWrite = new DataOutputStream(conn.getOutputStream());
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                fileInputStream.close();
                dos.writeBytes("\r\n--" + boundary + "--\r\n");
                dos.flush();

                BufferedReader rd = null;

                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    Log.i("Lifeclue", line);
                }
                    //parser(conn.getInputStream(), mHandler);
                    conn.disconnect();
                    Log.i("dis", "connected");
                    return "success";
            }
            return "0";
        } catch(NetworkOnMainThreadException e){
            return "Error : 메인 스레드 네트워크 작업 에러 - "+ e.getMessage();
        }catch(Exception e){
            Log.e("tag", "error");
            return "Error : " + e.getMessage();
        }
    }

    public String downNetwork3(Handler mHandler, int type, String mPath, HashMap<String, String> InfoTable){  //추후에 어플리케이션으로 구현해서 더함수화 하자
        try{
            URL url = new URL("http://175.211.100.229/monkeyme/monkeyme_server/RequestHandleServer.php");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            String lineEnd = "\r\n";
            String twoHypens = "--";
            String boundary = "3FFS4PKI7YH9S";

            byte[] image;
            String stringData1;
            String stringData2;
            if(conn != null){
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("content-type", "multipart/form-data;boundary=" + boundary);


                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                Log.i("test", "network in");
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                //dos.writeBytes("Content-Disposition: form-data; name=\"g_no\";filename=\""+fileName+"\""+lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"command\""+lineEnd+lineEnd+"uploadGameData");
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"g_no\""+lineEnd+lineEnd+InfoTable.get("g_no"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"memberNumber\"" + lineEnd + lineEnd + "4");    //내번호
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"targetNumber\"" + lineEnd + lineEnd + InfoTable.get("m_no")); //상대방번호
                Log.i("m_no", InfoTable.get("m_no"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");//URLEncoder.encode(InfoTable.get("keyword"), "utf-8")
                dos.writeBytes("Content-Disposition: form-data; name=\"keyword\"" + lineEnd + lineEnd + URLEncoder.encode(InfoTable.get("keyword"), "UTF-8"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"hint\"" + lineEnd + lineEnd + URLEncoder.encode(InfoTable.get("hint"), "UTF-8"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"b_count\""+lineEnd+lineEnd+InfoTable.get("b_count"));
                dos.writeBytes(lineEnd+"--"+boundary+"\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"round\""+lineEnd+lineEnd+"2");
                dos.writeBytes(lineEnd + "--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"movie.mp4\"\r\n");
                dos.writeBytes("Content-Type: application/octet-stream\r\n\r\n");
                FileInputStream fileInputStream = new FileInputStream(mPath);
                int bytesAvailable = fileInputStream.available();
                int maxBufferSize = 1000;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0)
                {
                    // Upload file part(s)
                    //DataOutputStream dataWrite = new DataOutputStream(conn.getOutputStream());
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                fileInputStream.close();
                dos.writeBytes("\r\n--" + boundary + "--\r\n");
                dos.flush();

                BufferedReader rd = null;

                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = rd.readLine()) != null) {
                    Log.i("Lifeclue", line);
                }
                //parser(conn.getInputStream(), mHandler);
                conn.disconnect();
                Log.i("dis", "connected");
                return "success";
            }
            return "0";
        } catch(NetworkOnMainThreadException e){
            return "Error : 메인 스레드 네트워크 작업 에러 - "+ e.getMessage();
        }catch(Exception e){
            Log.e("tag", "error");
            return "Error : " + e.getMessage();
        }
    }

    public void parser(InputStream istream, Handler mHandler) {
        Log.i("test", "parser1");

        ArrayList<HashMap<String, String>> hashArray = new ArrayList<HashMap<String, String>>();

        HashMap<String, ArrayList<HashMap<String, String>>> table = new HashMap<String, ArrayList<HashMap<String, String>>>();

        StringBuilder test = new StringBuilder();
        try {
            Log.i("test", "parser2");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(istream);
            Element order = doc.getDocumentElement();
            NodeList infoheads = order.getElementsByTagName("infohead");
            Log.i("test", "parser3");
            Node info = infoheads.item(0);
            NamedNodeMap Attrs = info.getAttributes();
            Node attr = Attrs.item(1);
            if(attr.getNodeValue().equals("succeed")){
                Log.e("tag", "downsucceed!");
            }
            if(attr.getNodeValue().equals("error")){
                Log.e("tag", "downError!");
            }

            NodeList states = order.getElementsByTagName("state");
            Node state = states.item(0);
            Attrs = state.getAttributes();
            table.put("state", new ArrayList<HashMap<String, String>>());
            table.get("state").add(0, new HashMap<String, String>());
            for(int i=0; i<Attrs.getLength(); i++){
                attr = Attrs.item(i);
                table.get("state").get(0).put(attr.getNodeName(), attr.getNodeValue());
            }

            NodeList lists = order.getElementsByTagName("list");
            Node list = lists.item(0);
            NodeList friends = list.getChildNodes();

            table.put("myturn", new ArrayList<HashMap<String, String>>());


            for(int i=0; i<friends.getLength(); i++){
                table.get("myturn").add(i, new HashMap<String, String>());
                Node friend = friends.item(i);
                Attrs = friend.getAttributes();
                for(int j=0; j< Attrs.getLength(); j++){
                    attr = Attrs.item(j);
                    table.get("myturn").get(i).put(attr.getNodeName(), attr.getNodeValue());
                }
            }

            list = lists.item(1);
            friends = list.getChildNodes();
            table.put("friendsturn", new ArrayList<HashMap<String, String>>());

            for(int i=0; i<friends.getLength(); i++){
                table.get("friendsturn").add(i, new HashMap<String, String>());
                Node friend = friends.item(i);
                Attrs = friend.getAttributes();
                for(int j=0; j< Attrs.getLength(); j++){
                    attr = Attrs.item(j);
                    table.get("friendsturn").get(i).put(attr.getNodeName(), attr.getNodeValue());
                    //Log.i("tag", attr.getNodeName());
                }
            }
        } catch (Exception e) {
            Log.e("tag", "Error!");
        }
        Message msg = new Message();
        msg.what = 0;
        msg.obj = table;
        mHandler.sendMessage(msg);
    }

    public void parser2(InputStream istream, Handler mHandler) {

        HashMap<String, String> table = new HashMap<String, String>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(istream);
            Element order = doc.getDocumentElement();
            NodeList infoheads = order.getElementsByTagName("infohead");
            Log.i("test", "parser3");
            Node info = infoheads.item(0);
            NamedNodeMap Attrs = info.getAttributes();
            Node attr = Attrs.item(1);
            if(attr.getNodeValue().equals("succeed")){
                Log.e("tag", "downsucceed!");
            }
            if(attr.getNodeValue().equals("error")){
                Log.e("tag", "downError!");
            }

            NodeList words = order.getElementsByTagName("wordinfo");
            for(int i=0; i<4; i++){
                Node word = words.item(i);
                Attrs = word.getAttributes();
                table.put(Attrs.item(0).getNodeValue(), Attrs.item(1).getNodeValue());
                //Log.i("word", Attrs.item(1).getNodeValue());
            }



        } catch (Exception e) {
            Log.e("tag", "Error!");
        }
        Message msg = new Message();
        msg.what = 0;
        msg.obj = table;
        mHandler.sendMessage(msg);
    }

    public void parser3(InputStream istream, Handler mHandler) {
        Log.i("test", "parser1");

        ArrayList<HashMap<String, String>> hashArray = new ArrayList<HashMap<String, String>>();


        try {
            Log.i("test", "parser2");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(istream);
            Element order = doc.getDocumentElement();
            NodeList infoheads = order.getElementsByTagName("infohead");
            Log.i("test", "parser3");
            Node info = infoheads.item(0);
            NamedNodeMap Attrs = info.getAttributes();
            Node attr = Attrs.item(1);
            if(attr.getNodeValue().equals("succeed")){
                Log.e("tag", "downsucceed!");
            }
            if(attr.getNodeValue().equals("error")){
                Log.e("tag", "downError!");
            }



            NodeList friends = order.getElementsByTagName("friendinfo");
            Node friend;

            for(int i=0; i<friends.getLength(); i++){
                hashArray.add(new HashMap<String, String>());
                friend = friends.item(i);
                Attrs = friend.getAttributes();
                for(int j=0; j<Attrs.getLength(); j++){
                    attr = Attrs.item(j);
                    hashArray.get(i).put(attr.getNodeName(), attr.getNodeValue());
                }
            }

        } catch (Exception e) {
            Log.e("tag", "Error!");
        }
        Message msg = new Message();
        msg.what = 0;
        msg.obj = hashArray;
        mHandler.sendMessage(msg);
    }

    public void parser4(InputStream istream, Handler mHandler) {
        Log.i("test", "parser1");

        ArrayList<HashMap<String, String>> hashArray = new ArrayList<HashMap<String, String>>();


        try {
            Log.i("test", "parser2");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(istream);
            Element order = doc.getDocumentElement();
            NodeList infoheads = order.getElementsByTagName("infohead");
            Log.i("test", "parser3");
            Node info = infoheads.item(0);
            NamedNodeMap Attrs = info.getAttributes();
            Node attr = Attrs.item(1);
            if(attr.getNodeValue().equals("succeed")){
                Log.e("tag", "downsucceed!");
            }
            if(attr.getNodeValue().equals("error")){
                Log.e("tag", "downError!");
            }



            NodeList randoms = order.getElementsByTagName("randominfo");
            Node random;

            for(int i=0; i<randoms.getLength(); i++){
                hashArray.add(new HashMap<String, String>());
                random = randoms.item(i);
                Attrs = random.getAttributes();
                for(int j=0; j<Attrs.getLength(); j++){
                    attr = Attrs.item(j);
                    hashArray.get(i).put(attr.getNodeName(), attr.getNodeValue());
                }
            }

        } catch (Exception e) {
            Log.e("tag", "Error!");
        }
        Message msg = new Message();
        msg.what = 0;
        msg.obj = hashArray;
        mHandler.sendMessage(msg);
    }

    public void parser5(InputStream istream, Handler mHandler) {    //댓글리스트
        Log.i("test", "parser1");

        ArrayList<HashMap<String, String>> hashArray = new ArrayList<HashMap<String, String>>();


        try {
            Log.i("test", "parser2");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(istream);
            Element order = doc.getDocumentElement();
            NodeList infoheads = order.getElementsByTagName("infohead");
            Log.i("test", "parser3");
            Node info = infoheads.item(0);
            NamedNodeMap Attrs = info.getAttributes();
            Node attr = Attrs.item(1);
            if(attr.getNodeValue().equals("succeed")){
                Log.e("tag", "downsucceed!");
            }
            if(attr.getNodeValue().equals("error")){
                Log.e("tag", "downError!");
            }



            NodeList replyinfos = order.getElementsByTagName("replyinfo");
            Node replyinfo;

            for(int i=0; i<replyinfos.getLength(); i++){
                hashArray.add(new HashMap<String, String>());
                replyinfo = replyinfos.item(i);
                Attrs = replyinfo.getAttributes();
                for(int j=0; j<Attrs.getLength(); j++){
                    attr = Attrs.item(j);
                    hashArray.get(i).put(attr.getNodeName(), attr.getNodeValue());
                }
            }

        } catch (Exception e) {
            Log.e("tag", "Error!");
        }
        Message msg = new Message();
        msg.what = 0;
        msg.obj = hashArray;
        mHandler.sendMessage(msg);
    }
}