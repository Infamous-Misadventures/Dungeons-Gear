package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.registry.EnchantmentInit;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.registry.MobEffectInit;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.DYNAMO_DAMAGE_MULTIPLIER_PER_STACK;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.DYNAMO_MAX_STACKS;

@Mod.EventBusSubscriber(modid= MODID)
public class DynamoEnchantment extends DamageBoostEnchantment {

    public DynamoEnchantment() {
        super(Enchantment.Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (!(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment));
    }

    public int getMaxLevel() {
        return 3;
    }

    public static void handleAddDynamoEnchantment(Player playerEntity) {
        ItemStack mainhand = playerEntity.getMainHandItem();
        if (ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.DYNAMO.get())) {
            int dynamoLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentInit.DYNAMO.get(), mainhand);
            MobEffectInstance currentEffectInstance = playerEntity.getEffect(MobEffectInit.DYNAMO.get());
            int i = dynamoLevel;
            if (currentEffectInstance != null) {
                i += currentEffectInstance.getAmplifier();
            }
            i = Mth.clamp(i, 0, DYNAMO_MAX_STACKS.get());
            MobEffectInstance effectinstance = new MobEffectInstance(MobEffectInit.DYNAMO.get(), 120000, i - 1);
            playerEntity.addEffect(effectinstance);
        }
    }

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player) {
            Player playerEntity = (Player) event.getSource().getEntity();
            ItemStack mainhand = playerEntity.getMainHandItem();
            if (ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.DYNAMO.get())) {
                MobEffectInstance effectinstance = playerEntity.getEffect(MobEffectInit.DYNAMO.get());
                if (effectinstance != null) {
                    int dynamoAmplifier = effectinstance.getAmplifier() + 1;
                    event.setAmount((float) (event.getAmount() * (1 + dynamoAmplifier * DYNAMO_DAMAGE_MULTIPLIER_PER_STACK.get())));
                    playerEntity.removeEffect(MobEffectInit.DYNAMO.get());
                }
            }
        }
    }

}
