package Home;

public class ImagesSlide {
    private int src;
    private int id;

    public ImagesSlide(int src, int id) {
        this.src = src;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }
}
