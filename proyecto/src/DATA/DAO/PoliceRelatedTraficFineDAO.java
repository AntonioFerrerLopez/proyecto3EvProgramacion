package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.PoliceRelatedTraficFine;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PoliceRelatedTraficFineDAO implements DAO<PoliceRelatedTraficFine> {

    private static PoliceRelatedTraficFineDAO instance = null ;
    private final Connection conn;
    private final String insertSQL = "INSERT INTO multastipo (descripcion, importe, tipo, carnetpuntos)  VALUES (?,?,?,?)";
    private final String obtainAllSQL = "SELECT * from multastipo";
    private final String obtainOneByIdSQL = "SELECT * from multastipo  WHERE id = ? ";
    private final String updateOneByIdSQL = "UPDATE multastipo SET descripcion = ?, importe = ?, tipo = ?,  carnetpuntos= ? WHERE id = ?";
    private final String deleteOneByIdSQL = "DELETE * from multastipo  WHERE id = ? ";

    public PoliceRelatedTraficFineDAO() throws SQLException {
        this.conn =  DbConnector.dbInstance().getConn();
    }

    public static PoliceRelatedTraficFineDAO instanceOf() throws SQLException {
        if(instance == null ){
            instance = new PoliceRelatedTraficFineDAO();
        }
        return instance;
    }

    @Override
    public boolean insert(PoliceRelatedTraficFine polRelFine) throws SQLException {
        boolean isInserted;
        PreparedStatement statement  = conn.prepareStatement(insertSQL);
        statement.setString(1, polRelFine.getDescription());
        statement.setDouble(2, polRelFine.getAmmount());
        statement.setString(3, polRelFine.getFineType());
        statement.setInt(4, polRelFine.getDrivingCardPoints());
        isInserted = statement.execute();
        DbConnector.dbInstance().closeConnection();
        return isInserted;
    }

    @Override
    public List<PoliceRelatedTraficFine> insertFromList(List<PoliceRelatedTraficFine> goL) {
        return null;
    }


    @Override
    public List<PoliceRelatedTraficFine> obtainAll() throws SQLException {
        List<PoliceRelatedTraficFine> listPolRelFine ;
        Statement obtainAllStm = conn.createStatement();
        listPolRelFine = formatToObject( obtainAllStm.executeQuery(obtainAllSQL) );
        DbConnector.dbInstance().closeConnection();
        return listPolRelFine;
    }

    @Override
    public PoliceRelatedTraficFine obtainOneById(Long id) throws SQLException {
        int FIRST_ELEMENT = 0;
        PoliceRelatedTraficFine polRelFine;
        PreparedStatement obtainOnePs = conn.prepareStatement(obtainOneByIdSQL);
        obtainOnePs.setLong(1, id);
        polRelFine = formatToObject(obtainOnePs.executeQuery()).get(FIRST_ELEMENT);
        DbConnector.dbInstance().closeConnection();
        return polRelFine;
    }

    @Override
    public boolean updateOneById(Long id, PoliceRelatedTraficFine polRelFine) throws SQLException {
        boolean isUpdated;
        PreparedStatement statement  = conn.prepareStatement(updateOneByIdSQL);
        statement.setString(1, polRelFine.getDescription());
        statement.setDouble(2, polRelFine.getAmmount());
        statement.setString(3, polRelFine.getFineType());
        statement.setInt(4, polRelFine.getDrivingCardPoints());
        statement.setLong(5, id);
        isUpdated = statement.execute();
        DbConnector.dbInstance().closeConnection();
        return isUpdated;
    }

    @Override
    public boolean deleteOneById(Long id) throws SQLException {
        boolean isDeleted;
        PreparedStatement deletePs  = conn.prepareStatement(deleteOneByIdSQL);
        deletePs.setLong(1, id);
        isDeleted = deletePs.execute();
        DbConnector.dbInstance().closeConnection();
        return isDeleted;
    }

    private List<PoliceRelatedTraficFine> formatToObject(ResultSet resultFromDb) throws SQLException {
        List<PoliceRelatedTraficFine> polRelFineFromDb = new ArrayList<>();
        while (resultFromDb.next()){
            PoliceRelatedTraficFine policeRelFine = new PoliceRelatedTraficFine(
                    resultFromDb.getLong("id"),
                    resultFromDb.getString("descripcion"),
                    resultFromDb.getDouble("importe"),
                    resultFromDb.getString("tipo"),
                    resultFromDb.getInt("carnetpuntos") ) ;
            polRelFineFromDb.add(policeRelFine);
        }
        return polRelFineFromDb ;
    }

}
