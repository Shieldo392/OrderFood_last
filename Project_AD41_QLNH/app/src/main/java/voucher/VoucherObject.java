package voucher;

public class VoucherObject {
    private int id;
    private String src;
    private String descr;
    private String content;
    private String code;

    public VoucherObject(int id, String src, String descr, String content, String code) {
        this.id = id;
        this.src = src;
        this.descr = descr;
        this.content = content;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
