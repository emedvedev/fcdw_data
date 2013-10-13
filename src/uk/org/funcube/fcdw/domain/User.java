package uk.org.funcube.fcdw.domain;

public interface User {

	void setPassword(String hashPassword);

	void setEnabled(boolean state);

	String getAuthKey();

}
