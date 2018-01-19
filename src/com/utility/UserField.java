package com.utility;

public enum UserField {
	USERNAME(0),MOBILE(1),EMAIL(2),UUID(3);
	
	private final int value;
	 
	    private UserField(int value) {
		this.value = value;
	}

		public int getValue() {
	        return value;
	    }
};
