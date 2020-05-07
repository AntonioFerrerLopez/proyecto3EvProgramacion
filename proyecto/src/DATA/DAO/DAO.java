package DATA.DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<GenericObject> {
    boolean insert(GenericObject go) throws SQLException;
    List<GenericObject> insertFromList(List<GenericObject> goL) ;
    List<GenericObject> obtainAll() throws SQLException;
    GenericObject obtainOneById(Long id) throws SQLException;
    boolean updateOneById(Long id , GenericObject go) throws SQLException;
    boolean deleteOneById(Long id) throws SQLException;
}
