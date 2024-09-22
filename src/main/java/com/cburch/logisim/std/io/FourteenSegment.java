/*
 * Logisim-evolution - digital logic design tool and simulator
 * Copyright by the Logisim-evolution developers
 *
 * https://github.com/logisim-evolution/
 *
 * This is free software released under GNU GPLv3 license
 */

package com.cburch.logisim.std.io;

import static com.cburch.logisim.std.Strings.S;

import com.cburch.logisim.circuit.appear.DynamicElement;
import com.cburch.logisim.circuit.appear.DynamicElementProvider;
import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.AttributeSet;
import com.cburch.logisim.data.Attributes;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Direction;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.fpga.data.ComponentMapInformationContainer;
import com.cburch.logisim.gui.icons.SevenSegmentIcon;
import com.cburch.logisim.instance.Instance;
import com.cburch.logisim.instance.InstanceDataSingleton;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;
import com.cburch.logisim.instance.StdAttr;
import com.cburch.logisim.tools.key.DirectionConfigurator;
import com.cburch.logisim.util.GraphicsUtil;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class FourteenSegment extends InstanceFactory implements DynamicElementProvider {
    /**
     * Unique identifier of the tool, used as reference in project files. Do NOT change as it will
     * prevent project files from loading.
     *
     * <p>Identifier value must MUST be unique string among all tools.
     */
    public static final String _ID = "14-Segment Display";

    static void drawBase(InstancePainter painter, boolean drawPoint) {
        ensureSegments();
        final var data = (InstanceDataSingleton) painter.getData();
        int summ = (data == null ? 0 : (Integer) data.getValue());
        final var active = painter.getAttributeValue(IoLibrary.ATTR_ACTIVE);
        int desired = active == null || active ? 1 : 0;

        final var bds = painter.getBounds();

        final var x = bds.getX() + 25;
        final var y = bds.getY();

        final var g = painter.getGraphics();
        final var onColor = painter.getAttributeValue(IoLibrary.ATTR_ON_COLOR);
        final var offColor = painter.getAttributeValue(IoLibrary.ATTR_OFF_COLOR);
        final var bgColor = painter.getAttributeValue(IoLibrary.ATTR_BACKGROUND);
        if (painter.shouldDrawColor() && bgColor.getAlpha() != 0) {
            g.setColor(bgColor);
            g.fillRect(bds.getX(), bds.getY(), bds.getWidth(), bds.getHeight());
            g.setColor(Color.BLACK);
        }
        //painter.drawBounds();
        g.drawRect(bds.getX(), bds.getY(), 80, bds.getHeight());
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= 14; i++) {
            if (painter.getShowState()) {
                g.setColor(((summ >> i) & 1) == desired ? onColor : offColor);
            }
            if(i == 8){
                int[] x_points ={x+3,x+3,x+10,x+10};
                int[] y_points ={y+12,y+18,y+27,y+21};

                g.fillPolygon(x_points, y_points, 4);
            }
            if(i == 10){
                int[] x_points ={x+16,x+16,x+23,x+23};
                int[] y_points ={y+27,y+21,y+12,y+18};

                g.fillPolygon(x_points, y_points, 4);
            }
            else if(i == 11){
                int[] x_points ={x+10,x+10,x+3,x+3};
                int[] y_points ={y+39,y+33,y+42,y+48};

                g.fillPolygon(x_points, y_points, 4);
            }
            else if(i == 13){
                int[] x_points ={x+16,x+16,x+23,x+23};
                int[] y_points ={y+33,y+39,y+48,y+42};

                g.fillPolygon(x_points, y_points, 4);
            }
            else if (i < 14) {
                Bounds seg = SEGMENTS[i];
                g.fillRect(x + seg.getX(), y + seg.getY(), seg.getWidth(), seg.getHeight());
            } else {
                if (drawPoint) g.fillOval(x + 28, y + 48, 5, 5); // draw decimal point
            }
        }
        g.setColor(Color.BLACK);
        painter.drawLabel();
        painter.drawPorts();
    }

    static void ensureSegments() {
        if (SEGMENTS == null) {
            SEGMENTS =
                    new Bounds[] {
                            Bounds.create(3, 8, 20, 4), // a
                            Bounds.create(24, 10, 4, 19), // b
                            Bounds.create(24, 30, 4, 19), // c
                            Bounds.create(3, 47, 20, 4), // d
                            Bounds.create(-2, 30, 4, 19), // e
                            Bounds.create(-2, 10, 4, 19), // f
                            Bounds.create(3, 28, 9, 4), // g1
                            Bounds.create(13, 28, 10, 4), // g2
                            Bounds.create(0, 0, 0, 0),//h
                            Bounds.create(11, 13, 4, 14), // i
                            Bounds.create(0, 0, 0, 0),//j
                            Bounds.create(0, 0, 0, 0),//k
                            Bounds.create(11, 33, 4, 13), // l
                            Bounds.create(0, 0, 0, 0)//m
                    };
        }
    }

    public static List<String> getLabels() {
        final var labelNames = new ArrayList<String>();
        for (int i = 0; i < 15; i++) labelNames.add("");
        labelNames.set(Segment_A, "Segment_A");
        labelNames.set(Segment_B, "Segment_B");
        labelNames.set(Segment_C, "Segment_C");
        labelNames.set(Segment_D, "Segment_D");
        labelNames.set(Segment_E, "Segment_E");
        labelNames.set(Segment_F, "Segment_F");
        labelNames.set(Segment_G1, "Segment_G1");
        labelNames.set(Segment_G2, "Segment_G2");
        labelNames.set(Segment_H, "Segment_H");
        labelNames.set(Segment_I, "Segment_I");
        labelNames.set(Segment_J, "Segment_J");
        labelNames.set(Segment_K, "Segment_K");
        labelNames.set(Segment_L, "Segment_L");
        labelNames.set(Segment_M, "Segment_M");
        labelNames.set(DP, "DecimalPoint");
        return labelNames;
    }

    public static String getOutputLabel(int id) {
        if (id < 0 || id > getLabels().size()) return "Undefined";
        return getLabels().get(id);
    }

    public static final int Segment_A = 0;
    public static final int Segment_B = 1;
    public static final int Segment_C = 2;
    public static final int Segment_D = 3;
    public static final int Segment_E = 4;
    public static final int Segment_F = 5;
    public static final int Segment_G1 = 6;
    public static final int Segment_G2 = 7;
    public static final int Segment_H = 8;
    public static final int Segment_I = 9;
    public static final int Segment_J = 10;
    public static final int Segment_K = 11;
    public static final int Segment_L = 12;
    public static final int Segment_M = 13;

    public static final int DP = 14;

    static Bounds[] SEGMENTS = null;

    static final Color DEFAULT_OFF = new Color(220, 220, 220);

    public static final Attribute<Boolean> ATTR_DP =
            Attributes.forBoolean("decimalPoint", S.getter("SevenSegDP"));

    public FourteenSegment() {
        super(
                _ID,
                S.getter("fourteenSegmentComponent"),
                new AbstractSimpleIoHdlGeneratorFactory(false),
                true);
        setAttributes(
                new Attribute[] {
                        IoLibrary.ATTR_ON_COLOR,
                        IoLibrary.ATTR_OFF_COLOR,
                        IoLibrary.ATTR_BACKGROUND,
                        IoLibrary.ATTR_ACTIVE,
                        ATTR_DP,
                        StdAttr.LABEL,
                        StdAttr.LABEL_LOC,
                        StdAttr.LABEL_FONT,
                        StdAttr.LABEL_VISIBILITY,
                        StdAttr.MAPINFO
                },
                new Object[] {
                        new Color(240, 0, 0),
                        DEFAULT_OFF,
                        IoLibrary.DEFAULT_BACKGROUND,
                        Boolean.TRUE,
                        Boolean.TRUE,
                        "",
                        Direction.EAST,
                        StdAttr.DEFAULT_LABEL_FONT,
                        false,
                        new ComponentMapInformationContainer(0, 8, 0, null, getLabels(), null)
                });
        setOffsetBounds(Bounds.create(-5, 0, 40, 60));
        setIcon(new SevenSegmentIcon(false));
        setKeyConfigurator(new DirectionConfigurator(StdAttr.LABEL_LOC, KeyEvent.ALT_DOWN_MASK));
    }

    private void updatePorts(Instance instance) {
        final var hasDp = instance.getAttributeValue(ATTR_DP);
        final var ps = new Port[hasDp ? 15 : 14];
        ps[Segment_A] = new Port(0, 0, Port.INPUT, 1);
        ps[Segment_B] = new Port(10, 0, Port.INPUT, 1);
        ps[Segment_C] = new Port(20, 0, Port.INPUT, 1);
        ps[Segment_D] = new Port(30, 0, Port.INPUT, 1);
        ps[Segment_E] = new Port(40, 0, Port.INPUT, 1);
        ps[Segment_F] = new Port(50, 0, Port.INPUT, 1);
        ps[Segment_G1] = new Port(60, 0, Port.INPUT, 1);
        ps[Segment_G2] = new Port(70, 0, Port.INPUT, 1);
        ps[Segment_H] = new Port(0, 60, Port.INPUT, 1);
        ps[Segment_I] = new Port(10, 60, Port.INPUT, 1);
        ps[Segment_J] = new Port(20, 60, Port.INPUT, 1);
        ps[Segment_K] = new Port(30, 60, Port.INPUT, 1);
        ps[Segment_L] = new Port(40, 60, Port.INPUT, 1);
        ps[Segment_M] = new Port(50, 60, Port.INPUT, 1);


        ps[Segment_A].setToolTip(S.getter("Segment_A"));
        ps[Segment_B].setToolTip(S.getter("Segment_B"));
        ps[Segment_C].setToolTip(S.getter("Segment_C"));
        ps[Segment_D].setToolTip(S.getter("Segment_D"));
        ps[Segment_E].setToolTip(S.getter("Segment_E"));
        ps[Segment_F].setToolTip(S.getter("Segment_F"));
        ps[Segment_G1].setToolTip(S.getter("Segment_G1"));
        ps[Segment_G2].setToolTip(S.getter("Segment_G2"));
        ps[Segment_H].setToolTip(S.getter("Segment_H"));
        ps[Segment_I].setToolTip(S.getter("Segment_I"));
        ps[Segment_J].setToolTip(S.getter("Segment_J"));
        ps[Segment_K].setToolTip(S.getter("Segment_K"));
        ps[Segment_L].setToolTip(S.getter("Segment_L"));
        ps[Segment_M].setToolTip(S.getter("Segment_M"));

        if (hasDp) {
            ps[DP] = new Port(60, 60, Port.INPUT, 1);
            ps[DP].setToolTip(S.getter("DecimalPoint"));
        }
        instance.setPorts(ps);
        instance.getAttributeValue(StdAttr.MAPINFO).setNrOfOutports(hasDp ? 15 : 14, getLabels());
    }

    @Override
    public boolean activeOnHigh(AttributeSet attrs) {
        return attrs.getValue(IoLibrary.ATTR_ACTIVE);
    }

    public static void computeTextField(Instance instance) {
        final var facing = instance.getAttributeValue(StdAttr.FACING);
        Object labelLoc = instance.getAttributeValue(StdAttr.LABEL_LOC);

        final var bds = instance.getBounds();
        int x = bds.getX() + bds.getWidth() / 2;
        int y = bds.getY() + bds.getHeight() / 2;
        int halign = GraphicsUtil.H_CENTER;
        int valign = GraphicsUtil.V_CENTER;
        if (labelLoc == Direction.NORTH) {
            y = bds.getY() - 2;
            valign = GraphicsUtil.V_BOTTOM;
        } else if (labelLoc == Direction.SOUTH) {
            y = bds.getY() + bds.getHeight() + 2;
            valign = GraphicsUtil.V_TOP;
        } else if (labelLoc == Direction.EAST) {
            x = bds.getX() + bds.getWidth() + 2;
            halign = GraphicsUtil.H_LEFT;
        } else if (labelLoc == Direction.WEST) {
            x = bds.getX() - 2;
            halign = GraphicsUtil.H_RIGHT;
        }
        if (labelLoc == facing) {
            if (labelLoc == Direction.NORTH || labelLoc == Direction.SOUTH) {
                x += 2;
                halign = GraphicsUtil.H_LEFT;
            } else {
                y -= 2;
                valign = GraphicsUtil.V_BOTTOM;
            }
        }

        instance.setTextField(StdAttr.LABEL, StdAttr.LABEL_FONT, x, y, halign, valign);
    }

    @Override
    protected void configureNewInstance(Instance instance) {
        instance
                .getAttributeSet()
                .setValue(
                        StdAttr.MAPINFO,
                        new ComponentMapInformationContainer(0, 15, 0, null, getLabels(), null));
        instance.addAttributeListener();
        updatePorts(instance);
        computeTextField(instance);
    }

    @Override
    protected void instanceAttributeChanged(Instance instance, Attribute<?> attr) {
        if (attr == StdAttr.FACING) {
            instance.recomputeBounds();
            computeTextField(instance);
        } else if (attr == StdAttr.LABEL_LOC) {
            computeTextField(instance);
        } else if (attr == ATTR_DP) {
            updatePorts(instance);
        }
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        drawBase(painter, painter.getAttributeValue(ATTR_DP));
    }

    @Override
    public void propagate(InstanceState state) {
        var summary = 0;
        final var max = state.getAttributeValue(ATTR_DP) ? 15 : 14;
        for (var i = 0; i < max; i++) {
            Value val = state.getPortValue(i);
            if (val == Value.TRUE) summary |= 1 << i;
        }
        Object value = summary;
        final var data = (InstanceDataSingleton) state.getData();
        if (data == null) {
            state.setData(new InstanceDataSingleton(value));
        } else {
            data.setValue(value);
        }
    }

    @Override
    public DynamicElement createDynamicElement(int x, int y, DynamicElement.Path path) {
        return new SevenSegmentShape(x, y, path);
    }
}

