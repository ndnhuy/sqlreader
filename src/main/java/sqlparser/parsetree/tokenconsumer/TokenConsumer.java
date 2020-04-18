package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.parsetree.ExprNode;

import java.util.Iterator;

public interface TokenConsumer {
    ExprNode build(LookForwardIterator<Token> tokenIterator);
}
