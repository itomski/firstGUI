package de.lubowiecki.firstgui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final String SER_DIR = System.getProperty("user.home") + "/firstgui";

    private static final String SER_FILE = "data.ser";

    private List<String> shoppingList = new ArrayList<>();

    @FXML
    private ListView<String> listOutput;

    @FXML
    private TextField txtInput;

    @FXML // macht die Methode für die GUI verfügbar
    protected void add() {
        String input = txtInput.getText();
        shoppingList.add(input);
        txtInput.clear();
        saveToFile(); // Nach jeder Änderung speichern
        show();
    }

    @FXML
    protected void delete() {
        String item = listOutput.getSelectionModel().getSelectedItem();
        if(item != null && shoppingList.contains(item)) {
            shoppingList.remove(item);
            show();
        }
    }

    private void show() {
        listOutput.getItems().clear();

        for(String item : shoppingList) {
            listOutput.getItems().add(item);
        }
    }

    private void saveToFile() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SER_DIR + "/" + SER_FILE))) {
            out.writeObject(shoppingList);
        }
        catch (Exception e) {
            System.out.println("Fehler beim Schreiben in die Datei. " + e.getMessage());
        }
    }

    private void readFromFile() {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(SER_DIR + "/" + SER_FILE))) {
            shoppingList = (List<String>) in.readObject();
        }
        catch (Exception e) {
            System.out.println("Fehler beim Lesen der Datei. " + e.getMessage());
            shoppingList = new ArrayList<>();
        }
    }

    private void checkDataDir() {
        Path path = Paths.get(SER_DIR);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // Wird automatisch verwendet, sobald die GUI geladen ist
        checkDataDir();
        readFromFile();
        show();
    }
}