package com.infamous.dungeons_gear.enchantments.melee_ranged;

import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.effects.CustomEffects;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.lists.MeleeRangedEnchantmentList;
import com.infamous.dungeons_gear.enchantments.types.AOEDamageEnchantment;
import com.infamous.dungeons_gear.enchantments.types.DamageBoostEnchantment;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.DYNAMO_DAMAGE_MULTIPLIER_PER_STACK;
import static com.infamous.dungeons_gear.config.DungeonsGearConfig.DYNAMO_MAX_STACKS;

@Mod.EventBusSubscriber(modid= MODID)
public class DynamoEnchantment extends DamageBoostEnchantment {

    public DynamoEnchantment() {
        super(Enchantment.Rarity.RARE, ModEnchantmentTypes.MELEE_RANGED, new EquipmentSlotType[]{
                EquipmentSlotType.MAINHAND});
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() ||
                (!(enchantment instanceof DamageEnchantment) && !(enchantment instanceof DamageBoostEnchantment) && !(enchantment instanceof AOEDamageEnchantment));
    }

    public int getMaxLevel() {
        return 3;
    }

    public static void handleAddDynamoEnchantment(PlayerEntity playerEntity) {
        ItemStack mainhand = playerEntity.getMainHandItem();
        if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.DYNAMO)) {
            int dynamoLevel = EnchantmentHelper.getItemEnchantmentLevel(MeleeRangedEnchantmentList.DYNAMO, mainhand);
            EffectInstance effectinstance1 = playerEntity.getEffect(CustomEffects.DYNAMO);
            int i = dynamoLevel;
            if (effectinstance1 != null) {
                i += effectinstance1.getAmplifier();
                playerEntity.removeEffectNoUpdate(CustomEffects.DYNAMO);
            } else {
                --i;
            }
            i = MathHelper.clamp(i, 0, DYNAMO_MAX_STACKS.get());
            EffectInstance effectinstance = new EffectInstance(CustomEffects.DYNAMO, 120000, i, false, false, true);
            playerEntity.addEffect(effectinstance);
        }
    }

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getEntity();
            ItemStack mainhand = playerEntity.getMainHandItem();
            if (ModEnchantmentHelper.hasEnchantment(mainhand, MeleeRangedEnchantmentList.DYNAMO)) {
                EffectInstance effectinstance = playerEntity.getEffect(CustomEffects.DYNAMO);
                if (effectinstance != null) {
                    int dynamoAmplifier = effectinstance.getAmplifier() + 1;
                    event.setAmount((float) (event.getAmount() * (1 + dynamoAmplifier * DYNAMO_DAMAGE_MULTIPLIER_PER_STACK.get())));
                    playerEntity.removeEffectNoUpdate(CustomEffects.DYNAMO);
                }
            }
        }
    }

}
