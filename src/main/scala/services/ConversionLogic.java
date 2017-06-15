package services;

import com.fasterxml.jackson.databind.JsonNode;
import common.domain.*;
import common.utils.MapperUtil;
import org.apache.log4j.Logger;
import org.bson.Document;
import services.MakeSets.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by satwik on 5/31/17.
 */
public class ConversionLogic {


    private static final String BEHAVIOR = "behavior";
    private static final String MALHEUR = "malheur";
    private static final String NETWORK = "network";
    private static final String SIGNATURES = "signatures";
    private static final String STATSIGNATURES = "statSignatures";
    private static final String STATIC = "static";

    private static Logger log = Logger.getLogger(ConversionLogic.class);

    public static Map<String, Set<String>> keyValuePair = new HashMap<>();

    private static Set<String> featureDecision(String feature, JsonNode value) {
        JsonNode innerValue = value.get(feature);
        switch (feature) {
            case BEHAVIOR:
                Behavior behavior = MapperUtil.fromObject(innerValue, Behavior.class);
                Set<String> behaviorPool = BehaviorSet.makeBehaviorSet(behavior);
//                generatePool.addToPool(behaviorPool);
                return behaviorPool;
            case MALHEUR:
                // This won't be used in the clustering but will be used as a reference for the cluster results.
                Malheur malheur = MapperUtil.fromObject(innerValue, Malheur.class);
                break;
            case NETWORK:
                Network network = MapperUtil.fromObject(innerValue, Network.class);
                Set<String> networkPool = NetworkSet.makeNetworkSet(network);
//                generatePool.addToPool(networkPool);
                return networkPool;
            case SIGNATURES:
                // TODO : Does this work ??
                Map<String, List<Object>> signatures = MapperUtil.fromObject(innerValue, Map.class);
                Set<String> signaturesPool = SignatureSet.makeSignatureSet(signatures);
//                generatePool.addToPool(signaturesPool);
                return signaturesPool;
            case STATSIGNATURES:
                Set<String> statSignPool = StatSignaturesSet.makeStatSignaturesSet(innerValue);
//                generatePool.addToPool(statSignPool);
                return statSignPool;
            case STATIC:
                Static staticDomainObject = MapperUtil.fromObject(innerValue, Static.class);
                Set<String> staticDomainPool = StaticSet.makeStaticSet(staticDomainObject);
//                generatePool.addToPool(staticDomainPool);
                return staticDomainPool;
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

    public static Set<String> getFeaturePool() {
        return generatePool.pool;
    }

}
