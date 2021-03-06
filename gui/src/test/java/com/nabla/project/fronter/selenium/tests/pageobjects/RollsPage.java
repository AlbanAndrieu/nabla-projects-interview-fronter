/*
 * Copyright (c) 2002-2004, Nabla
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Nabla' nor 'Alban' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package com.nabla.project.fronter.selenium.tests.pageobjects;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nabla.project.fronter.selenium.tests.helper.SeleniumHelper;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * DOCUMENT ME! albandri.
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 */
public class RollsPage extends LoadableComponent<RollsPage>
{

    private final String url = "/visma/rolls.xhtml";
    // private final String title = "JSF 2.0 Visma Loan test";

    @FindBy(name = "rolls_form:rolls")
    private WebElement   rolls;

    @FindBy(name = "loan_form:payment")
    private WebElement   calculate;

    public RollsPage()
    {
        PageFactory.initElements(SeleniumHelper.getDriver(), this);
    }

    @Override
    protected void load()
    {
        this.The_user_is_on_loan_page();
    }

    @Override
    protected void isLoaded()
    {
        final String url = SeleniumHelper.getDriver().getCurrentUrl();
        Assert.assertTrue("Not on the issue entry page: " + url, url.endsWith("/rolls.xhtml"));
        // Assert.assertTrue(this.driver.getTitle().equals(this.title));
    }

    public void close()
    {
        SeleniumHelper.close();
    }

    @Given("the user is on Rolls Page")
    public void The_user_is_on_loan_page()
    {

        SeleniumHelper.getDriver().get(SeleniumHelper.baseUrl + this.url);

        final JavascriptExecutor js = (JavascriptExecutor) SeleniumHelper.getDriver();

        // Get the Load Event End
        final long loadEventEnd = (Long) js.executeScript("return window.performance.timing.loadEventEnd;");

        // Get the Navigation Event Start
        final long navigationStart = (Long) js.executeScript("return window.performance.timing.navigationStart;");

        // Difference between Load Event End and Navigation Event Start is Page Load Time
        System.out.println("Page Load Time is " + ((loadEventEnd - navigationStart) / 1000) + " seconds.");

        // Wait for the Calculate Button
        new WebDriverWait(SeleniumHelper.getDriver(), 10).until(ExpectedConditions.presenceOfElementLocated(By.id("rolls_form:score")));

        // WebElement myDynamicElement = (new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.id("loan_form")));
        Assert.assertEquals("Kata Bowling Calculator", SeleniumHelper.getDriver().findElement(By.cssSelector("h3")).getText());

    }

    @When("he enters \"([^\"]*)\" as rolls")
    public void He_enters_rolls(final String aRolls)
    {
        this.rolls.clear();
        this.rolls.sendKeys(aRolls);
    }

    @And("he Submits request for score calculation")
    public void He_submits_request_for_score()
    {
        final WebDriverWait wait = new WebDriverWait(SeleniumHelper.getDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("rolls_form:score")));
        SeleniumHelper.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        this.calculate.click();
    }

    @Then("ensure the score is accurate with \"([^\"]*)\" message")
    public void Ensure_score_is_complete(final String msg)
    {
        final WebElement message = SeleniumHelper.getDriver().findElement(By.cssSelector("h4"));
        Assert.assertEquals(message.getText(), msg);
    }

    @Then("ensure a transaction failure message \"([^\"]*)\" is displayed")
    public void Ensure_a_transaction_failure_message(final int i, final String msg)
    {
        final WebElement message = SeleniumHelper.getDriver().findElement(By.xpath("//table[@id='rolls_form:panel']/tbody/tr[" + i + "]/td[3]/span"));
        Assert.assertEquals(message.getText(), msg);

    }

    public void calculateScore(final String aRolls)
    {

        this.He_enters_rolls(aRolls);

        this.He_submits_request_for_score();

    }

}
