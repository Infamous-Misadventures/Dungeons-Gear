package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.artifacts.ArtifactItem;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.HealthAbilityEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.CooldownTracker;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

@Mod.EventBusSubscriber(modid= MODID)
public class FinalShoutEnchantment extends HealthAbilityEnchantment {

    public FinalShoutEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof HealthAbilityEnchantment);
    }


    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent event){
        LivingEntity victim = event.getEntityLiving();
        if(victim != null && victim.isAlive()){
            if(victim instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity) victim;
                float currentHealth = player.getHealth();
                float maxHealth = player.getMaxHealth();
                float damageDealt = event.getAmount();
                if(currentHealth - damageDealt <= (0.25F * maxHealth)){
                    if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.FINAL_SHOUT)){
                        CooldownTracker cooldownTracker = player.getCooldownTracker();
                        for(Item item : cooldownTracker.cooldowns.keySet()){
                            if(item instanceof ArtifactItem){
                                cooldownTracker.cooldowns.remove(item);
                                Method notifyOnRemove = ObfuscationReflectionHelper.findMethod(CooldownTracker.class, "func_185146_c", Item.class);
                                try {
                                    notifyOnRemove.invoke(cooldownTracker, item);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    DungeonsGear.LOGGER.info("Reflection error trying to call CooldownTracker#notifyOnRemove!");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
