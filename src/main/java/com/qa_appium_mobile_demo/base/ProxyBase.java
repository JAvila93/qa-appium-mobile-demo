package com.qa_appium_mobile_demo.base;

import org.openqa.selenium.Proxy;

public class ProxyBase {

    private String proxyAddress;
    private int proxyPort;
    private String proxyUsername;
    private String proxyPassword;

    public Proxy createUnAuthenticatedProxy(String address, int port) {
        Proxy proxy = new Proxy();
        proxyAddress = address;
        proxyPort = port;
        proxy.setHttpProxy(proxyAddress + ":" + proxyPort);
        proxy.setSslProxy(proxyAddress + ":" + proxyPort);
        return proxy;
    }

    public Proxy createAuthenticatedProxy(String address, int port, String username, String password) {
        Proxy proxy = new Proxy();
        proxyAddress = address;
        proxyPort = port;
        proxyUsername = username;
        proxyPassword = password;
        String proxyAuth = proxyUsername + ":" + proxyPassword;
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        proxy.setHttpProxy(proxyAuth + "@" + proxyAddress + ":" + proxyPort);
        proxy.setSslProxy(proxyAuth + "@" + proxyAddress + ":" + proxyPort);
        return proxy;
    }
}
