package icashiercore;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConfigService {

    private static Set<String> TABLE_NAMES = new HashSet<>(Arrays.asList("ipd_channel_decision_rule"));
    private static Map<String, List<String>> jsonRowsCache = new HashMap<>();
    private static Map<String, String> all = new HashMap<>();

    public Map<String, String> getAll() {
        if (!all.isEmpty()) {
            return all;
        }
        try {
            String repoPath = "/Users/administrator/huynguyen/repos/icashiercoreConf";
            List<Path> tablePaths = Files.list(Paths.get(repoPath))
                 .filter(Files::isRegularFile)
                .filter(path -> TABLE_NAMES.stream().anyMatch(tableName -> path.toString().endsWith(tableName + ".json")))
                .collect(Collectors.toList());

            Map<String, String> result = new LinkedHashMap<>();
            for (Path p : tablePaths) {
                String content = new String(Files.readAllBytes(p));
                result.put(p.getFileName().toString(), content);
            }
            all = result;
            return result;
        } catch (IOException ex) {
            throw new RuntimeException("Error while reading files");
        }
    }

    public Map<String, String> search(String text) {
        Map<String, String> all = getAll();
        if (text == null || text.isEmpty()) {
            return all;
        }
        return all.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> filterText(entry.getKey(), entry.getValue(), text)));
    }

    private String filterText(String tableName, String jsonArr, String searchText) {
        if (!jsonRowsCache.containsKey(tableName)) {
            jsonRowsCache.put(tableName, split(jsonArr.toCharArray(), jsonArr.indexOf('{')));
        }
        String rs = jsonRowsCache.get(tableName).stream().filter(s -> s.toUpperCase().contains(searchText.toUpperCase())).collect(Collectors.joining(",\n  "));
        return "[\n  " + rs + "\n]";
    }

    private List<String> split(char[] text, int begin) {
        if (text[begin] != '{') {
            throw new RuntimeException("invalid start token of json");
        }
        List<String> result = new ArrayList<>();
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = begin; i < text.length; i++) {
            sb.append(text[i]);
            if (text[i] == '{') {
                stack.push(text[i]);
            } else if (text[i] == '}') {
                stack.pop();
            }

            if (stack.isEmpty()) {
                if (sb.toString().startsWith("{")) {
                    result.add(sb.toString());
                }
                sb.setLength(0);
            }
        }
        return result;
    }
}
