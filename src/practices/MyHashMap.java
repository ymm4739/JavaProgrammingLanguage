package practices;

import java.util.*;
import java.util.Set;

public class MyHashMap<K, V> implements MyMap {
    private LinkedList<MyMap.Entry<K, V>>[] table;
    private int entryNumbers;
    private int capacity;
    private int filledCount;
    private final int INIT_CAPACITY= 16;
    private final float LOAD_FACTOR_THROD = 0.9f;
    public MyHashMap(){
        capacity = INIT_CAPACITY;
        table = new LinkedList[capacity];
        filledCount = 0;
        for(int i = 0; i < capacity; i++){
            table[i] = null;
        }
    }

    private int hash(int hashCode){
        return hashCode & (capacity - 1);
    }

    private int getIndex(K key){
        return hash(key.hashCode());
    }

    private LinkedList<MyMap.Entry<K, V>> getLinkedList(K key){
        return table[getIndex(key)];
    }

    private boolean isNeedHash(){
        return filledCount > capacity * LOAD_FACTOR_THROD;
    }

    @Override
    public int size() {
        return entryNumbers;
    }

    @Override
    public boolean isEmpty() {
        return entryNumbers == 0;
    }

    @Override
    public boolean containsKey(Object key) {

        if(key == null){
            return false;
        }
        Set<K> set = keySet();
        return set.contains(key);


    }

    @Override
    public boolean containsValue(Object value) {
        return keySet().contains(value);
    }

    @Override
    public void clear() {
        for (LinkedList<MyMap.Entry<K, V>> linkedList:table
             ) {
            linkedList = null;
            entryNumbers = 0;
            capacity = INIT_CAPACITY;
        }
    }

    @Override
    public Object get(Object key) {
        if(key == null){
            return null;
        }
        LinkedList<MyMap.Entry<K, V>> linkedList = getLinkedList((K)key);
        if(linkedList == null){
            return null;
        }
        for (MyMap.Entry<K, V> entry:linkedList
        ) {
            if(entry.getKey().equals(key)){
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        if(key == null){
            return null;
        }
        LinkedList<MyMap.Entry<K, V>> linkedList = getLinkedList((K) key);
        if(linkedList == null){
            linkedList = new LinkedList<>();
            MyMap.Entry<K, V> entry = new MyMap.Entry<K, V>((K)key, (V)value);
            linkedList.addLast(entry);
            table[getIndex((K)key)] =  linkedList;
        }
        for (MyMap.Entry entry:linkedList
             ) {
            if(entry.getKey().equals(key)){
                entry.setValue((V)value);
                return value;
            }
        }
        linkedList.addLast(new MyMap.Entry<K, V>((K)key, (V)value));
        return value;
    }

    @Override
    public Object remove(Object key) {
        if(key == null){
            return null;
        }
        LinkedList<MyMap.Entry<K, V>> linkedList  = getLinkedList((K)key);
        if(linkedList == null){
            return null;
        }
        for (MyMap.Entry<K, V> entry:linkedList
             ) {
            if(entry.getKey().equals(key)){
                linkedList.remove(entry);
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void putAll(MyMap m) {
        Set<MyMap.Entry<K, V>> entries = entrySet();
        for (MyMap.Entry<K, V> entry:entries
             ) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Set keySet() {
        Set<MyMap.Entry<K, V>> set = entrySet();
        Set<K> result = new HashSet<>();
        for (MyMap.Entry<K, V> entry:set
        ) {
            result.add(entry.getKey());
        }
        return result;
    }

    @Override
    public Collection values() {
        Set<MyMap.Entry<K, V>> set = entrySet();
        Collection<V> result = new ArrayList<>();
        for (MyMap.Entry<K, V> entry:set
             ) {
            result.add(entry.getValue());
        }
        return result;
    }

    @Override
    public Set<MyMap.Entry<K,V>> entrySet() {
        Set<MyMap.Entry<K, V>> set = new HashSet<Entry<K, V>>();
        for (LinkedList<MyMap.Entry<K, V>> linkedList:table
             ) {
            for (MyMap.Entry<K, V> entry:linkedList
                 ) {
                set.add(entry);
            }
        }
        return set;
    }
}