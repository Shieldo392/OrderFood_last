package discovery;

public class menuItem {
    private int imvSrc;
    private String ten;

    public menuItem(int imvSrc, String ten) {
        this.imvSrc = imvSrc;
        this.ten = ten;
    }

    public int getImvSrc() {
        return imvSrc;
    }

    public void setImvSrc(int imvSrc) {
        this.imvSrc = imvSrc;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
