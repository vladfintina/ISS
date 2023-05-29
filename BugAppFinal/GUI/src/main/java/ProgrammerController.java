import domain.Bug;
import domain.BugStatus;
import domain.Programmer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import service.BugException;
import service.IBugsObserver;

import java.util.Arrays;

public class ProgrammerController extends Controller implements IBugsObserver {
    public TableView<Bug> tableViewBugs;
    public ComboBox<String> comboBoxStatus;
    public ComboBox<String> comboBoxRiskLevel;
    public Button filterStatusBtn;
    public Button filterRiskLevelBtn;
    public Button solvedBugBtn;
    public Button displayAllBtn;

    ObservableList<String> riskLevelsArray = FXCollections.observableArrayList("low","average","high");
    ObservableList<String> statusArray = FXCollections.observableArrayList("active", "solved");

    private Programmer programmer;

    public void setProgrammer(Programmer programmer) {
        this.programmer = programmer;
    }

    @Override
    public void initializeEverything() {
        comboBoxRiskLevel.setValue("low");
        comboBoxRiskLevel.setItems(riskLevelsArray);

        comboBoxStatus.setValue("active");
        comboBoxStatus.setItems(statusArray);

        initializeTable(tableViewBugs, Arrays.asList("Title", "Description", "Status", "RiskLevel"));
        ObservableList<Bug> observableArrayList =
                FXCollections.observableArrayList(service.getAllBugs());
        tableViewBugs.setItems(observableArrayList);
    }
    public void clickFilterStatus(ActionEvent actionEvent) {
        String status = comboBoxStatus.getValue();
        ObservableList<Bug> observableArrayList =
                FXCollections.observableArrayList(service.getBugsFilteredByStatus(status));
        tableViewBugs.setItems(observableArrayList);
    }

    public void clickFilterRiskLevel(ActionEvent actionEvent) {
        String riskLevel = comboBoxRiskLevel.getValue();
        ObservableList<Bug> observableArrayList =
                FXCollections.observableArrayList(service.getBugsFilteredByRiskLevel(riskLevel));
        tableViewBugs.setItems(observableArrayList);

    }

    public void clickSolvedBug(ActionEvent actionEvent) {
        Bug bug = tableViewBugs.getSelectionModel().getSelectedItem();
        if(bug == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Invalid bug");
            alert.setContentText("Please select a bug!");
            alert.showAndWait();
            return;
        }
        bug.setStatus(BugStatus.solved.toString());
        try {
            service.updateBugStatus(bug);
        }catch (BugException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Exception");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public void clickDisplayAll(ActionEvent actionEvent) {
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
}
