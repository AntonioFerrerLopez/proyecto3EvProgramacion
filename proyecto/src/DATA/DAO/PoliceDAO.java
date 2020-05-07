package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.Police;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class        PoliceDAO implements DAO<Police>{

    public PoliceDAO() throws SQLException {
        this.conn =  DbConnector.dbInstance().getConn();
    }

    private final Connection conn;
    private final String insertSQL = "INSERT INTO policia (nombre, numplaca, edad, departamento, foto)  VALUES (?,?,?,?,?)";
    private final String obtainAllSQL = "SELECT * from policia";
    private final String obtainOneByIdSQL = "SELECT * from policia  WHERE idPolicia = ? ";
    private final String updateOneByIdSQL = "UPDATE policia SET nombre = ?, numplaca = ?, edad = ?, departamento = ?, foto = ? WHERE idPolicia = ?";
    private final String deleteOneByIdSQL = "DELETE * from policia  WHERE idPolicia = ? ";

    @Override
    public boolean insert(Police police) throws SQLException {
        boolean isInserted;
        PreparedStatement statement  = conn.prepareStatement(insertSQL);
        statement.setString(1, police.getName());
        statement.setString(2, police.getPolicePlateNumber());
        statement.setInt(3, police.getAge());
        statement.setString(4, police.getDepartment());
        statement.setString(5, police.getPhotoLink());
        isInserted = statement.execute();
        DbConnector.dbInstance().closeConnection();
        return isInserted;
    }

    @Override
    public List<Police> obtainAll() throws SQLException {
        List<Police> policeList;
        Statement obtainAllStm = conn.createStatement();
        policeList = formatToObject( obtainAllStm.executeQuery(obtainAllSQL) );
        DbConnector.dbInstance().closeConnection();
        return policeList;
    }

    @Override
    public Police obtainOneById(Long id) throws SQLException {
        int FIRST_ELEMENT = 0;
        Police policeFromDb ;
        PreparedStatement obtainOnePs = conn.prepareStatement(obtainOneByIdSQL);
        obtainOnePs.setLong(1, id);
        policeFromDb = formatToObject(obtainOnePs.executeQuery()).get(FIRST_ELEMENT);
        DbConnector.dbInstance().closeConnection();
        return policeFromDb;
    }

    @Override
    public boolean updateOneById(Long id, Police police) throws SQLException {
        boolean isUpdated ;
        PreparedStatement statement  = conn.prepareStatement(updateOneByIdSQL);
        statement.setString(1, police.getName());
        statement.setString(2, police.getPolicePlateNumber());
        statement.setInt(3, police.getAge());
        statement.setString(4, police.getDepartment());
        statement.setString(5, police.getPhotoLink());
        statement.setLong(6, id);
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

    private List<Police> formatToObject(ResultSet resultFromDb) throws SQLException {
        List<Police> policeListFromDb = new ArrayList<>();
        while (resultFromDb.next()){
            Police police = new  Police(
                    resultFromDb.getLong("idPolicia"),
                    resultFromDb.getString("nombre"),
                    resultFromDb.getString("numplaca"),
                    resultFromDb.getInt("edad"),
                    resultFromDb.getString("departamento"),
                    resultFromDb.getString("foto") );
            policeListFromDb.add(police);
        }
         return policeListFromDb ;
    }

}
