package com.example.android.earthquake;


public class Report {
    private Double mMagnitude;


    private String mUrl;


    private long mDate;

    private String mPrimarylocation;

    private String mSeclocation;



    public Report(Double magnitude, String Primarylocation,String Seclocation, long date,String url) {
        mMagnitude=magnitude;
        mPrimarylocation=Primarylocation;
        mSeclocation= Seclocation;
        mDate=date;
        mUrl=url;
    }





    public Double getMagnitude() {
        return mMagnitude;
    }


    public String getmPrimarylocation() {
        return mPrimarylocation;
    }

    public String getmSeclocation() {
        return mSeclocation;
    }

    public long getDate(){return mDate;}

    public String getmUrl() {
        return mUrl;
    }

}
