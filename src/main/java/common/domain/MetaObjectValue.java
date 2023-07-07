package common.domain;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by satwik on 5/31/17.
 */
public class MetaObjectValue {
    private String feature;
    private String key;
    private JsonNode value;

    public JsonNode getValue() {
        return value;
    }

    public void setValue(JsonNode value) {
        this.value = value;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
