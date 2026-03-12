package com.rottenfoods.item;

import com.rottenfoods.Rottenfoods;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import java.util.function.Function;



public class ModItems {



    public static final Item ROTTEN_APPLE = registerItem(
            "rotten_apple",
            settings -> new Item(settings.food(
                    new FoodComponent.Builder()
                            .nutrition(1)
                            .saturationModifier(0.1f)
                            .alwaysEdible()
                            .build()
            )) {
                @Override
                public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
                    ItemStack result = super.finishUsing(stack, world, user);
                    if (!world.isClient()) {
                        user.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200, 2));
                        user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 160, 1));
                    }
                    return result;
                }
            },
            new Item.Settings()
    );

    public static final Item ROTTEN_CARROT = registerItem(
            "rotten_carrot",
            settings -> new Item(settings.food(
                    new FoodComponent.Builder()
                            .nutrition(1)
                            .saturationModifier(0.1f)
                            .alwaysEdible()
                            .build()
            )) {
                @Override
                public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
                    ItemStack result = super.finishUsing(stack, world, user);
                    if (!world.isClient()) {
                        user.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 240, 2));
                        user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 75, 1));
                    }
                    return result;
                }
            },
            new Item.Settings()
    );

    public static final Item ROTTEN_WHEAT = registerItem("rotten_wheat", Item::new, new Item.Settings());

    public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registerKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Rottenfoods.MOD_ID, name));
        return Items.register(registerKey, factory, settings);
    }

    private static void customIngredients(FabricItemGroupEntries entries) {
        entries.add(ROTTEN_APPLE);
        entries.add(ROTTEN_CARROT);
        entries.add(ROTTEN_WHEAT);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::customIngredients);
    }
}
