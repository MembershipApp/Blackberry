package com.infostretch.isosbb.db;

import com.infostretch.isosbb.models.Username;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;

/***
 * 
 * This Class is responsible for storing and retrinvig the username. The username is stored persistently.
 * 
 * @author Amit Gupta
 *
 */
public class LocalDB {
	static private Username username;
	static PersistentObject persist;

	static {
		long KEY = 0xa3b3159478f59a29L;
		persist = PersistentStore.getPersistentObject(KEY);
		username = (Username) persist.getContents();
		if (username == null) {
			username = new Username();
			persist.setContents(username);
			persist.commit();
		}
	}

	public void setUsername(Username username) {
		LocalDB.username.setUsername(username.getUsername());
		persist.commit();
	}
	
	public String getUsername() {
		if(username != null) {
			return username.getUsername();
		}
		return "";
	}
	
	public void clearDB() {
		username.setUsername("");
	}
}