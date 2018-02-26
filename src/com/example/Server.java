package com.example;

import java.io.*;
import java.net.*;

public class Server {
	
	static String desc_resp_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<DESCRIBE_RESPONSE>\r\n" + 
			"		<rtsp.response>RTSP/1.0 200 OK\\r\\n</rtsp.response>\r\n" + 
			"		<CSeq>2</CSeq>\r\n" + 
			"		<rtsp.Content-Type>application/sdp</rtsp.Content-Type>\r\n" + 
			"		<rtsp.Content-Length>460</rtsp.Content-Length>\r\n" + 
			"	</DESCRIBE_RESPONSE>\r\n" + 
			"</rtsp>";
	static String SETUPResXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<SETUP_RESPONSE>\r\n" + 
			"		<rtsp.response>rtsp://audio.example.com/media.mp3 RTSP/1.0\\\\r\\\\n</rtsp.request>\r\n" + 
			"		<CSeq>3</CSeq>\r\n" + 
			"       <Transport>RTP/AVP;unicast;client_port=1025;server_port=1025;ssrc=1234ABCD</Transport>\r\n"+
			"       <Session>12345678</Session>\r\n"+
			"	</SETUP_RESPONSE> \r\n"+			
			"</rtsp>";
	static String PLAYINGXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<PLAYING_RESPONSE>\r\n" + 
			"		<rtsp.response>PLAYING</rtsp.response>\r\n" + 
			"		<CSeq>4</CSeq>\r\n" + 
			//"        <Range>   Range: smpte=0:10:00-</Range>\r\n"+
			"	</PLAYING_RESPONSE> \r\n"+			
			"</rtsp>";
	static String PAUSEXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<PAUSE_RESPONSE>\r\n" + 
			"		<rtsp.response>PAUSE</rtsp.response>\r\n" + 
			"		<CSeq>5</CSeq>\r\n" + 
			//"        <Range>   Range: smpte=0:10:00-</Range>\r\n"+
			"	</PAUSE_RESPONSE> \r\n"+			
			"</rtsp>";
	static String TEARDOWNResXml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n" + 
			"<rtsp>\r\n" + 
			"	<TEARDOWN_RESPONSE>\r\n" + 
			"		<rtsp.response>RTSP/1.0 200 OK\\r\\n</rtsp.response>\r\n" + 
			"		<CSeq>5</CSeq>\r\n" + 
			"	</TEARDOWN_RESPONSE>\r\n" + 
			"</rtsp>";

	public static void main(String[] args) {
		
		ServerSocket RTSPServer = null;
        String line;
        DataInputStream is;
        DataOutputStream os;
        Socket clientSocket = null;
        
        try {
            RTSPServer = new ServerSocket(1028);
         }
         catch (IOException e) {
            System.out.println(e);
         }
        
        try {
            clientSocket =RTSPServer.accept();
            is = new DataInputStream(clientSocket.getInputStream());
            os = new DataOutputStream(clientSocket.getOutputStream());
            
            int X = 1; 
            
            while (X == 1) {
            	
            	if ((line=is.readUTF())!= null) {
                	if (line.contains("DESCRIBE")) {
                		System.out.println("Client : " + line);
                		os.writeUTF(desc_resp_xml);
                		os.flush();
                	}
            	}
            	if ((line=is.readUTF())!=null) {
            		
            		if(line.contains("SETUP")) {
                		System.out.println("Client : " + line);
                		os.writeUTF(SETUPResXml);
                		os.flush();
                		
                	}
					
				}
            	
            	if ((line=is.readUTF())!=null) {
            		
            		if(line.contains("PLAY")) {
                		System.out.println("Client : " + line);
                		os.writeUTF(PLAYINGXml);
                		os.flush();
                	}
					
				}
            	
            	if ((line=is.readUTF())!=null) {
            		
            		if(line.contains("PAUSE")) {
                		System.out.println("Client : " + line);
                		os.writeUTF(PAUSEXml);
                		os.flush();
                	}
					
				}
            	
            	if ((line=is.readUTF())!=null) {
            		
            		if(line.contains("TEARDOWN")) {
                		System.out.println("Client : " + line);
                		os.writeUTF(TEARDOWNResXml);
                		os.flush();
                	}
            		else {
            			System.out.println("Client : " + line);
                		os.writeUTF(PLAYINGXml);
                		os.flush();
            		}
				}
            	
            	X = 0;
				
			}
            
            is.close();
            os.close();
            clientSocket.close();
            System.out.println("Server Connection Closed");
        }
        
        catch (IOException e) {
            
         }
	}
}

      
   
            

