package common.utils;

import com.mongodb.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satwik on 17/6/17.
 */
public class DBUtil {

    private static Logger log = Logger.getLogger(DBUtil.class);

    public static MongoClient openConnection(String dbUrl, String dbPort, String authDB, String username, String password, boolean isAuthEnabled) {
        log.info("Connection to db: dBName: " + authDB);
        try {
            MongoClient client;
            String[] node = dbUrl.split(",");
            String[] port = dbPort.split(",");

            List<ServerAddress> list = new ArrayList<ServerAddress>();
            for (int i = 0; i < node.length; i++) {
                list.add(new ServerAddress(node[i], Integer.parseInt(port[i])));
            }
            MongoClientOptions.Builder builder = MongoClientOptions.builder();
            builder.readPreference(ReadPreference.secondary());

            if (isAuthEnabled) {
                MongoCredential credential = MongoCredential.createCredential(username, authDB, password.toCharArray());
                ArrayList<MongoCredential> credentials = new ArrayList<>();
                credentials.add(credential);
                client = new MongoClient(list, credentials, builder.build());
            } else
                client = new MongoClient(list, builder.build());
            return client;
        } catch (Exception e) {
            log.error("ERROR while connecting to mongo host: ", e);
        }
        return null;
    }

    public static void closeClient(MongoClient client) {
        if (client != null) {
            try {
                client.close();
            } catch (Exception e) {
                log.error("Exception closing client?.");
            }
        } else {
            log.error("Client cannot be null.");
        }
    }
}
