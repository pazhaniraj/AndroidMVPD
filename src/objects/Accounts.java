package objects;

public class Accounts {
	private String username;
	private String password;
	//how do i set up permissions truth table for every account and check it against 
	//the object table
	//private String[] chipTruthTable;
	
	public Accounts()
	{
		this.username = "";
		this.password = "";
		//what should be the chip truth table initial value whats it look like?
		//this table will let us know the user video privelages
	}
	
	public Accounts(String username, String password)
	{
		this.username = username;
		this.password = password;
		//what should be the chip truth table initial value whats it look like?
		//this table will let us know the user video privelages
	}
	public String getUsername()
	{
		return username;
	}
	public String getPassword()
	{
		return password;
	}

}