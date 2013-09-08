package com.leo.LicenseMonitorLocalNetwork.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.leo.LicenseMonitorLocalNetwork.staticdata.LicLiteData;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * All the network related utilities
 * 
 * @author leo
 *
 */
public class NetWorkUtil {
	
	/**
	 * Take ssh connection and cmd as a parameters and return
	 * the result by executing this cmd on the server through ssh
	 * 
	 * 
	 * connection
	 * cmd
	 * 
	 * @return result string by ssh executing the cmd on the other machine 
	 */
	public static String[] getCmdOutputStrs(Connection connection, String cmd){
		String[] outputStrArr = new String[]{"", ""};
		Session session = null;
        try {
        	session = connection.openSession();
        	session.execCommand(cmd);
	        InputStream stdout = new StreamGobbler(session.getStdout());
	        InputStream stderr = new StreamGobbler(session.getStderr());

	        InputStreamReader insrout=new InputStreamReader(stdout);
	        InputStreamReader insrerr=new InputStreamReader(stderr);

	        BufferedReader stdoutReader = new BufferedReader(insrout);

	        BufferedReader stderrReader = new BufferedReader(insrerr);

	        while (true)
	        {
	            String line = stdoutReader.readLine();
	            if (line == null)
	            {
	                break;
	            }
	            outputStrArr[0] += line + "\n";
	        }

	        while (true)
	        {
	            String line = stderrReader.readLine();
	            if (line == null)
	            {    break;}
	            outputStrArr[1] += line;
	        }
	        
	        stdoutReader.close();
	        stderrReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //close session and assign session null, for GC to collection it
        session.close();
        session = null;
        
		return outputStrArr;
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param connection
	 *
	 * @return
	 */
	private static String getserverDataFileName(Connection connection){
		String serverDataFileName = null;
		Session session = null;
		
        try {
        	session = connection.openSession();
        	session.execCommand(LicLiteData.CMD_GET_OUT_PUT_FILE_NAME);
        	
	        InputStream stdout = new StreamGobbler(session.getStdout());
	        InputStreamReader insrout=new InputStreamReader(stdout);
	        BufferedReader stdoutReader = new BufferedReader(insrout);



	        String line = stdoutReader.readLine();
	        if (line == null)
	        {
	        	serverDataFileName = line;
	        } 
	        stdoutReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
        //close session and assign session null, for GC to collection it
        session.close();
        session = null;
		
		return serverDataFileName;
	}
	
	public static void executeCmd(Connection connection, String cmd){
		Session session = null;
        try {
        	session = connection.openSession();
        	session.execCommand(cmd);
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //close session and assign session null, for GC to collection it
        session.close();
        session = null;
	}
	
	/**
	 * save lmgrd log remotely and download it to local dir
	 * 
	 * @param connection
	 * @param cmd
	 */
	public static void executeSCPCmd(Connection connection, String cmd, String localDir){
		Session session = null;
		Session session2 = null;
		
		SCPClient scp = null;
        try {
        	session = connection.openSession();
        	scp = connection.createSCPClient();
//minqi
//session.execCommand(cmdMinqi);
        	session.execCommand(cmd);
int i = session.waitForCondition(ChannelCondition.EOF, 5000);
//int i = session.waitUntilDataAvailable(1500);
System.out.println("ChannelCondition.EOF --> " + i);
//System.outen.println("fileName -> " + getserverDataFileName(connection));
        	/*
        	 * if file exist then download...
        	 */
        	
//        	scp.get("/home/leo2/Desktop/log/licliteserver-*", LicLiteData.licLiteDataDir);
        	scp.get("/home/leo/Desktop/log/licliteserver-*", localDir);
//minqi
//scp.get("/tmp/licliteserver-*", LicLiteData.licLiteDataDir);


        	session2 = connection.openSession();
//        	session2.execCommand("rm /home/leo2/Desktop/log/licliteserver-*");
        	session2.execCommand("rm /home/leo/Desktop/log/licliteserver-*");
//minqi
//session2.execCommand("rm /tmp/licliteserver-*");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //close session and assign session null, for GC to collection it
        session.close();
        session = null;
        session2.close();
        session2 = null;
	}
	
	/**
	 * download lmstat file to local tmp dir
	 * 
	 * @param connection
	 * @param cmd
	 * @param localDir
	 */
	public static void executeSCPCmdForNotification(Connection connection, String cmd, String localDir){
		Session session = null;
		Session session2 = null;
		
		SCPClient scp = null;
        try {
        	session = connection.openSession();
        	scp = connection.createSCPClient();

        	session.execCommand(cmd);
int i = session.waitForCondition(ChannelCondition.EOF, 5000);

System.out.println("ChannelCondition.EOF --> " + i);
//System.outen.println("fileName -> " + getserverDataFileName(connection));
        	/*
        	 * if file exist then download...
        	 */
        	
//        	scp.get("/home/leo2/Desktop/log/licliteserver-*", LicLiteData.licLiteDataDir);
        	scp.get("/home/leo/Desktop/log/" + LicLiteData.NOTIFICATION_TMP_FILE, localDir);
//minqi
//scp.get("/tmp/licliteserver-*", LicLiteData.licLiteDataDir);


        	session2 = connection.openSession();
//        	session2.execCommand("rm /home/leo2/Desktop/log/licliteserver-*");
        	session2.execCommand("rm /home/leo/Desktop/log/" + LicLiteData.NOTIFICATION_TMP_FILE);
//minqi
//session2.execCommand("rm /tmp/licliteserver-*");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //close session and assign session null, for GC to collection it
        session.close();
        session = null;
        session2.close();
        session2 = null;
	}
}


