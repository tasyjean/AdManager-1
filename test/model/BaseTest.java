package model;


import com.avaje.ebean.Ebean;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.api.db.DBApi;
import play.api.db.evolutions.Evolution;
import play.api.db.evolutions.Evolutions;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.collection.Seq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for Model testing
 */
public class BaseTest {

    public static FakeApplication app;
    public static String createDdl = "";
    public static String dropDdl = "";

    @BeforeClass
    public static void startApp() throws IOException {
        app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
        Helpers.start(app);
        Map<String, String> settings = new HashMap<String, String>();
        settings.put("db.default.url", "postgres://xenovon:gundulmugepeng@localhost:5432/audiovity_db");
        settings.put("db.default.user", "xenovon");
        settings.put("db.default.password", "gundulmugepeng");
        settings.put("db.default.jndiName", "DefaultDS"); // make connection available to dbunit through JNDI
        app = Helpers.fakeApplication(settings);
        Helpers.start(app);

        // Reading the evolution file
        String evolutionContent = FileUtils.readFileToString(app.getWrappedApplication().getFile("conf/evolutions/default/1.sql"));
        // Splitting the String to get Create & Drop DDL
        String[] splittedEvolutionContent = evolutionContent.split("# --- !Ups");
        String[] upsDowns = splittedEvolutionContent[1].split("# --- !Downs");
        createDdl = upsDowns[0];
        dropDdl = upsDowns[1];
        System.err.print("BEfore class JALANAAAAA----------------------------sjhfkdsf");
    }

    @AfterClass
    public static void stopApp() {
        Helpers.stop(app);
    }

    @Before
    public void createCleanDb() {
        Ebean.execute(Ebean.createCallableSql(dropDdl));
        Ebean.execute(Ebean.createCallableSql(createDdl));
    }

}