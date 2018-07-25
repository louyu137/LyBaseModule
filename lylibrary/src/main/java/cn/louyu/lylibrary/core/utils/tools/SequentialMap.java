package cn.louyu.lylibrary.core.utils.tools;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sdj003 on 2018/7/25.
 */

public class SequentialMap<K,V> extends AbstractMap<K,V> {
    private List<Pair<K,V>> list;
    public SequentialMap(){
        list=new ArrayList<Pair<K,V>>();
    }

    public SequentialMap(int initialCapacity){
        list=new ArrayList<Pair<K,V>>(initialCapacity);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        Iterator<Pair<K,V>> iterator=list.iterator();
        while (iterator.hasNext()){
            Pair<K,V> pair=iterator.next();
            if(pair.first.equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        Iterator<Pair<K,V>> iterator=list.iterator();
        while (iterator.hasNext()){
            Pair<K,V> pair=iterator.next();
            if(pair.second.equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object o) {
        Iterator<Pair<K,V>> iterator=list.iterator();
        while (iterator.hasNext()){
            Pair<K,V> pair=iterator.next();
            if(pair.first.equals(o)){
                return pair.second;
            }
        }
        return null;
    }

    public V get(int i) {
        return list.get(i).second;
    }


    @Override
    public V put(K f, V s) {
        for (int i=0;i<list.size();i++){
            if(list.get(i).first.equals(f)){
                list.remove(i);
                list.add(i,new Pair<K, V>(f,s));
                return s;
            }
        }
        list.add(new Pair<K, V>(f,s));
        return s;
    }

    @Override
    public V remove(Object o) {
        Iterator<Pair<K,V>> iterator=list.iterator();
        while (iterator.hasNext()){
            Pair<K,V> pair=iterator.next();
            if(pair.first.equals(o)){
                list.remove(pair);
                return pair.second;
            }
        }
        return null;
    }

    public V remove(int i) {
       return list.remove(i).second;
    }

    @Override
    public void putAll(@NonNull Map<? extends K, ? extends V> map) {
      for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()){
          list.add(new Pair<K, V>(entry.getKey(),entry.getValue()));
      }
    }

    @Override
    public void clear() {
        list.clear();
    }

    @NonNull
    @Override
    public Set<K> keySet() {
        Set<K> set=new HashSet<K>();
        Iterator<Pair<K,V>> iterator=list.iterator();
        while (iterator.hasNext()){
            Pair<K,V> pair=iterator.next();
            set.add(pair.first);
        }
        return set;
    }

    @NonNull
    @Override
    public Collection<V> values() {
        Collection<V> vs=new ArrayList<V>(list.size());
        Iterator<Pair<K,V>> iterator=list.iterator();
        while (iterator.hasNext()){
            Pair<K,V> pair=iterator.next();
            vs.add(pair.second);
        }
        return null;
    }

    @NonNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> set=new HashSet<Entry<K,V>>();
        Iterator<Pair<K,V>> iterator=list.iterator();
        while (iterator.hasNext()){
            Pair<K,V> pair=iterator.next();
            set.add(new SimpleEntry<K,V>(pair.first,pair.second));
        }
        return set;
    }
}
