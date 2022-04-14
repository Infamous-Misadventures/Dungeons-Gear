package com.infamous.dungeons_gear.enchantments.armor;

import com.infamous.dungeons_gear.capabilities.combo.ICombo;
import com.infamous.dungeons_gear.config.DungeonsGearConfig;
import com.infamous.dungeons_gear.enchantments.ModEnchantmentTypes;
import com.infamous.dungeons_gear.enchantments.types.DropsEnchantment;
import com.infamous.dungeons_gear.utilties.CapabilityHelper;
import com.infamous.dungeons_gear.utilties.ModEnchantmentHelper;
import com.infamous.dungeons_gear.enchantments.lists.ArmorEnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.DungeonsGear.MODID;

import net.minecraft.enchantment.Enchantment.Rarity;

@Mod.EventBusSubscriber(modid= MODID)
public class RecyclerEnchantment extends DropsEnchantment {

    public RecyclerEnchantment() {
        super(Rarity.RARE, ModEnchantmentTypes.ARMOR, new EquipmentSlotType[]{
                EquipmentSlotType.HEAD,
                EquipmentSlotType.CHEST,
                EquipmentSlotType.LEGS,
                EquipmentSlotType.FEET});
    }

    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean checkCompatibility(Enchantment enchantment) {
        return DungeonsGearConfig.ENABLE_OVERPOWERED_ENCHANTMENT_COMBOS.get() || !(enchantment instanceof DropsEnchantment);
    }


    @SubscribeEvent
    public static void onPlayerDamaged(LivingDamageEvent event){
        if(!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if(player.isAlive()){
            if(event.getSource().getDirectEntity() instanceof AbstractArrowEntity){
                ICombo comboCap = CapabilityHelper.getComboCapability(player);
                if(comboCap == null) return;
                if(ModEnchantmentHelper.hasEnchantment(player, ArmorEnchantmentList.RECYCLER)){
                    int arrowsInCounter = comboCap.getArrowsInCounter();
                    arrowsInCounter++;
                    comboCap.setArrowsInCounter(arrowsInCounter);

                    int recyclerLevel = EnchantmentHelper.getEnchantmentLevel(ArmorEnchantmentList.RECYCLER, player);
                    if(comboCap.getArrowsInCounter() >= 40 - 7*recyclerLevel){
                        ItemEntity arrowDrop = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), new ItemStack(Items.ARROW, 10));
                        player.level.addFreshEntity(arrowDrop);
                        comboCap.setArrowsInCounter(0);
                    }
                }
            }
        }
    }
}
