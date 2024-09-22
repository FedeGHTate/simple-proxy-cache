package sharumaki.h.f.cache;

public interface CacheServer {
    public void start(int port, String origin);
    public void clear();

    public boolean isOn();
}
