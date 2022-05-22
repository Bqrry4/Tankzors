package game.level;

import java.sql.*;

public class LevelLoader {

    public static void Init() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    //Kill me pls T_T..
    public void Load(Level lvl)
    {
        Connection c = null;
        Statement s = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:assets/db/info.db");
            c.setAutoCommit(false);
            s = c.createStatement();
        }
        catch (Exception e)
        {

        }
    }


}
