package avatar.data.tags;

import avatar.data.Keys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class QuestData extends AbstractSingleData<String, QuestData, QuestData.Immutable> {

    protected QuestData(String value) {
        super(value, Keys.QUEST_ID);
    }

    protected Value<String> getString() {
        return getValueGetter();
    }

    @Override
    protected Value<String> getValueGetter(){
        return Sponge.getRegistry().getValueFactory().createValue(Keys.QUEST_ID, getValue());
    }

    @Override
    public Optional<QuestData> fill(DataHolder dataHolder, MergeFunction mergeFunction) {
        Optional<QuestData> custom = dataHolder.get(QuestData.class);
        if (custom.isPresent()) {
            QuestData offenseData = custom.get();
            QuestData finalOffenseData = mergeFunction.merge(this, offenseData);
            this.setValue(finalOffenseData.getValue());
        }
        return Optional.of(this);
    }

    @Override
    public Optional<QuestData> from(DataContainer dataContainer) {
        if (dataContainer.contains(Keys.QUEST_ID.getQuery())) {
            return Optional.of(set(Keys.QUEST_ID, (String) dataContainer.get(Keys.QUEST_ID.getQuery()).orElse(getValue())));
        }
        return Optional.empty();
    }

    @Override
    public QuestData copy() {
        return new QuestData(this.getValue());
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    public static class Immutable extends AbstractImmutableSingleData<String, Immutable, QuestData> {

        protected Immutable(String value) {
            super(value, Keys.QUEST_ID);
        }

        @Override
        protected ImmutableValue<?> getValueGetter() {
            return Sponge.getRegistry().getValueFactory().createValue(Keys.QUEST_ID, getValue()).asImmutable();
        }

        @Override
        public QuestData asMutable() {
            return new QuestData(this.getValue());
        }

        @Override
        public int getContentVersion() {
            return 1;
        }
    }
}
