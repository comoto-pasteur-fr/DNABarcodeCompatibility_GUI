package dbc.main.experiment.visual.htmlOutput;

import dbc.main.experiment.visual.htmlOutput.HtmlBrowser;

public class TutorialPage extends HtmlBrowser {

    final String fileName1 = "/html pages/helloworld.html";

    public TutorialPage(){
        this.setFileName(fileName1);
        this.setFile();

    }
}
