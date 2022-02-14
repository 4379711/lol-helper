package yalong.site.frame.ui;

import yalong.site.bo.GlobalData;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

/**
 * 重新定义tab页面的绘制
 *
 * @author yaLong
 * @date 2022/2/11
 */
public class MyTabbedPaneUI extends BasicTabbedPaneUI {

    /**
     * 边框和背景的颜色
     */
    public static final Color SELECT_COLOR = new Color(57, 181, 215);
    /**
     * 渐变色的尾色
     */
    public static final Color END_COLOR_SELECT = new Color(220, 255, 250);
    public static final Color END_COLOR_NOT_SELECT = new Color(245, 255, 250);

    /**
     * 绘制tab页签的边框
     */
    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        // 去掉边框
    }


    /**
     * 绘制选项卡渐变背景色和圆角矩形
     */
    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gradient;
        switch (tabPlacement) {
            case LEFT:
                if (isSelected) {
                    gradient = new GradientPaint(x + 1, y, SELECT_COLOR, x + w, y, END_COLOR_SELECT, true);
                } else {
                    gradient = new GradientPaint(x + 1, y, Color.LIGHT_GRAY, x + w, y, END_COLOR_NOT_SELECT, true);
                }
                g2d.setPaint(gradient);
                //画圆角
                g2d.fillRoundRect(x + 1, y + 1, w - 1, h - 2, 10, 10);
                break;
            case RIGHT:
                if (isSelected) {
                    gradient = new GradientPaint(x + w, y, SELECT_COLOR, x + 1, y, END_COLOR_SELECT, true);
                } else {
                    gradient = new GradientPaint(x + w, y, Color.LIGHT_GRAY, x + 1, y, END_COLOR_NOT_SELECT, true);
                }
                g2d.setPaint(gradient);
                //画圆角
                g2d.fillRoundRect(x, y + 1, w - 1, h - 2, 10, 10);
                break;
            case BOTTOM:
                if (isSelected) {
                    gradient = new GradientPaint(x + 1, y + h, SELECT_COLOR, x + 1, y, END_COLOR_SELECT, true);
                } else {
                    gradient = new GradientPaint(x + 1, y + h, Color.LIGHT_GRAY, x + 1, y, END_COLOR_NOT_SELECT, true);
                }
                g2d.setPaint(gradient);
                //画圆角
                g2d.fillRoundRect(x + 1, y, w - 2, h - 1, 10, 10);
                break;
            case TOP:
            default:
                if (isSelected) {
                    gradient = new GradientPaint(x + 1, y, SELECT_COLOR, x + 1, y + h, END_COLOR_SELECT, true);
                } else {
                    gradient = new GradientPaint(x + 1, y, Color.LIGHT_GRAY, x + 1, y + h, END_COLOR_NOT_SELECT, true);
                }
                g2d.setPaint(gradient);
                //画圆角
                g2d.fillRoundRect(x + 1, y + 1, w - 2, h - 1, 10, 10);
        }
    }

    /**
     * 绘制TabbedPane容器的四周边框样式
     */
    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        // 不想要边框，直接重写一个空方法
    }

    /**
     * 绘制选中某个Tab后，获得焦点的样式
     */
    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // 重写空方法，用来去掉虚线
    }

    /**
     * 设置tab之间的间距
     */
    @Override
    protected LayoutManager createLayoutManager() {
        return new TabbedPaneLayout() {

            @Override
            protected void calculateTabRects(int tabPlacement, int tabCount) {
                final int spacer = 10;
                final int indent = 4;
                super.calculateTabRects(tabPlacement, tabCount);
                for (int i = 0; i < rects.length; i++) {
                    rects[i].y += i * spacer + indent;
                }
            }
        };
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 3;
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 20;
    }

    @Override
    protected int calculateMaxTabHeight(int tabPlacement) {
        int height = super.calculateMaxTabHeight(tabPlacement);
        return Math.min(height, GlobalData.HEIGHT);
    }

    @Override
    protected int calculateMaxTabWidth(int tabPlacement) {
        int width = super.calculateMaxTabWidth(tabPlacement);
        return Math.min(width, GlobalData.WIDTH);
    }


}
