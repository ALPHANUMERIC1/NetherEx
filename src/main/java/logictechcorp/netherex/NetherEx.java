/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logictechcorp.netherex;

import logictechcorp.libraryex.IModData;
import logictechcorp.libraryex.proxy.IProxy;
import logictechcorp.netherex.capability.CapabilityBlightChunkData;
import logictechcorp.netherex.capability.IBlightChunkData;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.handler.GuiHandler;
import logictechcorp.netherex.init.*;
import logictechcorp.netherex.village.PigtificateTradeManager;
import logictechcorp.netherex.world.biome.NetherBiomeManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = NetherEx.MOD_ID, name = NetherEx.NAME, version = NetherEx.VERSION, dependencies = NetherEx.DEPENDENCIES)
public class NetherEx implements IModData
{
    //TODO:
    // Finish Regrowth's Collapse and Blight's Ascension biomes

    public static final String MOD_ID = "netherex";
    public static final String NAME = "NetherEx";
    public static final String VERSION = "2.0.9";
    public static final String DEPENDENCIES = "required-after:libraryex@[1.0.9,);";

    @Mod.Instance(MOD_ID)
    public static NetherEx instance;

    @SidedProxy(clientSide = "logictechcorp.netherex.proxy.ClientProxy", serverSide = "logictechcorp.netherex.proxy.ServerProxy")
    public static IProxy proxy;

    private static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID)
    {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon()
        {
            return new ItemStack(Blocks.NETHERRACK);
        }
    };

    public static final boolean IS_BOP_LOADED = Loader.isModLoaded("biomesoplenty");

    public static final Logger LOGGER = LogManager.getLogger("NetherEx");

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event)
    {
        NetherExOverrides.overrideObjects();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        CapabilityManager.INSTANCE.register(IBlightChunkData.class, new CapabilityBlightChunkData.Storage(), new CapabilityBlightChunkData.Factory());
        proxy.preInit();
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event)
    {
        NetherExDataFixers.registerFixes();
        NetherExPigtificates.registerPigtificateCareers();
        NetherExBiomes.registerBiomes();
        NetherExRecipes.registerRecipes();
        NetherExOreDictionary.registerOres();
        NetherExFeatures.registerFeatures();
        NetherExCriteria.registerCriteria();
        proxy.init();
    }

    @Mod.EventHandler
    public void onFMLPostInitialization(FMLPostInitializationEvent event)
    {
        NetherExOverrides.overrideNether();
        proxy.postInit();
    }

    @Mod.EventHandler
    public void onFMLServerStarting(FMLServerStartingEvent event)
    {
        NetherBiomeManager.INSTANCE.readBiomeInfoFromConfigs(event.getServer());
        PigtificateTradeManager.readTradesFromConfigs(event.getServer());
    }

    @Mod.EventHandler
    public void onFMLServerStopping(FMLServerStoppingEvent event)
    {
        NetherBiomeManager.INSTANCE.writeBiomeInfoToConfigs(FMLCommonHandler.instance().getMinecraftServerInstance());
        PigtificateTradeManager.writeTradesToConfigs(FMLCommonHandler.instance().getMinecraftServerInstance());
    }

    @Override
    public String getModId()
    {
        return MOD_ID;
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return CREATIVE_TAB;
    }

    @Override
    public boolean writeRecipeJsons()
    {
        return ConfigHandler.internalConfig.recipes.writeRecipesToGlobalConfigFolder;
    }

    public static ResourceLocation getResource(String name)
    {
        return new ResourceLocation(NetherEx.MOD_ID + ":" + name);
    }
}
