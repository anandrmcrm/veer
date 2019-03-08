package com.vanan.CRM.PageObjects.WholeSitePages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.vanancrm.PageObjects.MainPages.AccessingElement;

public class ViewTicketDetails extends AccessingElement {

	private WebDriver driver;

	public ViewTicketDetails(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	private String allTicketParentElement = "//div[@id='ajax-dynamic-form']/div[@class='form-group    ']";

	private String allTicketStaticParentElement = "//form[@id='process_form']/div[@class='form-group']";

	public String getRunTimeTicketFieldValues(String field) {
		List<WebElement> elements = driver
				.findElements(By.xpath(allTicketParentElement));
		String value = "";
		WebElement eachElement;
		for (int i = 1; i <= elements.size(); i++) {

			eachElement = driver.findElement(
					By.xpath(allTicketParentElement + "[" + i + "]/label"));
			//System.out.println(eachElement.getText() + "))))))))))");

			if (eachElement.getText().contains(field)) {
				value = driver
						.findElement(By.xpath(
								allTicketParentElement + "[" + i + "]/div/p"))
						.getText();
				break;
			}

		}
		return value;
	}

	public String getStaticTicketFieldValues(String field) {
		List<WebElement> elements = driver
				.findElements(By.xpath(allTicketStaticParentElement));
		String value = "";
		WebElement eachElement;
		for (int i = 1; i <= elements.size(); i++) {
			try {
				eachElement = driver.findElement(By.xpath(
						allTicketStaticParentElement + "[" + i + "]/label"));
				// System.out.println(
				// eachElement.getText() + "}}}}}}}}}}}}}}}}}}}}}}}}");
				if (eachElement.getText().contains(field)) {
					value = driver.findElement(By.xpath(
							allTicketStaticParentElement + "[" + i + "]/div/p"))
							.getText();
					break;
				}
			} catch (Exception e) {
				continue;
				// TODO: handle exception
			}

		}
		return value;
	}

    public String getServiceValues() {

        WebElement eachElement = driver.findElement(
            By.xpath("//form[@id='process_form']/div[@class='form-group'][8]/p"));

        return eachElement.getText();
    }

    public String getVoiceDetails() {

        WebElement eachElement = driver.findElement(
            By.xpath("//div[@id='ajax-dynamic-form']/div[@class='form-group" +
                "']/div/p"));

        return eachElement.getText();
    }

	public String getAgentCodeFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   agent_code ']/div/p"));

		return eachElement.getText();
	}

	public String getHowFrequentlyNeedFieldValues(String field) {

		WebElement eachElement = driver.findElement(By
				.xpath("//div[@class='form-group   service_frequency ']/div/p"));

		return eachElement.getText();
	}

	public String getFileCommentsFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   filecomments ']/div/p"));

		return eachElement.getText();
	}

	public String getMailingFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   mailing_option ']/div/p"));

		return eachElement.getText();
	}

	public String getMailingAddressFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   mail_address ']/div/p"));

		return eachElement.getText();
	}

	public String getSpeakerCountFieldValues(String field) {

		WebElement eachElement = driver.findElement(By
				.xpath("//div[@class='form-group   speakers_count_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getTimeCodeFieldValues(String field) {

		WebElement eachElement = driver.findElement(By
				.xpath("//div[@class='form-group   timecode_freq_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getPaymentTypeFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   payment_type ']/div/p"));

		return eachElement.getText();
	}

	public String getPaymentDurationFieldValues(String field) {

		WebElement eachElement = driver.findElement(By.xpath(
				"//div[@class='form-group lbl_pay_duration  payment_duration ']/div/p"));

		return eachElement.getText();
	}

	public String getPaymentModeFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   payment_mode ']/div/p"));

		return eachElement.getText();
	}

	public String getPaypalFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   paypal_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getTwoCheckoutFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   2checkout_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getWiredFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   wired_cls ']/div/p"));

		return eachElement.getText();
	}

	public String getCommentsFieldValues(String field) {

		WebElement eachElement = driver.findElement(
				By.xpath("//div[@class='form-group   comments ']/div/p"));

		return eachElement.getText();
	}
}
