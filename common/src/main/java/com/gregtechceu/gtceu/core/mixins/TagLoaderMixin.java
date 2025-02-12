package com.gregtechceu.gtceu.core.mixins;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.core.IGTTagLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagLoader;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(TagLoader.class)
public class TagLoaderMixin<T> implements IGTTagLoader<T> {

    @Nullable
    @Unique
    private Registry<T> gtceu$storedRegistry;

    @Inject(method = "load", at = @At(value = "RETURN"))
    public void gtceu$load(ResourceManager resourceManager, CallbackInfoReturnable<Map<ResourceLocation, List<TagLoader.EntryWithSource>>> cir) {
        var value = cir.getReturnValue();
        if (gtceu$getRegistry() == null || gtceu$getRegistry() != Registry.ITEM) return;
        ChemicalHelper.UNIFICATION_ENTRY_ITEM.forEach((entry, itemLikes) -> {
            if (itemLikes.isEmpty()) return;
            var material = entry.material;
            if (material != null) {
                var materialTags = entry.tagPrefix.getItemTags(material);
                for (TagKey<Item> materialTag : materialTags) {
                    List<TagLoader.EntryWithSource> tags = new ArrayList<>();
                    itemLikes.forEach(item -> tags.add(new TagLoader.EntryWithSource(TagEntry.element(Registry.ITEM.getKey(item.asItem())), GTValues.CUSTOM_TAG_SOURCE)));
                    //GTCEu.LOGGER.info("added items " + itemLikes + " to tag " + materialTag);
                    value.computeIfAbsent(materialTag.location(), path -> new ArrayList<>()).addAll(tags);
                }

            }
        });
    }

    @Override
    @Nullable
    public Registry<T> gtceu$getRegistry() {
        return gtceu$storedRegistry;
    }

    @Override
    public void gtceu$setRegistry(Registry<T> registry) {
        this.gtceu$storedRegistry = registry;
    }

}
