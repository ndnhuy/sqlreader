package icashiercore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/config")
    public String index() {
        return "hello";
    }

    @GetMapping("/all")
    public Map<String, String> getAll() {
        return configService.getAll();
    }

    @GetMapping("/search")
    public Map<String, String> search(@RequestParam("text") String text) {
        return configService.search(text);
    }
}
