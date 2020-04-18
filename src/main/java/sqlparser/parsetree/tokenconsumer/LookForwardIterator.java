package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class LookForwardIterator<T> implements Iterator<T> {

    private Iterator<T> iterator;
    private Queue<T> stashedTokens = new ArrayDeque<>();

    public LookForwardIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    public T lookForward() {
        T token = iterator.next();
        stashedTokens.add(token);
        return token;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext() || !stashedTokens.isEmpty();
    }

    @Override
    public T next() {
        if (stashedTokens.isEmpty()) {
            return iterator.next();
        } else {
            return stashedTokens.remove();
        }
    }
}
