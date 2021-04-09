//Student Name: Sofi Bambrick
//Student ID: S1703586

package org.me.gcu.s1703586mpdcoursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ArrayList<ItemClass> mItems = new ArrayList<>();
    FrameLayout mapsLayout;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    ListDataAdapter listDataAdapter;
    private Context context;
    Button search;
    Button map;
    Button info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        linearLayoutManager = new LinearLayoutManager(context);

        mapsLayout = findViewById(R.id.mapsLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        search = (Button) findViewById(R.id.search);
        map = (Button) findViewById(R.id.map);
        info = (Button) findViewById(R.id.info);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Search.class);
                    startActivity(intent);
                }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Earthquake", mItems);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Student.class);
                startActivity(intent);
            }
        });

        new MyAsyncTask().execute();
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }


    public class MyAsyncTask extends AsyncTask<Integer, Void, ArrayList<ItemClass>> {

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Loading data...");
            progressDialog.show();
        }


        @Override
        protected ArrayList<ItemClass> doInBackground(Integer... params) {

            try {
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                ItemClass item = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                            item = new ItemClass();
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
                        mItems.add(item);

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
            return mItems;
        }

        @Override
        protected void onPostExecute(ArrayList<ItemClass> s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
            loadList(s);

        }
    }

    private void loadList(ArrayList<ItemClass> listData) {
        listDataAdapter = new ListDataAdapter(context, listData);
        recyclerView.setAdapter(listDataAdapter);
    }
}