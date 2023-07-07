package services.MakeSets;

import common.domain.Static;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by satwik on 5/31/17.
 */
public class StaticSet {

    private StaticSet() throws IllegalAccessError {
        throw new IllegalAccessError("Illegal access of the private constructor");
    }

    public static Set<String> makeStaticSet(Static staticDomainObject) {
        Set<String> returnSet = new HashSet<>();

        List<String> dirents = staticDomainObject.getDirents();
        List<String> dlls = staticDomainObject.getDlls();
        List<String> fnName = staticDomainObject.getFn_name();
        List<String> peidSignatures = staticDomainObject.getPeid_signatures();
        List<String> sections = staticDomainObject.getSections();

        returnSet.addAll(dirents);
        returnSet.addAll(dlls);
        returnSet.addAll(fnName);
        returnSet.addAll(peidSignatures);
        returnSet.addAll(sections);

        return returnSet;

    }
}
