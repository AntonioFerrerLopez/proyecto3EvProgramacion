package DATA.DATABASE;

import VIEW.TOOLS.Alerts;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static DbConnector dbInstance = null ;
    private Connection conn = null ;
    private final String JDBC_DRIVER = "jdbc:mariadb://";
    private final String DB_SERVER = "192.168.1.52";
    private final String DB_PORT = ":3306/";
    private final String DB_NAME  = "comisaria";
    private final String DB_USER_NAME = "adminComisaria";
    private final String DB_PASSWD = "uPWQoZfkyedimter";

    private DbConnector() throws SQLException {
    }

    public static DbConnector dbInstance() throws SQLException {
        if(dbInstance == null ){
            dbInstance = new DbConnector();
        }
        return dbInstance;
    }

    public Connection getConn() throws SQLException {
       if(conn == null){
                conn = DriverManager.getConnection(JDBC_DRIVER+DB_SERVER+DB_PORT+DB_NAME , DB_USER_NAME , DB_PASSWD);
       }
        return conn;
    }

    public void closeConnection(){
        if(conn != null ){
            try {
                conn.close();
                this.conn = null ;
            }catch (SQLException closingError){
                Alerts.instanceOf().generateWarningWithErrorCode(closingError.getErrorCode() , closingError.getMessage());
            }
        }
    }

}
