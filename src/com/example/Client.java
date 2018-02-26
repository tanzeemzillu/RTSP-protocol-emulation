package com.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client {
	static String DescribeXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<DESCRIBE>\r\n" + 
			"		<rtsp.request>rtsp://audio.example.com/media.mp3 RTSP/1.0\\\\r\\\\n</rtsp.request>\r\n" + 
			"		<CSeq>2</CSeq>\r\n" + 
			"	</DESCRIBE> \r\n"+			
			"</rtsp>";
	static String SETUPXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<SETUP>\r\n" + 
			"		<rtsp.request>rtsp://audio.example.com/media.mp3 RTSP/1.0\\\\r\\\\n</rtsp.request>\r\n" + 
			"		<CSeq>3</CSeq>\r\n" + 
			"       <Transport>RTP/AVP;unicast;client_port=1025</Transport>\r\n"+
			"	</SETUP> \r\n"+			
			"</rtsp>";
	static String PLAYXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<PLAY>\r\n" + 
			"		<rtsp.request>rtsp://audio.example.com/media.mp3 RTSP/1.0\\\\r\\\\n</rtsp.request>\r\n" + 
			"		<CSeq>4</CSeq>\r\n" + 
			"       <Session>12345678</Session>\r\n"+
			"       <Range>Range: smpte=0:10:00-</Range>\r\n"+
			"	</PLAY> \r\n"+			
			"</rtsp>";
	static String PAUSEXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<PAUSE>\r\n" + 
			"		<rtsp.request>rtsp://audio.example.com/media.mp3 RTSP/1.0\\\\r\\\\n</rtsp.request>\r\n" + 
			"			<CSeq>5</CSeq>\r\n" + 
			"        	<Session>12345678</Session>\r\n"+
			"        	<Range>Range: smpte=0:10:00-</Range>\r\n"+
			"	</PAUSE> \r\n"+			
			"</rtsp>";
	static String TEARDOWNXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<TEARDOWN>\r\n" + 
			"		<rtsp.request>rtsp://audio.example.com/media.mp3 RTSP/1.0\\\\r\\\\n</rtsp.request>\r\n" + 
			"		<CSeq>5</CSeq>\r\n" + 
			"       <Session>12345678</Session>\r\n"+    
			"	</TEARDOWN> \r\n"+			
			"</rtsp>";
			
			@SuppressWarnings({ "null", "resource" })
			public static void main(String[] args) {
				// declaration section
				// smtpClient: our client socket
				// os: output stream
				// is: input stream
				Socket RTSPSocket = null;  
				DataOutputStream os = null;
				DataInputStream is = null;
				// Initialization section:
				// Try to open a socket on port 1028
				// Try to open input and output streams
				try {
					RTSPSocket = new Socket("127.0.0.1", 1028);
					os = new DataOutputStream(RTSPSocket.getOutputStream());
					is = new DataInputStream(RTSPSocket.getInputStream());
				} 
				catch (UnknownHostException e) {
					System.err.println("Don't know about host: hostname");
				} 
				catch (IOException e) {
					System.err.println("Couldn't get I/O for the connection to: hostname");
				}
				// If everything has been initialized then we want to write some data
				// to the socket we have opened a connection to on port 25
				if (RTSPSocket != null && os != null && is != null) {
					try {
            	
						int x= 1;
            	
						while(x == 1){
							System.out.print("Enter DESCRIBE: ");
							Scanner msg = new Scanner(System.in);
							String iString = msg.nextLine();
                 	
							if (iString.equals("DESCRIBE")) {
    	
								os.writeUTF(DescribeXml);
								os.flush();
								String responseLine;
								if ((responseLine = is.readUTF()) != null) {
									System.out.println("Server: " + responseLine);
									System.out.print("Enter SETUP: ");
									msg = new Scanner(System.in);
									iString = msg.nextLine();
									if(iString.equals("SETUP") && responseLine.contains("DESCRIBE_RESPONSE")) {
										os.writeUTF(SETUPXml);
										os.flush();
									}
								}
								if ((responseLine = is.readUTF()) != null) {
									System.out.println("Server response: "+responseLine);
									System.out.print("Enter PLAY: ");
									msg = new Scanner(System.in);
									iString = msg.nextLine();
									if(iString.equals("PLAY")&&responseLine.contains("SETUP_RESPONSE")) {
										os.writeUTF(PLAYXml);
										os.flush();
									}
								}
								if ((responseLine = is.readUTF()) != null) {
									System.out.println("Server response: "+responseLine);
									System.out.print("Enter PAUSE: ");
									msg = new Scanner(System.in);
									iString = msg.nextLine();
									if(iString.equals("PAUSE")&&responseLine.contains("PLAYING_RESPONSE")) {
										os.writeUTF(PAUSEXml);
										os.flush();
									}
								}
								if ((responseLine = is.readUTF()) != null) {
									System.out.println("Server response: "+responseLine);
									System.out.print("Enter TEARDOWN: ");
									msg = new Scanner(System.in);
									iString = msg.nextLine();
									if(iString.equals("TEARDOWN") && responseLine.contains("PAUSE_RESPONSE")) {
										os.writeUTF(TEARDOWNXml);
										os.flush();
									}	
								}
								if ((responseLine = is.readUTF()) != null) {
									System.out.println("Server response: "+responseLine);
								}
								
								if (responseLine.indexOf("Ok") != -1 ) {
									break;
								}
							}
							
							x = 0;
						}
            	
						// clean up:
						// close the output stream
						// close the input stream
						// close the socket     	
						os.close();
						is.close();
						RTSPSocket.close();
            	
						System.out.println("Client Connection Closed");
					} 
					catch (UnknownHostException e) {
						System.err.println("Trying to connect to unknown host: " + e);
					} 
					catch (IOException e) {
						//System.err.println("IOException:  " + e);
					}
				}
			}           
	}