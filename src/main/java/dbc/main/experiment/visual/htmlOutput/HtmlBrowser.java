package dbc.main.experiment.visual.htmlOutput;

import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;

public class HtmlBrowser extends Region {


    final WebView webView = new WebView();
    final WebEngine webEngine = webView.getEngine();
    private String fileName;

    public void setFile() {

        URL url = getClass().getResource(fileName);
        webEngine.load(url.toString());
        this.getChildren().add(webView);
        webView.setPrefSize(950, 600);
        this.setPrefSize(950, 600);
    }



    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
