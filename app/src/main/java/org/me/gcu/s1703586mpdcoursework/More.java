//Student Name: Sofi Bambrick
//Student ID: S1703586

package org.me.gcu.s1703586mpdcoursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class More extends AppCompatActivity implements View.OnClickListener {

    private ItemClass earthquake;
    private TextView loc;
    private TextView cat;
    private TextView depth;
    private TextView mag;
    private TextView link;
    private TextView pub;
    private TextView lat;
    private TextView lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        earthquake = (ItemClass) getIntent().getExtras().getSerializable("Earthquake");
        setContentView(R.layout.activity_more);

        loc = findViewById(R.id.Loc);
        cat = findViewById(R.id.Cat);
        depth = findViewById(R.id.Depth);
        mag = findViewById(R.id.Mag);
        pub = findViewById(R.id.Pub);
        lat = findViewById(R.id.Lat);
        lon = findViewById(R.id.Lon);
        link = findViewById(R.id.Link);

        loc.setText(earthquake.getLocation());
        cat.setText(earthquake.getCategory());
        depth.setText(earthquake.getDepth());
        mag.setText(earthquake.getMagnitude());
        pub.setText(earthquake.getPubDate());
        lat.setText(earthquake.getLat());
        lon.setText(earthquake.getLon());
        link.setText(earthquake.getLink());

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Uri uri = Uri.parse(earthquake.getLink());
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}