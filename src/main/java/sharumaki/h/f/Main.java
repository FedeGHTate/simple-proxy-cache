package sharumaki.h.f;

import sharumaki.h.f.Cache.CacheCacheServerImpl;
import sharumaki.h.f.Cache.CacheServer;

public class Main {
    public static void main(String[] args) {


        ArgsManager argsManager = new ArgsManager(args);

        int PORT = argsManager.getValue("p") == null ? 3000 : Integer.parseInt(argsManager.getValue("p"));
        String ORIGIN = argsManager.getValue("origin");

        CacheServer cacheServer = new CacheCacheServerImpl(PORT);
        cacheServer.start();

        CLI cli = new CLI(cacheServer);
        cli.start();
    }

}