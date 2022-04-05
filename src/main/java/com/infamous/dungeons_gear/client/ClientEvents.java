package com.infamous.dungeons_gear.client;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.items.armor.FreezingResistanceArmorGear;
import com.infamous.dungeons_gear.items.artifacts.beacon.AbstractBeaconItem;
import com.infamous.dungeons_gear.items.artifacts.beacon.BeaconBeamRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void handleToolTip(ItemTooltipEvent event) {
        List<ITextComponent> tooltip = event.getToolTip();
        int index = 0;
        Item item = event.getItemStack().getItem();
        if (item instanceof FreezingResistanceArmorGear) {
            FreezingResistanceArmorGear armor = (FreezingResistanceArmorGear)item;
            // DOUBLE OR INT
            if(armor.getFreezingResistance() > 0){
                tooltip.add(index + 1, new TranslationTextComponent(
                        "+" + armor.getFreezingResistance() + "% ")
                        .append(new TranslationTextComponent(
                                "attribute.name.freezingResistance"))
                        .withStyle(TextFormatting.GREEN));
            }
        }
    }

// borrowed from direwolf20's MiningGadget mod
    @SubscribeEvent
    public static void renderWorldLastEvent(RenderWorldLastEvent event) {
        List<AbstractClientPlayerEntity> players = null;
        if (Minecraft.getInstance().level != null) {
            players = Minecraft.getInstance().level.players();

            PlayerEntity myplayer = Minecraft.getInstance().player;
            if(myplayer != null){
                for (PlayerEntity player : players) {
                    if (player.distanceToSqr(myplayer) > 500)
                        continue;


                    ItemStack heldItem = AbstractBeaconItem.getBeacon(player);
                    if (player.isUsingItem()
                            && heldItem.getItem() instanceof AbstractBeaconItem
                            && ((AbstractBeaconItem)heldItem.getItem()).canFire(player, heldItem)) {
                            BeaconBeamRenderer.renderBeam(event, player, Minecraft.getInstance().getFrameTime());
                    }
                }
            }
        }
    }
}
