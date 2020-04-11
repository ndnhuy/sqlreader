package sqlparser;

public enum TokenType {
    LITERAL, KEYWORD, IDENTIFIER, SEPARATOR, OPERATOR,
    QUERY,
    COLUMN_IDENTIFIER_LIST, TABLE_IDENTIFIER_LIST,
    PREDICATE,
    EXPRESSION,
    SELECT,
    FROM,
    WHERE,
    LEFT_BRACKET("("),
    RIGHT_BRACKET(")");

    private String code;

    TokenType() {
        this.code = this.name();
    }

    TokenType(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
