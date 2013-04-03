package com.infostretch.isosbb.util;

import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.blackberry.api.mail.Address;
import net.rim.blackberry.api.mail.Message;
import net.rim.blackberry.api.mail.MessagingException;
import net.rim.blackberry.api.mail.Multipart;
import net.rim.blackberry.api.mail.SupportedAttachmentPart;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

public class Utilities {

	public static void sendEmail(byte[] msgData) {

		String attachedFileName = "Logs.txt";
		Multipart multipart = new Multipart();
		SupportedAttachmentPart attach = new SupportedAttachmentPart(multipart,
				"text/plain", attachedFileName, msgData);
		multipart.addBodyPart(attach);
		Message msg = new Message();
		// add the recipient list to the message
		try {
			// msg.addRecipients(Message.RecipientType.TO, addresses);
			// set a subject for the message
			msg.setSubject("ISOS Logs: " + attachedFileName);
			msg.setContent(multipart);
		} catch (final MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			UiApplication.getUiApplication().invokeLater(new Runnable() {

				public void run() {

					Dialog.alert(e1.toString());
				}
			});
		}
		Invoke.invokeApplication(Invoke.APP_TYPE_MESSAGES,
				new MessageArguments(msg));

	}

}
