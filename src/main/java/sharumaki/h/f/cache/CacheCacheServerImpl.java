package sharumaki.h.f.cache;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class CacheCacheServerImpl implements CacheServer {

    CacheMemory cacheMemory = new CacheMemory();

    private final String resource = "/products";
    private boolean status = false;

    public void start(int port, String origin) {

        Thread thread = new Thread(() -> {
            HttpServer server;

            try{
                server = HttpServer.create(new InetSocketAddress(port),0);
                server.createContext("/", new GenericHandler(origin,this.cacheMemory));

                server.setExecutor(null);

                System.out.println("Starting server...");
                server.start();
                this.status = true;
                System.out.println(">Server up!");
            } catch (Exception e) {
                System.out.println("Error to create the server: " + e.getMessage());
            }
        });

        thread.start();

    }

    public void clear() {
        System.out.println("Clearing...");
        cacheMemory.clearCache();
        System.out.println("Clear");
    }

    @Override
    public boolean isOn() {
        return status;
    }
}
