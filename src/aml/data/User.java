package aml.data;

public class User {

	private String userID;
	// Role can be either NULL, "DIRECTOR" or "MATCHER"
//	private String role;
	// Round number
//	private String currentRound;
//	private String prevRound;
//	private String isRepeatUser;
	//Partner info
	private String partnerID;
	private String pairID;
	
	public User() {
	
	}
	
	public User(String userID, String partnerID, String pairID) {
		super();
		this.userID = userID;
//		this.role = role;
//		this.currentRound = currentRound;
//		this.prevRound = prevRound;
//		this.isRepeatUser = isRepeatUser;
		this.partnerID = partnerID;
		this.pairID = pairID;
	}


	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getPairID() {
		return pairID;
	}

	public void setPairID(String pairID) {
		this.pairID = pairID;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
//	public String getRole() {
//		return role;
//	}
//	public void setRole(String role) {
//		this.role = role;
//	}
//	public String getCurrentRound() {
//		return currentRound;
//	}
//	public void setCurrentRound(String currentRound) {
//		this.currentRound = currentRound;
//	}
//	public String getPrevRound() {
//		return prevRound;
//	}
//	public void setPrevRound(String prevRound) {
//		this.prevRound = prevRound;
//	}
//	public String getIsRepeatUser() {
//		return isRepeatUser;
//	}
//	public void setIsRepeatUser(String isRepeatUser) {
//		this.isRepeatUser = isRepeatUser;
//	}
	
}
