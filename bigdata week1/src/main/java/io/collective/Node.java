package mycache;
public class Node<K, V>
{
    K key;
    V value;
    long retentionInMillis;
    Node(K key, V value, long retentionInMillis)
    {
        this.key = key;
        this.value = value;
        this.retentionInMillis = retentionInMillis;
    }
}