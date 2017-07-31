/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import nex.block.BlockBasalt;
import nex.block.BlockNetherrack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static nex.init.NetherExBlocks.*;
import static nex.init.NetherExItems.ITEM_BRICK_NETHER;

@SuppressWarnings("ConstantConditions")
public class NetherExOreDict
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExOreDict");

    public static void init()
    {
        LOGGER.info("Ore Dictionary registration started.");

        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            addOreDictEntry("stoneBasalt", new ItemStack(BLOCK_BASALT, 1, type.ordinal()));

            if(type.ordinal() == 1)
            {
                addOreDictEntry("stoneBasaltPolished", new ItemStack(BLOCK_BASALT, 1, type.ordinal()));
            }
            else if(type.ordinal() == 2 || type.ordinal() == 3)
            {
                addOreDictEntry("stoneBasaltBrick", new ItemStack(BLOCK_BASALT, 1, type.ordinal()));
            }
        }

        addOreDictEntry("blockGlass", new ItemStack(BLOCK_GLASS_SOUL, 1, 0));
        addOreDictEntry("paneGlass", new ItemStack(BLOCK_GLASS_PANE_SOUL, 1, 0));

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            addOreDictEntry("netherrack", new ItemStack(BLOCK_NETHERRACK, 1, type.ordinal()));
            addOreDictEntry("ingotBrickNether", new ItemStack(ITEM_BRICK_NETHER, 1, type.ordinal()));
            addOreDictEntry("oreQuartz", new ItemStack(ORE_QUARTZ, 1, type.ordinal()));
        }

        addOreDictEntry("oreAmethyst", new ItemStack(NetherExBlocks.ORE_AMETHYST, 1, 0));
        addOreDictEntry("blockAmethyst", new ItemStack(NetherExBlocks.BLOCK_AMETHYST, 1, 0));
        addOreDictEntry("gemAmethyst", new ItemStack(NetherExItems.ITEM_CRYSTAL_AMETHYST, 1, 0));
        addOreDictEntry("oreRime", new ItemStack(NetherExBlocks.ORE_RIME, 1, 0));
        addOreDictEntry("blockRime", new ItemStack(NetherExBlocks.BLOCK_RIME, 1, 0));
        addOreDictEntry("gemRime", new ItemStack(NetherExItems.ITEM_CRYSTAL_RIME, 1, 0));
        addOreDictEntry("dyeBlack", new ItemStack(NetherExItems.ITEM_DUST_WITHER, 1, 0));

        LOGGER.info("Ore Dictionary registration completed.");
    }

    private static void addOreDictEntry(String name, ItemStack stack)
    {
        OreDictionary.registerOre(name, stack);
    }
}
