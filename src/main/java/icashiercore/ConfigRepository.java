package icashiercore;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ConfigRepository {

    private static Set<String> TABLE_NAMES = new HashSet<>(Arrays.asList("ipd_channel_decision_rule"));

    public JSONObject getAll() {
        try {
            String repoPath = "/Users/administrator/huynguyen/repos/icashiercoreConf";
            List<Path> tablePaths = Files.list(Paths.get(repoPath))
                 .filter(Files::isRegularFile)
                .filter(path -> TABLE_NAMES.stream().anyMatch(tableName -> path.toString().endsWith(tableName + ".json")))
                .collect(Collectors.toList());

            JSONObject result = new JSONObject();
            for (Path p : tablePaths) {
                String content = new String(Files.readAllBytes(p));
                result.put(p.getFileName().toString(), new JSONArray(content));
            }

            return result;
        } catch (IOException ex) {
            throw new RuntimeException("Error while reading files");
        }
    }
}
