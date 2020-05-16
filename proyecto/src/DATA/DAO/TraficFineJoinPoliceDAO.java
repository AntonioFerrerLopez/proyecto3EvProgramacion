package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.TraficFineJoinPolice;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TraficFineJoinPoliceDAO {

    private static TraficFineJoinPoliceDAO instance = null ;
    private final Connection conn ;

    private final String obtainAllFinesRelatedPolicesSQL = "SELECT multas.id, multas.descripcion, multas.fecha , " +
            "multas.importe , multas.nifinfractor , multastipo.descripcion, multastipo.carnetpuntos , policia.nombre  ," +
            "policia.numplaca FROM multastipo  INNER JOIN multas ON multas.idtipo = multastipo.id " +
            "INNER JOIN  policia ON policia.idPolicia = multas.idpolicia; ";

    public TraficFineJoinPoliceDAO() throws SQLException{
        this.conn =  DbConnector.dbInstance().getConn();
    }

    public static TraficFineJoinPoliceDAO instanceOf() throws SQLException {
        if(instance == null ){
            instance = new TraficFineJoinPoliceDAO();
        }
        return instance;
    }

    public List<TraficFineJoinPolice> obtainAllFinesRelatedPolices() throws SQLException {
        List<TraficFineJoinPolice> fineAndPolicesList;
        Statement obtainAllStm = conn.createStatement();
        fineAndPolicesList = formatToObject( obtainAllStm.executeQuery(obtainAllFinesRelatedPolicesSQL) );
        return fineAndPolicesList;
    }

    private List<TraficFineJoinPolice> formatToObject(ResultSet resultFromDb) throws SQLException {
        List<TraficFineJoinPolice> finesFromDb = new ArrayList<>();
        System.out.println(resultFromDb.toString());
        while (resultFromDb.next()){
            resultFromDb.getString("fecha");
            TraficFineJoinPolice traficFineJoinPolice = new TraficFineJoinPolice(
                    resultFromDb.getLong("multas.id"),
                    resultFromDb.getString("multas.descripcion"),
                    resultFromDb.getTimestamp("multas.fecha").toLocalDateTime() ,
                    resultFromDb.getDouble("multas.importe"),
                    resultFromDb.getString("multas.nifinfractor"),
                    resultFromDb.getString("multastipo.descripcion"),
                    resultFromDb.getInt("multastipo.carnetpuntos") ,
                    resultFromDb.getString("policia.nombre") ,
                    resultFromDb.getString("policia.numplaca") );

            finesFromDb.add(traficFineJoinPolice);
        }
        return finesFromDb ;
    }
}
