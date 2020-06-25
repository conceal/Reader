package com.example.syz.demo.util;

import android.os.Parcel;
import android.os.Parcelable;

public class TextSome implements Parcelable {
    private String headerImage;
    private String textSmile;
    private int goodNumber;
    private int shareNumber;
    private int badNumber;

    public int getBadNumber() {
        return badNumber;
    }

    public int getGoodNumber() {
        return goodNumber;
    }

    public int getShareNumber() {
        return shareNumber;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public String getTextSmile() {
        return textSmile;
    }

    public void setBadNumber(int badNumber) {
        this.badNumber = badNumber;
    }

    public void setGoodNumber(int goodNumber) {
        this.goodNumber = goodNumber;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public void setShareNumber(int shareNumber) {
        this.shareNumber = shareNumber;
    }

    public void setTextSmile(String textSmile) {
        this.textSmile = textSmile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(textSmile);
        dest.writeString(headerImage);
        dest.writeInt(goodNumber);
        dest.writeInt(badNumber);
        dest.writeInt(shareNumber);
    }

    public static final Creator<TextSome> CREATOR = new Creator<TextSome>() {
        @Override
        public TextSome createFromParcel(Parcel source) {
            TextSome textSome = new TextSome();
            textSome.textSmile = source.readString();
            textSome.headerImage = source.readString();
            textSome.goodNumber = source.readInt();
            textSome.badNumber = source.readInt();
            textSome.shareNumber = source.readInt();
            return textSome;
        }

        @Override
        public TextSome[] newArray(int size) {
            return new TextSome[size];
        }
    };
}
