package com.infamous.dungeons_gear.items.armor.models;

import com.infamous.dungeons_gear.items.armor.models.new_models.HungryHorrorArmorModel;
import com.infamous.dungeons_gear.items.armor.models.old_models.*;
import com.infamous.dungeons_libraries.items.gearconfig.client.ArmorGearModels;

import static com.infamous.dungeons_gear.registry.ItemRegistry.*;

public class ModArmorModels {

    public static void setupArmorModels(){
        ArmorGearModels.addModel(HUNTERS_ARMOR.get().getRegistryName(), HuntersArmorModel::new);
        ArmorGearModels.addModel(ARCHERS_ARMOR.get().getRegistryName(), ArchersArmorModel::new);
        ArmorGearModels.addModel(ARCHERS_ARMOR_HOOD.get().getRegistryName(), ArchersArmorModel::new);
        ArmorGearModels.addModel(BATTLE_ROBE.get().getRegistryName(), BattleRobeModel::new);
        ArmorGearModels.addModel(SPLENDID_ROBE.get().getRegistryName(), SplendidRobeModel::new);
        ArmorGearModels.addModel(DARK_ARMOR.get().getRegistryName(), DarkArmorModel::new);
        ArmorGearModels.addModel(DARK_ARMOR_HELMET.get().getRegistryName(), DarkArmorModel::new);
        ArmorGearModels.addModel(ROYAL_GUARD_ARMOR.get().getRegistryName(), DarkArmorModel::new);
        ArmorGearModels.addModel(ROYAL_GUARD_ARMOR_HELMET.get().getRegistryName(), DarkArmorModel::new);
        ArmorGearModels.addModel(TITANS_SHROUD.get().getRegistryName(), DarkArmorModel::new);
        ArmorGearModels.addModel(TITANS_SHROUD_HELMET.get().getRegistryName(), DarkArmorModel::new);
        ArmorGearModels.addModel(EVOCATION_ROBE.get().getRegistryName(), EvocationRobeModel::new);
        ArmorGearModels.addModel(EVOCATION_ROBE_HAT.get().getRegistryName(), EvocationRobeModel::new);
        ArmorGearModels.addModel(EMBER_ROBE.get().getRegistryName(), EvocationRobeModel::new);
        ArmorGearModels.addModel(EMBER_ROBE_HAT.get().getRegistryName(), EvocationRobeModel::new);
        ArmorGearModels.addModel(MERCENARY_ARMOR.get().getRegistryName(), MercenaryArmorModel::new);
        ArmorGearModels.addModel(MERCENARY_ARMOR_HELMET.get().getRegistryName(), MercenaryArmorModel::new);
        ArmorGearModels.addModel(RENEGADE_ARMOR.get().getRegistryName(), RenegadeArmorModel::new);
        ArmorGearModels.addModel(RENEGADE_ARMOR_HELMET.get().getRegistryName(), RenegadeArmorModel::new);
        ArmorGearModels.addModel(HUNGRY_HORROR_CHESTPLATE.get().getRegistryName(), HungryHorrorArmorModel::new);
        ArmorGearModels.addModel(HUNGRY_HORROR_HELMET.get().getRegistryName(), HungryHorrorArmorModel::new);
        ArmorGearModels.addModel(PHANTOM_ARMOR.get().getRegistryName(), PhantomArmorModel::new);
        ArmorGearModels.addModel(PHANTOM_ARMOR_HELMET.get().getRegistryName(), PhantomArmorModel::new);
        ArmorGearModels.addModel(FROST_BITE.get().getRegistryName(), PhantomArmorModel::new);
        ArmorGearModels.addModel(FROST_BITE_HELMET.get().getRegistryName(), PhantomArmorModel::new);
        ArmorGearModels.addModel(SCALE_MAIL.get().getRegistryName(), ScaleMailModel::new);
        ArmorGearModels.addModel(HIGHLAND_ARMOR.get().getRegistryName(), ScaleMailModel::new);
        ArmorGearModels.addModel(HIGHLAND_ARMOR_HELMET.get().getRegistryName(), ScaleMailModel::new);
        ArmorGearModels.addModel(EMERALD_GEAR.get().getRegistryName(), EmeraldArmorModel::new);
        ArmorGearModels.addModel(EMERALD_GEAR_HELMET.get().getRegistryName(), EmeraldArmorModel::new);
        ArmorGearModels.addModel(GILDED_GLORY.get().getRegistryName(), GildedGloryModel::new);
        ArmorGearModels.addModel(GILDED_GLORY_HELMET.get().getRegistryName(), GildedGloryModel::new);
        ArmorGearModels.addModel(OPULENT_ARMOR.get().getRegistryName(), OpulentArmorModel::new);
        ArmorGearModels.addModel(OPULENT_ARMOR_HELMET.get().getRegistryName(), OpulentArmorModel::new);
        ArmorGearModels.addModel(CHAMPIONS_ARMOR.get().getRegistryName(), ChampionsArmorModel::new);
        ArmorGearModels.addModel(CHAMPIONS_ARMOR_HELMET.get().getRegistryName(), ChampionsArmorModel::new);
        ArmorGearModels.addModel(HEROS_ARMOR.get().getRegistryName(), ChampionsArmorModel::new);
        ArmorGearModels.addModel(HEROS_ARMOR_HELMET.get().getRegistryName(), ChampionsArmorModel::new);
        ArmorGearModels.addModel(GRIM_ARMOR.get().getRegistryName(), GrimArmorModel::new);
        ArmorGearModels.addModel(GRIM_ARMOR_HELMET.get().getRegistryName(), GrimArmorModel::new);
        ArmorGearModels.addModel(WITHER_ARMOR.get().getRegistryName(), GrimArmorModel::new);
        ArmorGearModels.addModel(WITHER_ARMOR_HELMET.get().getRegistryName(), GrimArmorModel::new);
        ArmorGearModels.addModel(GUARDS_ARMOR.get().getRegistryName(), GuardsArmorModel::new);
        ArmorGearModels.addModel(GUARDS_ARMOR_HELMET.get().getRegistryName(), GuardsArmorModel::new);
        ArmorGearModels.addModel(CURIOUS_ARMOR.get().getRegistryName(), CuriousArmorModel::new);
        ArmorGearModels.addModel(CURIOUS_ARMOR_HELMET.get().getRegistryName(), CuriousArmorModel::new);
        ArmorGearModels.addModel(SNOW_ARMOR.get().getRegistryName(), SnowArmorModel::new);
        ArmorGearModels.addModel(SNOW_ARMOR_HELMET.get().getRegistryName(), SnowArmorModel::new);
        ArmorGearModels.addModel(FROST_ARMOR.get().getRegistryName(), FrostArmorModel::new);
        ArmorGearModels.addModel(FROST_ARMOR_HELMET.get().getRegistryName(), FrostArmorModel::new);
        ArmorGearModels.addModel(THIEF_ARMOR.get().getRegistryName(), ThiefArmorModel::new);
        ArmorGearModels.addModel(THIEF_ARMOR_HOOD.get().getRegistryName(), ThiefArmorModel::new);
        ArmorGearModels.addModel(SPIDER_ARMOR.get().getRegistryName(), ThiefArmorModel::new);
        ArmorGearModels.addModel(SPIDER_ARMOR_HOOD.get().getRegistryName(), ThiefArmorModel::new);
    }
}
