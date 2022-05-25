package Managers;

import game.level.LevelLoader;
import java.sql.*;

public class DataManager {


    public static void Read(String path)
    {
        Connection c;
        Statement s;
        try {
            Class.forName("org.sqlite.JDBC");

            c = DriverManager.getConnection("jdbc:sqlite:" + path);
            c.setAutoCommit(false);
            s = c.createStatement();


            ResultSet select = s.executeQuery("SELECT * FROM LevelProgress;");

            select.next();
            LevelLoader.LevelID = select.getInt(1);
            LevelLoader.Score = select.getInt(2);

            select.close();
            s.close();
            c.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(1);
        }
    }

    public static void Write(String path) {
        Connection c;
        Statement s;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + path);
            c.setAutoCommit(false);
            s = c.createStatement();

            s.execute("DELETE FROM LevelProgess");

            s.execute("INSERT INTO LevelProgess " +
                    "VALUES ('" + LevelLoader.LevelID + "', '" + LevelLoader.Score + "');");

            s.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(1);
        }
    }
}
