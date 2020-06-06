package sqlparser.parsetree2.model.clause;

import sqlparser.parsetree2.tokenconsumer.ASTNode;

public class WhereClause implements Clause {

    public WhereClause(ASTNode node) {

    }
    @Override
    public String getLabel() {
        return "WHERE";
    }
}
