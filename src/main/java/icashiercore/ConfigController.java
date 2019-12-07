package icashiercore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    private ConfigRepository configRepository;

    @GetMapping("/config")
    public String index() {
        return "hello";
    }

    @GetMapping("/all")
    public String getAll() {
        return configRepository.getAll().toString();
    }
}
