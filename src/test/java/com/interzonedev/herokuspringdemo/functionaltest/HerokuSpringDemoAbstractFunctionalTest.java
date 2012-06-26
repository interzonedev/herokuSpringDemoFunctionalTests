package com.interzonedev.herokuspringdemo.functionaltest;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.test.context.ContextConfiguration;

import com.interzonedev.sprintfix.dataset.dbunit.DbUnitDataSetTester;
import com.interzonedev.spunkfix.AbstractFunctionalTest;
import com.interzonedev.spunkfix.FunctionalTestProperties;
import com.interzonedev.spunkfix.driver.Browser;

@ContextConfiguration(locations = { "classpath:com/interzonedev/herokuspringdemo/spring/applicationContext-functionalTest.xml" })
public abstract class HerokuSpringDemoAbstractFunctionalTest extends AbstractFunctionalTest {

	protected static List<String> USERS_IGNORE_COLUMN_NAMES = Arrays.asList(new String[] { "id", "time_created",
			"time_updated" });

	protected Log log = LogFactory.getLog(getClass());

	@Inject
	private Properties functionalTestProperties;

	@Inject
	protected DataSource dataSource;

	@Inject
	protected DbUnitDataSetTester dbUnitDataSetTester;

	private FunctionalTestProperties functionalTestPropertiesValues;

	@PostConstruct
	public void init() {
		String applicationUrl = functionalTestProperties.getProperty("applicationUrl");
		String browserValue = functionalTestProperties.getProperty("browser");
		String elementWaitTimeoutInSecondsValue = functionalTestProperties.getProperty("elementWaitTimeoutInSeconds");

		Browser browser = Browser.getById(browserValue);
		Long elementWaitTimeoutInSeconds = Long.parseLong(elementWaitTimeoutInSecondsValue);

		functionalTestPropertiesValues = new FunctionalTestProperties(browser, applicationUrl,
				elementWaitTimeoutInSeconds);
	}

	@Override
	protected FunctionalTestProperties getFunctionalTestProperties() {
		return functionalTestPropertiesValues;
	}

	protected void openPageAndTestHeader(String url, String headerText) {
		log.debug("openPageAndTestHeader");

		browserOperations.openPage(driver, url);

		confirmPageLoadAndTestHeader(headerText);
	}

	protected void confirmPageLoadAndTestHeader(String headerText) {
		log.debug("confirmPageLoadAndTestHeader");

		confirmPageLoad();

		testHeader(headerText);
	}

	protected void confirmPageLoad() {
		log.debug("confirmPageLoad");

		WebElement contentContainer = browserOperations.waitForAndGetElement(driver, By.id("contentContainer"));
		Assert.assertNotNull(contentContainer);
	}

	protected void testHeader(String headerText) {
		log.debug("testHeader");

		WebElement pageHeader = driver.findElement(By.cssSelector("div#contentContainer div.pageHeader"));
		Assert.assertNotNull(pageHeader);
		Assert.assertTrue(pageHeader.getText().endsWith(headerText));
	}

	protected void testCurrentUrlEndsWith(String urlEnding) {
		log.debug("testCurrentUrlEndsWith");

		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.endsWith(urlEnding));
	}
}
