package io.collective;

import java.time.Clock;
import mycache.Cmap;
import mycache.MapFullException;
public class SimpleAgedCache {
    private Cmap<Object, Object> map;
    private Clock clock;
    public SimpleAgedCache(Clock clock) {
        this.map = new Cmap<Object, Object>(100);
        this.clock = clock;
    }

    public SimpleAgedCache() {
        this.map = new Cmap<Object, Object>(100);
        this.clock = Clock.systemUTC();
    }

    public void put(Object key, Object value, int retentionInMillis) {
        try
        {
            map.put(key, value, retentionInMillis + clock.millis());
        }
        catch(MapFullException e)
        {
            System.err.println("Error: " + e.getMessage());
        }
       
    }

    public boolean isEmpty() {
        map.cleanup(clock.millis());
        return 0 == map.size();
    }

    public int size() {
        map.cleanup(clock.millis());
        return map.size();
    }

    public Object get(Object key) {
        map.cleanup(clock.millis());
        return map.get(key);
    }


}