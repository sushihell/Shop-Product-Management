module org.apon.onlineshopadmin {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.apon.onlineshopadmin to javafx.fxml;
    exports org.apon.onlineshopadmin;
    exports product;
    opens product to javafx.fxml;
    exports order;
    opens order to javafx.fxml;
}