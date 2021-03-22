package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class PretragaController {
    public Button btnOdaberi;
    public TextField txtId;
    public ListView listId;
    private Grad grad;

    public PretragaController(Grad grad) {
        this.grad = grad;
    }

    @FXML
    public void initialize() {
    }

    public Grad getGrad() {
        return grad;
    }

    /*public  void findFile(String fieldTekst, File file) {
        File dir = new File("directoryName");
        String[] children = dir.list();

        if (children == null) {
            System.out.println("does not exist or is not a directory");
        } else {
            for (int i = 0; i < children.length; i++) {
                String filename = children[i];
                System.out.println(filename);
            }
        }
    }*/


    public void traziSliku(ActiveEvent activeEvent){
        String fieldTekst = txtId.getText();


        new Thread(() -> {
            File file = new File("/System/Library/CoreServices/loginwindow.app/Contents/Resources/LogOut.png");
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            //findFile(fieldTekst, new File("/Users"));


        }).start();
    }




}


/*package ba.unsa.etf.rpr;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.File;

public class PretragaController  {
    public Button btnOdaberi;
    public TextField txtId;
    public ListView listId;
    private Grad grad;

    public PretragaController(Grad grad) {
        this.grad = grad;
    }

    @FXML
    public void initialize() {

    }

    public Grad getGrad() {
        return grad;
    }

    public  void findFile(String fieldTekst, File file) {
        File dir = new File("directoryName");
        String[] children = dir.list();

        if (children == null) {
            System.out.println("does not exist or is not a directory");
        } else {
            for (int i = 0; i < children.length; i++) {
                String filename = children[i];
                System.out.println(filename);
            }
        }
    }


    public void traziSliku(ActiveEvent activeEvent){
        String fieldTekst = txtId.getText();
        

        new Thread(() -> {
            File file = new File("/System/Library/CoreServices/loginwindow.app/Contents/Resources/LogOut.png");
            Image image = new Image(file.toURI().toString());
            ImageView imageView = new ImageView(image);
            findFile(fieldTekst, new File("/Users"));


        }).start();
    }




}*/
