package sqlparser.parsetree2.model.clause;

import sqlparser.TokenType;
import sqlparser.parsetree.ParsingError;
import sqlparser.parsetree2.model.identifier.ColumnIdentifier;
import sqlparser.parsetree2.tokenconsumer.ASTNode;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SelectClause implements Clause {
    private List<ColumnIdentifier> columnIdentifiers;

    public SelectClause(ASTNode root) {
        if (TokenType.CLAUSE != root.getTokenType()) {
            throw new ParsingError("Must be CLAUSE");
        }
        if (!"select".equalsIgnoreCase(root.getLabel())) {
            throw new ParsingError("Must be SELECT");
        }
        if (!root.hasChildren()) {
            throw new ParsingError("Missing column identifiers");
        }
        init(root);
    }

    private void init(ASTNode root) {
        List<ASTNode> colNodes = new ArrayList<>();
        TokenType expectTokenType = TokenType.IDENTIFIER;
        for (ASTNode cNode : root) {
            if (cNode.getTokenType() != expectTokenType) {
                throw new ParsingError("Expect " + expectTokenType + " but is " + cNode.getToken());
            }
            if (cNode.getTokenType() == TokenType.IDENTIFIER) {
                colNodes.add(cNode);
                expectTokenType = TokenType.SEPARATOR;
            } else if (cNode.getTokenType() == TokenType.SEPARATOR) {
                expectTokenType = TokenType.IDENTIFIER;
            } else {
                throw new ParsingError(String.format("Unexpected token, only allow [%s, %s]", TokenType.IDENTIFIER, TokenType.SEPARATOR));
            }
        }
        columnIdentifiers = colNodes.stream().map(ColumnIdentifier::new).collect(toList());
    }

    @Override
    public String getLabel() {
        return "SELECT";
    }
}
