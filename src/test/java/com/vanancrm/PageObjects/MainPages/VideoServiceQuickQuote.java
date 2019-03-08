package com.vanancrm.PageObjects.MainPages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VideoServiceQuickQuote extends AccessingElement {

    private WebDriver driver;
    private JavascriptExecutor js;

    public VideoServiceQuickQuote(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    @FindBy(id = "qemail")
    private WebElement mailid;

    @FindBy(id = "qname")
    private WebElement name;

    @FindBy(id = "qcountrys")
    private WebElement country;

    @FindBy(id = "qacode")
    private WebElement areaCode;

    @FindBy(id = "qphone")
    private WebElement phoneNumber;

    @FindBy(id = "vmcasual")
    private WebElement maleCasual;

    @FindBy(id = "vmsuit")
    private WebElement maleSuit;

    @FindBy(id = "vfcasual")
    private WebElement femaleCasual;

    @FindBy(id = "vfsuit")
    private WebElement femaleSuit;

    @FindBy(id = "vivideo")
    private WebElement videoLength;

    @FindBy(id = "vlarge")
    private WebElement videoLarge;

    @FindBy(id = "vmedium")
    private WebElement videoMedium;

    @FindBy(id = "vsmall")
    private WebElement videoSmall;

    @FindBy(id = "vishot")
    private WebElement shotType;

    @FindBy(id = "vitransition")
    private WebElement videoTransition;

    @FindBy(id = "vitone")
    private WebElement videoTone;

    @FindBy(id = "viback")
    private WebElement videoBackground;

    @FindBy(id = "viscript")
    private WebElement videoScript;

    @FindBy(id = "qcomment")
    private WebElement comment;

    @FindBy(id = "qsubmit")
    private WebElement submitButton;

    public void enterName(String cname) {
        try {
            enterTestBoxValues(name, cname);
        } catch (Exception e) {
            System.out.println("Unable to enter a name value " + e);
        }
    }

    public void enterEmail(String email) {
        try {
            enterTestBoxValues(mailid, email);
        } catch (Exception e) {
            System.out.println("Unable to enter a Email ID value " + e);
        }
    }

    public void selectCountry(String ccountry) {
        try {
            selectDropDown(country, ccountry);

        } catch (Exception e) {
            System.out.println("Unable to select country " + e);
        }
    }

    public void enterAreaCode(String acode) {
        try {
            enterTestBoxValues(areaCode, acode);
        } catch (Exception e) {
            System.out.println("Unable to enter a area code value " + e);
        }
    }

    public void enterPhoneNumber(String phoneNo) {
        try {
            enterTestBoxValues(phoneNumber, phoneNo);
        } catch (Exception e) {
            System.out.println("Unable to enter a phone number value " + e);
        }
    }

    public void selectTalent(String... talents) {
        try {
            for (String talent : talents) {
                switch (talent) {

                    case "Male Casual":
                        clickElement(maleCasual);
                        break;

                    case "Male Suit":
                        clickElement(maleSuit);
                        break;

                    case "Female Casual":
                        clickElement(femaleCasual);
                        break;

                    case "Female Suit":
                        clickElement(femaleSuit);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to select Talent " + e);
        }
    }

    public void enterVideoLength(String videoLeng) {
        try {
            enterTestBoxValues(videoLength, videoLeng);
        } catch (Exception e) {
            System.out.println("Unable to enter a video length value " + e);
        }
    }

    public void selectVideoSize(String... sizes) {
        try {
            for (String size : sizes) {
                switch (size) {

                    case "Large":
                        clickElement(videoLarge);
                        break;

                    case "Medium":
                        clickElement(videoMedium);
                        break;

                    case "Small":
                        clickElement(videoSmall);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to select video size " + e);
        }
    }

    public void selectShotType(String shotTy) {
        try {
            selectDropDown(shotType, shotTy);

        } catch (Exception e) {
            System.out.println("Unable to select shot type " + e);
        }
    }

    public void selectVideoTransition(String transition) {
        try {
            selectDropDown(videoTransition, transition);

        } catch (Exception e) {
            System.out.println("Unable to select video transition " + e);
        }
    }

    public void selectToneOfVideo(String toneOfVideo) {
        try {
            selectDropDown(videoTone, toneOfVideo);

        } catch (Exception e) {
            System.out.println("Unable to select video tone " + e);
        }
    }

    public void enterBackground(String background) {
        try {
            enterTestBoxValues(videoBackground, background);
        } catch (Exception e) {
            System.out.println("Unable to enter a video background value " + e);
        }
    }

    public void enterVideoScript(String script) {
        try {
            enterTestBoxValues(videoScript, script);
        } catch (Exception e) {
            System.out.println("Unable to enter a video script value " + e);
        }
    }

    public void enterComments(String comments) {
        try {
            enterTestBoxValues(comment, comments);
        } catch (Exception e) {
            System.out.println("Unable to enter a comment value " + e);
        }
    }

    public void clickSubmit() {
        try {
            clickElement(submitButton);
        } catch (Exception e) {
            System.out.println("Unable to click submit button " + e);
        }
    }
}
