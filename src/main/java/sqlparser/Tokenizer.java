package sqlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tokenizer {
    private static Map<String, TokenType> lexemeToTokenType = new HashMap<>();
    public Tokenizer() {
        lexemeToTokenType.put("SELECT", TokenType.SELECT);
        lexemeToTokenType.put("FROM", TokenType.KEYWORD);
        lexemeToTokenType.put("WHERE", TokenType.KEYWORD);
        lexemeToTokenType.put(",", TokenType.SEPARATOR);
        lexemeToTokenType.put("(", TokenType.SEPARATOR);
        lexemeToTokenType.put(")", TokenType.SEPARATOR);
        lexemeToTokenType.put("=", TokenType.OPERATOR);
    }
    public List<Token> produceTokens(String source) {
        List<Token> tokens = new ArrayList<>();
        Scanner scanner = new Scanner();
        List<String> lexemes = scanner.produceLexemes(source);
        for (String lexeme : lexemes) {
            if (lexemeToTokenType.containsKey(lexeme)) {
                tokens.add(new Token(lexeme, lexemeToTokenType.get(lexeme)));
            } else {
                if (lexeme.startsWith("\"")) {
                    tokens.add(new Token(lexeme, TokenType.LITERAL));
                } else {
                    tokens.add(new Token(lexeme, TokenType.IDENTIFIER));
                }
            }
        }
        return tokens;
    }
}
