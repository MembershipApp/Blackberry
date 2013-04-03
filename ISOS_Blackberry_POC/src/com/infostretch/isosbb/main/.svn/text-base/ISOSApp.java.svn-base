package com.infostretch.isosbb.main;


import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.browser.field2.BrowserFieldRequest;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.UiApplication;

import com.infostretch.isosbb.location.LocationOperations;
import com.infostretch.isosbb.push.LoggerText;
import com.infostretch.isosbb.push.PushAgent;
import com.infostretch.isosbb.ui.BrowserFieldScreen;
import com.infostretch.isosbb.ui.SplashScreen;
import com.infostretch.isosbb.util.ISOSScriptableFunctions;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class ISOSApp extends UiApplication
{
	
	BrowserFieldScreen _browserScreen;
	public static final String URL = DeviceInfo.isSimulator() ? "local:///isos60/index.html" : (DeviceInfo.getSoftwareVersion().startsWith("5") ? "local:///isos50/index.html" : "local:///isos60/index.html");
	static PushAgent pushAgent;
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
    	LoggerText.addLog2(" Model:"+DeviceInfo.getDeviceName()+
//              " OS:"+DeviceInfo.getOSVersion()+
              " s/w ver.:"+DeviceInfo.getSoftwareVersion()
//              " Platform Ver.:"+DeviceInfo.getPlatformVersion()
                      );
    	pushAgent = new PushAgent();  //start the push message receiver
        ISOSApp theApp = new ISOSApp();       
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new instance of the Application
     */
    public ISOSApp()
    {        
    	try
        { 
    		
            BrowserFieldRequest request = new BrowserFieldRequest(URL);
            _browserScreen = new BrowserFieldScreen(request, true);
            extendJavaScript(_browserScreen.getBrowserField());
            //pushScreen(_browserScreen);
            pushScreen(new SplashScreen(_browserScreen));
            LocationOperations.getInstance();  //starts the location operations in the background
        }
        catch(Exception e)
        {
        	System.out.println("Exception in AppMain: " + e);
//            errorDialog("An error occurred, exiting Browser Field 2 Demo: " + e.toString(), true);            
        }
    }    
    
    public void extendJavaScript(BrowserField browserField) throws Exception
    {
    	browserField.extendScriptEngine(ISOSScriptableFunctions.FUNCTION_NAME_DO_NATIVE, 
    			new ISOSScriptableFunctions(ISOSScriptableFunctions.FUNCTION_DO_NATIVE, _browserScreen));
    }
}
