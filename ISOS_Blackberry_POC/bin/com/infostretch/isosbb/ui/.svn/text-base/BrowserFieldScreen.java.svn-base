/*
 * BrowserFieldScreen.java
 *
 * Copyright © 1998-2010 Research In Motion Ltd.
 * 
 * Note: For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 */

package com.infostretch.isosbb.ui;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.browser.field2.BrowserFieldConfig;
import net.rim.device.api.browser.field2.BrowserFieldHistory;
import net.rim.device.api.browser.field2.BrowserFieldListener;
import net.rim.device.api.browser.field2.BrowserFieldRequest;
import net.rim.device.api.script.ScriptEngine;
import net.rim.device.api.script.Scriptable;
import net.rim.device.api.script.ScriptableFunction;
import net.rim.device.api.system.Application;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.KeyListener;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;

import org.w3c.dom.Document;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import com.infostretch.isosbb.main.ISOSApp;
import com.infostretch.isosbb.push.LoggerText;

/**
 * The MainScreen class for the Browser Field application
 */
public class BrowserFieldScreen extends MainScreen {
	private BrowserField _browserField;
	private boolean _documentLoaded = false;
	private BrowserFieldRequest _request;
	private boolean isExternal = false;
	private static BrowserFieldScreen instance;

	/**
	 * Creates a new BrowserFieldScreen object
	 * 
	 * @param request
	 *            The URI of the content to display in this BrowserFieldScreen
	 * @param enableScriptMenu
	 *            True if a context menu is to be created for this
	 *            BrowserFieldScreen instance, false otherwise
	 */
	public BrowserFieldScreen(BrowserFieldRequest request,
			boolean enableScriptMenu) {
		addKeyListener(new BrowserFieldScreenKeyListener());
		BrowserFieldConfig config = new BrowserFieldConfig();
		config.setProperty(BrowserFieldConfig.ALLOW_CS_XHR, Boolean.TRUE);
		config.setProperty(BrowserFieldConfig.JAVASCRIPT_ENABLED, Boolean.TRUE);
		config.setProperty(BrowserFieldConfig.NAVIGATION_MODE,BrowserFieldConfig.NAVIGATION_MODE_POINTER);
		_browserField = new BrowserField(config);
		_browserField.addListener(new InnerBrowserListener());
		// ButtonField button = new ButtonField("Change Text",
		// ButtonField.CONSUME_CLICK) {
		// protected boolean touchEvent(TouchEvent message) {
		// if(message.getEvent() == TouchEvent.CLICK) {
		// _browserField.executeScript("changeText()");
		// return true;
		// }
		// return super.touchEvent(message);
		// }
		// };
		// add(button);
		add(_browserField);
		_browserField.setFocus();
		_request = request;
		instance = this;
	}
	
	public static BrowserFieldScreen getCopy() {
		return instance;
	}

	/**
	 * @see Screen#onUiEngineAttached(boolean)
	 */
	protected void onUiEngineAttached(boolean attached) {
		if (attached) {
			try {
				_browserField.requestContent(_request);
				LoggerText.addLog2("onUiEngineAttached 2:"+attached);
			} catch (Exception e) {
				deleteAll();
				add(new LabelField("ERROR:\n\n"));
				add(new LabelField(e.getMessage()));
			}
		}
	}

	/**
	 * @see MainScreen#onSavePrompt()
	 */
	public boolean onSavePrompt() {
		// Prevent the save dialog from being displayed
		return true;
	}

	/**
	 * Returns this screen's BrowserField object
	 * 
	 * @return This screen's BrowserField object
	 */
	public BrowserField getBrowserField() {
		return _browserField;
	}
	
	public boolean onClose() {
		getApplication().requestBackground();
		return true;
	}
	
	protected boolean keyChar(char c, int status, int time) {
		if(c == Keypad.KEY_ESCAPE) {
//			if(isExternal) {
//				_browserField.requestContent(ISOSApp.URL);
//				return true;
//			}
			onClose();
			return true;
		}
		return super.keyChar(c, status, time);
	}

	/**
	 * @see MainScreen#makeMenu(Menu, int)
	 */
	protected void makeMenu(Menu menu, int instance) {
		super.makeMenu(menu, instance);
		menu.add(logMenuItem);
		if (_documentLoaded
				&& _browserField.getDocumentUrl().equals(ISOSApp.URL)) {
			try {
				Scriptable contextMenuItems = (Scriptable) _browserField
						.getScriptEngine().executeScript("makeContextMenu()",
								null);
				if (contextMenuItems != null) {
					MenuItem defaultItem = null;
					Integer length = (Integer) contextMenuItems
							.getField("length");
					for (int i = 0; i < length.intValue(); i++) {
						Scriptable menuItem = (Scriptable) contextMenuItems
								.getElement(i);
						if (menuItem != null) {
							String label = (String) menuItem.getField("label");
							Object action = menuItem.getField("action");
							MenuItem item = null;
							if (action instanceof String) {
								item = new ScriptableMenuItem(label,
										new SimpleScriptableFunction(
												(String) action));
							} else if (action instanceof ScriptableFunction) {
								item = new ScriptableMenuItem(label,
										(ScriptableFunction) action);
							}
							if (item != null) {
								menu.add(item);
								Object isDefault = menuItem
										.getField("defaultItem");
								if (isDefault != null
										&& Scriptable.UNDEFINED
												.equals(isDefault) == false
										&& ((Boolean) isDefault).booleanValue()) {
									defaultItem = item;
								}
							}
						}
					}
					if (defaultItem != null) {
						menu.setDefault(defaultItem);
					}
				}
			} catch (Exception e) {
				// BrowserField2Demo.errorDialog("Error calling javascript script makeContextMenu().."
				// + e.getMessage(), false);
			}
		}
	}
	
	private MenuItem logMenuItem = new MenuItem("Log", 9, 9) {
		
		public void run() {
			UiApplication.getUiApplication().pushScreen(new LogScreen());
		}
	};

	public boolean isExternal() {
		return isExternal;
	}

	public void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}

	/**
	 * A class to listen for BrowserField events
	 */
	private class InnerBrowserListener extends BrowserFieldListener {
		/**
		 * @see BrowserFieldListener#documentCreated(BrowserField, ScriptEngine,
		 *      Document)
		 */

		public void documentCreated(BrowserField browserField,
				ScriptEngine scriptEngine, Document document) throws Exception {
			if (document instanceof EventTarget) {
				((EventTarget) document).addEventListener("load",
						new EventListener() {
							public void handleEvent(Event evt) {
								LoggerText.addLog3("documentCreated: 2");
								_documentLoaded = true;
//								if(isExternal) {
//									_browserField.executeScript("var dash = new dashBoardView();");
//									isExternal = false;
//								}
							}
						}, false);
			}
		}
		public void documentAborted(BrowserField browserField, Document document)
				throws Exception {
			LoggerText.addLog3("documentAborted ");
			super.documentAborted(browserField, document);
		}
		public void documentLoaded(BrowserField browserField, Document document)
				throws Exception {
			LoggerText.addLog3("documentLoaded ");
//			if(!(UiApplication.getUiApplication().getActiveScreen() instanceof BrowserFieldScreen))
//				UiApplication.getUiApplication().pushScreen(instance);
//			super.documentLoaded(browserField, document);
		}
		public void documentError(BrowserField browserField, Document document)
				throws Exception {
			LoggerText.addLog3("documentError ");
			super.documentError(browserField, document);
		}
		public void documentUnloading(BrowserField browserField,
				Document document) throws Exception {
			LoggerText.addLog3("documentUnloading ");
			super.documentUnloading(browserField, document);
		}
	}

	/**
	 * A MenuItem class used to launch various scriptable functions
	 */
	private static class ScriptableMenuItem extends MenuItem {
		ScriptableFunction _function;

		/**
		 * Creates a new ScriptableMenuItem object
		 * 
		 * @param label
		 *            The label for this MenuItem
		 * @param function
		 *            The ScriptableFunction to be executed by this MenuItem
		 */
		public ScriptableMenuItem(String label, ScriptableFunction function) {
			super(label, 0, 0);
			_function = function;
		}

		public void run() {
			LoggerText.addLog2("ScriptableMenuItem run ");
			if (Application.isEventDispatchThread()) {
				LoggerText.addLog2("ScriptableMenuItem run 1");
				new Thread(this).start();
			} else {
				try {
					LoggerText.addLog2("ScriptableMenuItem run 2");
					_function.invoke(null, null);
					LoggerText.addLog2("ScriptableMenuItem run 3");
				} catch (Exception e) {
					// BrowserField2Demo.errorDialog("Error invoking ScriptableFunction: "
					// + e.getMessage(), false);
				}
			}
		}
	}

	/**
	 * A class representing a function in the script environment
	 */
	private class SimpleScriptableFunction extends ScriptableFunction {
		String _action;

		/**
		 * Creates a new SimpleScriptableFunction object
		 * 
		 * @param action
		 *            The action to be executed by this ScriptableFunction
		 */
		public SimpleScriptableFunction(String action) {
			_action = action;
		}

		/**
		 * @see ScriptableFunction#invoke(Object, Object[])
		 */
		public Object invoke(Object thiz, Object[] args) throws Exception {
			LoggerText.addLog2("SimpleScriptableFunction invoke ");
			_browserField.getScriptEngine().executeScript(_action, null);
			return UNDEFINED;
		}
	}

	/**
	 * A KeyListener implementation
	 */
	private class BrowserFieldScreenKeyListener implements KeyListener {
		/**
		 * @see KeyListener#keyChar(char, int, int)
		 */
		public boolean keyChar(final char key, int status, int time) {
			if (key == 'n') {
				Runnable nextRunnable = new Runnable() {
					public void run() {
						try {
							BrowserFieldHistory browserFieldHistory = getBrowserField().getHistory();
							if (browserFieldHistory.canGoForward()) {
								browserFieldHistory.goForward();
							}
						} catch (Exception e) {
							System.out.println("Error executing js:next(): "+ e.getMessage());
						}
					}
				};
				new Thread(nextRunnable).start();
				return true;
			} else if (key == 'p' || key == Characters.ESCAPE) {
				Runnable previousRunnable = new Runnable() {
					public void run() {
						try {
							BrowserFieldHistory browserFieldHistory = getBrowserField().getHistory();
							if (browserFieldHistory.canGoBack()) {
								browserFieldHistory.goBack();
							} else {
								if (key == Characters.ESCAPE) {
									synchronized (Application.getEventLock()) {
										close();
									}
								}
							}
						} catch (Exception e) {
							System.out.println("Error executing js:previous(): "+ e.getMessage());
						}
					}
				};
				new Thread(previousRunnable).start();
				return true;
			} else if (key == Characters.ENTER) {
				Runnable submitRunnable = new Runnable() {
					public void run() {
						try {
							getBrowserField().getScriptEngine().executeScript("submitSearch()", null);
						} catch (final Exception e) {
							System.out.println("Error executing js:submitSearch(): "+ e.getMessage());
						}
					}
				};
				new Thread(submitRunnable).start();
				return true;
			}
			return false;
		}

		/**
		 * @see KeyListener#keyDown(int, int)
		 */
		public boolean keyDown(int keycode, int time) {
			return false;
		}

		/**
		 * @see KeyListener#keyRepeat(int, int)
		 */
		public boolean keyRepeat(int keycode, int time) {
			return false;
		}

		/**
		 * @see KeyListener#keyStatus(int, int)
		 */
		public boolean keyStatus(int keycode, int time) {
			return false;
		}

		/**
		 * @see KeyListener#keyUp(int, int)
		 */
		public boolean keyUp(int keycode, int time) {
			return false;
		}
	}
}
