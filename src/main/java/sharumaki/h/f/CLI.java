package sharumaki.h.f;

import sharumaki.h.f.cache.CacheCacheServerImpl;
import sharumaki.h.f.cache.CacheServer;
import sharumaki.h.f.commands.CLICommand;
import sharumaki.h.f.commands.ClearCacheCommand;
import sharumaki.h.f.commands.StartCachingServerCommand;

import java.util.*;

public class CLI {


    private final CacheServer server;
    private final CLICommand clearCacheCommand;
    private CLICommand startCachingServerCommand;

    public CLI(CacheServer cacheServerImpl) {
        this.server = cacheServerImpl;
        this.clearCacheCommand = new ClearCacheCommand(cacheServerImpl);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(">CLI Initialized");

        while(true) {
            System.out.print(">");
            String input = scanner.nextLine();
            List<String> parts = Arrays.stream(input.split(" ")).toList();

            if(parts.get(0).equalsIgnoreCase("caching-proxy")) {

                if(parts.contains("--port") && parts.contains("--origin")) {

                    var port = Integer.parseInt(getParam(parts, "--port"));
                    var origin = getParam(parts, "--origin");

                    if(startCachingServerCommand == null) {
                        this.startCachingServerCommand = new StartCachingServerCommand(server,port,origin);
                    }
                    startCachingServerCommand.execute();

                } else if ("--clear-cache".equalsIgnoreCase(parts.get(1)) && this.server.isOn()) {
                    clearCacheCommand.execute();
                } else {
                    System.out.println(">Please start the server");
                }


            } else {
                System.out.println(">Unknown command");
            }
        }
    }

    private String getParam(List<String> argsList, String paramName) {

        int index = argsList.indexOf(paramName);

        if(index + 1 > argsList.size() || index == -1) {
            throw new IllegalArgumentException();
        }

        return argsList.get(index + 1);
    }
}
