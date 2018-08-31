package com.example.android.miwok;

public class word {
    private String mDefaultTranslation;
    private String mGuajaratiTranslation;
    private int mImageResourceId=IMG;
    private int maudio;
    private static final int IMG=-1;
    public word(String defaultTranslation,String GujaratiTranslation,int Audio)
    {
        mDefaultTranslation=defaultTranslation;
        mGuajaratiTranslation=GujaratiTranslation;
        maudio=Audio;
    }
    public word(String defaultTranslation,String GujaratiTranslation,int ImageResourceId,int Audio)
    {
        mDefaultTranslation=defaultTranslation;
        mGuajaratiTranslation=GujaratiTranslation;
        mImageResourceId=ImageResourceId;
        maudio=Audio;
    }
    public String getDefaultTranslation()
    {
        return mDefaultTranslation;
    }
    public  String getGujaratiTranslation()
    {
        return mGuajaratiTranslation;
    }
    public int getmImageResourceId(){return mImageResourceId;}
    public int getaudio(){return maudio;}
    public boolean hasImage()
    {
        return mImageResourceId!=IMG;
    }
}
