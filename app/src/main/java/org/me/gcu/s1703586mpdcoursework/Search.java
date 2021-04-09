//Student Name: Sofi Bambrick
//Student ID: S1703586

package org.me.gcu.s1703586mpdcoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Search extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ArrayList<SearchClass> mSearch = new ArrayList<>();
    EditText start;
    EditText end;
    Button confirm;
    LinearLayout linear;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        start = (EditText) findViewById(R.id.start);
        end = (EditText) findViewById(R.id.end);
        linear = (LinearLayout) findViewById(R.id.linear);
        confirm = (Button) findViewById(R.id.confirmButton);
        confirm.setOnClickListener(this);

        new Search.MyAsyncTask().execute();
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {

        String startDateEntered = start.getText().toString();
        String endDateEntered = end.getText().toString();

        Date startDate = getDate(startDateEntered);
        Date endDate = getDate(endDateEntered);

        ArrayList<SearchClass> dates = new ArrayList<>();
        SearchClass getNorthley = null;
        SearchClass getSouthley = null;
        SearchClass getWestley = null;
        SearchClass getEastley = null;
        SearchClass getLargeMag = null;
        SearchClass getHighDepth = null;
        SearchClass getLowDepth = null;

        for (int i = 0; i < mSearch.size(); i++) {
            Date date = getDate(mSearch.get(i).getPubDate());
            if (startDate == null || endDate == null) {
                Toast.makeText(this, "No dates inputted", Toast.LENGTH_SHORT).show();
            } else if (startDate.equals(endDate)) {
                Toast.makeText(this, "Dates cannot match", Toast.LENGTH_SHORT).show();
            } else if (mSearch.isEmpty()) {
                Toast.makeText(this, "No Earthquakes within date range", Toast.LENGTH_SHORT).show();
            }
            if (date.after(startDate) && date.before(endDate)) {
                dates.add(mSearch.get(i));
            }
        }

        if (mSearch.size() >= 0) {
            for (int i = 0; i < mSearch.size(); i++) {
                if (i == 0) {

                    getNorthley = mSearch.get(i);
                    getSouthley = mSearch.get(i);
                    getEastley = mSearch.get(i);
                    getWestley = mSearch.get(i);
                    getLargeMag = mSearch.get(i);
                    getHighDepth = mSearch.get(i);
                    getLowDepth = mSearch.get(i);
                }

                if (Float.parseFloat(getNorthley.getLat()) < Float.parseFloat(mSearch.get(i).getLat())) {
                    getNorthley = mSearch.get(i);
                    TextView northley = new TextView(this);
                    northley.setText("Furthest North -  " + getNorthley.getLocation() + getNorthley.getLat() + getNorthley.getLon());
                    linear.addView(northley);
                }

                if (Float.parseFloat(getSouthley.getLat()) > Float.parseFloat(mSearch.get(i).getLat())) {
                    getSouthley = mSearch.get(i);
                    TextView southley = new TextView(this);
                    southley.setText("Furthest South - " + getSouthley.getLocation() + getSouthley.getLat() + getSouthley.getLon());
                    linear.addView(southley);
                }

                if (Float.parseFloat(getEastley.getLon()) < Float.parseFloat(mSearch.get(i).getLon())) {
                    getEastley = mSearch.get(i);
                    TextView easterly = new TextView(this);
                    easterly.setText("Furthest East - " + getEastley.getLocation() + getEastley.getLat() + getEastley.getLon());
                    linear.addView(easterly);
                }

                if (Float.parseFloat(getWestley.getLon()) > Float.parseFloat(mSearch.get(i).getLon())) {
                    getWestley = mSearch.get(i);
                    TextView westerly = new TextView(this);
                    westerly.setText("Furthest West - " + getWestley.getLocation() + getWestley.getLat() + getWestley.getLon());
                    linear.addView(westerly);
                }

                String currentMag = mSearch.get(i).getMagnitude().substring(11);
                String currentMag2 = getLargeMag.getMagnitude().substring(11);
                if (Float.parseFloat(currentMag2) < Float.parseFloat(currentMag)) {
                    getLargeMag = mSearch.get(i);
                    TextView largMag = new TextView(this);
                    largMag.setText("Largest Magnitude - " + getLargeMag.getLocation() + getLargeMag.getMagnitude());
                    linear.addView(largMag);
                    largMag.setTextColor(Color.RED);
                }

                String currentHighDepth = mSearch.get(i).getDepth().substring(7);
                currentHighDepth = currentHighDepth.substring(0, currentHighDepth.length() - 3);
                String currentHighDepth2 = getHighDepth.getDepth().substring(7);
                currentHighDepth2 = currentHighDepth2.substring(0, currentHighDepth2.length() - 3);
                if (Float.parseFloat(currentHighDepth2) < Float.parseFloat(currentHighDepth)) {
                    getHighDepth = mSearch.get(i);
                    TextView highDepth = new TextView(this);
                    highDepth.setText("Deepest - " + getHighDepth.getLocation() + getHighDepth.getDepth());
                    linear.addView(highDepth);
                }

                String currentLowDepth = mSearch.get(i).getDepth().substring(7);
                currentLowDepth = currentLowDepth.substring(0, currentLowDepth.length() - 3);
                String currentLowDepth2 = getLowDepth.getDepth().substring(7);
                currentLowDepth2 = currentLowDepth2.substring(0, currentLowDepth2.length() - 3);
                if (Float.parseFloat(currentLowDepth2) > Float.parseFloat(currentLowDepth)) {
                    getLowDepth = mSearch.get(i);
                    TextView lowDepth = new TextView(getApplicationContext());
                    lowDepth.setText("Shallowest - " + getLowDepth.getLocation() + getLowDepth.getDepth());
                    linear.addView(lowDepth);
                }
                linear.setBackgroundColor(Color.LTGRAY);
            }
        }
    }

    public class MyAsyncTask extends AsyncTask<Integer, Void, Exception> {
        Exception exception = null;

        @Override
        protected Exception doInBackground(Integer... params) {
            try {
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                SearchClass item = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                            item = new SearchClass();
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                item.setTitle(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) {
                                item.setDescription(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                item.setLink(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            if (insideItem) {
                                item.setPubDate(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("category")) {
                            if (insideItem) {
                                item.setCategory(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("geo:lat")) {
                            if (insideItem) {
                                item.setLat((xpp.nextText()));
                            }
                        } else if (xpp.getName().equalsIgnoreCase("geo:long")) {
                            if (insideItem) {
                                item.setLon((xpp.nextText()));
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                        Log.d("Debug", "Item is: " + item.toString());
                        mSearch.add(item);
                    }
                    eventType = xpp.next();
                }
            } catch (MalformedURLException e) {
                exception = e;
            } catch (XmlPullParserException e) {
                exception = e;
            } catch (IOException e) {
                exception = e;
            }
            return exception;
        }
    }

    public Date getDate(String date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss");
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }
}