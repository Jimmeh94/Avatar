package avatar.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Manager<T> {
    // ---------------------------------------------------
    protected List<T> objects = new ArrayList<>();

    public void add(T object){
        objects.add(object);
    }

    public void addAndReplace(T object){
        Optional<T> remove = objects.stream().filter(t -> t.getClass().equals(object.getClass())).findFirst();

        if(remove.isPresent()){
            objects.remove(remove.get());
        }

        objects.add(object);
    }

    public void remove(T object){
        objects.remove(object);
    }
}
