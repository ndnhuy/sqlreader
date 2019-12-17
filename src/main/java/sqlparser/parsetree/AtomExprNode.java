package sqlparser.parsetree;

import java.util.Collections;
import java.util.Iterator;

public class AtomExprNode<Token> implements ExprNode<Token> {

    private Token value;

    public AtomExprNode(Token value) {
        this.value = value;
    }

    public Token getToken() {
        return value;
    }

    @Override
    public Iterator<ExprNode> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public void add(ExprNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAtom() {
        return true;
    }
}
