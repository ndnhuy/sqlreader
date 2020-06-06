package sqlparser.parsetree2;

import sqlparser.Token;

import java.util.List;

public class FromClause implements Clause {

    public FromClause(List<Token> tokens) {

    }

    @Override
    public String getLabel() {
        return "FROM";
    }
}
