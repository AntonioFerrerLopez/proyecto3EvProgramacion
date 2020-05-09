package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.Police;
import MODEL.TraficFine;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TraficFineDAO implements DAO<TraficFine> {
    private static TraficFineDAO instance = null ;
    private final Connection conn;
    private final String insertSQL = "INSERT INTO multas (descripcion, fecha, importe, idpolicia, nifinfractor, idtipo)  VALUES (?,?,?,?,?,?)";
    private final String obtainAllSQL = "SELECT * from multas";
    private final String obtainOneByIdSQL = "SELECT * from multas  WHERE idPolicia = ? ";
    private final String updateOneByIdSQL = "UPDATE multas SET descripcion = ?, fecha = ?, importe = ?, idpolicia = ?, nifinfractor = ?, idtipo = ?  WHERE idPolicia = ?";
    private final String deleteOneByIdSQL = "DELETE * from multas  WHERE id = ? ";

    public TraficFineDAO() throws SQLException {
        this.conn =  DbConnector.dbInstance().getConn();
    }

    public static TraficFineDAO instanceOf() throws SQLException {
        if(instance == null ){
            instance = new TraficFineDAO();
        }
        return instance;
    }

    @Override
    public boolean insert(TraficFine traficFine) throws SQLException {
        boolean isInserted;
        PreparedStatement statement  = conn.prepareStatement(insertSQL);
        statement.setString(1, traficFine.getDescription());
        statement.setDate(2, traficFine.getDateToDb());
        statement.setDouble(3, traficFine.getAmmount());
        statement.setLong(4, traficFine.getIdPolice());
        statement.setString(5, traficFine.getNifOffender());
        statement.setLong(6, traficFine.getIdtypeOfFine());
        isInserted = statement.execute();
        return isInserted;
    }

    @Override
    public List<TraficFine> insertFromList(List<TraficFine> goL)  {
        return null;
    }


    @Override
    public List<TraficFine> obtainAll() throws SQLException {
        List<TraficFine> policeList;
        Statement obtainAllStm = conn.createStatement();
        policeList = formatToObject( obtainAllStm.executeQuery(obtainAllSQL) );
        return policeList;
    }

    @Override
    public TraficFine obtainOneById(Long id) throws SQLException {
        int FIRST_ELEMENT = 0;
        TraficFine traficFine ;
        PreparedStatement obtainOnePs = conn.prepareStatement(obtainOneByIdSQL);
        obtainOnePs.setLong(1, id);
        traficFine = formatToObject(obtainOnePs.executeQuery()).get(FIRST_ELEMENT);
        return traficFine;
    }

    @Override
    public boolean updateOneById(Long id, TraficFine traficFine) throws SQLException {
        boolean isUpdated;
        PreparedStatement statement  = conn.prepareStatement(updateOneByIdSQL);
        statement.setString(1, traficFine.getDescription());
        statement.setDate(2, traficFine.getDateToDb());
        statement.setDouble(3, traficFine.getAmmount());
        statement.setLong(4, traficFine.getIdPolice());
        statement.setString(5, traficFine.getNifOffender());
        statement.setLong(6, traficFine.getIdtypeOfFine());
        isUpdated = statement.execute();
        return isUpdated;
    }

    @Override
    public boolean deleteOneById(Long id) throws SQLException {
        boolean isDeleted;
        PreparedStatement deletePs  = conn.prepareStatement(deleteOneByIdSQL);
        deletePs.setLong(1, id);
        isDeleted = deletePs.execute();
        return isDeleted;
    }
    private List<TraficFine> formatToObject(ResultSet resultFromDb) throws SQLException {
        List<TraficFine> finesFromDb = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateFromDbFormatted = null;
        while (resultFromDb.next()){
            dateFromDbFormatted = LocalDateTime.parse(resultFromDb.getString("fecha"), formatter);
            resultFromDb.getString("fecha");
            TraficFine police = new  TraficFine(
                    resultFromDb.getLong("id"),
                    resultFromDb.getString("descripcion"),
                    dateFromDbFormatted ,
                    resultFromDb.getDouble("importe"),
                    resultFromDb.getLong("idpolicia"),
                    resultFromDb.getString("nifinfractor"),
                    resultFromDb.getLong("idtipo") );
            finesFromDb.add(police);
        }
        return finesFromDb ;
    }

}
