package sqlparser.parsetree2.model;

public class Schema implements CatalogObject {
    private String label;

    public Schema(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
