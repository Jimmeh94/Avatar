package avatar.managers;

import avatar.Avatar;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.Order;

public class ListenerManager {

    //So far all classes that need to be manually unregistered are:
    //Ability properties
    //Quest conditions if not being deactivated()

    public static void register(Class<? extends Event> clazz, EventListener eventListener){
        Sponge.getEventManager().registerListener(Avatar.INSTANCE, clazz, eventListener);
    }

    public static void register(Class<? extends Event> clazz, Order order, EventListener eventListener){
        Sponge.getEventManager().registerListener(Avatar.INSTANCE, clazz, order, eventListener);
    }

    public static void unregister(EventListener eventListener){
        Sponge.getEventManager().unregisterListeners(eventListener);
    }

}
