package services.MakeSets;

import common.domain.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by satwik on 5/31/17.
 */
public class NetworkSet {

    private NetworkSet() throws IllegalAccessException {
        throw new IllegalAccessException("Illegal access of private constructor");
    }

    public static Set<String> makeNetworkSet(Network network) {

        Set<String> returnValue = new HashSet<>();
        Set<Integer> returnVal = new HashSet<>();

        Dns dns = network.getDns();
        Domains domains = network.getDomains();
        Hosts hosts = network.getHosts();
        Udp udp = network.getUdp();

        List<String> request = dns.getRequest();
        List<String> type = dns.getType();

        List<String> domain = domains.getDomain();
        List<String> ip = domains.getIp();

        List<String> countryName = hosts.getCountry_name();
        List<String> hostname = hosts.getHostname();
        List<String> ip1 = hosts.getIp();

        List<Integer> dport = udp.getDport();
        List<Integer> sport = udp.getSport();
        List<Integer> offset = udp.getOffset();
        List<String> dst = udp.getDst();
        List<String> src = udp.getSrc();

        returnValue.addAll(request);
        returnValue.addAll(type);
        returnValue.addAll(domain);
        returnValue.addAll(ip);
        returnValue.addAll(countryName);
        returnValue.addAll(hostname);
        returnValue.addAll(ip1);

        returnVal.addAll(dport);
        returnVal.addAll(sport);
        returnVal.addAll(offset);

        returnValue.addAll(dst);
        returnValue.addAll(src);

        for (Integer integer : returnVal) {
            returnValue.add(integer.toString());
        }

        return returnValue;
    }
}
