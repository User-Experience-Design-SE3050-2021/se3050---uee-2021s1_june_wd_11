package com.example.cakehut;

import android.os.Parcel;
import android.os.Parcelable;

public class CakeModel implements Parcelable {

    private String weight;
    private String falvour;
    private String requirement;
    private String name;
    private String address;
    private String date;
    private  String image;
    private  String cid;


    public CakeModel() {
    }

    public CakeModel(String weight, String falvour, String requirement, String name, String address, String date, String image, String cid) {
        this.weight = weight;
        this.falvour = falvour;
        this.requirement = requirement;
        this.name = name;
        this.address = address;
        this.date = date;
        this.image = image;
        this.cid = cid;
    }

    protected CakeModel(Parcel in) {
        weight = in.readString();
        falvour = in.readString();
        requirement = in.readString();
        name = in.readString();
        address = in.readString();
        date = in.readString();
        image = in.readString();
        cid = in.readString();
    }

    public static final Creator<CakeModel> CREATOR = new Creator<CakeModel>() {
        @Override
        public CakeModel createFromParcel(Parcel in) {
            return new CakeModel(in);
        }

        @Override
        public CakeModel[] newArray(int size) {
            return new CakeModel[size];
        }
    };

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFalvour() {
        return falvour;
    }

    public void setFalvour(String falvour) {
        this.falvour = falvour;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weight);
        dest.writeString(falvour);
        dest.writeString(requirement);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(date);
        dest.writeString(image);
        dest.writeString(cid);
    }
}
