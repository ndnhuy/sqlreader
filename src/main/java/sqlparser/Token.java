package sqlparser;

public class Token {
    private String lexeme;
    private TokenType tokenType;

    public Token(String lexeme, TokenType tokenType) {
        this.lexeme = lexeme;
        this.tokenType = tokenType;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return String.format("%s#%s", lexeme, tokenType.name());
    }
}
