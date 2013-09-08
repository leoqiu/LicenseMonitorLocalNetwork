package com.leo.LicenseMonitorLocalNetwork.networks;

import java.io.IOException;

import ch.ethz.ssh2.Connection;

/**
 * Get a authenticated ssh connection
 * 
 * @author leo
 *
 */
public class SshManager {
	private String serverName;
	private String userName;
	private String passWord;
	
	public SshManager(String serverName, String userName, String passWord) {
		this.serverName = serverName;
		this.userName = userName;
		this.passWord = passWord;
	}
	
	/**
	 * Get a ssh conn
	 */
	private Connection getSshConn(){
		return new Connection(serverName);
	}
	
	/**
	 * Get a authenticated ssh conn
	 * 
	 *  while getting the autheticated connection
	 * @return connection
	 */
	public Connection getAuthenticatedSshConn(int timeOut){

        boolean isAuthenticated = false;
		int connectionAttempts = 0;
		
System.out.println("#1:    " + serverName + " "  + userName+ " " + passWord);

		Connection conn = getSshConn();
		connectionAttempts++;
System.out.println("#2:    " + conn);
System.out.println("#3:    " + connectionAttempts);      

        try {
        	/*
        	 * define connection timeout
        	 */
        	conn.connect(null, timeOut, 0);
			isAuthenticated = conn.authenticateWithPassword(userName, passWord);
System.out.println("isAuthenticated ---> : " + isAuthenticated);
			if (isAuthenticated == false)
	            throw new IOException("Authentication failed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        if(isAuthenticated)
        	return conn;
        else
        	return null;
        
	}
}
