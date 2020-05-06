package DATA.DATABASE;

import VIEW.TOOLS.Alerts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private static DbConnector dbInstance = null ;
    private final String JDBC_DRIVER = "jdbc:mariadb://";
    private final String DB_SERVER = "192.168.1.52";
    private final String DB_PORT = ":3306/";
    private final String DB_NAME  = "Comisaria";
    private final String DB_USER_NAME = "adminComisaria";
    private final String DB_PASSWD = "uPWQoZfkyedimter";

    private Connection conn ;

    private DbConnector() throws SQLException {
        this.conn = DriverManager.getConnection(JDBC_DRIVER+DB_SERVER+DB_PORT+DB_NAME , DB_USER_NAME , DB_PASSWD);
    }

    public static DbConnector dbInstance() throws SQLException {
        if(dbInstance == null ){
            dbInstance = new DbConnector();
        }
        return dbInstance;
    }

    public Connection getConn() {
        return conn;
    }

    public boolean closeConnection(){
        boolean closed  = false;
        try {
            if(!conn.isClosed()){
                try {
                    conn.close();
                    closed =  true;
                }catch (SQLException closingError){
                    Alerts.instanceOf().generateWarningWithErrorCode(closingError.getErrorCode() , closingError.getMessage());
                }
            }
        } catch (SQLException isCloseError) {
            Alerts.instanceOf().generateWarningWithErrorCode(isCloseError.getErrorCode() , isCloseError.getMessage());
        }
        return closed;
    }

}
