package com.interzonedev.herokuspringdemo.users;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.interzonedev.herokuspringdemo.functionaltest.HerokuSpringDemoAbstractFunctionalTest;
import com.interzonedev.sprintfix.dataset.DataSet;

public class ViewAllUsersFunctionalTest extends HerokuSpringDemoAbstractFunctionalTest {

	@Test
	@DataSet(filename = "dataset/users/multiUsersDataSet.xml", dataSourceBeanId = "dataSource")
	public void testViewAllUsersWithUsers() {
		log.debug("testViewAllUsersWithUsers");

		openPageAndTestHeader("users", "View All Users");

		List<WebElement> userContainers = driver.findElements(By.cssSelector("div.userContainer"));
		Assert.assertNotNull(userContainers);
		Assert.assertEquals(2, userContainers.size());
	}

	@Test
	@DataSet(filename = "dataset/users/emptyUsersDataSet.xml", dataSourceBeanId = "dataSource")
	public void testViewAllUsersNoUsers() {
		log.debug("testViewAllUsersNoUsers");

		openPageAndTestHeader("users", "View All Users");

		WebElement viewAllUsersContainers = driver.findElement(By.cssSelector("div.viewAllUsers"));
		Assert.assertNotNull(viewAllUsersContainers);
		Assert.assertTrue(viewAllUsersContainers.getText().contains("There are no results."));
	}

	@Test
	@DataSet(filename = "dataset/users/multiUsersDataSet.xml", dataSourceBeanId = "dataSource")
	public void testViewAllUsersDeleteUserAccept() {
		log.debug("testViewAllUsersDeleteUserAccept");

		openPageAndTestHeader("users", "View All Users");

		List<WebElement> userContainersBefore = driver.findElements(By.cssSelector("div.userContainer"));
		Assert.assertNotNull(userContainersBefore);
		Assert.assertEquals(2, userContainersBefore.size());

		List<WebElement> deleteLinks = driver.findElements(By.cssSelector("a.control-delete"));
		Assert.assertNotNull(deleteLinks);
		Assert.assertEquals(2, deleteLinks.size());

		WebElement firstDeleteLink = deleteLinks.get(0);
		firstDeleteLink.click();

		Alert confirmation = driver.switchTo().alert();
		String confirmationText = confirmation.getText();
		Assert.assertEquals("Are you sure you want to delete the user?", confirmationText);

		confirmation.accept();

		List<WebElement> userContainersAfter = functionalTestHelper.waitForAndGetElements(driver,
				By.cssSelector("div.userContainer"));
		Assert.assertNotNull(userContainersAfter);
		Assert.assertEquals(1, userContainersAfter.size());
	}

	@Test
	@DataSet(filename = "dataset/users/multiUsersDataSet.xml", dataSourceBeanId = "dataSource")
	public void testViewAllUsersDeleteUserDismiss() {
		log.debug("testViewAllUsersDeleteUserDismiss");

		openPageAndTestHeader("users", "View All Users");

		List<WebElement> userContainersBefore = driver.findElements(By.cssSelector("div.userContainer"));
		Assert.assertNotNull(userContainersBefore);
		Assert.assertEquals(2, userContainersBefore.size());

		List<WebElement> deleteLinks = driver.findElements(By.cssSelector("a.control-delete"));
		Assert.assertNotNull(deleteLinks);
		Assert.assertEquals(2, deleteLinks.size());

		WebElement firstDeleteLink = deleteLinks.get(0);
		firstDeleteLink.click();

		Alert confirmation = driver.switchTo().alert();
		String confirmationText = confirmation.getText();
		Assert.assertEquals("Are you sure you want to delete the user?", confirmationText);

		confirmation.dismiss();

		List<WebElement> userContainersAfter = functionalTestHelper.waitForAndGetElements(driver,
				By.cssSelector("div.userContainer"));
		Assert.assertNotNull(userContainersAfter);
		Assert.assertEquals(2, userContainersAfter.size());
	}
}