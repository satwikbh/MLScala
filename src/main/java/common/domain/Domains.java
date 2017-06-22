package common.domain;

import java.util.List;

/**
 * Created by satwik on 5/31/17.
 */
public class Domains {
    private List<String> ip;
    private List<String> domain;

    public List<String> getIp() {
        return ip;
    }

    public void setIp(List<String> ip) {
        this.ip = ip;
    }

    public List<String> getDomain() {
        return domain;
    }

    public void setDomain(List<String> domain) {
        this.domain = domain;
    }
}
