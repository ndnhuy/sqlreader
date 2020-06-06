package sqlparser.parsetree2.model.clause;

import sqlparser.parsetree2.tokenconsumer.ASTNode;

public class FromClause implements Clause {

    public FromClause(ASTNode root) {

    }

    @Override
    public String getLabel() {
        return "FROM";
    }
}
