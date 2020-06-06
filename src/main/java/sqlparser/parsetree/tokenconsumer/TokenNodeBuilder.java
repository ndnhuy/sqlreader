package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;
import sqlparser.parsetree.ParsingError;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TokenNodeBuilder {

    public ExprNode build(List<Token> tokens) {
        List<TokenType> tokenTypes = tokens.stream().map(Token::getTokenType).collect(Collectors.toList());
        Integer[] indexes = Stream.of(TokenType.SELECT, TokenType.FROM, TokenType.WHERE)
                .map(tokenTypes::indexOf)
                .toArray(Integer[]::new);
        if (indexes[0] == -1) {
            throw new ParsingError("Must start with SELECT");
        }
        if (indexes[1] == -1) {
            throw new ParsingError("Missing FROM");
        }
        if (indexes[0] >= indexes[1] || (indexes[2] != -1 && indexes[0] >= indexes[2])) {
            throw new ParsingError("Must start with SELECT");
        }

        return null;
    }
}
