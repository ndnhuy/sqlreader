package sqlparser.parsetree2;

import sqlparser.Token;

import java.util.List;

public class WhereClause implements Clause {

    public WhereClause(List<Token> tokens) {

    }
    @Override
    public String getLabel() {
        return "WHERE";
    }
}
