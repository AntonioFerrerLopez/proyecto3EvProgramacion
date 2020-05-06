package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.PoliceRelatedTraficFine;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PoliceRelatedTraficFineDAO implements DAO<PoliceRelatedTraficFine> {

    public PoliceRelatedTraficFineDAO() throws SQLException {
        this.conn =  DbConnector.dbInstance().getConn();
    }

    private Connection conn;
    private String insertSQL = "INSERT INTO multastipo (descripcion, importe, tipo, carnetpuntos)  VALUES (?,?,?,?)";
    private String obtainAllSQL = "SELECT * from multastipo";
    private String obtainOneByIdSQL = "SELECT * from multastipo  WHERE id = ? ";
    private String updateOneByIdSQL = "UPDATE multastipo SET descripcion = ?, importe = ?, tipo = ?,  carnetpuntos= ? WHERE id = ?";
    private String deleteOneByIdSQL = "DELETE * from multastipo  WHERE id = ? ";


    @Override
    public boolean insert(PoliceRelatedTraficFine polRelFine) throws SQLException {
        PreparedStatement statement  = conn.prepareStatement(insertSQL);
        statement.setString(1, polRelFine.getDescription());
        statement.setDouble(2, polRelFine.getAmmount());
        statement.setString(3, polRelFine.getFineType());
        statement.setInt(4, polRelFine.getDrivingCardPoints());
        return statement.execute();
    }

    @Override
    public List<PoliceRelatedTraficFine> obtainAll() throws SQLException {
        Statement obtainAllStm = conn.createStatement();
        return formatToObject( obtainAllStm.executeQuery(obtainAllSQL) );
    }

    @Override
    public PoliceRelatedTraficFine obtainOneById(Long id) throws SQLException {
        int FIRST_ELEMENT = 0;
        PreparedStatement obtainOnePs = conn.prepareStatement(obtainOneByIdSQL);
        obtainOnePs.setLong(1, id);
        return formatToObject(obtainOnePs.executeQuery()).get(FIRST_ELEMENT);
    }

    @Override
    public boolean updateOneById(Long id, PoliceRelatedTraficFine polRelFine) throws SQLException {
        PreparedStatement statement  = conn.prepareStatement(insertSQL);
        statement.setString(1, polRelFine.getDescription());
        statement.setDouble(2, polRelFine.getAmmount());
        statement.setString(3, polRelFine.getFineType());
        statement.setInt(4, polRelFine.getDrivingCardPoints());
        statement.setLong(5, id);
        return statement.execute();
    }

    @Override
    public boolean deleteOneById(Long id) throws SQLException {
        PreparedStatement deletePs  = conn.prepareStatement(deleteOneByIdSQL);
        deletePs.setLong(1, id);
        return deletePs.execute();
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
