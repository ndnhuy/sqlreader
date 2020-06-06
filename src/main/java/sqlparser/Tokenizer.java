package sqlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokenizer {
    private static Map<String, TokenType> lexemeToTokenType = new HashMap<>();
    public Tokenizer() {
        lexemeToTokenType.put("SELECT", TokenType.STATEMENT);
        lexemeToTokenType.put("FROM", TokenType.CLAUSE);
        lexemeToTokenType.put("WHERE", TokenType.CLAUSE);
        lexemeToTokenType.put("GROUP", TokenType.CLAUSE);
        lexemeToTokenType.put("ORDER", TokenType.CLAUSE);
        lexemeToTokenType.put(",", TokenType.SEPARATOR);
        lexemeToTokenType.put("(", TokenType.LEFT_BRACKET);
        lexemeToTokenType.put(")", TokenType.RIGHT_BRACKET);
        lexemeToTokenType.put("=", TokenType.COMPARISON_OPERATOR);
        lexemeToTokenType.put("<", TokenType.COMPARISON_OPERATOR);
        lexemeToTokenType.put(">", TokenType.COMPARISON_OPERATOR);
        lexemeToTokenType.put("AND", TokenType.LOGICAL_OPERATOR);
        lexemeToTokenType.put("OR", TokenType.LOGICAL_OPERATOR);
        lexemeToTokenType.put(";", TokenType.IDENTIFIER);
    }
    public List<Token> produceTokens(String source) {
        List<Token> tokens = new ArrayList<>();
        Scanner scanner = new Scanner();
        List<String> lexemes = scanner.produceLexemes(source);
        for (String lexeme : lexemes) {
            if (lexemeToTokenType.containsKey(lexeme)) {
                tokens.add(new Token(lexeme, lexemeToTokenType.get(lexeme)));
            } else {
                if (lexeme.startsWith("\"") || lexeme.startsWith("\'")) {
                    tokens.add(new Token(lexeme, TokenType.STRING_LITERAL));
                } else if (!lexeme.equalsIgnoreCase("\n")) {
                    tokens.add(new Token(lexeme, TokenType.IDENTIFIER));
                }
            }
        }
        return tokens;
    }
}
