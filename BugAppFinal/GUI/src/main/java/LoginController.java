import domain.Tester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.BugException;

import java.util.Objects;

public class LoginController extends Controller{

    ObservableList<String> jobFunctions = FXCollections.observableArrayList("Programmer","Tester" );
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Button loginButton;
    public ComboBox<String> functionBox;

    private Parent testControllerParent;
    private TesterController testerController;
    private Parent programmerControllerParent;
    private ProgrammerController programmerController;

    public void setTesterController(TesterController testerController){this.testerController =testerController;}
    public void setTesterParent(Parent parent){testControllerParent = parent;}
    public void setProgrammerControllerParent(Parent programmerControllerParent) {
        this.programmerControllerParent = programmerControllerParent;
    }
    public void setProgrammerController(ProgrammerController programmerController) {
        this.programmerController = programmerController;
    }

    public void initializeEverything()
    {
        functionBox.setValue("Programmer");
        functionBox.setItems(jobFunctions);
    }

    public void loginVerification() {
        String jobTitle = functionBox.getValue();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        try{
            if(Objects.equals(jobTitle, "Tester")){
                var tester = service.loginVerificationTester(username, password, testerController);
                Scene scene = new Scene(testControllerParent, 900, 600);
                Stage stage = new Stage();

                testerController.setTester(tester);
                testerController.initializeEverything();

                stage.setTitle(tester.getUsername());
                stage.setScene(scene);
                stage.show();

            }
            else{
                var programmer = service.loginVerificationProgrammer(username, password, programmerController);
                Scene scene = new Scene(programmerControllerParent, 900, 600);
                Stage stage = new Stage();

                programmerController.setProgrammer(programmer);
                programmerController.initializeEverything();

                stage.setTitle(programmer.getUsername());
                stage.setScene(scene);
                stage.show();

            }
        }catch(BugException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ex.getMessage());
            alert.setHeaderText("Invalid data");
            alert.setContentText("Sorry your username or password is invalid!");
            alert.showAndWait();
        }


    }


}
