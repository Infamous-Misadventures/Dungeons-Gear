package com.infamous.dungeons_gear.loot;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.mixin.VillagerTradingManagerAccessor;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class TradeEventsReloader implements ISelectiveResourceReloadListener {


    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
//        event.addListener(new TradeEventsReloader());
    }

    @Override
    public void onResourceManagerReload(IResourceManager p_195410_1_, Predicate<IResourceType> resourcePredicate) {
        DungeonsGear.LOGGER.info("Beginning retriggering trader events");
        VillagerTradingManagerAccessor.invokePostWandererEvent();
        VillagerTradingManagerAccessor.invokePostVillagerEvents();
        DungeonsGear.LOGGER.info("Finished retriggering trader events");
    }
}
