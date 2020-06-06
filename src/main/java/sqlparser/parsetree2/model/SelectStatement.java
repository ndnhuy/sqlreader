package sqlparser.parsetree2.model;

import sqlparser.TokenType;
import sqlparser.parsetree.ParsingError;
import sqlparser.parsetree2.model.clause.FromClause;
import sqlparser.parsetree2.model.clause.SelectClause;
import sqlparser.parsetree2.model.clause.WhereClause;
import sqlparser.parsetree2.tokenconsumer.ASTNode;

import java.util.Iterator;

public class SelectStatement implements Statement {
    private SelectClause selectClause;
    private FromClause fromClause;
    private WhereClause whereClause;

    public void SelectStatement(ASTNode root) {
        if (TokenType.STATEMENT != root.getTokenType()) {
            throw new ParsingError("Must be STATEMENT node");
        }
        if (!root.hasChildren()) {
            throw new ParsingError("Missing clauses");
        }
        init(root);
    }

    private void init(ASTNode root) {
        Iterator<ASTNode> children = root.iterator();
        selectClause = new SelectClause(children.next());
        fromClause = new FromClause(children.next());
        whereClause = new WhereClause(children.next());
    }
}
