package com.infostretch.isosbb.ui;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.browser.field2.BrowserFieldConfig;
import net.rim.device.api.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class WebScreen extends MainScreen
{
    /**
     * Creates a new WebScreen object
     */
    public WebScreen()
    {        
    	BrowserFieldConfig config = new BrowserFieldConfig();
		config.setProperty(BrowserFieldConfig.ALLOW_CS_XHR, Boolean.TRUE);
		config.setProperty(BrowserFieldConfig.JAVASCRIPT_ENABLED, Boolean.TRUE);
    	BrowserField f = new BrowserField(config);
    	add(f);
    	String url = "http://mobile.usablenet.com/mt/www.internationalsos.com/MasterPortal/default.aspx?templatemembnum=11BWEB000048&membnum=14AYCA000033&membershiptype=comp&content=landing&countryid=111";
    	f.requestContent(url);
    }
}
