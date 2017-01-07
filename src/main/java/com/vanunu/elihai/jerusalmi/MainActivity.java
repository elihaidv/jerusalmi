package com.vanunu.elihai.jerusalmi;

import android.content.Intent;
import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy'&mn='MM'&dy='dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dafYpmiClick(View view) {
        final Intent intent = new Intent(this, singlePage.class);
        String start = "24/04/2014";
        String end = "04/08/2018";

        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = "http://www.yeshiva.org.il/Calendar/calaj.aspx?op=dj&pl=156&yr=" +sdf.format(new Date())+ "&lng=heb";

                try {
                    InputStream is = new URL(url).openStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                    String jsonText = rd.readLine();


                    String daf = new JSONObject(jsonText).getJSONArray("limud").getJSONObject(1).getString("value");
                    Gematria gm = new Gematria();
                    String[] dafName = daf.split(" ");
                    int paper = gm.getWordValue(dafName[dafName.length - 1]);
                    int mass = getMass(dafName[0] + (dafName.length == 3 ? " " + dafName[1] : ""));
                    String fileName = "tlmud/" + mass + "/" + paper + "a.txt";
                    intent.putExtra("dafSource", fileName);
                    MainActivity.this.startActivity(intent);
                } catch (IOException e) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "בדוק חיבור לרשת", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    public void aboutClicked(View view) {this.startActivity(new Intent(this, about.class)); }
    public void browser(View view) {
        this.startActivity(new Intent(this, browser.class));
    }

    public void prevDaf(View view) {
        Intent intent = new Intent(this, singlePage.class);
        intent.putExtra("dafSource", getSharedPreferences("lastPage", MODE_PRIVATE).getString("lastPage", ""));
        this.startActivity(intent);
    }
    private int getMass(String massechetName) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.getAssets().open("res.xml"), "UTF-8");
            doc.getDocumentElement().normalize();

            NodeList massechetNodes = doc.getElementsByTagName("massechet");
            for (int i = 0; i < massechetNodes.getLength(); i++) {
                if (((Element) massechetNodes.item(i)).getAttributes().getNamedItem("name").getNodeValue().equals(massechetName)) {
                    return Integer.parseInt(((Element) massechetNodes.item(i)).getAttributes().getNamedItem("count").getNodeValue());
                }
            }
        } catch (Exception e) {
        }
        return 0;
    }
}
