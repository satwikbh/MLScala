package services.MakeSets;

import com.fasterxml.jackson.databind.JsonNode;
import common.utils.MapperUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by satwik on 5/31/17.
 */
public class StatSignaturesSet {

    private StatSignaturesSet() throws IllegalAccessException {
        throw new IllegalAccessException("Illegal access of the private constructor");
    }

    public static Set<String> makeStatSignaturesSet(JsonNode innerValue) {
        Set<String> returnSet = new HashSet<>();

        List<String> statSignatures1 = MapperUtil.fromObject(innerValue, List.class);
        returnSet.addAll(statSignatures1);

        return returnSet;

    }
}
