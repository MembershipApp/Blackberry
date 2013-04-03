/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.infostretch.isosbb.push;

import net.rim.device.api.util.Persistable;

/**
 * Encapsulates push configuration for the application
 */
public class PushConfig implements Persistable {

    // Default properties used for the demo.
    // You should register with BlackBerry Push API server and obtain
    // similar values for your application.
    
    // TCP port to listen for push messages
    private int port = 32651;
    // Application ID generated during Push API registration
    private String appId = "3131-ac535r06e61i2414aa9501c93160ak9s256";
    // URL to the Push BPAS server
    private String bpasUrl = "http://cp3131.pushapi.eval.blackberry.com/";
    // URL to Content Provider using Frameworks library
    private String contentProviderUrl = "";

    private static PushConfig instance;
    
//    static {
//        instance = PersistentStorage.getConfig();
//        if( instance == null ) {
//            instance = new PushConfig();
//            PersistentStorage.setConfig( instance );
//        }
//    }

    /**
     * Defines whether application supports application level acknowledgment
     */
    private boolean applicationAcknoledgment = true;

    public static int getPort() {
        return instance.port;
    }

    public static String getAppId() {
        return instance.appId;
    }

    public static String getBpasUrl() {
        return instance.bpasUrl;
    }

    public static boolean isApplicationAcknoledgementSupported() {
        return instance.applicationAcknoledgment;
    }

    public static String getContentProviderUrl() {
        return instance.contentProviderUrl;
    }

    public static void setPort( int port ) {
        instance.port = port;
    }

    public static void setAppId( String appId ) {
        instance.appId = appId;
    }

    public static void setBpasUrl( String bpasUrl ) {
        instance.bpasUrl = bpasUrl;
    }

    public static void setContentProviderUrl( String contentProviderUrl ) {
        instance.contentProviderUrl = contentProviderUrl;
    }

    public static void setApplicationAcknoledgment( boolean applicationAcknoledgment ) {
        instance.applicationAcknoledgment = applicationAcknoledgment;
    }

    public static void saveSettings() {
//        PersistentStorage.setConfig( instance );
    }
    
}
