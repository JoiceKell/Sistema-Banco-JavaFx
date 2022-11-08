//teste

module br.com.fpbank.banco {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;


    opens br.com.fpbank.banco to javafx.fxml;
    exports br.com.fpbank.banco;
    exports br.com.fpbank.banco.Controllers;
    exports br.com.fpbank.banco.Controllers.Admin;
    exports br.com.fpbank.banco.Controllers.Client;
    exports br.com.fpbank.banco.Models;
    exports br.com.fpbank.banco.Views;
    opens br.com.fpbank.banco.Controllers to javafx.fxml;
}
