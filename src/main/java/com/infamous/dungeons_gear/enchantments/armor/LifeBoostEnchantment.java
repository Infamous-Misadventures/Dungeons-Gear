package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.DungeonsEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = DungeonsGear.MODID)
public class LifeBoostEnchantment extends DungeonsEnchantment {
    private static final UUID LIFE_BOOST = UUID.fromString("ddea725c-1b2e-432e-b96d-871526e69606");

    public LifeBoostEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, ModEnchantmentTypes.ARMOR_SLOT);
    }

    public int getMaxLevel() {
        return 3;
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.Clone event){
        PlayerEntity oldPlayer = event.getOriginal();
        DamageSource lastDamageSource = oldPlayer.getLastDamageSource();
        if(lastDamageSource != null
                && (lastDamageSource.getTrueSource() == null || lastDamageSource.getTrueSource() == oldPlayer)) return;
        // must have been killed by another entity to receive the life boost

        ModifiableAttributeInstance oldMaxHealth = oldPlayer.getAttribute(Attributes.MAX_HEALTH);
        double oldLifeBoostValue = 0;
        if(oldMaxHealth != null){
            AttributeModifier oldLifeBoost = oldMaxHealth.getModifier(LIFE_BOOST);
            if(oldLifeBoost != null){
                oldLifeBoostValue = oldLifeBoost.getAmount();
            }
        }

        PlayerEntity newPlayer = event.getPlayer();
        int lifeBoostLevel = EnchantmentHelper.getMaxEnchantmentLevel(ArmorEnchantmentList.LIFE_BOOST, oldPlayer);
        if(lifeBoostLevel > 0){
            ModifiableAttributeInstance newMaxHealth = newPlayer.getAttribute(Attributes.MAX_HEALTH);
            if (newMaxHealth != null) {
                newMaxHealth.applyPersistentModifier(new AttributeModifier(LIFE_BOOST, "life boost addition", (2.0D * lifeBoostLevel) + oldLifeBoostValue, AttributeModifier.Operation.ADDITION));
            }
        }
    }
}
