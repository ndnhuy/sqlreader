package sqlparser.parsetree2.model;

import sqlparser.parsetree2.model.CatalogObject;

public class Table implements CatalogObject {
    private String label;

    public Table(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
