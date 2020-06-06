package sqlparser;

public enum TokenType {
    @Deprecated
    SELECT, FROM, WHERE,CONDITIONAL_OPERATOR,EXPRESSION_OPERATOR,LITERAL,




    STATEMENT,
    CLAUSE,
    STRING_LITERAL,
    COMPARISON_OPERATOR,
    LOGICAL_OPERATOR,
    SEPARATOR,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    IDENTIFIER;

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
