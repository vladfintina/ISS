import domain.Bug;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.IBugRepository;
import repository.IProgrammerRepository;
import repository.ITesterRepository;
import repository.hibernate.*;
import service.Service;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        IBugRepository bugRepository = new BugHibernateRepository();
        IProgrammerRepository programmerRepository = new ProgrammerHibernateRepository();
        ITesterRepository testerRepository = new TesterHibernateRepository();

        Service service = new Service(programmerRepository, testerRepository, bugRepository);
        /*var allBugs = bugRepository.findAll();
        for(var bug: allBugs )
        {
            System.out.println(bug);
        }*/

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
        Parent root =  fxmlLoader.load();

        LoginController loginCtrl = fxmlLoader.<LoginController>getController();
        loginCtrl.initializeEverything();
        loginCtrl.setService(service);

        //tester
        FXMLLoader testerLoader = new FXMLLoader(getClass().getClassLoader().getResource("testerWindow.fxml"));
        Parent testerRoot = testerLoader.load();

        TesterController testerController = testerLoader.getController();
        testerController.setService(service);

        loginCtrl.setTesterController(testerController);
        loginCtrl.setTesterParent(testerRoot);

        //Programmer
        FXMLLoader programmerLoader = new FXMLLoader(getClass().getClassLoader().getResource("programmerWindow.fxml"));
        Parent programmerRoot = programmerLoader.load();

        ProgrammerController programmerController = programmerLoader.getController();
        programmerController.setService(service);

        loginCtrl.setProgrammerController(programmerController);
        loginCtrl.setProgrammerControllerParent(programmerRoot);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("LOGIN!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}