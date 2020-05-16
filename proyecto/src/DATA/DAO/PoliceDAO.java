package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.Police;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PoliceDAO implements CRUD<Police> {
    private static PoliceDAO instance = null ;
    private Connection conn;
    private final String insertSQL = "INSERT INTO policia (nombre, numplaca, edad, departamento, foto)  VALUES (?,?,?,?,?)";
    private final String obtainAllSQL = "SELECT * from policia";
    private final String obtainOneByIdSQL = "SELECT * from policia  WHERE idPolicia = ? ";
    private final String obtainOneByDepartmentSQL = "SELECT * from policia  WHERE departamento = ? ";
    private final String updateOneByIdSQL = "UPDATE policia SET nombre = ?, numplaca = ?, edad = ?, departamento = ?, foto = ? WHERE idPolicia = ?";
    private final String updateAllPhotoReferences = "UPDATE policia SET foto = numplaca";
    private final String deleteOneByIdSQL = "DELETE * from policia  WHERE idPolicia = ? ";

    private PoliceDAO() throws SQLException {
        this.conn =  DbConnector.dbInstance().getConn();
    }

    public static PoliceDAO instanceOf() throws SQLException {
        if(instance == null ){
            instance = new PoliceDAO();
        }
        return instance;
    }

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
        List<Police> policeList;
        Statement obtainAllStm = conn.createStatement();
        policeList = formatToObject( obtainAllStm.executeQuery(obtainAllSQL) );
        return policeList;
    }

    @Override
    public Police obtainOneById(Long id) throws SQLException {
        int FIRST_ELEMENT = 0;
        Police policeFromDb ;
        PreparedStatement obtainOnePs = conn.prepareStatement(obtainOneByIdSQL);
        obtainOnePs.setLong(1, id);
        policeFromDb = formatToObject(obtainOnePs.executeQuery()).get(FIRST_ELEMENT);
        return policeFromDb;
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
        return  deletePs.execute();
    }

    public List<Police> obtainListByDepartment(String department) throws SQLException {
        List<Police> policeList;
        PreparedStatement obtainListFiltered = conn.prepareStatement(obtainOneByDepartmentSQL);
        obtainListFiltered.setString(1, department);
        policeList = formatToObject(obtainListFiltered.executeQuery());
        return policeList;
    }

    public List<Police> insertFromList(List<Police> policeList) {
        List<Police> notInsertedPolice = new ArrayList<>();
        for(Police police : policeList ){
            try {
                insert(police);
            } catch (SQLException errorSql) {
                notInsertedPolice.add(police);
            }
        }
        return notInsertedPolice;
    }

    public void updatePhotoLinkAllPolices() throws SQLException {
        Statement updateStm = conn.createStatement();
        updateStm.execute(updateAllPhotoReferences);
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
