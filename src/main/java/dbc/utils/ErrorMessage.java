package dbc.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ErrorMessage extends Alert {

    private Label message;

    private ErrorMessage(){
        super(AlertType.ERROR);
        BorderPane p = new BorderPane();

        message = new Label();
        message.setWrapText(true);
        message.setAlignment(Pos.BASELINE_RIGHT);
        message.setTextAlignment(TextAlignment.CENTER);
        p.setCenter(message);
        message.setFont(Font.font(12));
        this.getDialogPane().contentProperty().set(p);
        this.getDialogPane().setPrefSize(300, 200);
        this.getDialogPane().setMinWidth(150.0);

    }

    public static void showMessage(String message) {
        final ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(message);
        errorMessage.showAndWait();
    }

    private void setMessage(String message) {
        this.message.setText(message);
    }
}
