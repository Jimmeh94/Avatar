package avatar.managers;

import java.util.ArrayList;
import java.util.List;

public abstract class Manager<T> {

    protected List<T> objects = new ArrayList<>();

    public void add(T object){
        objects.add(object);
    }

    public void addAndReplace(T object){
        T remove = null;
        for(T t: objects){
            if(t.getClass() == object.getClass()){
                remove = t;
                break;
            }
        }

        if(remove != null){
            objects.remove(remove);
        }

        objects.add(object);
    }

    public void remove(T object){
        objects.remove(object);
    }

    public boolean has(T object){
        return objects.contains(object);
    }
}
