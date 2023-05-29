import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

import java.util.List;

public abstract class Controller {

    protected Service service;

    protected static <E> void initializeTable(TableView<E> table, List<String> columnNames) {
        for (String columnName : columnNames) {
            TableColumn<E, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(columnName));
            table.getColumns().add(column);
        }
    }

    public void setService(Service server) {
        this.service = server;
    }

    public abstract void initializeEverything();

}