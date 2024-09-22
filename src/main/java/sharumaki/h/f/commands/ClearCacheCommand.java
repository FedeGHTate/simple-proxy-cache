package sharumaki.h.f.commands;

import sharumaki.h.f.cache.CacheServer;

public class ClearCacheCommand implements CLICommand{

    CacheServer cacheServer;

    public ClearCacheCommand(CacheServer cacheServer) {
        this.cacheServer = cacheServer;
    }

    @Override
    public void execute() {
        if(cacheServer.isOn()) {
            cacheServer.clear();
        } else {
            System.out.println("The server is disabled. Please start it");
        }
    }
}
