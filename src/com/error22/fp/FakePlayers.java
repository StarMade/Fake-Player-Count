package com.error22.fp;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Observable;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.schema.schine.network.RegisteredClientOnServer;
import org.schema.schine.network.server.ServerController;

import com.google.common.io.Files;

import deobf.Class1078alN;
import io.codearte.jfairy.Fairy;

public class FakePlayers {
	private static FakePlayers INSTANCE;
	private Class1078alN serverState;
	private ServerController serverController;
	private int unnamedPlayers;

	public void init(Class1078alN class1078alN) {
		try {
			
			System.err.println("[E22-FP] Init");
			serverState = class1078alN;
			serverController = (ServerController) class1078alN.method3371getController();
			
			JSONObject obj = (JSONObject) new JSONParser().parse(Files.toString(new File("fakePlayers.json"), Charset.defaultCharset()));
			
			unnamedPlayers = (int) (long) obj.get("unnamedPlayers");
			int namedPlayers = (int) (long) obj.get("namedPlayers");
			int randomPlayers = (int) (long) obj.get("randomPlayers");
			int playerCount = namedPlayers+ (new Random().nextInt(randomPlayers));
			System.err.println("[E22-FP] Config Loaded (namedPlayers "+namedPlayers+", namedPlayers "+namedPlayers+", randomPlayers "+randomPlayers+", playerCount "+playerCount+")");
			
			Fairy fairy = Fairy.create();
			for (int i = 0; i < playerCount; i++) {
				String name = fairy.person().username();
				System.out.println("[F22-FP] Adding Player ("+i+"/"+playerCount+") " + name);
				addPlayer(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addPlayer(String username) {
		addPlayer(new FakePlayer(serverState.getNextFreeObjectId(), username, serverState));
	}

	private void addPlayer(RegisteredClientOnServer client) {
		try {
			if(serverController.onLoggedIn(client) == 0){
				serverState.getClients().put(client.getId(), client);

				Method meth = Observable.class.getDeclaredMethod("setChanged");
				meth.setAccessible(true);
				meth.invoke(serverController);
				serverController.notifyObservers();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getUnnamedPlayers() {
		return unnamedPlayers;
	}

	public static FakePlayers getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FakePlayers();
		}
		return INSTANCE;
	}

}
