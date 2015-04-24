package testCasesFSGO;

import java.util.ArrayList;
import objects.Accounts;
import objects.MVPD;
import org.junit.Test;
import driver.AndroidLaunch;

public class InitStates extends AndroidLaunch {

	@Test
	public void TestScenario() throws InterruptedException {
		System.out.println(mvpd.size());
		for(MVPD mv :mvpd)
			System.out.println(mv.toString());
		for (MVPD mvpds : mvpd) {
			System.out.println("Hello");
			System.out.println("Name : " + mvpds.getName());
			accountLooper(mvpds.getAccount(), mvpds.getName());
		}
		
	}

	private void accountLooper(ArrayList<Accounts> accounts, String accountName)
			throws InterruptedException {

		for (Accounts account : accounts) {
			if (loginFlag)
				amTest.loginAccountSettings(accountName);
			loginFlag = amTest.login(account);
			System.out.println(account.getUsername() + " Login: " + loginFlag);
			if (loginFlag) {
				amTest.playVideos();
				amTest.logout();
			}

		}

	}
	
	long totalTime;
	AbstractActions amTest = new AbstractActions();
	boolean loginFlag = true;

	
}