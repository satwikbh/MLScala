package services.MakeSets;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by satwik on 5/31/17.
 */
public class SignatureSet {

    private SignatureSet() throws IllegalAccessError {
        throw new IllegalAccessError("Illegal access of the private constructor");
    }

    public static Set<String> makeSignatureSet(Map<String, List<Object>> signatures) {

        Set<String> returnSet = new HashSet<>();

        for (Map.Entry<String, List<Object>> stringJsonNodeEntry : signatures.entrySet()) {
            List<Object> valueList = stringJsonNodeEntry.getValue();
            for (Object o : valueList) {
                if (o instanceof Integer) {
                    returnSet.add(o.toString());
                }
                if (o instanceof String) {
                    returnSet.add(o.toString());
                }
            }
        }

        return returnSet;
    }
}
