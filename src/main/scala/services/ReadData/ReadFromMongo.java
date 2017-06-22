package services.ReadData;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.MongoClient;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.utils.DBUtil;
import common.utils.MapperUtil;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by satwik on 17/6/17.
 */
public class ReadFromMongo {

    private static Logger log = Logger.getLogger(ReadFromMongo.class);

    public static Map<String, List<String>> getAllMongoDocs() {
        log.info("Started reading all documents from Mongo");
        long startTime = System.currentTimeMillis();
        Map<String, List<String>> allDocsList = new HashMap<>();
        String url = "localhost";
        String port = "27017";
        String authDB = "admin";
        String dbName = "cuckoo";
        String collectionName = "cluster2db";
        String distinctKey = "key";

        MongoClient mongoClient = DBUtil.openConnection(url, port, authDB, null, null, false);
        assert mongoClient != null;
        MongoDatabase database = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        DistinctIterable<String> listOfKeys = collection.distinct(distinctKey, String.class);
        for (String key : listOfKeys) {
            Map<String, String> query = new HashMap<>();
            query.put("key", key);

            JsonNode queryNode = MapperUtil.getJsonNodeFromObject(query);
            Document parse = Document.parse(queryNode.toString());
            FindIterable<Document> documents = collection.find(parse);
            List<String> docs = new ArrayList<>();

            for (Document document : documents) {
                JsonNode docNode = MapperUtil.fromObject(document, JsonNode.class);
                String value = docNode.textValue();
                docs.add(value);
            }

            allDocsList.put(key, docs);
        }
        log.info("Completed reading all documents from Mongo : " + (System.currentTimeMillis() - startTime));
        return allDocsList;
    }

    public static void main(String[] args) {
        Map<String, List<String>> allMongoDocs = getAllMongoDocs();
        System.out.println("Size of the docs : " + allMongoDocs.size());
    }

}
