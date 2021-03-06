/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at LICENSE.html or
 * http://www.sun.com/cddl.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this License Header
 * Notice in each file.
 *
 * If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s): Alexandre (Shura) Iline. (shurymury@gmail.com)
 *
 * The Original Software is the Jemmy library.
 * The Initial Developer of the Original Software is Alexandre Iline.
 * All Rights Reserved.
 *
 */

package org.jemmy.interfaces;


import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.dock.Shortcut;
import org.jemmy.env.Timeout;
import org.jemmy.interfaces.Mouse.MouseButton;


/**
 * 
 * @author shura
 */
public interface Drag extends ControlInterface {
    /**
     *
     */
    public static final Timeout BEFORE_DRAG_TIMEOUT = new Timeout("Control.before.drag", 500);
    /**
     *
     */
    public static final Timeout BEFORE_DROP_TIMEOUT = new Timeout("Control.after.drag", 500);
    /**
     *
     */
    public static final Timeout IN_DRAG_TIMEOUT = new Timeout("Control.in.drag", 10);

    /**
     *
     * @param targetPoint target point specified in component local coordinates
     */
    @Shortcut
    public void dnd(Point targetPoint);
    /**
     *
     * @param target
     * @param targetPoint target point specified in target component local coordinates
     */
    @Shortcut
    public void dnd(Wrap target, Point targetPoint);
    /**
     *
     * @param point source point specified in component local coordinates
     * @param target
     * @param targetPoint target point specified in target component local coordinates
     */
    @Shortcut
    public void dnd(Point point, Wrap target, Point targetPoint);
    /**
     *
     * @param point source point specified in component local coordinates
     * @param target
     * @param targetPoint target point specified in target component local coordinates
     * @param button
     */
    @Shortcut
    public void dnd(Point point, Wrap target, Point targetPoint, MouseButton button);
    /**
     *
     * @param point source point specified in component local coordinates
     * @param target
     * @param targetPoint target point specified in target component local coordinates
     * @param button
     * @param modifiers
     */
    @Shortcut
    public void dnd(Point point, Wrap target, Point targetPoint, MouseButton button, Modifier... modifiers);
}
