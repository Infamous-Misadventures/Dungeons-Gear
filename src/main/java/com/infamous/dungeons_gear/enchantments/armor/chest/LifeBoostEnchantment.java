package com.infamous.dungeons_gear.enchantments.armor.chest;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes.ARMOR_SLOT;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class LifeBoostEnchantment extends DungeonsEnchantment {
    private static final UUID LIFE_BOOST = UUID.fromString("ddea725c-1b2e-432e-b96d-871526e69606");

    public LifeBoostEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_CHEST, ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.Clone event){
        Player oldPlayer = event.getOriginal();
        DamageSource lastDamageSource = oldPlayer.getLastDamageSource();
        if(lastDamageSource != null
                && (lastDamageSource.getEntity() == null || lastDamageSource.getEntity() == oldPlayer)) return;
        // must have been killed by another entity to receive the life boost

        AttributeInstance oldMaxHealth = oldPlayer.getAttribute(Attributes.MAX_HEALTH);
        double oldLifeBoostValue = 0;
        if(oldMaxHealth != null){
            AttributeModifier oldLifeBoost = oldMaxHealth.getModifier(LIFE_BOOST);
            if(oldLifeBoost != null){
                oldLifeBoostValue = oldLifeBoost.getAmount();
            }
        }

        Player newPlayer = event.getPlayer();
        int lifeBoostLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.LIFE_BOOST, oldPlayer);
        if(lifeBoostLevel > 0){
            AttributeInstance newMaxHealth = newPlayer.getAttribute(Attributes.MAX_HEALTH);
            if (newMaxHealth != null) {
                newMaxHealth.addPermanentModifier(new AttributeModifier(LIFE_BOOST, "life boost addition", (2.0D * lifeBoostLevel) + oldLifeBoostValue, AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
