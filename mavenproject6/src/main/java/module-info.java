module com.mycompany.mavenproject6 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.mavenproject6 to javafx.fxml;
    exports com.mycompany.mavenproject6;
}
