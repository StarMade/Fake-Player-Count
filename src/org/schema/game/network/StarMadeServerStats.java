package org.schema.game.network;

import deobf.Class1078alN;
import java.util.HashMap;
import org.schema.schine.network.server.ServerProcessor;

import com.error22.fp.FakePlayers;

public class StarMadeServerStats
{
  public long totalMemory;
  public long freeMemory;
  public long takenMemory;
  public int totalPackagesQueued;
  public int lastAllocatedSegmentData;
  public int playerCount;
  public long ping;
  
  public static StarMadeServerStats decode(Object[] paramArrayOfObject)
  {
    StarMadeServerStats localStarMadeServerStats;
    (localStarMadeServerStats = new StarMadeServerStats()).totalMemory = ((Long)paramArrayOfObject[0]).longValue();
    localStarMadeServerStats.freeMemory = ((Long)paramArrayOfObject[1]).longValue();
    localStarMadeServerStats.takenMemory = (localStarMadeServerStats.totalMemory - localStarMadeServerStats.freeMemory);
    
    localStarMadeServerStats.totalPackagesQueued = ((Integer)paramArrayOfObject[2]).intValue();
    localStarMadeServerStats.lastAllocatedSegmentData = ((Integer)paramArrayOfObject[3]).intValue();
    localStarMadeServerStats.playerCount = ((Integer)paramArrayOfObject[4]).intValue();
    
    return localStarMadeServerStats;
  }
  
  public static Object[] encode(Class1078alN paramClass1078alN)
  {
    long l1 = Runtime.getRuntime().totalMemory();
    long l2 = Runtime.getRuntime().freeMemory();
    return new Object[] { Long.valueOf(l1), Long.valueOf(l2), Integer.valueOf(ServerProcessor.totalPackagesQueued), Integer.valueOf(Class1078alN.field5075b), FakePlayers.getInstance().getUnnamedPlayers() + Integer.valueOf(paramClass1078alN.method3422a().size()) };
  }
}
