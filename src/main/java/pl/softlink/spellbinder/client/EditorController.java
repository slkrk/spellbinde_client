package pl.softlink.spellbinder.client;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import pl.softlink.spellbinder.client.event.DocumentChangedRemotelyEvent;
import pl.softlink.spellbinder.client.event.EditorKeyPressedEvent;
import pl.softlink.spellbinder.client.event.EventListener;

public class EditorController implements EventListener<DocumentChangedRemotelyEvent> {


    @FXML
    private Pane root;

    public EditorController() {
        super();
        Context.getMainContext().setEditorController(this);
    }

    @FXML
    protected void onKeyReleased(KeyEvent keyEvent) {

        String newContent = ((TextArea) root.getChildren().get(0)).getText();
        Context.getMainContext().postEvent(new EditorKeyPressedEvent(newContent));

//        String newContent = ((TextArea) root.getChildren().get(0)).getText();
//        String diff = TextDiff.generate(content, newContent);
//
//        if (diff != null) {
//
//            JSONObject payloadJson = new JSONObject();
//            payloadJson.put("diff", diff);
//            String payloadString = payloadJson.toString();
////            PushRunnable.send(payloadString);
//        }
//
//        content = TextDiff.apply(content, diff);
//
//        Logger.getAnonymousLogger().info("Pressed key: " + keyEvent.getText());
    }


    public void onEvent(DocumentChangedRemotelyEvent event) {
        ((TextArea) root.getChildren().get(0)).setText(event.getDocument().getContent());
    }

}