package com.qa_appium_mobile_demo.base;

public abstract class BasePage {

	protected BaseDriver driver;
	protected String m_BaseUrl;
	protected String m_Path = "";
	
	public BasePage(BaseDriver driver, String baseUrl, ExtentWrapper wrapper) {
		this.driver = driver;
		this.driver.extent = wrapper;
		m_BaseUrl = baseUrl;
	}
	
	public BasePage(BaseDriver driver, String baseUrl, ExtentWrapper wrapper, String path) {
		this.driver = driver;
		this.driver.extent = wrapper;
		m_BaseUrl = baseUrl;
		m_Path = path;
	}
	
	public final BasePage get() {
		get("");
		return this;
	}
	
	public BasePage get(String urlSuffix) {
		String url = m_BaseUrl + m_Path + urlSuffix;
		driver.get(url);
		return this;
	}
}
