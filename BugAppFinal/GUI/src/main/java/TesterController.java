import domain.Bug;
import domain.BugStatus;
import domain.Tester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import service.BugException;
import service.IBugsObserver;

import java.util.Arrays;
import java.util.Objects;

public class TesterController extends Controller implements IBugsObserver {

    ObservableList<String> riskLevelsArray = FXCollections.observableArrayList("low","average","high");
    public TableView<Bug> tableViewBugs;
    public TextField titleTextField;
    public TextArea descriptionTextArea;
    public ComboBox<String> riskLevelComboBox;
    public Button addButton;

    private Tester tester;

    public void setTester(Tester tester){this.tester = tester;}

    @Override
    public void initializeEverything() {
        riskLevelComboBox.setValue("low");
        riskLevelComboBox.setItems(riskLevelsArray);

        initializeTable(tableViewBugs, Arrays.asList("Title", "Description", "Status", "RiskLevel"));
        ObservableList<Bug> observableArrayList =
                FXCollections.observableArrayList(service.getAllBugs());
        tableViewBugs.setItems(observableArrayList);

    }

    @Override
    public void updateReceived() throws BugException {
        ObservableList<Bug> observableArrayList =
                FXCollections.observableArrayList(service.getAllBugs());
        tableViewBugs.setItems(observableArrayList);
    }

    public void addBug() {
        String title = titleTextField.getText();
        String description = descriptionTextArea.getText();
        String riskLevel = riskLevelComboBox.getValue();
        String status = BugStatus.active.toString();
        if(Objects.equals(title, "") || Objects.equals(description, ""))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Invalid data");
            alert.setContentText("Sorry Title and Description cannot be empty!");
            alert.showAndWait();
            return;
        }
        Bug bug = new Bug(title, description, status, riskLevel);

        try{
            service.addBug(bug);
        }catch (BugException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Exception");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }


    }
}
