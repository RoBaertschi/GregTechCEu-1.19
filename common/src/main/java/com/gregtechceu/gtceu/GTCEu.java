package com.gregtechceu.gtceu;

import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtlib.GTLib;
import com.gregtechceu.gtlib.Platform;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GTCEu {
    public static final String MOD_ID = "gtceu";
    public static final String MODID_KUBEJS = "kubejs";
    public static final String MODID_IRIS = "iris";
    public static final String MODID_CREATE = "create";
    public static final String NAME = "GregTechCEu";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static void init() {
        LOGGER.info("{} is initializing on platform: {}", NAME, Platform.platformName());
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, FormattingUtil.toLowerCaseUnder(path));
    }

    public static String appendIdString(String id) {
        return id.indexOf(':') == -1 ? (MOD_ID + ":" + id) : id;
    }

    public static ResourceLocation appendId(String id) {
        String[] strings = new String[]{"gtceu", id};
        int i = id.indexOf(':');
        if (i >= 0) {
            strings[1] = id.substring(i + 1);
            if (i >= 1) {
                strings[0] = id.substring(0, i);
            }
        }
        return new ResourceLocation(strings[0], strings[1]);
    }

    public static boolean isKubeJSLoaded() {
        return GTLib.isModLoaded(MODID_KUBEJS);
    }

    public static boolean isCreateLoaded() {
        return GTLib.isModLoaded(MODID_CREATE);
    }

    public static boolean isIrisLoaded() {
        return GTLib.isModLoaded(MODID_IRIS);
    }
}
