package com.cjburkey.cjscomputation.block;

import com.cjburkey.cjscomputation.CJsComputation;
import com.cjburkey.cjscomputation.Debug;
import com.cjburkey.cjscomputation.computer.ComputerHandler;
import com.cjburkey.cjscomputation.computer.ComputerPlaced;
import com.cjburkey.cjscomputation.tile.TileEntityComputer;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockComputer extends Block implements ITileEntityProvider {
    
    public BlockComputer() {
        super(Material.IRON);
        setHardness(2.5f);
        setResistance(3.5f);
        setHarvestLevel("pickaxe", 0);
        setLightLevel(0);
        setLightOpacity(0);
        setCreativeTab(CreativeTabs.REDSTONE);
    }
    
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        
        if (world.isRemote) {
            return;
        }
        
        // Register a new computer with the tile entity
        TileEntity te = world.getTileEntity(pos);
        if (te == null || !(te instanceof TileEntityComputer)) {
            Debug.warn("Tile entity not found at {}, {}, {} in dimension {}", pos.getX(), pos.getY(), pos.getZ(), world.provider.getDimension());
            return;
        }
        TileEntityComputer tec = (TileEntityComputer) te;
        ComputerPlaced computer = new ComputerPlaced(ComputerHandler.get(), tec.getWorld(), tec.getPos());
        ComputerHandler.get().addComputer(computer);
        tec.setComputer(computer);
    }
    
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float x, float y, float z) {
        super.onBlockActivated(world, pos, state, player, hand, facing, x, y, z);
        
        if (world.isRemote) {
            return true;
        }
        
        TileEntity te = world.getTileEntity(pos);
        if (te == null || !(te instanceof TileEntityComputer)) {
            Debug.warn("Tile entity not found at {}, {}, {} in dimension {}", pos.getX(), pos.getY(), pos.getZ(), world.provider.getDimension());
            return true;
        }
        TileEntityComputer tec = (TileEntityComputer) te;
        
        // The "x" coordinate is the computer id because there's not many other good ways to transmit data without packets. I'm too lazy.
        // TODO: Transmit computer id with a packet rather than the coordinates
        player.openGui(CJsComputation.getInstance(), 0, world, tec.getId(), 0, 0);
        return true;
    }
    
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityComputer();
    }
    
}