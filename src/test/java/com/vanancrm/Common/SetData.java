package com.vanancrm.Common;

import java.util.ArrayList;
import java.util.List;

public class SetData {
	
	private List<String> fileTypes = new ArrayList<String>();
	private List<Double> pages = new ArrayList<Double>();
	private List<String> sourceLanguages = new ArrayList<String>();
	private List<String> transactionLanguages = new ArrayList<String>();
	private List<String> categorys = new ArrayList<String>();
	private List<Double> tats = new ArrayList<Double>();
	private List<Double> amounts = new ArrayList<Double>();
	private List<Double> totals = new ArrayList<Double>();
	private List<Double> expedites = new ArrayList<Double>();
	private List<Double> subtotals = new ArrayList<Double>();
	private List<Double> transactions = new ArrayList<Double>();
	private List<Double> grandtotals = new ArrayList<Double>();
	
	private List<String> urls = new ArrayList<String>();
	private List<String> titles = new ArrayList<String>();
	private List<String> siteNames = new ArrayList<String>();

	private List<String> locators = new ArrayList<String>();
	private List<String> uiElements = new ArrayList<String>();

	public void setFileType(String fileType) {
		fileTypes.add(fileType);
	}
	
	public void setPage(double page) {
		pages.add(page);
	}
	
	public void setSourceLanguage(String sourceLanguage) {
		sourceLanguages.add(sourceLanguage);
	}
	
	public void setTransactionLanguage(String transactionLanguage) {
		transactionLanguages.add(transactionLanguage);
	}
	
	public void setCategory(String category) {
		categorys.add(category);
	}
	
	public void setTat(double tat) {
		tats.add(tat);
	}
	
	public void setAmount(double amount) {
		amounts.add(amount);
	}
	
	public void setTotal(double total) {
		totals.add(total);
	}
	
	public void setTransaction(double transaction) {
		transactions.add(transaction);
	}
	
	public void setGrandtotals(double grandtotal) {
		grandtotals.add(grandtotal);
	}
	
	public void setExpedite(double expedite) {
		expedites.add(expedite);
	}
	
	public void setSubTotal(double subtotal) {
		subtotals.add(subtotal);
	}
	
	public void setUrls(String url) {
		urls.add(url);
	}
	
	public void setTitles(String title) {
		titles.add(title);
	}
	
	public void setSiteNames(String siteName) {
		siteNames.add(siteName);
	}

	public void setLocators(String locator) {
		locators.add(locator);
	}

	public void setUIElements(String uiElement) {
		uiElements.add(uiElement);
	}

	public List<String> getFileType() {
		return fileTypes;
	}
	
	public List<Double> getPage() {
		return pages;
	}
	
	public List<String> getSourceLanguage() {
		return sourceLanguages;
	}
	
	public List<String> getTransactionLanguage() {
		return transactionLanguages;
	}
	
	public List<String> getCategory() {
		return categorys;
	}
	
	public List<Double> getTat() {
		return tats;
	}
	
	public List<Double> getAmount() {
		return amounts;
	}
	
	public List<Double>  getTotal() {
		return totals;
	}
	
	public List<Double> getTransaction() {
		return transactions;
	}
	
	public List<Double> getGrandtotals() {
		return grandtotals;
	}
	
	public List<Double> getExpedite() {
		return expedites;
	}
	
	public List<Double> getSubTotal() {
		return subtotals;
	}
	
	public List<String> getUrls() {
		return urls;
	}
	
	public List<String> getTitles() {
		return titles;
	}
	
	public List<String> getSiteNames() {
		return siteNames;
	}
	
	public List<String> getLocators() {
		return locators;
	}

	public List<String> getUIElements() {
		return uiElements;
	}
}
