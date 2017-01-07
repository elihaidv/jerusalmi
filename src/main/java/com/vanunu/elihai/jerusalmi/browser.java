package com.vanunu.elihai.jerusalmi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;

public class browser extends AppCompatActivity {

    private Inte massechetChoosen = new Inte();
    private Inte pageChoosen = new Inte();
    private Inte paperChoose = new Inte();
    private ListView lvMassechet;
    private ListView lvPaper;
    private ListView lvPage;
    private ArrayAdapter<String> MassAdapter;
    private ArrayAdapter<String> PaperAdapter;
    private ArrayAdapter<String> pagesAdapter;
    private NodeList massechetNodes;
    private View lastMass;
    private View lastPage;
    private View lastPaper;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);


        lvMassechet = (ListView) findViewById(R.id.massechetList);
        lvPage = (ListView) findViewById(R.id.pageList);
        lvPaper = (ListView) findViewById(R.id.paperList);

        itemClicked icMass = new itemClicked(massechetChoosen);
        itemClicked icPap = new itemClicked(pageChoosen);
        itemClicked icPag = new itemClicked(paperChoose);

        lvMassechet.setOnItemClickListener(icMass);
        lvPage.setOnItemClickListener(icPag);
        lvPaper.setOnItemClickListener(icPap);

        MassAdapter = new MyCustomAdapter(this, icMass, massechetChoosen);
        PaperAdapter = new MyCustomAdapter(this, icPap, paperChoose);
        pagesAdapter = new MyCustomAdapter(this, icPag, pageChoosen);

        try {

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.getAssets().open("res.xml"), "UTF-8");
            doc.getDocumentElement().normalize();

            massechetNodes = doc.getElementsByTagName("massechet");
            for (int i = 0; i < massechetNodes.getLength(); i++) {
                MassAdapter.add(((Element) massechetNodes.item(i)).getAttributes().getNamedItem("name").getNodeValue());
            }

            setPages(Integer.parseInt(massechetNodes.item(0).getTextContent()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        pagesAdapter.add("א");
        pagesAdapter.add("ב");

        lvMassechet.setAdapter(MassAdapter);
        lvPaper.setAdapter(PaperAdapter);
        lvPage.setAdapter(pagesAdapter);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    public void OKClicked(View v) {
        Intent intent = new Intent(this, singlePage.class);
        intent.putExtra("dafSource", "tlmud/" + (massechetChoosen.x + 1) + "/" + (paperChoose.x + 1) + (pageChoosen.x == 0 ? "a" : "b") + ".txt");
        this.startActivity(intent);
    }

    private class itemClicked implements AdapterView.OnItemClickListener {

        private Inte in;
        private View mView;

        public itemClicked(Inte i) {
            in = i;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            in.x = position;
            if (mView != null) {
                mView.setBackgroundColor(Color.TRANSPARENT);
            }
            mView = view;
            view.setBackgroundColor(Color.parseColor("#FF57760D"));
        }
        public void setView(View view) {
            mView = view;
        }
    }

    private void setPages(int limit) {
        PaperAdapter.clear();
        for (int i = 1; i <= limit; i++) {
            PaperAdapter.add(Gematria.getLetters(i, true));
        }
    }

    private class Inte {
        public int x = -1;
    }

    private class MyCustomAdapter extends ArrayAdapter<String> {

        private Inte in;
        private itemClicked ic;

        public MyCustomAdapter(Context context, itemClicked mIC, Inte i) {
            super(context, android.R.layout.simple_list_item_1);
            ic =mIC;
            in = i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            v.setBackgroundColor(Color.TRANSPARENT);
            if (position == in.x) {
                v.setBackgroundColor(Color.parseColor("#FF57760D"));
                ic.setView(convertView);
            }
            return v;
        }
    }
}
