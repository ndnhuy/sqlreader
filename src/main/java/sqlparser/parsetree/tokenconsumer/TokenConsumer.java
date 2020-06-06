package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.parsetree.ExprNode;

public interface TokenConsumer {
    ExprNode build(LookForwardIterator<Token> tokenIterator);
}
