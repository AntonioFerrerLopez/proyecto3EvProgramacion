package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.Police;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PoliceDAO implements DAO<Police>{

    public PoliceDAO() throws SQLException {
        this.conn =  DbConnector.dbInstance().getConn();
    }

    private Connection conn;
    private String insertSQL = "INSERT INTO policia (nombre, numplaca, edad, departamento, foto)  VALUES (?,?,?,?,?)";
    private String obtainAllSQL = "SELECT * from policia";
    private String obtainOneByIdSQL = "SELECT * from policia  WHERE idPolicia = ? ";
    private String updateOneByIdSQL = "UPDATE policia SET nombre = ?, numplaca = ?, edad = ?, departamento = ?, foto = ? WHERE idPolicia = ?";
    private String deleteOneByIdSQL = "DELETE * from policia  WHERE idPolicia = ? ";

    @Override
    public boolean insert(Police police) throws SQLException {
        PreparedStatement statement  = conn.prepareStatement(insertSQL);
        statement.setString(1, police.getName());
        statement.setString(2, police.getPolicePlateNumber());
        statement.setInt(3, police.getAge());
        statement.setString(4, police.getDepartment());
        statement.setString(5, police.getPhotoLink());
        return statement.execute();
    }

    @Override
    public List<Police> obtainAll() throws SQLException {
        Statement obtainAllStm = conn.createStatement();
        return formatToObject( obtainAllStm.executeQuery(obtainAllSQL) );
    }

    @Override
    public Police obtainOneById(Long id) throws SQLException {
        int FIRST_ELEMENT = 0;
        PreparedStatement obtainOnePs = conn.prepareStatement(obtainOneByIdSQL);
        obtainOnePs.setLong(1, id);
        return formatToObject(obtainOnePs.executeQuery()).get(FIRST_ELEMENT);
    }

    @Override
    public boolean updateOneById(Long id, Police police) throws SQLException {
        PreparedStatement statement  = conn.prepareStatement(updateOneByIdSQL);
        statement.setString(1, police.getName());
        statement.setString(2, police.getPolicePlateNumber());
        statement.setInt(3, police.getAge());
        statement.setString(4, police.getDepartment());
        statement.setString(5, police.getPhotoLink());
        statement.setLong(6, id);
        return statement.execute();
    }

    @Override
    public boolean deleteOneById(Long id) throws SQLException {
        PreparedStatement deletePs  = conn.prepareStatement(deleteOneByIdSQL);
        deletePs.setLong(1, id);
        return deletePs.execute();
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
