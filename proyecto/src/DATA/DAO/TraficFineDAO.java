package DATA.DAO;

import DATA.DATABASE.DbConnector;
import MODEL.Police;
import MODEL.TraficFine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TraficFineDAO implements DAO<TraficFineDAO> {


    public TraficFineDAO() throws SQLException {
        this.conn =  DbConnector.dbInstance().getConn();
    }

    private final Connection conn;
    private String insertSQL = "INSERT INTO multas (descripcion, fecha, importe, idpolicia, nifinfractor, idtipo)  VALUES (?,?,?,?,?,?)";
    private String obtainAllSQL = "SELECT * from multas";
    private String obtainOneByIdSQL = "SELECT * from multas  WHERE idPolicia = ? ";
    private String updateOneByIdSQL = "UPDATE multas SET descripcion = ?, fecha = ?, importe = ?, idpolicia = ?, nifinfractor = ?, idtipo = ?  WHERE idPolicia = ?";
    private String deleteOneByIdSQL = "DELETE * from multas  WHERE id = ? ";

    @Override
    public boolean insert(TraficFineDAO go) throws SQLException {
return true;
    }

    @Override
    public List<TraficFineDAO> obtainAll() throws SQLException {
        return null;
    }


    @Override
    public TraficFineDAO obtainOneById(Long id) throws SQLException {
        return null;
    }

    @Override
    public boolean updateOneById(Long id, TraficFineDAO go) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteOneById(Long id) throws SQLException {
        return false;
    }

    private List<TraficFine> formatToObject(ResultSet resultFromDb) throws SQLException {
        List<TraficFine> finesFromDb = new ArrayList<>();
        while (resultFromDb.next()){

            TraficFine police = new  TraficFine(
                    resultFromDb.getLong("id"),
                    resultFromDb.getString("descripcion"),
                    resultFromDb.getDate("fecha").toLocalDate(),
                    resultFromDb.getDouble("importe"),
                    resultFromDb.getInt("idpolicia"),
                    resultFromDb.getString("nifinfractor"),
                    resultFromDb.getInt("idtipo") );
            finesFromDb.add(police);
        }
        return finesFromDb ;
    }

}
