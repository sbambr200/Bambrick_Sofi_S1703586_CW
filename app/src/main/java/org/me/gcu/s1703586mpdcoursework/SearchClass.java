//Student Name: Sofi Bambrick
//Student ID: S1703586

package org.me.gcu.s1703586mpdcoursework;

public class SearchClass implements  Comparable {

    String title;
    String description;
    String link;
    String pubDate;
    String category;
    String lat;
    String lon;
    String location;
    String depth;
    String magnitude;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        String[] result = description.split(";");
        for(int i = 0; i < result.length; i++)  {
            if(i==1)    {
                setLocation((result[1]));
            }
            else
            if(i==3)   {
                setDepth((result[3]));
            }
            else
            if(i==4)    {
                setMagnitude((result[4]));
            }
        }
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    @Override
    public int compareTo(Object o) {

        SearchClass mag = (SearchClass)o;
        String mag1 = mag.getMagnitude().substring(11);
        String mag2 = this.getMagnitude().substring(11);
        Float floatMag1 = Float.parseFloat(mag1);
        Float floatMag2 = Float.parseFloat(mag2);
        return floatMag1.compareTo(floatMag2);
    }
}