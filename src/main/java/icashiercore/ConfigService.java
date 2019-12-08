package icashiercore;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
@JsonPropertyOrder
public class ConfigService {

    private static Set<String> TABLE_NAMES = new HashSet<>(Arrays.asList("ipd_channel_decision_rule"));

    public Map<String, String> getAll() {
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

            return result;
        } catch (IOException ex) {
            throw new RuntimeException("Error while reading files");
        }
    }

    public Map<String, String> search(String text) {
        Map<String, String> all = getAll();
        return all.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> newContent(entry.getValue(), text)));
    }

    private String newContent(String content, String searchText) {
        JSONArray arr = new JSONArray(content);
        JSONArray newArr = new JSONArray();
        for (Object o : arr) {
            JSONObject row = (JSONObject) o;
            if (row.toString().toUpperCase().contains(searchText.toUpperCase())) {
                newArr.put(row);
            }
        }
        return newArr.toString();
    }
}
