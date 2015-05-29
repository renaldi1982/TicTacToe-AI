package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class ServerModel {
	
	private static final String SERVERURL = "http://cs2.uco.edu/~gq011/tictactoe/server/";
	
	private static final ServerModel instance = new ServerModel();
	public static ServerModel getInstance() {
		return instance;
	}
	
	private String gameid;
	private String playerid;
	public ServerModel(){
		gameid = "";
		playerid = "";
	}
	
	public String host(){
		gameid = start();
		playerid = connect(gameid);
		
		return gameid;
	}
	public String join(String gameid) {
		this.gameid = gameid;
		playerid = connect(gameid);
		
		return gameid;
	}
	public int getGameStatus() {
		return Integer.parseInt(status(gameid));
	}
	public char[] getGameGrid() {
		char[] grid = grid(gameid);
		return grid;
		//return new char[] {'0','0','0','0','0','0','0','0','0'};
		
		
	}
	public boolean placeMove(int position) {
		System.out.println("Moving to :"+position);
		return move(gameid, playerid, position);
	}
	public boolean getAchi() {
		String mode = mode(gameid);
		if(mode.equals("slide")){
			return true;
		}
		return false;
	}


	
	public String getGameId() {
		return gameid;
	}

	public void setGameId(String gameId) {
		this.gameid = gameId;
	}
	
	
	
	//join myself to game
	
	
	
	
	private String start(){
		String gameid = sendGet(SERVERURL+"?controller=api&method=start");
		return gameid;
	}
	private String connect(String gameid){
		String playerid = sendGet(SERVERURL+"?controller=api&method=connect&gameid="+gameid);
		return playerid;
	}
	private String status(String gameid){
		String status = sendGet(SERVERURL+"?controller=api&method=status&gameid="+gameid);
		return status;
	}
	private String mode(String gameid){
		String mode = sendGet(SERVERURL+"?controller=api&method=mode&gameid="+gameid);
		return mode;
	}
	private boolean move(String gameid, String playerid, int position){
		String ret = sendGet(SERVERURL+"?controller=api&method=move&gameid="+gameid+"&playerid="+playerid+"&position="+position);
		return ret.equals("true");
	}
	private char[] grid(String gameid){
		String json = sendGet(SERVERURL+"?controller=api&method=grid&gameid="+gameid);
		json = json.replaceAll("\\[", "");
		json = json.replaceAll("\\]", "");
		json = json.replaceAll("\"", "");
		String[] stringArray = json.split(",", -1);
		char[] grid = new char[9];
		for(int i=0; i<stringArray.length; i++){
			if(stringArray[i] != "" && stringArray[i].toCharArray().length > 0){
				grid[i] = stringArray[i].toCharArray()[0];
			}
			else{
				grid[i] = 0;
			}
		}
		return grid;
		//return new char[] {'0','0','0','0','0','0','0','0','0'};
	}
	
	private String sendGet(String url) {

		String ret = "";
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			//con.setRequestMethod("GET");
	 
			//add request header
			//con.setRequestProperty("User-Agent", USER_AGENT);
	 
			//int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			ret = response.toString();
			
		} 
		catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}



	


	
	
}
