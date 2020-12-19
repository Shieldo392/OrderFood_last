package FoodOject;

public class Food {
    private int id;
    private String tenSP;
    private String moTa;
    private int giaBan;
    private String imgSrc;
    private float rating;

    public Food(int id, String tenSP, String moTa, int giaBan, String imgSrc, float rating) {
        this.id = id;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.imgSrc = imgSrc;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

