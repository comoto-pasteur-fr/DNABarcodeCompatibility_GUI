package dbc.main.experiment.visual.htmlOutput;

import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;

public class HtmlBrowser extends Pane{


    final WebView webView = new WebView();
    final WebEngine webEngine = webView.getEngine();
    private String fileName;

    public void setFile() {

        URL url = getClass().getResource(fileName);
//        if (url1 == null){
//            System.out.println("pas de fichier");
//        }
//        try {
//        url = file.toURI().toURL();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        webEngine.load(url.toString());
        this.getChildren().add(webView);

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
