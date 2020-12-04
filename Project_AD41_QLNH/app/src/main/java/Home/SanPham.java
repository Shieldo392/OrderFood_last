package Home;

public class SanPham {
    private int srcImage;
    private String tenSP;
    private int giaSP;

    public SanPham(int srcImage, String tenSP, int giaSP) {
        this.srcImage = srcImage;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
    }

    public int getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(int srcImage) {
        this.srcImage = srcImage;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }
}
