package com.rottenfoods.drops;

import com.rottenfoods.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModLootTables {

    public static void modifyLootTables() {

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {

            if (!source.isBuiltin()) return;

            if (key.getValue().equals(Identifier.of("minecraft", "blocks/oak_leaves")) ||
                    key.getValue().equals(Identifier.of("minecraft", "blocks/dark_oak_leaves"))) {

                LootPool.Builder pool = LootPool.builder()
                        .conditionally(
                                TableBonusLootCondition.builder(
                                        registries.getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE),
                                        0.005f, // no fortune
                                        0.00625f, // fortune I
                                        0.00833f, // fortune II
                                        0.025f // fortune III
                                )
                        )
                        .with(
                                AlternativeEntry.builder(
                                        ItemEntry.builder(ModItems.ROTTEN_APPLE).weight(1),
                                        ItemEntry.builder(Items.APPLE).weight(4)
                                )
                        );

                tableBuilder.pool(pool);
            }

            if (key.getValue().equals(Identifier.of("minecraft", "blocks/carrots"))) {

                LootPool.Builder pool = LootPool.builder()
                        .conditionally(RandomChanceLootCondition.builder(0.1f))
                        .with(ItemEntry.builder(ModItems.ROTTEN_CARROT))
                        .rolls(ConstantLootNumberProvider.create(1));

                tableBuilder.pool(pool);
            }

            if (key.getValue().equals(Identifier.of("minecraft", "chests/stronghold_corridor"))) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.ROTTEN_CARROT).weight(5))
                        .rolls(ConstantLootNumberProvider.create(1));
                tableBuilder.pool(pool);
            }
        });
    }
}