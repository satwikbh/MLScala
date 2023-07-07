package common.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by satwik on 22/6/17.
 */
public class Config {

    private static Logger log = Logger.getLogger(Config.class);

    private static Config ourInstance = new Config();
    private Properties properties;

    private Config() {
        String baseName = "Config.properties";

        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(baseName)) {
            properties = new Properties();
            properties.load(resourceAsStream);
        } catch (IOException e) {
            log.error("Resouce bundle exception", e);
        } catch (Exception e) {
            log.error("Exception.", e);
        }
    }

    public static Config getInstance() {
        return ourInstance;
    }

    public String getMongoDBHost() {
        return properties.getProperty("mongo.db.host");
    }

    public String getMongoDBPort() {
        return properties.getProperty("mongo.db.port");
    }

    public String getMongoDBName() {
        return properties.getProperty("mongo.db.name");
    }

    public String getMongoDBCollectionName() {
        return properties.getProperty("mongo.collection.name");
    }

    public String getMongoDBUsername() {
        return properties.getProperty("mongo.username");
    }

    public String getMongoDBPassword() {
        return properties.getProperty("mongo.password");
    }

    public Boolean mongoIsAuthEnabled() {
        return Boolean.valueOf(properties.getProperty("mongo.isauthenabled"));
    }

    public String getMongoDBAuthDB() {
        return properties.getProperty("mongo.authdb");
    }

}
