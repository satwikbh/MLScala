package common.domain;

import java.util.List;

/**
 * Created by satwik on 5/31/17.
 */
public class Hosts {
    private List<String> country_name;
    private List<String> ip;
    private List<String> hostname;

    public List<String> getCountry_name() {
        return country_name;
    }

    public void setCountry_name(List<String> country_name) {
        this.country_name = country_name;
    }

    public List<String> getIp() {
        return ip;
    }

    public void setIp(List<String> ip) {
        this.ip = ip;
    }

    public List<String> getHostname() {
        return hostname;
    }

    public void setHostname(List<String> hostname) {
        this.hostname = hostname;
    }
}
