package com.example.basak.listtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by basak on 2015-03-26.
 */
public class Login extends Activity{
    ProgressDialog mProgress;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText edit = (EditText)findViewById(R.id.edit_mail);
                mProgress = ProgressDialog.show(Login.this,
                        "Wait", "Downloading...");
                DownThread  thread = new DownThread("http://175.211.100.229/monkeyme/monkeyme_server/RequestHandleServer.php", edit.getText().toString());
                thread.start();
            }
        });
    }

    class DownThread extends Thread {
        String mAddr;
        String mail;

        DownThread(String addr, String mail) {
            mAddr = addr;
            this.mail = mail;
        }

        public void run() {
            String result = DownloadHtml(mAddr, mail);
            Message message = mAfterDown.obtainMessage();
            message.obj = result;
            mAfterDown.sendMessage(message);
        }

        String DownloadHtml(String addr, String mail) {
            StringBuilder html = new StringBuilder();
            try {
                URL url = new URL(addr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if (conn != null) {
                    Log.i("tag", "conected 1");
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("content=type", "application/x-www-form-urlencoded");

                    StringBuffer buffer = new StringBuffer();
                    //buffer.append("command").append("=").append("login").append("&").append("email").append("=").append(mail).append("&").append("password").append("=").append("1234");
                    buffer.append("command=updateMain&memberNumber=1");
                    OutputStreamWriter outStream = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                    PrintWriter writer = new PrintWriter(outStream);
                    writer.write(buffer.toString());
                    writer.flush();


                    return parser2(conn.getInputStream());


                }
                conn.disconnect();
            } catch (NetworkOnMainThreadException e) {
                return "Error : 메인 스레드 네트워크 작업 에러 - " + e.getMessage();
            } catch (Exception e) {
                Log.e("tag", "error");
                return "Error : " + e.getMessage();
            }
            return html.toString();
        }
    }



    String parser2(InputStream istream){
        String result="";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(istream);

            Element order = doc.getDocumentElement();
            NodeList lists = order.getElementsByTagName("list");
            Node list = lists.item(0);
            NodeList friends = list.getChildNodes();
            for(int i=0; i<friends.getLength(); i++){
                result += "turn"+"="+"myturn"+"\n";
                Node friend = friends.item(i);
                NamedNodeMap Attrs = friend.getAttributes();
                for(int j=0; j< Attrs.getLength(); j++){
                    Node attr = Attrs.item(j);
                    result += attr.getNodeName()+"="+attr.getNodeValue()+"\n";
                }
            }
            list = lists.item(1);
            friends = list.getChildNodes();
            for(int i=0; i<friends.getLength(); i++){
                result += "turn"+"="+"friendsturn"+"\n";
                Node friend = friends.item(i);
                NamedNodeMap Attrs = friend.getAttributes();
                for(int j=0; j< Attrs.getLength(); j++){
                    Node attr = Attrs.item(j);
                    result += attr.getNodeName()+"="+attr.getNodeValue()+"\n";
                }
            }
            /**
            for(int i=0; i<books.getLength(); i++){
                Node book = books.item(i);
                NamedNodeMap Attrs = book.getAttributes();
                Node attr = Attrs.item(0);
                result = result+(attr.getNodeName()+"="+attr.getNodeValue());

                Node title = titles.item(i);
                Node text = title.getFirstChild();
                result = result + ("\ntitle : "+text.getNodeValue());

                Node author = authors.item(i);
                text = author.getFirstChild();
                result = result + ("\nauthor : "+text.getNodeValue());

                Node summary = summarys.item(i);
                text = summary.getFirstChild();
                result = result + ("\ntitle : "+text.getNodeValue());

                Node image = images.item(i);
                text = image.getFirstChild();
                result = result + ("\nimage : "+text.getNodeValue()+"\n\n");
            }
             **/

        } catch(Exception e){
            Log.e("tag", "Error!");
        }
        return result;
    }
    /**
    String parser2(InputStream istream){
        String result="";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(istream);

            Element order = doc.getDocumentElement();
            NodeList books = order.getElementsByTagName("Book");
            NodeList titles = order.getElementsByTagName("title");
            NodeList authors = order.getElementsByTagName("author");
            NodeList summarys = order.getElementsByTagName("summary");
            NodeList images = order.getElementsByTagName("image");

            for(int i=0; i<books.getLength(); i++){
                Node book = books.item(i);
                NamedNodeMap Attrs = book.getAttributes();
                Node attr = Attrs.item(0);
                result = result+(attr.getNodeName()+"="+attr.getNodeValue());

                Node title = titles.item(i);
                Node text = title.getFirstChild();
                result = result + ("\ntitle : "+text.getNodeValue());

                Node author = authors.item(i);
                text = author.getFirstChild();
                result = result + ("\nauthor : "+text.getNodeValue());

                Node summary = summarys.item(i);
                text = summary.getFirstChild();
                result = result + ("\ntitle : "+text.getNodeValue());

                Node image = images.item(i);
                text = image.getFirstChild();
                result = result + ("\nimage : "+text.getNodeValue()+"\n\n");
            }

        } catch(Exception e){
            Log.e("tag", "Error!");
        }
        return result;
    }**/

    Handler mAfterDown = new Handler() {
        public void handleMessage(Message msg) {
            mProgress.dismiss();
            TextView result = (TextView)findViewById(R.id.result);
            result.setText((String)msg.obj);
        }
    };
}

