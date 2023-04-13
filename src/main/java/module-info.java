module de.lubowiecki.firstgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.lubowiecki.firstgui to javafx.fxml;
    exports de.lubowiecki.firstgui;
}