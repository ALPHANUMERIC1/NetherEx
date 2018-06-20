/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

package nex.world.gen.feature;

import lex.api.config.IConfig;
import lex.world.gen.feature.Feature;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.block.BlockEnokiMushroomCap;
import nex.init.NetherExBlocks;

import java.util.Random;

public class FeatureEnoki extends Feature
{

    public FeatureEnoki(IConfig config)
    {
        super(config);
    }

    public FeatureEnoki(int genAttemptsIn, float genProbabilityIn, boolean randomizeGenAttemptsIn, int minHeightIn, int maxHeightIn)
    {
        super(genAttemptsIn, genProbabilityIn, randomizeGenAttemptsIn, minHeightIn, maxHeightIn);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(world.isAirBlock(pos.down()) && NetherExBlocks.ENOKI_MUSHROOM_CAP.canSurvive(world, pos) && rand.nextInt(8) == 7)
        {
            BlockEnokiMushroomCap.generatePlant(world, pos, rand, 8);
            return true;
        }

        return false;
    }
}