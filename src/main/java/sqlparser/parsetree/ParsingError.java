package sqlparser.parsetree;

public class ParsingError extends RuntimeException {
    public ParsingError(String message) {
        super(message);
    }
}
