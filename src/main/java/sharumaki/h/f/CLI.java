package sharumaki.h.f;

import sharumaki.h.f.Cache.CacheCacheServerImpl;
import sharumaki.h.f.Cache.CacheServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLI {

    CacheServer server;
    public CLI(CacheServer cacheServerImpl) {
        this.server = cacheServerImpl;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            String input = scanner.nextLine();

            if ("caching-proxy close".equalsIgnoreCase(input)) {
                System.out.println("Closing...");
                break;
            } else if ("caching-proxy --clear-cache".equalsIgnoreCase(input)) {
                server.clear();
            } else {
                System.out.println("Unknown command");
            }

        }
    }
}
