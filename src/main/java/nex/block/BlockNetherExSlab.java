/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.block;

import com.google.common.base.CaseFormat;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.NetherEx;

public abstract class BlockNetherExSlab extends BlockSlab
{
    public static boolean isDoubleStatic;
    private boolean isDouble;

    public BlockNetherExSlab(String name, Material material, boolean isDoubleIn)
    {
        super(singleClassHack(material, isDoubleIn));

        isDouble = isDoubleIn;

        if(!isDoubleIn)
        {
            useNeighborBrightness = true;
            setCreativeTab(NetherEx.CREATIVE_TAB);
        }
        if(isDoubleIn)
        {
            name += "_double";
        }

        setSoundType(SoundType.STONE);
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().toString()));
    }

    @Override
    public boolean isDouble()
    {
        return isDouble;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = getStateFromMeta(meta);
        return isDouble() ? state : (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? state.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM) : state.withProperty(HALF, BlockSlab.EnumBlockHalf.TOP));
    }

    private static Material singleClassHack(Material material, boolean isDoubleIn)
    {
        isDoubleStatic = isDoubleIn;
        return material;
    }
}
