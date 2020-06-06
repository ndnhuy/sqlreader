package sqlparser.parsetree2.model.identifier;

import sqlparser.parsetree2.model.CatalogObject;

public class Column implements CatalogObject {
    private String label;

    public Column(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
