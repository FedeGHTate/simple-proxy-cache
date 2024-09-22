package sharumaki.h.f;

import sharumaki.h.f.cache.CacheCacheServerImpl;
import sharumaki.h.f.cache.CacheServer;

public class Main {
    public static void main(String[] args) {
        CacheServer cacheServer = new CacheCacheServerImpl();

        CLI cli = new CLI(cacheServer);
        cli.start();
    }

}