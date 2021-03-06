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
package org.jemmy.fx;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import org.jemmy.Point;
import org.jemmy.Rectangle;
import org.jemmy.action.GetAction;
import org.jemmy.control.As;
import org.jemmy.control.ControlInterfaces;
import org.jemmy.control.ControlType;
import org.jemmy.control.MethodProperties;
import org.jemmy.control.Property;
import org.jemmy.control.Wrap;
import org.jemmy.control.Wrapper;
import org.jemmy.dock.DefaultWrapper;
import org.jemmy.dock.DockInfo;
import org.jemmy.dock.ObjectLookup;
import org.jemmy.env.Environment;
import org.jemmy.interfaces.Focus;
import org.jemmy.interfaces.Focusable;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.interfaces.TypeControlInterface;
import org.jemmy.lookup.LookupCriteria;

/**
 *
 * @author shura, mrkam
 */
@ControlType(Node.class)
@MethodProperties({"getBaselineOffset", "getBoundsInLocal", "getBoundsInParent",
    "getCacheHint", "getClip", "getContentBias", "getCursor", "getEffect",
    "getId", "getLayoutBounds", "getLayoutX", "getLayoutY", "getOpacity",
    "getParent", "getProperties", "getRotate", "getRotationAxis", "getScaleX",
    "getScaleY", "getScaleZ", "getStyle", "getStyleClass", "getTransforms",
    "getTranslateX", "getTranslateY", "getTranslateZ", "getUserData",
    "hasProperties", "isCache", "isDisable", "isDisabled",
    FXClickFocus.IS_FOCUSED_PROP, "isHover", "isManaged",
    "isMouseTransparent", "isPickOnBounds", "isPressed", "isResizable",
    "isVisible"})
@ControlInterfaces(value = Parent.class, encapsulates = Node.class)
@DockInfo(generateSubtypeLookups = true)
public class NodeWrap<T extends Node> extends Wrap<T> implements Focusable {

    protected Scene scene;
    private Parent parent = null;
    private Mouse mouse = null;
    private Focus focus;
    private static Wrapper wrapper = new NodeWrapper(Root.ROOT.getEnvironment());

    @DefaultWrapper
    public static <TP extends Node> Wrap<? extends TP> wrap(Environment env, Class<TP> type, TP control) {
        Wrap<? extends TP> res = wrapper.wrap(type, control);
        res.setEnvironment(env);
        return res;
    }

    @ObjectLookup("id")
    public static <B extends Node> LookupCriteria<B> idLookup(Class<B> tp, String id) {
        return new ByID<B>(id);
    }

    @ObjectLookup("type")
    public static <B extends Node> LookupCriteria<B> typeLookup(Class<B> tp, final Class<?> subtype) {
        return new LookupCriteria<B>() {

            public boolean check(B control) {
                return subtype.isInstance(control);
            }
        };
    }

    private NodeWrap(Environment env, Scene scene, T node) {
        super(env, node);
        this.scene = scene;
        focus = new FXClickFocus(this);
    }

    public NodeWrap(Environment env, T node) {
        this(env, node.getScene(), node);
    }

    @Property("scene")
    public Scene getScene() {
        return scene;
    }

    public static Rectangle getScreenBounds(final Environment env, final Node nd) {
        GetAction<Rectangle> bounds = new GetAction<Rectangle>() {

            @Override
            public void run(Object... parameters) {
                Bounds rect = nd.localToScene(nd.getLayoutBounds());
                Rectangle res = SceneWrap.getScreenBounds(env, nd.getScene());
                res.x += rect.getMinX();
                res.y += rect.getMinY();
                res.width = (int) rect.getWidth();
                res.height = (int) rect.getHeight();
                setResult(res);
            }
        };
        env.getExecutor().execute(env, true, bounds);
        return bounds.getResult();
    }

    @Override
    public Rectangle getScreenBounds() {
        return getScreenBounds(getEnvironment(), getControl());
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> boolean is(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.isAssignableFrom(interfaceClass) && Node.class.equals(type)) {
            return true;
        }
        return super.is(interfaceClass, type);
    }

    @Override
    public <TYPE, INTERFACE extends TypeControlInterface<TYPE>> INTERFACE as(Class<INTERFACE> interfaceClass, Class<TYPE> type) {
        if (Parent.class.isAssignableFrom(interfaceClass) && Node.class.equals(type)) {
            if (parent == null) {
                parent = new NodeParentImpl(this);
            }
            return (INTERFACE) parent;
        }
        return super.as(interfaceClass, type);
    }

    @Override
    @As(Mouse.class)
    public Mouse mouse() {
        if (mouse == null) {
            mouse = super.mouse();
            if (!Root.isPropertyTrue(getEnvironment(), Root.USE_NATIVE_COORDINATES)) {
                mouse = new RelativeMouse(super.mouse(), this);
            }
        }
        return mouse;
    }

    public Focus focuser() {
        return focus;
    }

    /**
     * Defines whether a cell is within another cell.
     *
     * @param parent
     * @param cell
     * @param env
     * @return
     */
    public static boolean isInside(Node parent, Node cell, Environment env) {
        if (cell != null) {
            Rectangle bounds = NodeWrap.getScreenBounds(env, cell);
            Rectangle viewBounds = NodeWrap.getScreenBounds(env, parent);
            return (bounds.y > viewBounds.y
                    && bounds.y + bounds.height < viewBounds.y + viewBounds.height);
        } else {
            return false;
        }
    }

        /**
     * Transforms point in local control coordinate system to screen coordinates.
     * @param local
     * @return
     * @see #toLocal(org.jemmy.Point)
     */
    @Override
    public Point toAbsolute(final Point local) {
        Point p = convertToAbsoluteLayout(this, local);
        Rectangle screenBounds = getScreenBounds();
        return new Point(p.x + screenBounds.x, p.y + screenBounds.y);
    }

    /**
     * Transforms point in screen coordinates to local control coordinate system.
     * @param global
     * @return coordinates which should be used for mouse operations.
     * @see #toAbsolute(org.jemmy.Point)
     */
    @Override
    public Point toLocal(final Point global) {
        Rectangle screenBounds = getScreenBounds();
        Point local = new Point(global.x - screenBounds.x, global.y - screenBounds.y);
        return convertToLocalLayout(this, local);
    }

    static Point convertToAbsoluteLayout(final NodeWrap<? extends Node> node, final Point p) {
        return new GetAction<Point>() {
            @Override
            public void run(Object... parameters) {
                Bounds layout = node.getControl().getLayoutBounds();
                Bounds toScene = node.getControl().localToScene(layout);
                Point2D loc = node.getControl().localToScene(layout.getMinX() + p.x, layout.getMinY() + p.y);
                setResult(new Point(loc.getX() - toScene.getMinX(), loc.getY() - toScene.getMinY()));
            }
        }.dispatch(node.getEnvironment());
    }

    static Point convertToLocalLayout(final NodeWrap<? extends Node> node, final Point p) {
        return new GetAction<Point>() {
            @Override
            public void run(Object... parameters) {
                Bounds layout = node.getControl().getLayoutBounds();
                Bounds toScene = node.getControl().localToScene(layout);
                Point2D loc = node.getControl().sceneToLocal(toScene.getMinX() + p.x, toScene.getMinY() + p.y);
                setResult(new Point(loc.getX() - layout.getMinX(), loc.getY() - layout.getMinY()));
            }
        }.dispatch(node.getEnvironment());
    }

    public boolean isFocused() {
        return new GetAction<Boolean>() {
                    @Override
                    public void run(Object... parameters) {
                        setResult(getControl().isFocused() ||
                            (as(Parent.class, Node.class).lookup(new LookupCriteria<Node>() {
                                public boolean check(Node node) {
                                    return node.isFocused();
                                }
                            }).size() > 0));
                    }
                }.dispatch(getEnvironment());
    }
}
