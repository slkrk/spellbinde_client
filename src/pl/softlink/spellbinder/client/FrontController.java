package pl.softlink.spellbinder.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.logging.Logger;
import pl.softlink.spellbinder.lib.diff_match_patch;


public class FrontController {

    private String content = "";

    @FXML
    private Pane root;

//    @FXML
//    private TextArea mainPane_textArea;

    @FXML
    protected void onKeyReleased(KeyEvent keyEvent) {

        diff_match_patch dmp = new diff_match_patch();
        String newContent = ((TextArea) root.getChildren().get(0)).getText();
        LinkedList<diff_match_patch.Diff> diffList =  dmp.diff_main(content, newContent, true);
        String delta = dmp.diff_toDelta(diffList);
        LinkedList<diff_match_patch.Diff> diffList2 = dmp.diff_fromDelta(content, delta);

        LinkedList<diff_match_patch.Patch> patchList = dmp.patch_make(content, diffList2);
        Object[] result = dmp.patch_apply(patchList, content);

        content = (String) result[0];


//        content = newContent;



//        diff_match_patch.Diff diff = new diff_match_patch.Diff(diff_match_patch.Operation.EQUAL, content);


        Logger.getAnonymousLogger().info("Pressed key: " + keyEvent.getText());
    }

}