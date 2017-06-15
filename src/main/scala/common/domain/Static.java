package common.domain;

import java.util.List;

/**
 * Created by satwik on 5/31/17.
 */
public class Static {

    private List<String> dirents;
    private List<String> fn_name;
    private List<String> dlls;
    private List<String> sections;
    private List<String> peid_signatures;

    public List<String> getDirents() {
        return dirents;
    }

    public void setDirents(List<String> dirents) {
        this.dirents = dirents;
    }

    public List<String> getFn_name() {
        return fn_name;
    }

    public void setFn_name(List<String> fn_name) {
        this.fn_name = fn_name;
    }

    public List<String> getDlls() {
        return dlls;
    }

    public void setDlls(List<String> dlls) {
        this.dlls = dlls;
    }

    public List<String> getSections() {
        return sections;
    }

    public void setSections(List<String> sections) {
        this.sections = sections;
    }

    public List<String> getPeid_signatures() {
        return peid_signatures;
    }

    public void setPeid_signatures(List<String> peid_signatures) {
        this.peid_signatures = peid_signatures;
    }
}
