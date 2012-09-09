/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation. Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.jemmy.fx.control.caspian;

import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import org.jemmy.fx.ByStyleClass;
import org.jemmy.control.Wrap;
import org.jemmy.input.KnobDragScrollerImpl;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.Scroll;

/**
 * Scroller implementation which uses d'n'd of the knob of ScrollBar.
 *
 * Ported from the project JemmyFX, original class
 * is   org.jemmy.fx.control.caspian.ScrollBarScroller
 *
 * @see KnobDragScrollerImpl
 * @author shura
 */
public class ScrollBarScroller extends KnobTrackScrollerImpl {

    /**
     *
     * @param wrap
     * @param scroll
     */
    public ScrollBarScroller(Wrap<? extends ScrollBar> wrap, Scroll scroll, final Class skinClass) {
        super(wrap, scroll);
        setWraps(wrap, skinClass);
    }

    /**
     *
     * @return
     */
    @Override
    public Wrap<? extends Node> getWrap() {
        return (Wrap<? extends Node>) super.getWrap();
    }


}