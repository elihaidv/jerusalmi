package com.vanunu.elihai.jerusalmi;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class singlePage extends AppCompatActivity {

    private TextView tvTitle;
    private JustifyTextView tvContent;
    private ScrollView sv;
    private String fileName;
    private String title;
    private String content;
    private ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_page);
        fileName = this.getIntent().getStringExtra("dafSource");
        tvContent = (JustifyTextView) this.findViewById(R.id.dafText);
        tvTitle = (TextView) this.findViewById(R.id.dafTile);
        sv = (ScrollView) this.findViewById(R.id.SCROLLER_ID);

        tvTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ShmulikCLM.ttf"));
        tvContent.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ComixNo2CLM-Medium.ttf"));
        makePage();

        sv.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeRight() {
                tvContent.setText("");
                if (fileName.charAt(fileName.length() - 5) == 'b') {
                    int page = Integer.parseInt(fileName.substring(8).replaceAll("\\D+", ""));
                    page++;
                    fileName = fileName.substring(0, fileName.lastIndexOf("/") + 1) + page + "a.txt";
                } else {
                    fileName = fileName.replaceAll("a.", "b.");
                }
                makePage();
            }

            public void onSwipeLeft() {
                tvContent.setText("");
                if (fileName.charAt(fileName.length() - 5) == 'a') {
                    int page = Integer.parseInt(fileName.substring(8).replaceAll("\\D+", ""));
                    page--;
                    fileName = fileName.substring(0, fileName.lastIndexOf("/") + 1) + page + "b.txt";
                } else {
                    fileName = fileName.replaceAll("b.", "a.");
                }
                makePage();
            }
        });
        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }
    private void makePage() {

        try {
            Scanner scanner = new Scanner(this.getAssets().open(fileName), "UTF-8");
            title = scanner.nextLine();
            content = "";
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("מסכת") && line.contains("דף") && line.contains("פרק")) {
                    content +=  "פרק" + line.split("פרק")[1].substring(0,3)  + "\n";
                } else {
                    content += line + "\n";
                }

            }
            content = content.replaceAll("[(]","&").replaceAll("[)]","(").replaceAll("&",")");
            tvContent.setText(content + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n...");
            tvTitle.setText(title);
            sv.fullScroll(ScrollView.FOCUS_UP);
            getSharedPreferences("lastPage", MODE_PRIVATE).edit().putString("lastPage", fileName).commit();
        } catch (IOException e) {
            tvContent.setText("אין מה להציג");

            e.printStackTrace();
        }
    }
    private class simpleOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            float size = tvContent.getTextSize();

            float factor = detector.getScaleFactor();
            float product = size*factor;
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);
            return true;
        }
    }
}
