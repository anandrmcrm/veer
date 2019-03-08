package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class QuickQuote extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;

    public QuickQuote(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "qemailcrm")
    private WebElement mailid;

    @FindBy(id = "minutes")
    private WebElement minutes;

    @FindBy(id = "language")
    private WebElement sourceLang;

    @FindBy(id = "academic_level")
    private WebElement academicLevel;

    @FindBy(id = "service")
    private WebElement categorys;

    @FindBy(id = "file_type")
    private WebElement fileType;

    @FindBy(id = "formatting")
    private WebElement formatting;

    @FindBy(id = "no_of_pages")
    private WebElement pages;

    @FindBy(id = "qphonecrm")
    private WebElement phoneNumber;

    @FindBy(id = "format")
    private WebElement format;

    @FindBy(id = "need_transcript")
    private WebElement needTranscript;

    @FindBy(id = "need_translate")
    private WebElement needTranslate;

    @FindBy(id = "source_language")
    private WebElement sLanguage;

    @FindBy(id = "target_language")
    private WebElement targetLanguage;

    @FindBy(id = "comment")
    private WebElement comment;

    @FindBy(id = "age_group")
    private WebElement ageGroup;

    @FindBy(id = "paper_type")
    private WebElement paperType;

    @FindBy(id = "broadcasts")
    private WebElement broadcasts;

    @FindBy(id = "wordcount")
    private WebElement wordcount;

    @FindBy(id = "Pages")
    private WebElement popupPages;

    @FindBy(id = "NeedTranslation")
    private WebElement needTranslation;

    @FindBy(
        xpath = "//div[@class='col-lg-12']/div[@class='row']/input[@type='submit']")
    private WebElement getQuote;

    public void enterEmail(String email) {
        try {
            enterTestBoxValues(mailid, email);
        } catch (Exception e) {
            System.out.println("Unable to enter a Email ID value " + e);
        }
    }

    public void enterPopUpPages(String page) {
        try {
            enterTestBoxValues(popupPages, page);
        } catch (Exception e) {
            System.out.println("Unable to enter a pages value " + e);
        }
    }

    public void enterMinutes(String minute) {
        try {
            enterTestBoxValues(minutes, minute);
        } catch (Exception e) {
            System.out.println("Unable to enter a minute value " + e);
        }
    }

    public void enterWordCount(String count) {
        try {
            enterTestBoxValues(wordcount, count);
        } catch (Exception e) {
            System.out.println("Unable to enter a wordcount value " + e);
        }
    }

    public void enterPages(String page) {
        try {
            enterTestBoxValues(pages, page);
        } catch (Exception e) {
            System.out.println("Unable to enter a page value " + e);
        }
    }

    public void enterPhoneNumber(String number) {
        try {
            enterTestBoxValues(phoneNumber, number);
        } catch (Exception e) {
            System.out.println("Unable to enter a phone number " + e);
        }
    }

    public void selectLanguageFrom(String sourceLanguage) {
        try {
            selectDropDown(sourceLang, sourceLanguage);

        } catch (Exception e) {
            System.out.println("Unable to select source language " + e);
        }
    }

    public void selectAcademicLevel(String academic) {
        try {

            selectDropDown(academicLevel, academic);

        } catch (Exception e) {
            System.out.println("Unable to select academic level " + e);
        }
    }

    public void selectLanguageTo(String toLanguage) {
        try {
            selectDropDown(targetLanguage, toLanguage);

        } catch (Exception e) {
            System.out.println("Unable to select target language " + e);
        }
    }

    public void selectSourceLanguage(String fromLanguage) {
        try {
            selectDropDown(sLanguage, fromLanguage);

        } catch (Exception e) {
            System.out.println("Unable to select source language " + e);
        }
    }

    public void category(String category) {
        try {
            selectDropDown(categorys, category);
        } catch (Exception e) {
            System.out.println("Unable to select category " + e);
        }
    }

    public void clickGetQuote() {
        try {
            clickElement(getQuote);
        } catch (Exception e) {
            System.out.println("Unable to click Get Quote button " + e);
        }
    }

    public TypingQuote clickTypingGetQuote() {

        try {
            clickElement(getQuote);
        } catch (Exception e) {
            System.out.println("Unable to click Get Quote button " + e);
        }
        TypingQuote typingQuote = new TypingQuote(driver);
        return typingQuote;
    }

    public void selectFormatting(String type) {
        try {
            selectDropDown(formatting, type);
        } catch (Exception e) {
            System.out.println("Unable to select formatting type " + e);
        }
    }

    public void selectFileType(String file) {
        try {
            selectDropDown(fileType, file);
        } catch (Exception e) {
            System.out.println("Unable to select file type " + e);
        }
    }

    public void selectFormat(String type) {
        try {
            selectDropDown(format, type);
        } catch (Exception e) {
            System.out.println("Unable to select format " + e);
        }
    }

    public void selectAgeGroup(String ageGroups) {
        try {
            selectDropDown(ageGroup, ageGroups);
        } catch (Exception e) {
            System.out.println("Unable to select age groups " + e);
        }
    }

    public void selectPaperType(String paper) {
        try {
            selectDropDown(paperType, paper);
        } catch (Exception e) {
            System.out.println("Unable to select paper type " + e);
        }
    }
    public void selectTranscript() {

        clickElement(needTranscript);
    }

    public void selectTranslate() {

        clickElement(needTranslate);
    }


    public void selectBroadcast() {

        clickElement(broadcasts);
    }

    public void selectTranslation() {

        clickElement(needTranslation);
    }

    public void selectGender(String gender) {

        List<WebElement> list = driver.findElements(By.name("gender"));
        int count = list.size();
        for (int i = 0; i < count; i++) {
            String sValue = list.get(i).getAttribute("value");
            if (sValue.equalsIgnoreCase(gender)) {

                list.get(i).click();
                break;
            }
        }
    }

    public void enterComments(String comments) {
        try {
            enterTestBoxValues(comment, comments);
        } catch (Exception e) {
            System.out.println("Unable to enter a comment " + e);
        }
    }

    public void clickPopupSubmit() {
        clickElement(driver.findElement(By.xpath(
            "//div[@class='modal-footer']/..//button[contains(text(),'Submit')]")));
    }

}
