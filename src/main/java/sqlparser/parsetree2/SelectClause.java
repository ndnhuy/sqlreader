package sqlparser.parsetree2;

import sqlparser.Token;

import java.util.List;

public class SelectClause implements Clause {

    public SelectClause(List<Token> tokens) {

    }

    @Override
    public String getLabel() {
        return "SELECT";
    }
}
