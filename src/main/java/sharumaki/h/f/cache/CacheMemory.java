package sharumaki.h.f.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CacheMemory {
    Map<String, String> memory = new HashMap<>();

    public Optional<String> getContent(String key) {
        String value = this.memory.get(key);

        if(value == null){
            return Optional.empty();
        }

        return Optional.of(value);

    }

    public void clearCache() {
        this.memory.clear();
    }

    public void add(String key, String value) {
        this.memory.put(key, value);
    }
}
