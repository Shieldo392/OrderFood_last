package data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdversitedObject {
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("id")
    @Expose
    private String id;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
