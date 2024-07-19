package mycache;
import mycache.Node;
import mycache.MapFullException;
public class Cmap<K, V>
{
    private Node<K, V>[] table;
    private int capacity;
    private int size;
    public Cmap(int capacity)
    {
        this.capacity = capacity;
        this.table = new Node[capacity];
        this.size = 0;
    }
    private int hash(K key)
    {
        return Math.abs(key.hashCode() % capacity);
    }
    public void put(K key, V value, long retentionInMillis)throws MapFullException
    {
        if(size >= capacity) throw new MapFullException("map is full, can not add more");
        int index = hash(key);
        while(null != this.table[index] && !this.table[index].key.equals(key))
        {
            index = (index + 1) % this.capacity;
        }
        table[index] = new Node<>(key, value, retentionInMillis);
        this.size++;
    }

    public V get(K key)
    {
        if (0 == this.size) return null;
        int index = hash(key);
        while(null != this.table[index])
        {
            if(this.table[index].key.equals(key))
            {
                return this.table[index].value;
            }
            index = (index + 1) % this.capacity;
        }
        return null;
    }
    public void remove(K key)
    {
        if(this.size == 0) return;
        int index = hash(key);
        while(null != this.table[index])
        {
            if(this.table[index].key.equals(key))
            {
                this.table[index] = null;
                this.size --;
                return;
            }
            index = (index + 1) % this.capacity;
        }
    }

    public int size()
    {
        return this.size;
    }

    public void cleanup(long mills)
    {
        if(0 == size()) return;
       
        for(int i = 0; i < this.capacity; ++i)
        {
            Node<K, V> node = this.table[i];
            if(null != node)
            {
                if(isexpired(mills, node))
                {
                    remove(node.key);
                }
            }
        }
    }

    private boolean isexpired(long mills, Node<K, V> node)
    {
        System.out.print("mills:");
        System.out.println(mills);
        System.out.print("retention:");
        System.out.println(node.retentionInMillis);
        return mills > node.retentionInMillis;
    }

}