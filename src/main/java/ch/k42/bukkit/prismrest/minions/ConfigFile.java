package ch.k42.bukkit.prismrest.minions;

import org.jboss.logging.Logger;

import javax.inject.Singleton;
import java.io.*;
import java.util.Properties;

/**
 * Created by Thomas on 28.03.14.
 */
@Singleton //useless? FIXME
public class ConfigFile {
    private static final String FILE = "prismrest.properties";

    private static ConfigFile _instance = new ConfigFile();

    private Logger logger = Logger.getLogger(ConfigFile.class);
    private Properties properties;

    private ConfigFile() {
        properties = new Properties();
        InputStream in = null;
        try{
            in = new FileInputStream(FILE);
            logger.debug("Loading Config File: " + FILE);
            properties.load(in);
        } catch (FileNotFoundException e){
            logger.debug("No config file found.");
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException f) {
                logger.error(f);
            }
            in = getClass().getClassLoader().getResourceAsStream(FILE);
            if(in!=null){
                try {
                    logger.info("Bootstrapping config file.");
                    properties.load(in);
                    FileOutputStream fout = new FileOutputStream(FILE);
                    properties.store(fout,null);
                } catch (IOException e1) {
                    logger.error(e1);
                }
            }else{
                logger.debug("Failed bootstrapping config file.");
            }

        }catch (IOException e) {
            logger.error(e);
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    public static String getProperty(String property,String def){
        String prop = _instance.properties.getProperty(property);
        return prop!=null ? prop : def;
    }

}
