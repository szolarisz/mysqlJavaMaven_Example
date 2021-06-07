import java.sql.*;

public class pelda01 {

    public static void main(String [] args){

        /*
        Szükséges dolgok:
        - JDBC
        jdbc:mysql
        - távoli szerver címe
        nálunk ez most //localhost
        - port amin elérjük a MySQL szervert
        :3306
        - felhasználó neve
        root
        - jelszó

        - adatbázis neve
        esti

        Mivel ez IO műveletnek tekinthető -> try-catch
         */

        try{
            String url = "jdbc:mysql://localhost:3306/esti?autoReconnect=true&useSSL=false";
            String user="root";
            String passwd ="";

            Connection kapcsolat = DriverManager.getConnection(url, user, passwd); //Ez amit el tud kapni először;
            Statement myStatement = kapcsolat.createStatement();

            String mySQLCommand = "SELECT nev FROM dolgozo ORDER BY nev DESC";
            ResultSet eredmeny = myStatement.executeQuery( mySQLCommand );

            while( eredmeny.next() )
                System.out.println( eredmeny.getString("nev"));

            mySQLCommand = "SELECT nev, vegzettseg.vegzettseg AS vnev FROM dolgozo INNER JOIN vegzettseg ON dolgozo.vegzettseg=vegz_kod ORDER BY nev DESC;";
            eredmeny = myStatement.executeQuery( mySQLCommand);

            while( eredmeny.next() )
                System.out.println(eredmeny.getString("nev") + " -> "+eredmeny.getString("vnev"));

            /*
            DML - INSERT INTO; DELETE; UPDATE;
            executeUpdate
             */
            mySQLCommand = "INSERT INTO vegzettseg VALUES(11,'PhD')";
            myStatement.executeUpdate(mySQLCommand);

            mySQLCommand = "SELECT max(vegz_kod) AS maxi FROM vegzettseg";
            eredmeny = myStatement.executeQuery( mySQLCommand);
            eredmeny.next();
            int max = Integer.parseInt(eredmeny.getString("maxi")) + 1;

            mySQLCommand ="INSERT INTO vegzettseg VALUES("+max+", 'doktori iskola')";
            myStatement.executeUpdate(mySQLCommand);

            kapcsolat.close();
        }catch ( SQLException exc){
            exc.printStackTrace();
        }

    }
}
