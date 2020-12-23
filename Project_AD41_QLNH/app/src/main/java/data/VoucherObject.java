package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoucherObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("descr")
    @Expose
    private String descr;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("code")
    @Expose
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
