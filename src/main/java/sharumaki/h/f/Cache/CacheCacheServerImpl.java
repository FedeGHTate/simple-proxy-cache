package sharumaki.h.f.Cache;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class CacheCacheServerImpl implements CacheServer {

    private final int port;
    CacheMemory cacheMemory = new CacheMemory();

    public CacheCacheServerImpl(int port) {
        this.port = port;
    }

    public void start() {

        Thread thread = new Thread(() -> {
            HttpServer server;

            try{
                server = HttpServer.create(new InetSocketAddress(this.port),0);
                server.createContext("/products", new ProductsHandler("/products",this.cacheMemory));

                server.setExecutor(null);

                System.out.println("Starting server...");
                server.start();
                System.out.println("Server up!");
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
}
