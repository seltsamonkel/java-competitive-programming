package hash;
import java.util.Collection;
import java.util.HashMap;

public class HashSetCollection<K, V, C extends Collection<V>> {
    private HashMap<K, C> map;
    private Class cClass;

    public HashSetCollection(C instance) {
        map = new HashMap<>();
        this.cClass = instance.getClass();
    }

    public C get(K key) {
            return map.get(key);
    }

    public void add(K key, V value) {
            C list = map.getOrDefault(key, getInstance());
            list.add(value);
            map.put(key, list);
    }

    public int size() {
            return map.size();
    }

    public int size(K key) {
            return get(key).size();
    }

    public boolean containsKey(K key) {
            return map.containsKey(key);
    }

    public boolean containsValue(K key, V value) {
        return map.containsKey(key) && map.get(key).contains(value);
    }

    public C removeKey(K key) {
            return map.remove(key);
    }

    public boolean removeValue(K key, V value) {
        C collection = map.get(key);
        if (collection  == null) {
            return false;
        }
        boolean found = collection.remove(value);
        if (found && collection.size() == 0) {
            removeKey(key);
        }
        return found;
    }

    private C getInstance() {
        try {
            return  (C)cClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}