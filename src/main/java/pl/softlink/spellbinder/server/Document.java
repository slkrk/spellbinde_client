package pl.softlink.spellbinder.server;

public class Document extends pl.softlink.spellbinder.global.Document {

    private pl.softlink.spellbinder.server.model.Document documentModel;

    public Document(pl.softlink.spellbinder.server.model.Document documentModel) {
        super(documentModel.getId(), documentModel.getContent());
        this.documentModel = documentModel;
    }

    public void patch(String diff) {
        super.patch(diff);
        documentModel.setContent(getContent());
        documentModel.save();
    }

    @Override
    public void setDocumentName(String text) {
        documentModel.setName(text);
        documentModel.save();
    }

}
