package common.domain;

/**
 * Created by satwik on 5/31/17.
 */
public class Network {

    private Domains domains;
    private Udp udp;
    private Hosts hosts;
    private Dns dns;

    public Domains getDomains() {
        return domains;
    }

    public void setDomains(Domains domains) {
        this.domains = domains;
    }

    public Udp getUdp() {
        return udp;
    }

    public void setUdp(Udp udp) {
        this.udp = udp;
    }

    public Hosts getHosts() {
        return hosts;
    }

    public void setHosts(Hosts hosts) {
        this.hosts = hosts;
    }

    public Dns getDns() {
        return dns;
    }

    public void setDns(Dns dns) {
        this.dns = dns;
    }
}
