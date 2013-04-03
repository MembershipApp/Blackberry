package com.infostretch.isosbb.ui;

import java.util.Timer;
import java.util.TimerTask;

import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.system.GIFEncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

/**
 * A custom waiting pop screen used as network waiting screen.
 * 
 * <pre>
 * <code>
 * WaitingScreen.showPopUP();
 * WaitingScreen.removePopUP();
 * </code>
 * </pre>
 * 
 * Created by IntelliJ IDEA. User: arun Date: Sep 4, 2009 Time: 12:16:52 PM
 */
public class WaitingScreen extends PopupScreen {

    private final static GIFEncodedImage gifImage = (GIFEncodedImage) EncodedImage.getEncodedImageResource("bbloading1.gif");
    /** This value is used for WaitingScreen object. */
    static WaitingScreen me;

    /**
     * call this to show the popup screen
     */
    public static void showPopUP() {

        UiApplication.getUiApplication().invokeLater(new Runnable() {

            public void run() {

                if (me == null) {
                    me = new WaitingScreen();
                }
                if (!UiApplication.getUiApplication().getActiveScreen().equals(me))
                    UiApplication.getUiApplication().pushModalScreen(me);

            }
        });
    }

    /**
     * call this to remove popup screen
     */
    public static void removePopUP() {

        if (!UiApplication.getUiApplication().isEventThread()) {
            UiApplication.getUiApplication().invokeLater(new Runnable() {

                public void run() {

                    removeWaitingDialog();
                }
            });
        } else {
            removeWaitingDialog();
        }

    }

    static void removeWaitingDialog() {

        if (me != null) {
            if (UiApplication.getUiApplication().getActiveScreen().equals(me))
                UiApplication.getUiApplication().popScreen(me);
            me = null;
        }
    }

    /**
     * private constructor
     */
    WaitingScreen() {

        super(new VerticalFieldManager());
        HorizontalFieldManager hfm = new HorizontalFieldManager(Field.FIELD_HCENTER);
        hfm.add(new AnimatedGIFField(gifImage));
        add(hfm);
    }

    class AnimatedGIFField extends BitmapField {

        private GIFEncodedImage waitImage; // The image to draw.
        int currentFrame; // The current frame in the animation
        // sequence.
        private Timer task;
        final int totalFrames;

        public AnimatedGIFField(GIFEncodedImage image) {

            this(image, 0);
        }

        AnimatedGIFField(GIFEncodedImage image, long style) {

            // Call super to setup the field with the specified style.
            // The image is passed in as well for the field to
            // configure its required size.
            super(image.getBitmap(), style);

            // Store the image and it's dimensions.
            this.waitImage = image;
            image.getWidth();
            image.getHeight();
            totalFrames = waitImage.getFrameCount();
            task = new Timer();
        }

        protected void paint(Graphics graphics) {

            // Call super.paint. This will draw the first background
            // frame and handle any required focus drawing.
            super.paint(graphics);

            // Don't redraw the background if this is the first frame.
            if (this.currentFrame != 0) {
                // Draw the animation frame.
                graphics.drawImage(waitImage.getFrameLeft(currentFrame), waitImage.getFrameTop(currentFrame), waitImage.getFrameWidth(currentFrame),
                        waitImage.getFrameHeight(currentFrame), waitImage, currentFrame, 0, 0);

            }
        }

        // Stop the animation thread when the screen the field is on is
        // popped off of the display stack.
        protected void onUndisplay() {

            task.cancel();
            super.onUndisplay();
        }

        // schedule the timer task for frame by frame animation.
        protected void onDisplay() {

            task.schedule(new TimerTask() {

                public void run() {

                    currentFrame = (++currentFrame) % totalFrames;
                    UiApplication.getUiApplication().invokeAndWait(new Runnable() {

                        public void run() {

                            invalidate();
                        }
                    });

                }
            }, 1, waitImage.getFrameDelay(1) * 10);
            super.onDisplay();
        }
    }
}
