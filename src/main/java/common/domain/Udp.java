package common.domain;

import java.util.List;

/**
 * Created by satwik on 5/31/17.
 */
public class Udp {

    private List<Integer> dport;
    private List<Integer> sport;
    private List<Integer> offset;
    private List<String> src;
    private List<String> dst;

    public List<Integer> getDport() {
        return dport;
    }

    public void setDport(List<Integer> dport) {
        this.dport = dport;
    }

    public List<Integer> getSport() {
        return sport;
    }

    public void setSport(List<Integer> sport) {
        this.sport = sport;
    }

    public List<Integer> getOffset() {
        return offset;
    }

    public void setOffset(List<Integer> offset) {
        this.offset = offset;
    }

    public List<String> getSrc() {
        return src;
    }

    public void setSrc(List<String> src) {
        this.src = src;
    }

    public List<String> getDst() {
        return dst;
    }

    public void setDst(List<String> dst) {
        this.dst = dst;
    }
}
