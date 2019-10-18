package aml.utils;

public class Queries {

	
	public static final String updateIsWaitingFlag = "UPDATE partner_pairing " +
			"SET iswaiting='NO' " +
			"WHERE myid=?";
	
	/*
	 * This Query is to see if any director is waiting 
	 */
	public static final String checkPartnerAssigned = "SELECT partnerid, pairid from partner_pairing WHERE " +
			"myid=? AND iswaiting='YES'";
	
		
	/*
	 * This Query is to see if any director is waiting 
	 */
	public static final String checkDirectorPresent = "SELECT myid from partner_pairing WHERE " +
			"myid=? AND partnerid IS NULL AND iswaiting='YES'";
	
	/*
	 * This Query is to update the database indicating that the user is alone. 
	 * Set: Partner ID, TimeStamp, PartnerIDassigned and the DIRECTOR ID in the end
	 */
	public static final String updateUserStatusOnNoPartners = "INSERT INTO partner_pairing " +
			"(myid, iswaiting) VALUES  (?, 'YES') ";
	
	/*
	 * This Query is to update the database indicating that the partners have been paired. 
	 * Set: Partner ID, TimeStamp, pairid and the DIRECTOR ID in the end
	 */
	public static final String updatePairIDPartnerID = "UPDATE partner_pairing " +
			"SET partnerid=?, timepaired=?, pairid=? " +
			"WHERE myid=? AND iswaiting='YES'";
	
	/*
	 * Fetch the users who are currently waiting for a partner
	 */
	public static final String checkAnyoneWaitingQuery = "SELECT myid from partner_pairing WHERE " +
			"iswaiting='YES'";
	
	/*
	 * Set parameter value of just set_id;
	 */
	public static final String pullSetIDQuestionsQuery = "SELECT qid, setid, qnum, object_1, object_2, object_3," +
			" object_4, object_5, object_6, object_7, object_8, solution, comments FROM coref_questions WHERE " +
			"setid=?";
	
	/*
	 * Set parameter value of the userid and the partnerid. This is to fetch the unique question per user.
	 */
	public static final String pullCoveredQuestionsQuery = "SELECT myid, partnerid, qid, role, my_event," +
			" partner_event, my_ip, partner_ip FROM players where myid=? AND partnerid=?";
	
	public static final String pullUserPresent = "SELECT myid, partnerid FROM partner_pairing WHERE "
			+ "myid = ? OR partnerid = ?";
	
	public static final String pullUsersWhoHavePlayed = "SELECT userid FROM participated";
}
