package Home;

public class ImagesSlide {
    private String src;
    private int id;

    public ImagesSlide(String src, int id) {
        this.src = src;
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
