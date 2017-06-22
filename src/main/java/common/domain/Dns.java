package common.domain;

import java.util.List;

/**
 * Created by satwik on 5/31/17.
 */
public class Dns {
    private List<String> request;
    private List<String> type;

    public List<String> getRequest() {
        return request;
    }

    public void setRequest(List<String> request) {
        this.request = request;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
}
