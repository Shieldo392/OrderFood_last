package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tenSP")
    @Expose
    private String tenSP;
    @SerializedName("moTa")
    @Expose
    private String moTa;
    @SerializedName("giaBan")
    @Expose
    private String giaBan;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("rating")
    @Expose
    private String rating;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

}
