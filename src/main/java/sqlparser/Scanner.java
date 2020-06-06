package sqlparser;

import java.util.ArrayList;
import java.util.List;

public class Scanner {
    public List<String> produceLexemes(String source) {
        List<String> lexemes = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean literalReading = false;
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (literalReading) {
                sb.append(c);
                if (c == '"') {
                    literalReading = false;
                }
                continue;
            }
            switch (c) {
                case ' ':
                case '\n':
                case ';':
                    add(lexemes, sb.toString());
                    sb.setLength(0);
                    break;
                case ',':
                    add(lexemes, sb.toString());
                    add(lexemes, String.valueOf(c));
                    sb.setLength(0);
                    break;
                case '(':
                case ')':
                    add(lexemes, sb.toString());
                    add(lexemes, String.valueOf(c));
                    sb.setLength(0);
                    break;
                case '"':
                    literalReading = true;
                    sb.append(c);
                    break;
                case '=':
                    add(lexemes, sb.toString());
                    add(lexemes, String.valueOf(c));
                    sb.setLength(0);
                    break;
                default:
                    sb.append(c);
            }
        }
        add(lexemes, sb.toString());
        return lexemes;
    }

    private void add(List<String> l, String s) {
        if (s != null && !s.isEmpty()) {
            l.add(s);
        }
    }
}
