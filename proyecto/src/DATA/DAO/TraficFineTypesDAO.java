package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.TraficFineTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TraficFineTypesDAO implements CRUD<TraficFineTypes> {

    private static TraficFineTypesDAO instance = null ;
    private final Connection conn;
    private final String insertSQL = "INSERT INTO multastipo (descripcion, importe, tipo, carnetpuntos)  VALUES (?,?,?,?)";
    private final String obtainAllSQL = "SELECT * from multastipo";
    private final String obtainOneByIdSQL = "SELECT * from multastipo  WHERE id = ? ";
    private String obtainIdFilteringByDescription = "SELECT id FROM multastipo WHERE descripcion LIKE ";
    private final String updateOneByIdSQL = "UPDATE multastipo SET descripcion = ?, importe = ?, tipo = ?,  carnetpuntos= ? WHERE id = ?";
    private final String deleteOneByIdSQL = "DELETE * from multastipo  WHERE id = ? ";

    public TraficFineTypesDAO() throws SQLException {
        this.conn =  DbConnector.dbInstance().getConn();
    }

    public static TraficFineTypesDAO instanceOf() throws SQLException {
        if(instance == null ){
            instance = new TraficFineTypesDAO();
        }
        return instance;
    }

    @Override
    public boolean insert(TraficFineTypes polRelFine) throws SQLException {
        PreparedStatement statement  = conn.prepareStatement(insertSQL);
        statement.setString(1, polRelFine.getDescription());
        statement.setDouble(2, polRelFine.getAmmount());
        statement.setString(3, polRelFine.getFineType());
        statement.setInt(4, polRelFine.getDrivingCardPoints());
        return  statement.execute();
    }

    @Override
    public List<TraficFineTypes> insertFromList(List<TraficFineTypes> goL) {
        return null;
    }

    @Override
    public List<TraficFineTypes> obtainAll() throws SQLException {
        List<TraficFineTypes> listPolRelFine ;
        Statement obtainAllStm = conn.createStatement();
        listPolRelFine = formatToObject( obtainAllStm.executeQuery(obtainAllSQL) );
        return listPolRelFine;
    }

    @Override
    public TraficFineTypes obtainOneById(Long id) throws SQLException {
        int FIRST_ELEMENT = 0;
        PreparedStatement obtainOnePs = conn.prepareStatement(obtainOneByIdSQL);
        obtainOnePs.setLong(1, id);
        return formatToObject(obtainOnePs.executeQuery()).get(FIRST_ELEMENT);
    }

    public Long obtainIdFirterByDescription(String description) throws SQLException {
        String descriptionForSql = "'" + description + "'";
        Statement obtainIdByDescStm = conn.createStatement();
        ResultSet resultQuery = obtainIdByDescStm.executeQuery(obtainIdFilteringByDescription + descriptionForSql);
        resultQuery.next();
        return resultQuery.getLong("id");
    }

    @Override
    public boolean updateOneById(Long id, TraficFineTypes polRelFine) throws SQLException {
        PreparedStatement statement  = conn.prepareStatement(updateOneByIdSQL);
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

    private List<TraficFineTypes> formatToObject(ResultSet resultFromDb) throws SQLException {
        List<TraficFineTypes> polRelFineFromDb = new ArrayList<>();
        while (resultFromDb.next()){
            TraficFineTypes policeRelFine = new TraficFineTypes(
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
