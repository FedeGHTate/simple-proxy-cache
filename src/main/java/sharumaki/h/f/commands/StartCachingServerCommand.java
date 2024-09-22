package sharumaki.h.f.commands;

import sharumaki.h.f.cache.CacheServer;

public class StartCachingServerCommand implements CLICommand{

    private CacheServer cacheServer;
    private int port;


    private String origin;

    public StartCachingServerCommand(CacheServer cacheServer, int port, String origin) {
        this.cacheServer = cacheServer;
        this.port = port;
        this.origin = origin;
    }

    @Override
    public void execute() {

        if(!cacheServer.isOn()) {
            cacheServer.start(port, origin);
        } else {
            System.out.println("The server is already running.");
        }
    }
}
