package objects;

import java.util.ArrayList;


public class MVPD {
	
	private ArrayList<Accounts> accounts = new ArrayList<Accounts>();
	private String name;
	
	
	public MVPD()
	{
		this.name = "";
	}
	public MVPD(String name)
	{
		this.name = name;
	}
	
	public void addAccounts(Accounts account) {
		accounts.add(account);
		
	}
	
	public ArrayList<Accounts> getAccount()
	{
		return accounts;
	}
	
	public String getName()
	{
		return name;
	}
}
