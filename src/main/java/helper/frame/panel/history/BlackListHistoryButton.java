package helper.frame.panel.history;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import helper.bo.BlackListBO;
import helper.cache.FrameInnerCache;
import helper.cache.FrameSetting;
import helper.constant.GameConstant;
import helper.frame.panel.base.BaseButton;
import helper.frame.utils.FrameTipUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @_@
 */
@Slf4j
public class BlackListHistoryButton extends BaseButton {
    public BlackListHistoryButton() {
        this.setText("黑名单查询");
        this.addActionListener(actionListener());
    }

    private ActionListener actionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame jFrame = new JFrame("黑名单查询");
                FrameInnerCache.blackListFrame = jFrame;
                Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/assets/logo.png"));
                jFrame.setIconImage(image);
                jFrame.setSize(FrameSetting.MATCH_WIDTH, FrameSetting.MATCH_HEIGHT);
                //窗口居中
                jFrame.setLocationRelativeTo(null);

                File file = new File(GameConstant.BLACK_LIST_FILE);
                if (!FileUtil.exist(file)) {
                    FileUtil.mkdir(file);
                }
                BlackListMatchPanel blackListMatchPanel = new BlackListMatchPanel(true);
                jFrame.add(blackListMatchPanel);
                jFrame.setVisible(true);
                List<String> blacklistFiles = ListUtil.reverse(FileUtil.listFileNames(file.getAbsolutePath()));
                List<String> page = ListUtil.page(0, FrameSetting.PAGE_SIZE, blacklistFiles);
                List<BlackListBO> blackLists = new ArrayList<BlackListBO>();
                List<String> errorList = new ArrayList<>();
                for (String s : page) {
                    File blacklistFile = new File(GameConstant.BLACK_LIST_FILE + s);
                    String jsonString = FileUtil.readUtf8String(blacklistFile);
                    BlackListBO blackListBO = JSON.parseObject(jsonString, BlackListBO.class);
                    if (blackListBO != null) {
                        blackLists.add(blackListBO);
                    } else {
                        errorList.add(blacklistFile.getAbsolutePath());
                    }
                }
                if (!errorList.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("含有非战绩文件请删除：\n");
                    for (String s : errorList) {
                        sb.append(s);
                        sb.append("\n");
                    }
                    FrameTipUtil.errorMsg(sb.toString());
                    FrameInnerCache.blackListFrame.setVisible(false);
                }
                blackListMatchPanel.resetIndex();
                blackListMatchPanel.setData(blackLists);
            }
        };
    }

}
