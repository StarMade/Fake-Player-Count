package org.schema.game.network;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.schema.game.common.data.player.PlayerState;

import deobf.Class1078alN;
import deobf.Class265aAC;

public class StarMadePlayerStats
{
    public static final int ONLINE_ONLY = 1;
    public static final int INFO_PLAYER_DETAILS = 4;
    public static final int INFO_LAST_LOGIN = 8;
    public static final int INFO_LOGGED_IP = 16;
    private static int paramSize;
    public ReceivedPlayer[] receivedPlayers;
    
    public static StarMadePlayerStats decode(final Object[] array, final int n) {
        final int n2 = array.length / StarMadePlayerStats.paramSize;
        final StarMadePlayerStats starMadePlayerStats;
        (starMadePlayerStats = new StarMadePlayerStats()).receivedPlayers = new ReceivedPlayer[n2];
        for (int i = 0; i < n2; ++i) {
            (starMadePlayerStats.receivedPlayers[i] = new ReceivedPlayer()).decode(array, i * StarMadePlayerStats.paramSize, n);
        }
        return starMadePlayerStats;
    }
    
    public static Object[] encode(final Class1078alN class1078alN, int int123) {
        File[] listFiles = new File(Class1078alN.field5054b).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("ENTITY_PLAYERSTATE");
			}
		});
        final long currentTimeMillis = System.currentTimeMillis();
        System.err.println("[StarMadePlayerStates] READING " + listFiles.length + " Player States");
        final ArrayList<PlayerState> list = new ArrayList<PlayerState>();
        for (int i = 0; i < listFiles.length; ++i) {
            try {
                final Class265aAC method2891a = Class265aAC.method2891a(new BufferedInputStream(new FileInputStream(listFiles[i])), true, false);
                final PlayerState playerState;
                (playerState = new PlayerState(class1078alN)).initialize();
                playerState.fromTagStructure(method2891a);
                final String name = listFiles[i].getName();
                playerState.setName(name.substring(19, name.lastIndexOf(".")));
                list.add(playerState);
            }
            catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        System.err.println("[StarMadePlayerStates] READING " + listFiles.length + " Player States took: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
        final Object[] array = new Object[list.size() * StarMadePlayerStats.paramSize];
        for (int j = 0; j < list.size(); ++j) {
            final PlayerState playerState2 = list.get(j);
            final int n = j * StarMadePlayerStats.paramSize;
            final StringBuilder sb = new StringBuilder();
            final ArrayList<Object> list2;
            (list2 = new ArrayList<Object>()).addAll(playerState2.getHosts());
            for (int k = 0; k < list2.size(); ++k) {
                sb.append(list2.get(k));
                if (k < playerState2.getHosts().size() - 1) {
                    sb.append(",");
                }
            }
            array[n] = playerState2.getName();
            array[n + 1] = playerState2.getLastLogin();
            array[n + 2] = playerState2.getLastLogout();
            array[n + 3] = sb.toString();
            try{
            	PrintWriter pw = new PrintWriter(new File("player-stat-out.txt"));
            	pw.println("   "+playerState2.getName()+"  "+ playerState2.getLastLogin()+ "   "+playerState2.getLastLogout()+"  "+sb.toString());
                pw.close();
            }catch(Exception e){
            	e.printStackTrace();
            }
            
        }
        for (int l = 0; l < array.length; ++l) {
            assert array[l] != null : Arrays.toString(array);
        }
        return array;
    }
    
    static {
        StarMadePlayerStats.paramSize = 4;
    }
}
