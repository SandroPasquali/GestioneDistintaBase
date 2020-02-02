package db;





//import com.ibm.as400.access.AS400;
//import com.ibm.as400.access.AS400ConnectionPool;
//import com.ibm.as400.access.AS400JDBCConnectionPool;
//import com.ibm.as400.access.AS400JDBCConnectionPoolDataSource;
//import com.ibm.as400.access.AS400JDBCPooledConnection;
//import com.ibm.as400.access.ConnectionPoolException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mchange.v2.c3p0.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DBConnect {

        
    /*
        parametri di connessione relativi all'AS400 e MySQL
    */
    
    private final static String JDBC_URL_MYSQL = "jdbc:mysql://localhost/test?user=root&password=root";
    //private final static String JDBC_URL_MYSQL = "jdbc:mysql://192.168.1.165/test?user=netpro&password=netpro";
    
    
    //private static ComboPooledDataSource cpds = null;
    private static ComboPooledDataSource dataSource = null;
    
    
    /*
    private static AS400ConnectionPool as400ConnectionPool=null;
    
    private static AS400JDBCConnectionPoolDataSource as400JDBCPoolDataSource=null;
    private static AS400JDBCConnectionPool as400pool=null;
    private static AS400JDBCPooledConnection as400JDBCPooledConnection=null;
    
    private final static String JDBC_URL_AS400 = "jdbc:as400://192.168.1.6/POLLIVE/";
    private final static String USER_ID_AS400 = "AS400";
    private final static String PASSWORD_AS400 = "POLLI720";
    private final static String SYSTEM_ID_AS400 = "S658AC00";
    */
    	
    

       
    
    public static Connection getConnectionByMySQLPool() {

    try {

        if (dataSource == null) {
            // creare ed attivare il Connection Pool
            dataSource = new ComboPooledDataSource();
            dataSource.setJdbcUrl(JDBC_URL_MYSQL);
        }

        return dataSource.getConnection();

    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Errore nella connessione", e);
    }
}//eom


    public static Connection getConnectionByMySQLDriverManager() {

            try {
                    Class.forName("com.mysql.jdbc.Driver");

            } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Driver JDBC non trovato", e);
            }
            Connection c;
            try {
                c = DriverManager.getConnection(JDBC_URL_MYSQL);	

            } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Impossibile connettersi al database", e);
            }
            return c;
    }//eom










    /*
    public static Connection getConnectionByAS400DriverManager() {

            try {

                    Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Driver JDBC non trovato", e);
            }
            Connection c;
            try {
                c = DriverManager.getConnection(JDBC_URL_AS400,USER_ID_AS400,PASSWORD_AS400);
            } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Impossibile connettersi al database", e);
            }
            return c;
    }//eom
	*/


    /*
    public static AS400 getConnectionByAS400Pool() {
        
        
        //da utilizzare per connessione diretta al sistema AS400 e non db2
        
        
        
        try {
            
            if(as400ConnectionPool==null){
                
                // Create an AS400ConnectionPool.
                as400ConnectionPool = new AS400ConnectionPool();

                // Set a maximum of 128 connections to this pool.
                as400ConnectionPool.setMaxConnections(128);

                // Set a maximum lifetime for 30 minutes for connections.
                as400ConnectionPool.setMaxLifetime(1000 * 60 * 30);     // 30 min  Max lifetime since created.

                // Preconnect 5 connections to the AS400.COMMAND service.
                as400ConnectionPool.fill(SYSTEM_ID_AS400, USER_ID_AS400, PASSWORD_AS400, AS400.COMMAND, 1);
                System.out.println();
                System.out.println("Preconnected 1 connection to the AS400.COMMAND service");

                // Call getActiveConnectionCount and getAvailableConnectionCount to see how many
               // connections are in use and available for a particular system.
               System.out.println("Number of active connections: "
                       + as400ConnectionPool.getActiveConnectionCount(SYSTEM_ID_AS400, USER_ID_AS400));
               System.out.println("Number of available connections for use: "
                       + as400ConnectionPool.getAvailableConnectionCount(SYSTEM_ID_AS400, USER_ID_AS400));

            
            
            }//if


            
           

            // Create a connection to the AS400.COMMAND service. (Use the service number
            // constants defined in the AS400 class (FILE, PRINT, COMMAND, DATAQUEUE, and so on.))
            // Since connections have already been filled, the usual time spent connecting
            // to the command service is avoided.
            return as400ConnectionPool.getConnection(SYSTEM_ID_AS400, USER_ID_AS400, PASSWORD_AS400, AS400.COMMAND);

            //System.out.println();
            //System.out.println("getConnection gives out an existing connection to user");
            //System.out.println("Number of active connections: "
            //        + pool.getActiveConnectionCount(SYSTEM_ID_AS400, USER_ID_AS400));
            //System.out.println("Number of available connections for use:  "
            //        + pool.getAvailableConnectionCount(SYSTEM_ID_AS400, USER_ID_AS400));

        } catch (ConnectionPoolException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Errore nella connessione", ex);
        }

    }//eom
    */
    
    /*
    public static Connection getConnectionByAS400PooledConnection() {

        try {

            if(as400JDBCPoolDataSource==null){
                as400JDBCPoolDataSource = new AS400JDBCConnectionPoolDataSource("192.168.1.6");
                as400JDBCPoolDataSource.setUser("AS400");
                as400JDBCPoolDataSource.setPassword("POLLI720");
                as400JDBCPoolDataSource.setDatabaseName("S658AC00");
                //Connection conn=null;
            }//if

            if (as400JDBCPooledConnection==null){
            
                // Get a PooledConnection and get the connection handle to the database.
                 as400JDBCPooledConnection =  (AS400JDBCPooledConnection) as400JDBCPoolDataSource.getPooledConnection();
            }//if
            return as400JDBCPooledConnection.getConnection();
            //conn.setSchema("pollive");
        } //
        catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Errore nella connessione", ex);
        }
      
    }//eom
	*/
    /*
    public static Connection getAS400JDBCConnectionPool() {

        try {
            
            if(as400JDBCPoolDataSource == null){
                // creare ed attivare il Connection Pool
                as400JDBCPoolDataSource = new AS400JDBCConnectionPoolDataSource();
                as400JDBCPoolDataSource.setServerName("192.168.1.6");
                as400JDBCPoolDataSource.setDatabaseName("S658AC00");
                as400JDBCPoolDataSource.setUser("AS400");
                as400JDBCPoolDataSource.setPassword("POLLI720");
                as400JDBCPoolDataSource.setLibraries("POLLIVE");
                as400JDBCPoolDataSource.setDriver("toolbox");
            }//if
            
            if (as400pool==null){
                //  Create  an  AS400JDBCConnectionPool  object.
                as400pool = new AS400JDBCConnectionPool(as400JDBCPoolDataSource);

                //  Adds  10  connections  to  the  pool  that  can  be  used  by  the
                //  application  (creates  the  physical  database  connections  based  on
                //  the  data  source).
                as400pool.setMaxLifetime(-1); //which means taht there is no limit on the time
                //pool.setMaxConnections(100);
                //pool.setCleanupInterval(1000);to indicate to mantainance thread.
                as400pool.fill(1);
            }//if
            
             //  Get  a  handle  to  a  database  connection  from  the  pool.
            return as400pool.getConnection();
        } //try
        catch (ConnectionPoolException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Errore nella connessione", ex);
        }//catch
}//eom
*/	
    /*
    public static Connection getAS400JDBCConnectionPoolDataSource() {
        try {
            //catch
            
            
            if(as400JDBCPoolDataSource == null){
                // creare ed attivare il Connection Pool
                as400JDBCPoolDataSource = new AS400JDBCConnectionPoolDataSource();
                as400JDBCPoolDataSource.setServerName("192.168.1.6");
                as400JDBCPoolDataSource.setDatabaseName("S658AC00");
                as400JDBCPoolDataSource.setUser("AS400");
                as400JDBCPoolDataSource.setPassword("POLLI720");
                as400JDBCPoolDataSource.setLibraries("POLLIVE");
                as400JDBCPoolDataSource.setDriver("toolbox");
            }
            return as400JDBCPoolDataSource.getConnection();
        } //eom
        catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Errore nella connessione", ex);
        }
    }//eom
	*/
    
    
    
}//eoc


  


        //1)  Create  a  data  source  to  the  System  i5  database.
        //AS400JDBCDataSource dataSource = new AS400JDBCDataSource();
        
       
        
        //dataSource.setNaming(USER_ID_AS400);
        
        //dataSource.setUser(USER);
        //dataSource.setPassword(PASSWORD);
        
        //dataSource.setPassword(PASSWORD);
        
        //dataSource.setNaming(NAME);

        //systemi_jdbc_pool.setCleanupInterval(miliseconds)
        
        //System.out.println("Server Name: "+dataSource.getServerName());
        //System.out.println("Db name: "+dataSource.getDatabaseName());
        
        //Context context = null;
        
        /*
        try {
            //2)  Register  the  datasource  with  the  Java  Naming  and  Directory  Interface  (JNDI).
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
            context = new InitialContext(env);
            context.bind("jdbc/as400", dataSource);
            
        } catch (NamingException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        // Return  an  AS400JDBCDataSource  object  from  JNDI  and  get  a  connection.
        //AS400JDBCDataSource  datasource  =  (AS400JDBCDataSource)  context.lookup("jdbc/as400");
        //Connection  connection  =  datasource.getConnection("myUser",  "MYPWD"); 