package avatar.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Manager<T> {

    private static List<Manager> managers = new ArrayList<>();

    public static Manager getManager(ManagerKey key){
        for(Manager manager: managers){
            if(manager.getKey() == key)
                return manager;
        }
        return null;
    }

    // ---------------------------------------------------
    protected List<T> objects = new ArrayList<>();
    private ManagerKey key;

    public Manager(ManagerKey key){
        this.key = key;
        managers.add(this);
    }

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

    public ManagerKey getKey() {
        return key;
    }

    public enum ManagerKey{
        USER,
        AREA
    }
}
