package avatar.data.tags;

import avatar.data.Keys;
import avatar.util.misc.Callback;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class ConfirmationButtonData extends AbstractSingleData<Callback, ConfirmationButtonData, ConfirmationButtonData.Immutable> {

    protected ConfirmationButtonData(Callback value) {
        super(value, Keys.CONFIRMATION_MENU_BUTTON);
    }

    protected Value<Callback> getCallback() {
        return getValueGetter();
    }

    @Override
    protected Value<Callback> getValueGetter(){
        return Sponge.getRegistry().getValueFactory().createValue(Keys.CONFIRMATION_MENU_BUTTON, getValue());
    }

    @Override
    public Optional<ConfirmationButtonData> fill(DataHolder dataHolder, MergeFunction mergeFunction) {
        Optional<ConfirmationButtonData> custom = dataHolder.get(ConfirmationButtonData.class);
        if (custom.isPresent()) {
            ConfirmationButtonData offenseData = custom.get();
            ConfirmationButtonData finalOffenseData = mergeFunction.merge(this, offenseData);
            this.setValue(finalOffenseData.getValue());
        }
        return Optional.of(this);
    }

    @Override
    public Optional<ConfirmationButtonData> from(DataContainer dataContainer) {
        if (dataContainer.contains(Keys.CONFIRMATION_MENU_BUTTON.getQuery())) {
            return Optional.of(set(Keys.CONFIRMATION_MENU_BUTTON, (Callback) dataContainer.get(Keys.CONFIRMATION_MENU_BUTTON.getQuery()).orElse(getValue())));
        }
        return Optional.empty();
    }

    @Override
    public ConfirmationButtonData copy() {
        return new ConfirmationButtonData(this.getValue());
    }

    @Override
    public Immutable asImmutable() {
        return new Immutable(this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    public static class Immutable extends AbstractImmutableSingleData<Callback, Immutable, ConfirmationButtonData> {

        protected Immutable(Callback value) {
            super(value, Keys.CONFIRMATION_MENU_BUTTON);
        }

        @Override
        protected ImmutableValue<?> getValueGetter() {
            return Sponge.getRegistry().getValueFactory().createValue(Keys.CONFIRMATION_MENU_BUTTON, getValue()).asImmutable();
        }

        @Override
        public ConfirmationButtonData asMutable() {
            return new ConfirmationButtonData(this.getValue());
        }

        @Override
        public int getContentVersion() {
            return 1;
        }
    }

    public static class Builder extends AbstractDataBuilder<ConfirmationButtonData> implements DataManipulatorBuilder<ConfirmationButtonData, Immutable> {


        public Builder(Class<ConfirmationButtonData> requiredClass, int supportedVersion) {
            super(requiredClass, supportedVersion);
        }

        @Override
        public ConfirmationButtonData create() {
            Callback callback = () -> false;
            return new ConfirmationButtonData(callback);
        }

        public Optional<ConfirmationButtonData> createFrom(Callback string) {
            return Optional.of(new ConfirmationButtonData(string));
        }

        @Override
        public Optional<ConfirmationButtonData> createFrom(DataHolder dataHolder) {
            return create().fill(dataHolder);
        }

        @Override
        protected Optional<ConfirmationButtonData> buildContent(DataView container) throws InvalidDataException {
            if (!container.contains(Keys.CONFIRMATION_MENU_BUTTON.getQuery())) {
                return Optional.empty();
            }

            Callback offense = (Callback) container.get(Keys.CONFIRMATION_MENU_BUTTON.getQuery()).get();

            return Optional.of(new ConfirmationButtonData(offense));
        }
    }
}
