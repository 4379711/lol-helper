package yalong.site.frame.panel.client;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import yalong.site.bo.ChampionBO;
import yalong.site.bo.SkinBO;
import yalong.site.cache.AppCache;
import yalong.site.cache.FrameCache;
import yalong.site.cache.FrameInnerCache;
import yalong.site.frame.bo.ComponentBO;
import yalong.site.frame.bo.ItemBO;
import yalong.site.frame.panel.base.BaseComboBox;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * @author chengshuangxiong
 */
@Slf4j
public class CareerBackgroundBox extends BaseComboBox<ItemBO> {

    public CareerBackgroundBox() {
        this.addItem(new ItemBO(null, "选择生涯背景英雄"));
        this.setEditable(true);
        this.addItemListener(listener());
        JTextField editorComponent = (JTextField) this.getEditor().getEditorComponent();
        editorComponent.getDocument().addDocumentListener(documentListener());
    }

    /**
     * @return 带布局的盒子
     */
    public static ComponentBO builder() {
        CareerBackgroundBox box = new CareerBackgroundBox();
        FrameInnerCache.careerBackgroundBox = box;
        GridBagConstraints grid = new GridBagConstraints(
                // 第(0,5)个格子
                0, 4,
                // 占1列,占1行
                1, 1,
                //横向占100%长度,纵向占100%长度
                2, 2,
                //居中,组件小的话就两边铺满窗格
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                // 窗格之间的距离
                new Insets(0, 0, 0, 0),
                // 增加组件的首选宽度和高度
                0, 0
        );
        return new ComponentBO(box, grid);
    }

    private ItemListener listener() {
        return e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                ItemBO item = (ItemBO) e.getItem();
                if (item.getValue() == null) {
                    FrameCache.careerChampionId = null;
                } else {
                    FrameCache.careerChampionId = Integer.parseInt(item.getValue());
                    try {
                        // 先清空上一个英雄的皮肤
                        for (int i = FrameInnerCache.careerBackgroundSkinBox.getItemCount() - 1; i >= 1; i--) {
                            FrameInnerCache.careerBackgroundSkinBox.removeItemAt(i);
                        }
                        // 根据所选英雄获取皮肤
                        List<SkinBO> skin = AppCache.api.getSkinByChampionId(FrameCache.careerChampionId);
                        for (SkinBO bo : skin) {
                            FrameInnerCache.careerBackgroundSkinBox.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
                        }
                    } catch (IOException ex) {
                        log.error("选择生涯背景接口错误", ex);
                    }
                }

            }
        };
    }

    private DocumentListener documentListener(){
        CareerBackgroundBox box = this;
        JTextField editorComponent = (JTextField) box.getEditor().getEditorComponent();
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // 当文档内容增加时触发
                // 获取当前弹框的内容
                String name = box.getEditor().getItem().toString();
                searchChampion(name);
                editorComponent.setText(name);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // 当文档内容减少时触发
                // 获取当前弹框的内容
                String name = box.getEditor().getItem().toString();
                searchChampion(name);
                editorComponent.setText(name);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 一般不触发，除非使用 StyledDocument
            }
        };
    }

    private void searchChampion(String name){
        // 确保 UI 更新不会立即触发新的文档事件
        SwingUtilities.invokeLater(() -> {
            if("选择生涯背景英雄".contains(name)){
                return;
            }
            if (!StrUtil.isBlank(name)){
                // 先清空所有英雄
                for (int i = FrameInnerCache.careerBackgroundBox.getItemCount() - 1; i >= 1; i--) {
                    FrameInnerCache.careerBackgroundBox.removeItemAt(i);
                }
                // 搜索英雄
                for (ChampionBO bo : AppCache.api.getChampionByName(name)) {
                    FrameInnerCache.careerBackgroundBox.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
                }
            }else {
                for (ChampionBO bo : AppCache.allChampion) {
                    FrameInnerCache.careerBackgroundBox.addItem(new ItemBO(String.valueOf(bo.getId()), bo.getName()));
                }
            }
        });
    }
}
