package sqlparser.parsetree2;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ParsingError;

import java.util.List;
import java.util.stream.IntStream;

public class SelectStatement implements Statement {
    private SelectClause selectClause;
    private FromClause fromClause;
    private WhereClause whereClause;

    public void SelectStatement(List<Token> tokens) {
        if (TokenType.STATEMENT != tokens.get(0).getTokenType() || !"SELECT".equalsIgnoreCase(tokens.get(0).getLexeme())) {
            throw new ParsingError("Must start with SELECT");
        }

        int fromIndex = findIndexOfClause(tokens, "FROM");
        if (fromIndex == -1) {
            throw new ParsingError("Missing FROM");
        }

        selectClause = new SelectClause(tokens.subList(1, fromIndex));

        fromClause = new FromClause(tokens.subList(1, fromIndex));

        int whereIndex = findIndexOfClause(tokens, "WHERE");
        if (whereIndex > -1) {
            whereClause = new WhereClause(tokens.subList(whereIndex+1, tokens.size()));
        }
    }

    private int findIndexOfClause(List<Token> tokens, String clauseLabel) {
        return IntStream.range(0, tokens.size())
                            .filter(i -> tokens.get(i).getTokenType() == TokenType.STATEMENT
                                    && clauseLabel.equalsIgnoreCase(tokens.get(i).getLexeme()))
                            .findFirst()
                            .orElse(-1);
    }
}
