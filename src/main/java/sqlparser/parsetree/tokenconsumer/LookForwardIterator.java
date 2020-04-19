package sqlparser.parsetree.tokenconsumer;

import java.util.Iterator;

public class LookForwardIterator<T> implements Iterator<T> {

    private Iterator<T> iterator;
    private T nextToken;

    public LookForwardIterator(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    public T lookForward() {
        if (nextToken != null) {
            return nextToken;
        }
        T token = iterator.next();
        nextToken = token;
        return token;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext() || nextToken != null;
    }

    @Override
    public T next() {
        if (nextToken == null) {
            return iterator.next();
        } else {
            T res = nextToken;
            nextToken = null;
            return res;
        }
    }
}
