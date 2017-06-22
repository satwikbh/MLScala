package services.Conversion;

import com.fasterxml.jackson.databind.JsonNode;
import common.domain.*;
import common.utils.MapperUtil;
import org.apache.log4j.Logger;
import org.bson.Document;
import services.MakeSets.*;

import java.util.*;

/**
 * Created by satwik on 5/31/17.
 */
public class ConversionLogic {


    private static final String BEHAVIOR = "behavior";
    private static final String MALHEUR = "malheur";
    private static final String NETWORK = "network";
    private static final String SIGNATURES = "signatures";
    private static final String STAT_SIGNATURES = "statSignatures";
    private static final String STATIC = "static";

    private static Logger log = Logger.getLogger(ConversionLogic.class);

    public static Map<String, Set<String>> keyValuePair = new HashMap<>();

    private static Set<String> featureDecision(String feature, JsonNode value) {
        JsonNode innerValue = value.get(feature);
        switch (feature) {
            case BEHAVIOR:
                Behavior behavior = MapperUtil.fromObject(innerValue, Behavior.class);
                return BehaviorSet.makeBehaviorSet(behavior);
            case MALHEUR:
                // This won't be used in the clustering but will be used as a reference for the cluster results.
                Malheur malheur = MapperUtil.fromObject(innerValue, Malheur.class);
                break;
            case NETWORK:
                Network network = MapperUtil.fromObject(innerValue, Network.class);
                return NetworkSet.makeNetworkSet(network);
            case SIGNATURES:
                // TODO : Does this work ??
                Map<String, List<Object>> signatures = MapperUtil.fromObject(innerValue, Map.class);
                return SignatureSet.makeSignatureSet(signatures);
            case STAT_SIGNATURES:
                return StatSignaturesSet.makeStatSignaturesSet(innerValue);
            case STATIC:
                Static staticDomainObject = MapperUtil.fromObject(innerValue, Static.class);
                return StaticSet.makeStaticSet(staticDomainObject);
            default:
                log.error("Unknown Feature record");
                break;
        }
        return null;
    }

    public static Map<String, Set<String>> getDoc(Document document) {

        JsonNode eachDoc = MapperUtil.fromObject(document, JsonNode.class);
        MetaObjectValue metaObjectValue = MapperUtil.fromObject(eachDoc, MetaObjectValue.class);
        JsonNode value = metaObjectValue.getValue();
        String feature = metaObjectValue.getFeature();
        String key = metaObjectValue.getKey();

        Map<String, Set<String>> returnValue = new HashMap<>();
        Set<String> strings = featureDecision(feature, value.get(key));
        returnValue.put(key, strings);

        return returnValue;
    }

    public static Set<String> getDoc(String document) {

        JsonNode eachDoc = MapperUtil.fromObject(document, JsonNode.class);
        MetaObjectValue metaObjectValue = MapperUtil.fromObject(eachDoc, MetaObjectValue.class);
        JsonNode value;
        String feature;
        String key;
        if (metaObjectValue != null) {
            value = metaObjectValue.getValue();
            feature = metaObjectValue.getFeature();
            key = metaObjectValue.getKey();
            return featureDecision(feature, value.get(key));
        } else {
            return new HashSet<>();
        }
    }

    public static Set<String> getFeaturePool() {
        return generatePool.pool;
    }

}
