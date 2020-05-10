package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.TraficFine;
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

    private final String obtainAllFinesRelatedPolicesSQL = "SELECT multas.id AS fineId, multas.descripcion AS descriptionFine, multas.fecha AS dateFine , multas.importe AS ammountFine, multas.nifinfractor AS nifInfractorFine, \n" +
            "multastipo.descripcion AS descriptionType , multastipo.carnetpuntos AS drivingCardPoints,\n" +
            " policia.nombre AS policeName ,policia.numplaca AS policePlateNumber\n" +
            "FROM multastipo  INNER JOIN multas ON multas.idtipo = multastipo.id INNER JOIN  policia ON policia.idPolicia = multas.idpolicia; ";

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
        while (resultFromDb.next()){
            resultFromDb.getString("fecha");
            TraficFineJoinPolice traficFineJoinPolice = new TraficFineJoinPolice(
                    resultFromDb.getLong("idFine"),
                    resultFromDb.getString("descriptionFine"),
                    resultFromDb.getTimestamp("dateFine").toLocalDateTime() ,
                    resultFromDb.getDouble("ammountFine"),
                    resultFromDb.getString("nifInfractorFine"),
                    resultFromDb.getString("descriptionType"),
                    resultFromDb.getInt("drivingCardPoints") ,
                    resultFromDb.getString("policeName") ,
                    resultFromDb.getString("policePlateNumber") );

            finesFromDb.add(traficFineJoinPolice);
        }
        return finesFromDb ;
    }
}
