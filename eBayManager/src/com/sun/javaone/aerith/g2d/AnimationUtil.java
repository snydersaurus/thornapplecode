/*
 * AnimationUtil.java
 *
 * Created on April 3, 2006, 10:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.javaone.aerith.g2d;

import org.jdesktop.animation.timing.interpolation.KeyFrames;
import org.jdesktop.animation.timing.interpolation.KeyValues;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.swingx.JXPanel;

/**
 *
 * @author rbair
 */
public final class AnimationUtil {

    /** Creates a new instance of AnimationUtil */
    private AnimationUtil() {
    }

    public static Animator createFadeInAnimation(JXPanel panel) {
        return createFadeAnimation(panel, 0.01f, .99f);
    }

    public static Animator createFadeOutAnimation(JXPanel panel) {
        return createFadeAnimation(panel, 0.99f, .01f);
    }

    public static Animator createFadeAnimation(JXPanel panel, float start, float end) {
//        KeyValues keyValues = KeyValues.create(new float[] { start, end });
//        KeyFrames keyFrames = new KeyFrames(keyValues);
//        PropertySetter range = new PropertySetter(panel,"alpha", keyFrames);
//        Animator animator = new Animator(4000,1d,Animator.RepeatBehavior.REVERSE,range);
        Animator animator = PropertySetter.createAnimator(1000, panel, 
                "alpha", new Float(start), new Float(end));
        animator.setAcceleration(0.7f);
        animator.setDeceleration(0.3f);
        return animator;
    }
}
