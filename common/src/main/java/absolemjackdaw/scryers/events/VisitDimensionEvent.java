package absolemjackdaw.scryers.events;

import absolemjackdaw.scryers.playerdata.ScryData;
import dev.upcraft.sparkweave.api.event.Event;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public interface VisitDimensionEvent {

    Event<VisitDimensionEvent> EVENT = Event.create(VisitDimensionEvent.class, listeners -> (player, data, newEntry) -> {
        for (var listener : listeners) {
            newEntry = listener.onVisitDimension(player, data, newEntry);
            if(newEntry == null) {
                return null;
            }
        }

        return newEntry;
    });

    /**
     * Fired when a player tries to record a new {@linkplain ScryData.TeleportTarget} into their history.
     * @return a possibly modified entry, or {@code null} to cancel the event entirely.
     */
    @Nullable
    ScryData.TeleportTarget onVisitDimension(ServerPlayer player, ScryData data, ScryData.TeleportTarget newEntry);
}
