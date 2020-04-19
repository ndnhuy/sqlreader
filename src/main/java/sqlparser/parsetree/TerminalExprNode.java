package sqlparser.parsetree;

import sqlparser.TokenType;

import java.util.Collections;
import java.util.Iterator;

public class TerminalExprNode implements ExprNode {
    private TokenType tokenType;
    private String value;

    public TerminalExprNode(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Iterator<ExprNode> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public String toString() {
        return print(",");
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public String print(String separator) {
        return value;
    }
}
