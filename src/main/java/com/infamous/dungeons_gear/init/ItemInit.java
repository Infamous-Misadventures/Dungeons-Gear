package com.infamous.dungeons_gear.init;

import com.infamous.dungeons_gear.DungeonsGear;
import com.infamous.dungeons_gear.armor.*;
import com.infamous.dungeons_gear.artifacts.*;
import com.infamous.dungeons_gear.artifacts.corruptedbeacon.CorruptedBeaconItem;
import com.infamous.dungeons_gear.melee.*;
import com.infamous.dungeons_gear.items.ArmorList;
import com.infamous.dungeons_gear.items.ArtifactList;
import com.infamous.dungeons_gear.items.ToolMaterialList;
import com.infamous.dungeons_gear.items.WeaponList;
import com.infamous.dungeons_gear.ranged.bows.*;
import com.infamous.dungeons_gear.ranged.crossbows.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.infamous.dungeons_gear.items.ArmorList.*;
import static com.infamous.dungeons_gear.items.ArmorMaterialList.*;
import static com.infamous.dungeons_gear.items.ArtifactList.*;
import static com.infamous.dungeons_gear.items.ArtifactList.WONDERFUL_WHEAT;
import static com.infamous.dungeons_gear.items.RangedWeaponList.*;
import static com.infamous.dungeons_gear.items.RangedWeaponList.SOUL_CROSSBOW;
import static com.infamous.dungeons_gear.items.WeaponList.*;
import static com.infamous.dungeons_gear.items.WeaponList.DAGGER;

public class ItemInit {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            itemRegistryEvent.getRegistry().registerAll(

                    // DUAL WIELD
                    WeaponList.DAGGER = new DaggerItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("dagger")),
                    WeaponList.FANG_OF_FROST = new DaggerItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("fang_of_frost")),
                    WeaponList.MOON_DAGGER = new DaggerItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("moon_dagger")),

                    WeaponList.SICKLE = new SickleItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 2, (1.6f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("sickle")),
                    WeaponList.NIGHTMARES_BITE = new SickleItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 2, (1.6f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("nightmares_bite")),
                    WeaponList.THE_LAST_LAUGH = new SickleItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 2, (1.6f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("the_last_laugh")),

                    WeaponList.GAUNTLET = new GauntletItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("gauntlet")),
                    WeaponList.FIGHTERS_BINDING = new GauntletItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (4.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("fighters_binding")),
                    WeaponList.MAULER = new GauntletItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("mauler")),
                    WeaponList.SOUL_FIST = new GauntletItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (2.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("soul_fist")),
                    // DUAL WIELD

                    WeaponList.RAPIER = new RapierItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (4.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("rapier")),
                    WeaponList.BEE_STINGER = new RapierItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (4.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("bee_stinger")),
                    WeaponList.FREEZING_FOIL = new RapierItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 1, (4.0f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("freezing_foil")),

                    // MODERATE DAMAGE, MODERATE ATTACK SPEED
                    // SCYTHES
                    WeaponList.SOUL_SCYTHE = new SoulScytheItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 4, (1.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("soul_scythe")),
                    WeaponList.FROST_SCYTHE = new SoulScytheItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 4, (1.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("frost_scythe")),
                    WeaponList.JAILORS_SCYTHE = new SoulScytheItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 4, (1.4f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("jailors_scythe")),

                    // CUTLASSES
                    WeaponList.CUTLASS = new CutlassItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 4, (1.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("cutlass")),
                    WeaponList.DANCERS_SWORD = new CutlassItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 4, (1.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("dancers_sword")),
                    WeaponList.NAMELESS_BLADE = new CutlassItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 4, (1.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("nameless_blade")),

                    // SWORD
                    WeaponList.HAWKBRAND = new DungeonsSwordItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 5, (1.6f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("hawkbrand")),

                    // STAFFS
                    WeaponList.BATTLESTAFF = new StaffItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 2, (3.2f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("battlestaff")),
                    WeaponList.BATTLESTAFF_OF_TERROR = new StaffItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 2, (3.2f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("battlestaff_of_terror")),
                    WeaponList.GROWING_STAFF = new StaffItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 2, (3.2f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("growing_staff")),
                    // MODERATE DAMAGE, MODERATE ATTACK SPEED

                    // HIGH DAMAGE, LOW ATTACK SPEED
                    // AXES
                    WeaponList.FIREBRAND = new DungeonsAxeItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("firebrand")),
                    WeaponList.HIGHLAND_AXE = new DungeonsAxeItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("highland_axe")),

                    // DOUBLE AXES
                    WeaponList.DOUBLE_AXE = new DoubleAxeItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("double_axe")),
                    WeaponList.CURSED_AXE = new DoubleAxeItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("cursed_axe")),
                    WeaponList.WHIRLWIND = new DoubleAxeItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("whirlwind")),

                    // MACES
                    WeaponList.MACE = new MaceItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("mace")),
                    WeaponList.FLAIL = new MaceItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("flail")),
                    WeaponList.SUNS_GRACE = new MaceItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("suns_grace")),

                    // HAMMERS
                    WeaponList.GREAT_HAMMER = new GreatHammerItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("great_hammer")),
                    WeaponList.HAMMER_OF_GRAVITY = new GreatHammerItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("hammer_of_gravity")),
                    WeaponList.STORMLANDER = new GreatHammerItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("stormlander")),

                    // KATANAS
                    WeaponList.KATANA = new KatanaItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("katana")),
                    WeaponList.DARK_KATANA = new KatanaItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("dark_katana")),
                    WeaponList.MASTERS_KATANA = new KatanaItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("masters_katana")),

                    // KNIVES
                    WeaponList.SOUL_KNIFE = new SoulKnifeItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("soul_knife")),
                    WeaponList.ETERNAL_KNIFE = new SoulKnifeItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("eternal_knife")),
                    WeaponList.TRUTHSEEKER = new SoulKnifeItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 9, (0.8f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("truthseeker")),

                    // GREATSWORDS
                    WeaponList.CLAYMORE = new ClaymoreItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 10, (0.7f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("claymore")),
                    WeaponList.BROADSWORD = new ClaymoreItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 11, (0.7f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("broadsword")),
                    WeaponList.HEARTSTEALER = new ClaymoreItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 10, (0.7f-4.0f), new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("heartstealer")),
                    // HIGH DAMAGE, LOW ATTACK SPEED

                    // LONG ATTACK REACH
                    // SPEARS
                    WeaponList.SPEAR = new SpearItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 5, (1.2f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("spear")),
                    WeaponList.FORTUNE_SPEAR = new SpearItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 5, (1.2f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("fortune_spear")),
                    WeaponList.WHISPERING_SPEAR = new SpearItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 5, (1.2f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("whispering_spear")),

                    // GLAIVES
                    WeaponList.GLAIVE = new GlaiveItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 7, (1.0f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("glaive")),
                    WeaponList.GRAVE_BANE = new GlaiveItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 7, (1.0f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("grave_bane")),
                    WeaponList.VENOM_GLAIVE = new GlaiveItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 7, (1.0f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("venom_glaive")),

                    // WHIPS
                    WeaponList.WHIP = new WhipItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("whip")),
                    WeaponList.VINE_WHIP = new WhipItem(ToolMaterialList.DUNGEONS_MELEE_WEAPON, 8, (0.9f-4.0f), 2.0F, new Item.Properties().group(DungeonsGear.MELEE_WEAPON_GROUP)).setRegistryName(location("vine_whip")),
                    // LONG ATTACK REACH

                    BONEBOW = new DungeonsBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, true).setRegistryName(location("bonebow")),
                    TWIN_BOW = new DungeonsBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, true).setRegistryName(location("twin_bow")),

                    SOUL_BOW = new SoulBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, false).setRegistryName(location("soul_bow")),
                    BOW_OF_LOST_SOULS = new SoulBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, true).setRegistryName(location("bow_of_lost_souls")),
                    NOCTURNAL_BOW = new SoulBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, true).setRegistryName(location("nocturnal_bow")),

                    POWER_BOW = new PowerBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 25.0F, false).setRegistryName(location("power_bow")),
                    ELITE_POWER_BOW = new PowerBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 25.0F, true).setRegistryName(location("elite_power_bow")),
                    SABREWING = new PowerBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 25.0F, true).setRegistryName(location("sabrewing")),

                    LONGBOW = new LongbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 25.0F, false).setRegistryName(location("longbow")),
                    GUARDIAN_BOW = new LongbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 25.0F, true).setRegistryName(location("guardian_bow")),
                    RED_SNAKE = new LongbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 25.0F, true).setRegistryName(location("red_snake")),

                    HUNTING_BOW = new HuntingBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, false).setRegistryName(location("hunting_bow")),
                    HUNTERS_PROMISE = new HuntingBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, true).setRegistryName(location("hunters_promise")),
                    MASTERS_BOW = new HuntingBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, true).setRegistryName(location("masters_bow")),

                    SHORTBOW = new ShortbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 15.0F, false).setRegistryName(location("shortbow")),
                    MECHANICAL_SHORTBOW = new ShortbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 15.0F, true).setRegistryName(location("mechanical_shortbow")),
                    PURPLE_STORM = new ShortbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 15.0F, true).setRegistryName(location("purple_storm")),

                    TRICKBOW = new TrickbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, false).setRegistryName(location("trickbow")),
                    THE_GREEN_MENACE = new TrickbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, true).setRegistryName(location("the_green_menace")),
                    THE_PINK_SCOUNDREL = new TrickbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, true).setRegistryName(location("the_pink_scoundrel")),

                    SNOW_BOW = new SnowBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, false).setRegistryName(location("snow_bow")),
                    WINTERS_TOUCH = new SnowBowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(384), 20.0F, true).setRegistryName(location("winters_touch")),

                    RAPID_CROSSBOW = new RapidCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 20, false).setRegistryName(location("rapid_crossbow")),
                    BUTTERFLY_CROSSBOW = new RapidCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 20, true).setRegistryName(location("butterfly_crossbow")),
                    AUTO_CROSSBOW = new RapidCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 20, true).setRegistryName(location("auto_crossbow")),

                    AZURE_SEEKER = new DungeonsCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("azure_seeker")),
                    THE_SLICER = new DungeonsCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("the_slicer")),

                    HEAVY_CROSSBOW = new HeavyCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 30, false).setRegistryName(location("heavy_crossbow")),
                    DOOM_CROSSBOW = new HeavyCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 30, true).setRegistryName(location("doom_crossbow")),
                    SLAYER_CROSSBOW = new HeavyCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 30, true).setRegistryName(location("slayer_crossbow")),

                    SOUL_CROSSBOW = new SoulCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, false).setRegistryName(location("soul_crossbow")),
                    FERAL_SOUL_CROSSBOW = new SoulCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("feral_soul_crossbow")),
                    VOIDCALLER = new SoulCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("voidcaller")),

                    SCATTER_CROSSBOW = new ScatterCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, false).setRegistryName(location("scatter_crossbow")),
                    HARP_CROSSBOW = new ScatterCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("harp_crossbow")),
                    LIGHTNING_HARP_CROSSBOW = new ScatterCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("lightning_harp_crossbow")),

                    EXPLODING_CROSSBOW = new ExplodingCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, false).setRegistryName(location("exploding_crossbow")),
                    FIREBOLT_THROWER = new ExplodingCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("firebolt_thrower")),
                    IMPLODING_CROSSBOW = new ExplodingCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("imploding_crossbow")),

                    BURST_CROSSBOW = new BurstCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, false).setRegistryName(location("burst_crossbow")),
                    CORRUPTED_CROSSBOW = new BurstCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("corrupted_crossbow")),
                    SOUL_HUNTER_CROSSBOW = new BurstCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("soul_hunter_crossbow")),

                    DUAL_CROSSBOW = new DualCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, false).setRegistryName(location("dual_crossbow")),
                    BABY_CROSSBOW = new DualCrossbowItem(new Item.Properties().group(DungeonsGear.RANGED_WEAPON_GROUP).maxDamage(326), 25, true).setRegistryName(location("baby_crossbow")),

                    ArtifactList.BOOTS_OF_SWIFTNESS = new BootsOfSwiftnessItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("boots_of_swiftness")),
                    ArtifactList.DEATH_CAP_MUSHROOM = new DeathCapMushroomItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("death_cap_mushroom")),
                    ArtifactList.GOLEM_KIT = new GolemKitItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("golem_kit")),
                    ArtifactList.TASTY_BONE = new TastyBoneItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("tasty_bone")),
                    ArtifactList.WONDERFUL_WHEAT = new WonderfulWheatItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("wonderful_wheat")),
                    ArtifactList.GONG_OF_WEAKENING = new GongOfWeakeningItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("gong_of_weakening")),
                    ArtifactList.LIGHTNING_ROD = new LightningRodItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("lightning_rod")),
                    ArtifactList.IRON_HIDE_AMULET = new IronHideAmuletItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("iron_hide_amulet")),
                    ArtifactList.LOVE_MEDALLION = new LoveMedallionItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("love_medallion")),
                    ArtifactList.GHOST_CLOAK = new GhostCloakItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("ghost_cloak")),
                    ArtifactList.HARVESTER = new HarvesterItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("harvester")),
                    ArtifactList.SHOCK_POWDER = new ShockPowderItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("shock_powder")),
                    ArtifactList.CORRUPTED_SEEDS = new CorruptedSeedsItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("corrupted_seeds")),
                    ArtifactList.ICE_WAND = new IceWandItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("ice_wand")),
                    ArtifactList.WIND_HORN = new WindHornItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("wind_horn")),
                    ArtifactList.SOUL_HEALER = new SoulHealerItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("soul_healer")),
                    ArtifactList.LIGHT_FEATHER = new LightFeatherItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("light_feather")),
                    ArtifactList.FLAMING_QUIVER = new FlamingQuiver(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("flaming_quiver")),
                    ArtifactList.TORMENT_QUIVER = new TormentQuiver(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("torment_quiver")),
                    ArtifactList.TOTEM_OF_REGENERATION = new TotemOfRegenerationItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("totem_of_regeneration")),
                    ArtifactList.TOTEM_OF_SHIELDING = new TotemOfShieldingItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("totem_of_shielding")),
                    ArtifactList.TOTEM_OF_SOUL_PROTECTION = new TotemOfSoulProtectionItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("totem_of_soul_protection")),
                    ArtifactList.CORRUPTED_BEACON = new CorruptedBeaconItem(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("corrupted_beacon")),
                    ArtifactList.BUZZY_NEST = new BuzzyNestArtifact(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("buzzy_nest")),
                    ArtifactList.ENCHANTED_GRASS = new EnchantedGrassArtifact(new Item.Properties().maxStackSize(1).group(DungeonsGear.ARTIFACT_GROUP).maxDamage(64)).setRegistryName(location("enchanted_grass")),

                    // done
                    ArmorList.HUNTERS_ARMOR = new HuntersArmorItem(VEST, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("hunters_vest")),
                    ArmorList.ARCHERS_ARMOR = new HuntersArmorItem(VEST, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("archers_vest")),
                    ArmorList.ARCHERS_ARMOR_HOOD = new HuntersArmorItem(VEST, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("archers_hood")),

                    // done
                    ArmorList.BATTLE_ROBE = new BattleRobeItem(ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("battle_robe")),
                    ArmorList.SPLENDID_ROBE = new BattleRobeItem(ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("splendid_robe")),

                    // done
                    ArmorList.CHAMPIONS_ARMOR = new ChampionsArmorItem(MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("champions_chestplate")),
                    ArmorList.CHAMPIONS_ARMOR_HELMET = new ChampionsArmorItem(MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("champions_helmet")),
                    ArmorList.HEROS_ARMOR = new ChampionsArmorItem(MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("heros_chestplate")),
                    ArmorList.HEROS_ARMOR_HELMET = new ChampionsArmorItem(MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("heros_helmet")),

                    // done
                    ArmorList.DARK_ARMOR = new DarkArmorItem(HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("dark_chestplate")),
                    ArmorList.DARK_ARMOR_HELMET = new DarkArmorItem(HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("dark_helmet")),
                    ArmorList.ROYAL_GUARD_ARMOR = new RoyalGuardArmorItem(HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("royal_guard_chestplate")),
                    ArmorList.ROYAL_GUARD_ARMOR_HELMET = new RoyalGuardArmorItem(HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("royal_guard_helmet")),
                    ArmorList.TITANS_SHROUD = new DarkArmorItem(HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("titans_shroud_chestplate")),
                    ArmorList.TITANS_SHROUD_HELMET = new DarkArmorItem(HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("titans_shroud_helmet")),

                    // done
                    ArmorList.EVOCATION_ROBE = new EvocationRobeItem(ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("evocation_robe")),
                    ArmorList.EVOCATION_ROBE_HAT = new EvocationRobeItem(ROBE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("evocation_hat")),
                    ArmorList.EMBER_ROBE = new EvocationRobeItem(ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("ember_robe")),
                    ArmorList.EMBER_ROBE_HAT = new EvocationRobeItem(ROBE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("ember_hat")),

                    // done
                    ArmorList.GRIM_ARMOR = new GrimArmorItem(BONE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("grim_chestplate")),
                    ArmorList.GRIM_ARMOR_HELMET = new GrimArmorItem(BONE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("grim_helmet")),
                    ArmorList.WITHER_ARMOR = new GrimArmorItem(BONE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("wither_chestplate")),
                    ArmorList.WITHER_ARMOR_HELMET = new GrimArmorItem(BONE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("wither_helmet")),

                    // done
                    ArmorList.GUARDS_ARMOR = new GuardsArmorItem(LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("guards_chestplate")),
                    ArmorList.GUARDS_ARMOR_HELMET = new GuardsArmorItem(LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("guards_helmet")),
                    ArmorList.CURIOUS_ARMOR = new GuardsArmorItem(LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("curious_chestplate")),
                    ArmorList.CURIOUS_ARMOR_HELMET = new GuardsArmorItem(LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("curious_helmet")),

                    // done
                    ArmorList.MERCENARY_ARMOR = new MercenaryArmorItem(HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("mercenary_chestplate")),
                    ArmorList.MERCENARY_ARMOR_HELMET = new MercenaryArmorItem(HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("mercenary_helmet")),
                    ArmorList.RENEGADE_ARMOR = new MercenaryArmorItem(HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("renegade_chestplate")),
                    ArmorList.RENEGADE_ARMOR_HELMET = new MercenaryArmorItem(HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("renegade_helmet")),

                    // done
                    ArmorList.OCELOT_ARMOR = new OcelotArmorItem(PELT, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("ocelot_vest")),
                    ArmorList.OCELOT_ARMOR_HOOD = new OcelotArmorItem(PELT, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("ocelot_hood")),
                    ArmorList.SHADOW_WALKER = new OcelotArmorItem(PELT, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("shadow_walker_vest")),
                    ArmorList.SHADOW_WALKER_HOOD = new OcelotArmorItem(PELT, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("shadow_walker_hood")),

                    // done
                    ArmorList.PHANTOM_ARMOR = new PhantomArmorItem(BONE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("phantom_chestplate")),
                    ArmorList.PHANTOM_ARMOR_HELMET = new PhantomArmorItem(BONE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("phantom_helmet")),
                    ArmorList.FROST_BITE = new PhantomArmorItem(BONE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("frost_bite_chestplate")),
                    ArmorList.FROST_BITE_HELMET = new PhantomArmorItem(BONE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("frost_bite_helmet")),

                    // done
                    ArmorList.PLATE_ARMOR = new PlateArmorItem(HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("plate_chestplate")),
                    ArmorList.PLATE_ARMOR_HELMET = new PlateArmorItem(HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("plate_helmet")),
                    ArmorList.FULL_METAL_ARMOR = new PlateArmorItem(HEAVY_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("full_metal_chestplate")),
                    ArmorList.FULL_METAL_ARMOR_HELMET = new PlateArmorItem(HEAVY_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("full_metal_helmet")),

                    // done
                    ArmorList.REINFORCED_MAIL = new ReinforcedMailItem(MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("reinforced_mail_chestplate")),
                    ArmorList.REINFORCED_MAIL_HELMET = new ReinforcedMailItem(MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("reinforced_mail_helmet")),
                    ArmorList.STALWART_ARMOR = new ReinforcedMailItem(MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("stalwart_chestplate")),
                    ArmorList.STALWART_ARMOR_HELMET = new ReinforcedMailItem(MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("stalwart_helmet")),

                    // done
                    ArmorList.SCALE_MAIL = new ScaleMailItem(LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("scale_mail_chestplate")),
                    ArmorList.HIGHLAND_ARMOR = new ScaleMailItem(LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("highland_chestplate")),
                    ArmorList.HIGHLAND_ARMOR_HELMET = new ScaleMailItem(LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("highland_helmet")),

                    ArmorList.SNOW_ARMOR = new SnowArmorItem(MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("snow_chestplate")),
                    ArmorList.SNOW_ARMOR_HELMET = new SnowArmorItem(MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("snow_helmet")),
                    ArmorList.FROST_ARMOR = new SnowArmorItem(MEDIUM_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("frost_chestplate")),
                    ArmorList.FROST_ARMOR_HELMET = new SnowArmorItem(MEDIUM_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("frost_helmet")),

                    ArmorList.SOUL_ROBE = new SoulRobeItem(ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("soul_robe")),
                    ArmorList.SOUL_ROBE_HOOD = new SoulRobeItem(ROBE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("soul_hood")),
                    ArmorList.SOULDANCER_ROBE = new SoulRobeItem(ROBE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("souldancer_robe")),
                    ArmorList.SOULDANCER_ROBE_HOOD = new SoulRobeItem(ROBE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("souldancer_hood")),

                    // done
                    ArmorList.SPELUNKER_ARMOR = new SpelunkerArmorItem(LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("spelunker_chestplate")),
                    ArmorList.SPELUNKER_ARMOR_HELMET = new SpelunkerArmorItem(LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("spelunker_helmet")),
                    ArmorList.CAVE_CRAWLER = new SpelunkerArmorItem(LIGHT_PLATE, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("cave_crawler_chestplate")),
                    ArmorList.CAVE_CRAWLER_HELMET = new SpelunkerArmorItem(LIGHT_PLATE, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("cave_crawler_helmet")),

                    // done
                    ArmorList.THIEF_ARMOR = new ThiefArmorItem(VEST, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("thief_vest")),
                    ArmorList.THIEF_ARMOR_HOOD = new ThiefArmorItem(VEST, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("thief_hood")),
                    ArmorList.SPIDER_ARMOR = new ThiefArmorItem(VEST, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("spider_vest")),
                    ArmorList.SPIDER_ARMOR_HOOD = new ThiefArmorItem(VEST, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("spider_hood")),

                    // done
                    ArmorList.WOLF_ARMOR = new WolfArmorItem(PELT, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("wolf_vest")),
                    ArmorList.WOLF_ARMOR_HOOD = new WolfArmorItem(PELT, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), false).setRegistryName(location("wolf_hood")),
                    ArmorList.FOX_ARMOR = new WolfArmorItem(PELT, EquipmentSlotType.CHEST, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("fox_vest")),
                    ArmorList.FOX_ARMOR_HOOD = new WolfArmorItem(PELT, EquipmentSlotType.HEAD, new Item.Properties().group(DungeonsGear.ARMOR_GROUP), true).setRegistryName(location("fox_hood"))

            );

            putItemsInMap();
        }

        private static void putItemsInMap() {
            artifactMap.put(BOOTS_OF_SWIFTNESS, BOOTS_OF_SWIFTNESS.getRegistryName());
            artifactMap.put(CORRUPTED_BEACON, CORRUPTED_BEACON.getRegistryName());
            artifactMap.put(CORRUPTED_SEEDS, CORRUPTED_SEEDS.getRegistryName());
            artifactMap.put(DEATH_CAP_MUSHROOM, DEATH_CAP_MUSHROOM.getRegistryName());
            artifactMap.put(FLAMING_QUIVER, FLAMING_QUIVER.getRegistryName());
            artifactMap.put(GHOST_CLOAK, GHOST_CLOAK.getRegistryName());
            artifactMap.put(GOLEM_KIT, GOLEM_KIT.getRegistryName());
            artifactMap.put(GONG_OF_WEAKENING, GONG_OF_WEAKENING.getRegistryName());
            artifactMap.put(HARVESTER, HARVESTER.getRegistryName());
            artifactMap.put(ICE_WAND, ICE_WAND.getRegistryName());
            artifactMap.put(IRON_HIDE_AMULET, IRON_HIDE_AMULET.getRegistryName());
            artifactMap.put(LIGHT_FEATHER, LIGHT_FEATHER.getRegistryName());
            artifactMap.put(LIGHTNING_ROD, LIGHTNING_ROD.getRegistryName());
            artifactMap.put(LOVE_MEDALLION, LOVE_MEDALLION.getRegistryName());
            artifactMap.put(SHOCK_POWDER, SHOCK_POWDER.getRegistryName());
            artifactMap.put(SOUL_HEALER, SOUL_HEALER.getRegistryName());
            artifactMap.put(TASTY_BONE, TASTY_BONE.getRegistryName());
            artifactMap.put(TORMENT_QUIVER, TORMENT_QUIVER.getRegistryName());
            artifactMap.put(TOTEM_OF_REGENERATION, TOTEM_OF_REGENERATION.getRegistryName());
            artifactMap.put(TOTEM_OF_SHIELDING, TOTEM_OF_SHIELDING.getRegistryName());
            artifactMap.put(TOTEM_OF_SOUL_PROTECTION, TOTEM_OF_SOUL_PROTECTION.getRegistryName());
            artifactMap.put(WIND_HORN, WIND_HORN.getRegistryName());
            artifactMap.put(WONDERFUL_WHEAT, WONDERFUL_WHEAT.getRegistryName());
            artifactMap.put(BUZZY_NEST, BUZZY_NEST.getRegistryName());
            artifactMap.put(ENCHANTED_GRASS, ENCHANTED_GRASS.getRegistryName());

            uniqueWeaponMap.put(CURSED_AXE, CURSED_AXE.getRegistryName());
            uniqueWeaponMap.put(FIREBRAND, FIREBRAND.getRegistryName());
            uniqueWeaponMap.put(HIGHLAND_AXE, HIGHLAND_AXE.getRegistryName());
            uniqueWeaponMap.put(WHIRLWIND, WHIRLWIND.getRegistryName());
            uniqueWeaponMap.put(FANG_OF_FROST, FANG_OF_FROST.getRegistryName());
            uniqueWeaponMap.put(MOON_DAGGER, MOON_DAGGER.getRegistryName());
            uniqueWeaponMap.put(GRAVE_BANE, GRAVE_BANE.getRegistryName());
            uniqueWeaponMap.put(VENOM_GLAIVE, VENOM_GLAIVE.getRegistryName());
            uniqueWeaponMap.put(HAMMER_OF_GRAVITY, HAMMER_OF_GRAVITY.getRegistryName());
            uniqueWeaponMap.put(STORMLANDER, STORMLANDER.getRegistryName());
            uniqueWeaponMap.put(FLAIL, FLAIL.getRegistryName());
            uniqueWeaponMap.put(SUNS_GRACE, SUNS_GRACE.getRegistryName());
            uniqueWeaponMap.put(FROST_SCYTHE, FROST_SCYTHE.getRegistryName());
            uniqueWeaponMap.put(JAILORS_SCYTHE, JAILORS_SCYTHE.getRegistryName());
            uniqueWeaponMap.put(NIGHTMARES_BITE, NIGHTMARES_BITE.getRegistryName());
            uniqueWeaponMap.put(THE_LAST_LAUGH, THE_LAST_LAUGH.getRegistryName());
            uniqueWeaponMap.put(FORTUNE_SPEAR, FORTUNE_SPEAR.getRegistryName());
            uniqueWeaponMap.put(WHISPERING_SPEAR, WHISPERING_SPEAR.getRegistryName());
            uniqueWeaponMap.put(BATTLESTAFF_OF_TERROR, BATTLESTAFF_OF_TERROR.getRegistryName());
            uniqueWeaponMap.put(GROWING_STAFF, GROWING_STAFF.getRegistryName());
            uniqueWeaponMap.put(VINE_WHIP, VINE_WHIP.getRegistryName());
            uniqueWeaponMap.put(FIGHTERS_BINDING, FIGHTERS_BINDING.getRegistryName());
            uniqueWeaponMap.put(MAULER, MAULER.getRegistryName());
            uniqueWeaponMap.put(SOUL_FIST, SOUL_FIST.getRegistryName());
            uniqueWeaponMap.put(BROADSWORD, BROADSWORD.getRegistryName());
            uniqueWeaponMap.put(DANCERS_SWORD, DANCERS_SWORD.getRegistryName());
            uniqueWeaponMap.put(DARK_KATANA, DARK_KATANA.getRegistryName());
            uniqueWeaponMap.put(ETERNAL_KNIFE, ETERNAL_KNIFE.getRegistryName());
            uniqueWeaponMap.put(HAWKBRAND, HAWKBRAND.getRegistryName());
            uniqueWeaponMap.put(HEARTSTEALER, HEARTSTEALER.getRegistryName());
            uniqueWeaponMap.put(MASTERS_KATANA, MASTERS_KATANA.getRegistryName());
            uniqueWeaponMap.put(NAMELESS_BLADE, NAMELESS_BLADE.getRegistryName());
            uniqueWeaponMap.put(TRUTHSEEKER, TRUTHSEEKER.getRegistryName());
            uniqueWeaponMap.put(FREEZING_FOIL, FREEZING_FOIL.getRegistryName());
            uniqueWeaponMap.put(BEE_STINGER, BEE_STINGER.getRegistryName());

            commonWeaponMap.put(DOUBLE_AXE, DOUBLE_AXE.getRegistryName());
            commonWeaponMap.put(CLAYMORE, CLAYMORE.getRegistryName());
            commonWeaponMap.put(CUTLASS, CUTLASS.getRegistryName());
            commonWeaponMap.put(KATANA, KATANA.getRegistryName());
            commonWeaponMap.put(SOUL_KNIFE, SOUL_KNIFE.getRegistryName());
            commonWeaponMap.put(GAUNTLET, GAUNTLET.getRegistryName());
            commonWeaponMap.put(WHIP, WHIP.getRegistryName());
            commonWeaponMap.put(SPEAR, SPEAR.getRegistryName());
            commonWeaponMap.put(BATTLESTAFF, BATTLESTAFF.getRegistryName());
            commonWeaponMap.put(SICKLE, SICKLE.getRegistryName());
            commonWeaponMap.put(SOUL_SCYTHE, SOUL_SCYTHE.getRegistryName());
            commonWeaponMap.put(MACE, MACE.getRegistryName());
            commonWeaponMap.put(GREAT_HAMMER, GREAT_HAMMER.getRegistryName());
            commonWeaponMap.put(GLAIVE, GLAIVE.getRegistryName());
            commonWeaponMap.put(DAGGER, DAGGER.getRegistryName());
            commonWeaponMap.put(RAPIER, RAPIER.getRegistryName());

            uniqueRangedWeaponMap.put(BONEBOW, BONEBOW.getRegistryName());
            uniqueRangedWeaponMap.put(BOW_OF_LOST_SOULS, BOW_OF_LOST_SOULS.getRegistryName());
            uniqueRangedWeaponMap.put(ELITE_POWER_BOW, ELITE_POWER_BOW.getRegistryName());
            uniqueRangedWeaponMap.put(GUARDIAN_BOW, GUARDIAN_BOW.getRegistryName());
            uniqueRangedWeaponMap.put(HUNTERS_PROMISE, HUNTERS_PROMISE.getRegistryName());
            uniqueRangedWeaponMap.put(MASTERS_BOW, MASTERS_BOW.getRegistryName());
            uniqueRangedWeaponMap.put(MECHANICAL_SHORTBOW, MECHANICAL_SHORTBOW.getRegistryName());
            uniqueRangedWeaponMap.put(NOCTURNAL_BOW, NOCTURNAL_BOW.getRegistryName());
            uniqueRangedWeaponMap.put(PURPLE_STORM, PURPLE_STORM.getRegistryName());
            uniqueRangedWeaponMap.put(RED_SNAKE, RED_SNAKE.getRegistryName());
            uniqueRangedWeaponMap.put(SABREWING, SABREWING.getRegistryName());
            uniqueRangedWeaponMap.put(THE_GREEN_MENACE, THE_GREEN_MENACE.getRegistryName());
            uniqueRangedWeaponMap.put(THE_PINK_SCOUNDREL, THE_PINK_SCOUNDREL.getRegistryName());
            uniqueRangedWeaponMap.put(TWIN_BOW, TWIN_BOW.getRegistryName());
            uniqueRangedWeaponMap.put(WINTERS_TOUCH, WINTERS_TOUCH.getRegistryName());
            uniqueRangedWeaponMap.put(AUTO_CROSSBOW, AUTO_CROSSBOW.getRegistryName());
            uniqueRangedWeaponMap.put(AZURE_SEEKER, AZURE_SEEKER.getRegistryName());
            uniqueRangedWeaponMap.put(BUTTERFLY_CROSSBOW, BUTTERFLY_CROSSBOW.getRegistryName());
            uniqueRangedWeaponMap.put(DOOM_CROSSBOW, DOOM_CROSSBOW.getRegistryName());
            uniqueRangedWeaponMap.put(FERAL_SOUL_CROSSBOW, FERAL_SOUL_CROSSBOW.getRegistryName());
            uniqueRangedWeaponMap.put(FIREBOLT_THROWER, FIREBOLT_THROWER.getRegistryName());
            uniqueRangedWeaponMap.put(HARP_CROSSBOW, HARP_CROSSBOW.getRegistryName());
            uniqueRangedWeaponMap.put(LIGHTNING_HARP_CROSSBOW, LIGHTNING_HARP_CROSSBOW.getRegistryName());
            uniqueRangedWeaponMap.put(SLAYER_CROSSBOW, SLAYER_CROSSBOW.getRegistryName());
            uniqueRangedWeaponMap.put(THE_SLICER, THE_SLICER.getRegistryName());
            uniqueRangedWeaponMap.put(VOIDCALLER, VOIDCALLER.getRegistryName());
            uniqueRangedWeaponMap.put(BABY_CROSSBOW, BABY_CROSSBOW.getRegistryName());
            uniqueRangedWeaponMap.put(IMPLODING_CROSSBOW, IMPLODING_CROSSBOW.getRegistryName());

            commonRangedWeaponMap.put(HUNTING_BOW, HUNTING_BOW.getRegistryName());
            commonRangedWeaponMap.put(LONGBOW, LONGBOW.getRegistryName());
            commonRangedWeaponMap.put(SHORTBOW, SHORTBOW.getRegistryName());
            commonRangedWeaponMap.put(POWER_BOW, POWER_BOW.getRegistryName());
            commonRangedWeaponMap.put(SOUL_BOW, SOUL_BOW.getRegistryName());
            commonRangedWeaponMap.put(TRICKBOW, TRICKBOW.getRegistryName());
            commonRangedWeaponMap.put(SNOW_BOW, SNOW_BOW.getRegistryName());
            commonRangedWeaponMap.put(EXPLODING_CROSSBOW, EXPLODING_CROSSBOW.getRegistryName());
            commonRangedWeaponMap.put(HEAVY_CROSSBOW, HEAVY_CROSSBOW.getRegistryName());
            commonRangedWeaponMap.put(RAPID_CROSSBOW, RAPID_CROSSBOW.getRegistryName());
            commonRangedWeaponMap.put(SCATTER_CROSSBOW, SCATTER_CROSSBOW.getRegistryName());
            commonRangedWeaponMap.put(SOUL_CROSSBOW, SOUL_CROSSBOW.getRegistryName());
            commonRangedWeaponMap.put(DUAL_CROSSBOW, DUAL_CROSSBOW.getRegistryName());

            commonLeatherArmorMap.put(HUNTERS_ARMOR, HUNTERS_ARMOR.getRegistryName());
            commonLeatherArmorMap.put(PHANTOM_ARMOR, PHANTOM_ARMOR.getRegistryName());
            commonLeatherArmorMap.put(PHANTOM_ARMOR_HELMET, PHANTOM_ARMOR_HELMET.getRegistryName());
            commonLeatherArmorMap.put(THIEF_ARMOR, THIEF_ARMOR.getRegistryName());
            commonLeatherArmorMap.put(THIEF_ARMOR_HOOD, THIEF_ARMOR_HOOD.getRegistryName());
            commonLeatherArmorMap.put(GRIM_ARMOR, GRIM_ARMOR.getRegistryName());
            commonLeatherArmorMap.put(GRIM_ARMOR_HELMET, GRIM_ARMOR_HELMET.getRegistryName());
            commonLeatherArmorMap.put(WOLF_ARMOR, WOLF_ARMOR.getRegistryName());
            commonLeatherArmorMap.put(WOLF_ARMOR_HOOD, WOLF_ARMOR_HOOD.getRegistryName());
            commonLeatherArmorMap.put(OCELOT_ARMOR, OCELOT_ARMOR.getRegistryName());
            commonLeatherArmorMap.put(OCELOT_ARMOR_HOOD, OCELOT_ARMOR_HOOD.getRegistryName());
            commonLeatherArmorMap.put(BATTLE_ROBE, BATTLE_ROBE.getRegistryName());
            commonLeatherArmorMap.put(EVOCATION_ROBE, EVOCATION_ROBE.getRegistryName());
            commonLeatherArmorMap.put(EVOCATION_ROBE_HAT, EVOCATION_ROBE_HAT.getRegistryName());
            commonLeatherArmorMap.put(SOUL_ROBE, SOUL_ROBE.getRegistryName());
            commonLeatherArmorMap.put(SOUL_ROBE_HOOD, SOUL_ROBE_HOOD.getRegistryName());

            uniqueLeatherArmorMap.put(ARCHERS_ARMOR, ARCHERS_ARMOR.getRegistryName());
            uniqueLeatherArmorMap.put(ARCHERS_ARMOR_HOOD, ARCHERS_ARMOR_HOOD.getRegistryName());
            uniqueLeatherArmorMap.put(FROST_BITE, FROST_BITE.getRegistryName());
            uniqueLeatherArmorMap.put(FROST_BITE_HELMET, FROST_BITE_HELMET.getRegistryName());
            uniqueLeatherArmorMap.put(SPIDER_ARMOR, SPIDER_ARMOR.getRegistryName());
            uniqueLeatherArmorMap.put(SPIDER_ARMOR_HOOD, SPIDER_ARMOR_HOOD.getRegistryName());
            uniqueLeatherArmorMap.put(WITHER_ARMOR, WITHER_ARMOR.getRegistryName());
            uniqueLeatherArmorMap.put(WITHER_ARMOR_HELMET, WITHER_ARMOR_HELMET.getRegistryName());
            uniqueLeatherArmorMap.put(FOX_ARMOR, FOX_ARMOR.getRegistryName());
            uniqueLeatherArmorMap.put(FOX_ARMOR_HOOD, FOX_ARMOR_HOOD.getRegistryName());
            uniqueLeatherArmorMap.put(SHADOW_WALKER, SHADOW_WALKER.getRegistryName());
            uniqueLeatherArmorMap.put(SHADOW_WALKER_HOOD, SHADOW_WALKER_HOOD.getRegistryName());
            uniqueLeatherArmorMap.put(SPLENDID_ROBE, SPLENDID_ROBE.getRegistryName());
            uniqueLeatherArmorMap.put(EMBER_ROBE, EMBER_ROBE.getRegistryName());
            uniqueLeatherArmorMap.put(EMBER_ROBE_HAT, EMBER_ROBE_HAT.getRegistryName());
            uniqueLeatherArmorMap.put(SOULDANCER_ROBE, SOULDANCER_ROBE.getRegistryName());
            uniqueLeatherArmorMap.put(SOULDANCER_ROBE_HOOD, SOULDANCER_ROBE_HOOD.getRegistryName());

            commonMetalArmorMap.put(SPELUNKER_ARMOR, SPELUNKER_ARMOR.getRegistryName());
            commonMetalArmorMap.put(SPELUNKER_ARMOR_HELMET, SPELUNKER_ARMOR_HELMET.getRegistryName());
            commonMetalArmorMap.put(SCALE_MAIL, SCALE_MAIL.getRegistryName());
            commonMetalArmorMap.put(GUARDS_ARMOR, GUARDS_ARMOR.getRegistryName());
            commonMetalArmorMap.put(GUARDS_ARMOR_HELMET, GUARDS_ARMOR_HELMET.getRegistryName());
            commonMetalArmorMap.put(REINFORCED_MAIL, REINFORCED_MAIL.getRegistryName());
            commonMetalArmorMap.put(REINFORCED_MAIL_HELMET, REINFORCED_MAIL_HELMET.getRegistryName());
            commonMetalArmorMap.put(SNOW_ARMOR, SNOW_ARMOR.getRegistryName());
            commonMetalArmorMap.put(SNOW_ARMOR_HELMET, SNOW_ARMOR_HELMET.getRegistryName());
            commonMetalArmorMap.put(CHAMPIONS_ARMOR, CHAMPIONS_ARMOR.getRegistryName());
            commonMetalArmorMap.put(CHAMPIONS_ARMOR_HELMET, CHAMPIONS_ARMOR_HELMET.getRegistryName());
            commonMetalArmorMap.put(DARK_ARMOR, DARK_ARMOR.getRegistryName());
            commonMetalArmorMap.put(DARK_ARMOR_HELMET, DARK_ARMOR_HELMET.getRegistryName());
            commonMetalArmorMap.put(PLATE_ARMOR, PLATE_ARMOR.getRegistryName());
            commonMetalArmorMap.put(PLATE_ARMOR_HELMET, PLATE_ARMOR_HELMET.getRegistryName());
            commonMetalArmorMap.put(MERCENARY_ARMOR, MERCENARY_ARMOR.getRegistryName());
            commonMetalArmorMap.put(MERCENARY_ARMOR_HELMET, MERCENARY_ARMOR_HELMET.getRegistryName());

            uniqueMetalArmorMap.put(CAVE_CRAWLER, CAVE_CRAWLER.getRegistryName());
            uniqueMetalArmorMap.put(CAVE_CRAWLER_HELMET, CAVE_CRAWLER_HELMET.getRegistryName());
            uniqueMetalArmorMap.put(HIGHLAND_ARMOR, HIGHLAND_ARMOR.getRegistryName());
            uniqueMetalArmorMap.put(HIGHLAND_ARMOR_HELMET, HIGHLAND_ARMOR_HELMET.getRegistryName());
            uniqueMetalArmorMap.put(CURIOUS_ARMOR, CURIOUS_ARMOR.getRegistryName());
            uniqueMetalArmorMap.put(CURIOUS_ARMOR_HELMET, CURIOUS_ARMOR_HELMET.getRegistryName());
            uniqueMetalArmorMap.put(STALWART_ARMOR, STALWART_ARMOR.getRegistryName());
            uniqueMetalArmorMap.put(STALWART_ARMOR_HELMET, STALWART_ARMOR_HELMET.getRegistryName());
            uniqueMetalArmorMap.put(FROST_ARMOR, FROST_ARMOR.getRegistryName());
            uniqueMetalArmorMap.put(FROST_ARMOR_HELMET, FROST_ARMOR_HELMET.getRegistryName());
            uniqueMetalArmorMap.put(HEROS_ARMOR, HEROS_ARMOR.getRegistryName());
            uniqueMetalArmorMap.put(HEROS_ARMOR_HELMET, HEROS_ARMOR_HELMET.getRegistryName());
            uniqueMetalArmorMap.put(TITANS_SHROUD, TITANS_SHROUD.getRegistryName());
            uniqueMetalArmorMap.put(TITANS_SHROUD_HELMET, TITANS_SHROUD_HELMET.getRegistryName());
            uniqueMetalArmorMap.put(FULL_METAL_ARMOR, FULL_METAL_ARMOR.getRegistryName());
            uniqueMetalArmorMap.put(FULL_METAL_ARMOR_HELMET, FULL_METAL_ARMOR_HELMET.getRegistryName());
            uniqueMetalArmorMap.put(RENEGADE_ARMOR, RENEGADE_ARMOR.getRegistryName());
            uniqueMetalArmorMap.put(RENEGADE_ARMOR_HELMET, RENEGADE_ARMOR_HELMET.getRegistryName());
        }

        private static ResourceLocation location(String name) {
            return new ResourceLocation(DungeonsGear.MODID, name);
        }
    }
}
