package com.utility;

public enum Errorcode {
	EC_SUCCESS(0),
	
	/*error codes for login and signup*/
	EC_USER_NOT_VERIFIED(1),
	EC_USER_NOT_AUTHENTICATED(2),
	EC_PROFILE_UPDATE_FAILED(3),
	EC_FILE_READ_FAILED(4),
	EC_UNABLE_TO_SEND_EMAIL(5),
	EC_USER_REGISTRATION_FAILED(6),
	EC_USER_VERIFICATION_FAILED(7),
	EC_RESET_PASSWORD_FAILED(8),
	EC_ACCOUNT_UNKNOWN(9),
	EC_INCORRECT_CREDENTIALS(10),
	EC_ACCOUNT_LOCKED(11),
	
	EC_FAILED_DB_UPDATE(12),
	
	EC_DATA_NOT_FOUND(13),
	EC_MAX_ERROR(255);
	
	
	public static String errmsgstr[]=
		{
			"The requested operation was successfully completed",						
			"User is not verified yet",
			"User is not authenticated",
			"Profile update failed",
			"File read failed",
			"New User registration failed",
			"Failed to send an email for verification",
			"User verification is failed",
			"Password reset failed",
			"User credentials is unknown",
			"User name or password is incorrect",
			"User Account is locked",
			"Unable to update record",
			"Information not found"
		};
	
	private final int value;
	 
	private Errorcode(int value) {
		this.value = value;
	}

	public int getValue() {
	     return value;
	}
	
	
	public static void main()
	{
		System.out.println(Errorcode.EC_SUCCESS);
	}
}
