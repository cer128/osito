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
package org.jemmy.fx.control;

import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.env.Environment;
import org.jemmy.fx.NodeWrap;
import org.jemmy.fx.Root;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.MenuOwner;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.TypeControlInterface;

@ControlType({MenuBar.class})
@ControlInterfaces(value = {Parent.class, MenuOwner.class},
encapsulates = {MenuItem.class, MenuItem.class},name={"asMenuParent"})

public class MenuBarWrap<CONTROL extends MenuBar> extends NodeWrap<CONTROL> {

    private StringMenuOwnerImpl menuOwner = new StringMenuOwnerImpl(this, this.as(Parent.class, Menu.class));
    
    private Focus focus = Root.ROOT.getThemeFactory().menuBarFocuser(this);

    /**
     *
     * @param env
     * @param scene
     * @param nd
     */
    @SuppressWarnings("unchecked")
    public MenuBarWrap(Environment env, CONTROL nd) {
        super(env, nd);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (MenuItem.class.isAssignableFrom(type)) {
            if (Parent.class.isAssignableFrom(interfaceClass)) {
                return true;
            }
            if (MenuOwner.class.isAssignableFrom(interfaceClass)) {
                return true;
            }
        }
        return super.is(interfaceClass, type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (MenuItem.class.isAssignableFrom(type)) {
            if (Parent.class.equals(interfaceClass)) {
                return (INTERFACE) new AbstractMenuItemsParent(this, type) {
                    @Override
                    protected List getControls() {
                        return getControl().getMenus();
                    }
                };
            }
            if (MenuOwner.class.isAssignableFrom(interfaceClass)) {
                return (INTERFACE) menuOwner;
            }
        }
        return super.as(interfaceClass, type);
    }

    @Override
    public Focus focuser() {
        return focus;
    }

    @Override
    public boolean isFocused() {
        return true; // stab: currently no way to detemine pseudo-focused state
    }
}