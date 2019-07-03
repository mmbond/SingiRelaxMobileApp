package rs.ispit.projekat.api;

import android.os.Handler;

public class ReadDataHandler extends Handler {

    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
