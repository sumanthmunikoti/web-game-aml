package aml.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import aml.utils.FileUtils;
import aml.utils.Queries;

public class ValidateAndPair {


	public static boolean hasPlayerEverPlayedTheGame(String dbURL, 
			String dbUsername, String dbPass, 
			String uid) {
		Connection connect = null;
		PreparedStatement pstmt = null;
		ArrayList<String> users = new ArrayList<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
					.getConnection(dbURL+
							"?user="+dbUsername+"&password="+dbPass);

			pstmt = connect.prepareStatement(Queries.pullUsersWhoHavePlayed);

			ResultSet res = pstmt.executeQuery();
			while (res.next()) { 
				users.add(res.getString(1).toUpperCase());
			}

		} catch (Exception e) {
			//FileUtils.writeToFileAlreadyExisting("seethis.log", e.getMessage());
			e.printStackTrace();
			return false;
		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}

		}
		//System.out.println("USERS IN THE SYSTEM : "+users);
		if (users.contains(uid.toUpperCase())) {
			return true;
		} else {
			return false;
		}
	}


	public static boolean hasPlayerPlayed(String dbURL, String dbUsername, String dbPass, 
			String uid) {
		Connection connect = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
					.getConnection(dbURL+
							"?user="+dbUsername+"&password="+dbPass);

			pstmt = connect.prepareStatement(Queries.pullUserPresent);
			pstmt.setString(1, uid);
			pstmt.setString(2, uid);

			ResultSet res = pstmt.executeQuery();
			if (res.next()) { 
				return true;
			}

		} catch (Exception e) {
			FileUtils.writeToFileAlreadyExisting("seethis.log", e.getMessage());
			e.printStackTrace();
		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}

		}
		return false;
	}


	/* Returns : WAIT, PROCEED OR THE ROLE ASSIGNED
	 * WAIT means, the user should keep waiting.
	 * ROLE assigned == DIRECTOR means, come back 
	 * ROLE assigned == MATCHER means, come back // NEVER OCCURS. INVALID
	 * PROCEED means, move to the next servlet [PROCEED#ROLE#USERID#PARTNERID#PAIRID]
	 * 
	 * It is the caller's responsibility to save the ROLE ASSIGNED and pass it
	 * on the next callback. 
	 */
	public static synchronized String validatePairCreateRoles(String userID, 
			String myRole, String dbSchema, String dbUsername, String dbPass, String dbURL){
		Connection connect = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		String action = "INVALID";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager
					.getConnection(dbURL+
							"?user="+dbUsername+"&password="+dbPass);

			if(myRole.equals("NONE")) {
				pstmt = connect.prepareStatement(Queries.checkAnyoneWaitingQuery);
				resultSet = pstmt.executeQuery();
				// If anyone is in waiting then isWaiting=true. If no one is waiting then iswaiting=false;
				boolean isWaiting = resultSet.next();
				//FileUtils.writeToFileAlreadyExisting("seethis.log", myRole+" : ISWAITING = "+isWaiting);				
				if(!isWaiting) {
					//ASSIGN ROLE OF A DIRECTOR
					PreparedStatement pstmtNoPartner = 
							connect.prepareStatement(Queries.updateUserStatusOnNoPartners);					
					pstmtNoPartner.setString(1, userID);
					pstmtNoPartner.executeUpdate();
					FileUtils.writeToFileAlreadyExisting("seethis.log", "EXECUTING : "+Queries.updateUserStatusOnNoPartners + " ID: "+userID);
					action = "DIRECTOR";
				} else {
					//ASSIGN ROLE OF A MATCHER AND MOVE ON
					String userWaiting = resultSet.getString("myid");
					// Check if the waiting person is not me
					if(userWaiting.equals(userID)) {
						action = "WAIT";
					}

					//SEEMS LIKE SOME ONE IS WAITING AND IT IS NOT ME. THEREFORE I AM A MATCHER
					//AND I WILL UPDATE HIS ROW ENTRY WITH MY ID AND PAIRID AND TIME
					pstmt = connect.prepareStatement(Queries.updatePairIDPartnerID);
					String pairID = userID+""+userWaiting+""+(System.currentTimeMillis()%1000);
					Date date = new Date(); 
					pstmt.setString(1, userID);
					pstmt.setString(2, new Time(date.getTime()).toString());
					pstmt.setString(3, pairID);
					pstmt.setString(4, userWaiting);
					//FileUtils.writeToFileAlreadyExisting("seethis.log", "EXCECUTING"+Queries.updatePairIDPartnerID);
					pstmt.executeUpdate();
					action = "PROCEED#MATCHER#"+userID+"#"+userWaiting+"#"+pairID;
				}
			} else if(myRole.equals("DIRECTOR")) {
				pstmt = connect.prepareStatement(Queries.checkPartnerAssigned);
				pstmt.setString(1, userID);
				resultSet = pstmt.executeQuery();
				boolean isPartnerAssigned = false;
				if(resultSet.next()) {
					String userWaiting = resultSet.getString("partnerid");
					String pairID = resultSet.getString("pairid");
					FileUtils.writeToFileAlreadyExisting("seethis.log", myRole+" : UW = "+userWaiting + ":: PID :" +pairID);
					if(userWaiting!=null && pairID!=null) {
						isPartnerAssigned = true;
					}
				}
				//FileUtils.writeToFileAlreadyExisting("seethis.log", myRole+" : ISPARTNERASSIGNED = "+isPartnerAssigned);
				if(!isPartnerAssigned) {
					// CONTINUE WAITING
					action = "WAIT";
				} else {
					// UPDATE TABLE ISWAITING FLAG AND MOVE ON
					String userWaiting = resultSet.getString("partnerid");
					String pairID = resultSet.getString("pairid");					
					pstmt = connect.prepareStatement(Queries.updateIsWaitingFlag);
					pstmt.setString(1, userID);
					pstmt.executeUpdate();
					FileUtils.writeToFileAlreadyExisting("seethis.log", "EXCECUTING : "+Queries.updateIsWaitingFlag + " ID: "+userID);
					action = "PROCEED#DIRECTOR#"+userID+"#"+userWaiting+"#"+pairID;
				}
			} else {
				action = "INVALID";
			}

		} catch (Exception e) {
			FileUtils.writeToFileAlreadyExisting("seethis.log", userID+" : ERROR : "+e.getMessage());
			e.printStackTrace();
		} finally {

			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {

			}

		}
		return action;
	}

}
