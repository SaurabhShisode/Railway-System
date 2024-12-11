import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class ResultSetTableModel extends AbstractTableModel {
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int numberOfColumns;

    public ResultSetTableModel(ResultSet resultSet) throws SQLException {
        this.resultSet = resultSet;
        metaData = resultSet.getMetaData();
        numberOfColumns = metaData.getColumnCount();
    }

    @Override
    public int getRowCount() {
        try {
            resultSet.last();  // Move to the last row
            return resultSet.getRow();  // Return the row count
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return numberOfColumns;
    }

    @Override
    public String getColumnName(int columnIndex) {
        try {
            return metaData.getColumnLabel(columnIndex + 1);  // columnIndex is zero-based, JDBC is 1-based
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            resultSet.absolute(rowIndex + 1);  // Move to the correct row
            return resultSet.getObject(columnIndex + 1);  // Fetch the value at columnIndex
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
