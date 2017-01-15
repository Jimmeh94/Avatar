package avatar.data;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

public class Keys {

    public static final Key<Value<String>> QUEST_ID = KeyFactory.makeSingleKey(
            TypeToken.of(String.class), new TypeToken<Value<String>>() {}, DataQuery.of("QuestID"), "avatar:questid", "Quest ID");
}
