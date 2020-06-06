package sqlparser.parsetree2.model.identifier;

import sqlparser.TokenType;
import sqlparser.parsetree.ParsingError;
import sqlparser.parsetree2.model.Schema;
import sqlparser.parsetree2.model.Table;
import sqlparser.parsetree2.tokenconsumer.ASTNode;

public class ColumnIdentifier implements Identifier {
    private Schema schema;
    private Table table;
    private Column column;

    public ColumnIdentifier(ASTNode node) {
        if (TokenType.IDENTIFIER != node.getTokenType()) {
            throw new ParsingError("Must be " + TokenType.IDENTIFIER);
        }
        String[] splits = node.getLabel().split("\\.");
        if (splits.length == 1) {
            column = new Column(splits[0]);
        } else if (splits.length == 2) {
            table = new Table(splits[0]);
            column = new Column(splits[1]);
        } else if (splits.length == 3) {
            schema = new Schema(splits[0]);
            table = new Table(splits[1]);
            column = new Column(splits[2]);
        }
    }
}
