package ba.unsa.etf.rpr;

import com.sun.javafx.menu.MenuItemBase;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class GradController {
    public TextField fieldNaziv;
    public TextField fieldBrojStanovnika;
    public ChoiceBox<Drzava> choiceDrzava;
    public ObservableList<Drzava> listDrzave;
    private Grad grad;
    public TextField fieldPostanskiBroj;
    public Button btnOk;
    public Button btnCancel;

    public GradController(Grad grad, ArrayList<Drzava> drzave) {
        this.grad = grad;
        listDrzave = FXCollections.observableArrayList(drzave);
    }

    @FXML
    public void initialize() {
        choiceDrzava.setItems(listDrzave);
        if (grad != null) {
            fieldNaziv.setText(grad.getNaziv());
            fieldBrojStanovnika.setText(Integer.toString(grad.getBrojStanovnika()));
            fieldPostanskiBroj.setText(Integer.toString(grad.getPostanskiBroj()));
            // choiceDrzava.getSelectionModel().select(grad.getDrzava());
            // ovo ne radi jer grad.getDrzava() nije identički jednak objekat kao član listDrzave
            for (Drzava drzava : listDrzave)
                if (drzava.getId() == grad.getDrzava().getId())
                    choiceDrzava.getSelectionModel().select(drzava);
        } else {
            choiceDrzava.getSelectionModel().selectFirst();
        }
    }

    public Grad getGrad() {
        return grad;
    }

    public void clickCancel(ActionEvent actionEvent) {
        grad = null;
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }

    private void ulazi() {
        fieldNaziv.setEditable(true);
        fieldBrojStanovnika.setEditable(true);
        fieldPostanskiBroj.setEditable(true);
        choiceDrzava.disableProperty().setValue(false);
        ;
        fieldNaziv.getScene().setCursor(Cursor.DEFAULT);
        btnOk.disableProperty().setValue(false);
        btnCancel.disableProperty().setValue(false);
    }

    private void ulazi2() {
        fieldNaziv.setEditable(false);
        fieldBrojStanovnika.setEditable(false);
        fieldPostanskiBroj.setEditable(false);
        choiceDrzava.disableProperty().setValue(true);
        fieldNaziv.getScene().setCursor(Cursor.WAIT);
        btnOk.disableProperty().setValue(true);
        btnCancel.disableProperty().setValue(true);
    }

    public void clickOk(ActionEvent actionEvent) {

        ulazi2();
        AtomicBoolean sveOk = new AtomicBoolean(true);

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk.set(false);
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }


        int brojStanovnika = 0;
        try {
            brojStanovnika = Integer.parseInt(fieldBrojStanovnika.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (brojStanovnika <= 0) {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeNijeIspravno");
            sveOk.set(false);
        } else {
            fieldBrojStanovnika.getStyleClass().removeAll("poljeNijeIspravno");
            fieldBrojStanovnika.getStyleClass().add("poljeIspravno");
        }


        Thread thread1 = new Thread(() -> {
            String link = "http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=";
            link += fieldPostanskiBroj.getText().trim();
            try {
                URL url = new URL(link);
                BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                String json = "", line = null;
                while ((line = input.readLine()) != null) {
                    json += line;
                }
                input.close();
                boolean temp = json.equals("OK");
                if (temp) {
                    Platform.runLater(() -> {
                        fieldPostanskiBroj.getStyleClass().removeAll("poljeNijeIspravno");
                        fieldPostanskiBroj.getStyleClass().add("poljeIspravno");
                    });

                } else {
                    ulazi();
                    sveOk.set(false);
                    Platform.runLater(() -> {
                        fieldPostanskiBroj.getStyleClass().removeAll("poljeIspravno");
                        fieldPostanskiBroj.getStyleClass().add("poljeNijeIspravno");
                    });
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        });
        thread1.start();


        if (grad == null) grad = new Grad();
        grad.setNaziv(fieldNaziv.getText());
        grad.setBrojStanovnika(Integer.parseInt(fieldBrojStanovnika.getText()));
        grad.setDrzava(choiceDrzava.getValue());
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        grad.setPostanskiBroj(Integer.parseInt(fieldPostanskiBroj.getText()));
        stage.close();
    }


    public void actionPromijeni(ActionEvent actionEvent){
        TextInputDialog dialog = new TextInputDialog("Picture");

        dialog.setTitle("Odabir slike");
        dialog.setHeaderText("Napisi ime slike:");
        dialog.setContentText("Slika:");

        Optional<String> result = dialog.showAndWait();


    }
    public void actionOdaberiSliku(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pretraga.fxml"));
            PretragaController pretragaController = new PretragaController(grad);
            loader.setController(pretragaController);
            root = loader.load();
            stage.setTitle("Pretraga datoteka");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
