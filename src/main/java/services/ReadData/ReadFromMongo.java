package services.ReadData;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.MongoClient;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import common.config.Config;
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
    private static Config config = Config.getInstance();

    private static MongoClient getClient() {
        String url = config.getMongoDBHost();
        String port = config.getMongoDBPort();
        String authDB = config.getMongoDBAuthDB();
        String username = config.getMongoDBUsername();
        String password = config.getMongoDBPassword();
        Boolean isAuthEnabled = config.mongoIsAuthEnabled();
        MongoClient mongoClient = DBUtil.openConnection(url, port, authDB, username, password, isAuthEnabled);
        assert mongoClient != null;
        return mongoClient;
    }

    public static Map<String, List<String>> getAllMongoDocs() {
        log.info("Started reading all documents from Mongo");
        long startTime = System.currentTimeMillis();
        Map<String, List<String>> allDocsList = new HashMap<>();

        MongoClient client = getClient();

        String dbName = config.getMongoDBName();
        String collectionName = config.getMongoDBCollectionName();
        String distinctKey = "key";

        MongoDatabase database = client.getDatabase(dbName);
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
                String value = MapperUtil.getJsonNodeFromObject(document).toString();
                docs.add(value);
            }

            allDocsList.put(key, docs);
        }
        log.info("Completed reading all documents from Mongo : " + (System.currentTimeMillis() - startTime));
        return allDocsList;
    }
}
