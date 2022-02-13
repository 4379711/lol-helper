package yalong.site.frame.panel.message;

import yalong.site.frame.bo.GlobalDataBO;
import yalong.site.frame.panel.base.BaseTextArea;
import yalong.site.utils.FileUtil;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 输入框
 *
 * @author yaLong
 * @date 2022/2/12
 */
public class MessageTextArea extends BaseTextArea {
    private final String filePath = "dat";
    private boolean change = false;

    public MessageTextArea() {
        // 加载保存的数据
        GlobalDataBO.data = FileUtil.readFile(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        GlobalDataBO.data.forEach(i -> stringBuilder.append(i).append(System.lineSeparator()));
        this.setText(stringBuilder.toString());
        // 鼠标移出时保存
        this.addMouseListener(saveFile());
        this.getDocument().addDocumentListener(changeFlag());
    }

    private DocumentListener changeFlag() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                change = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                change = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                change = true;
            }
        };
    }

    private MouseListener saveFile() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (change) {
                    MessageTextArea textArea = (MessageTextArea) e.getComponent();
                    String text = textArea.getText();
                    String[] split = text.split(System.lineSeparator());
                    ArrayList<String> stringList = new ArrayList<>(Arrays.asList(split));
                    //写到文件中保存
                    FileUtil.writeFile(filePath, stringList);
                    //更新数据到全局变量中
                    GlobalDataBO.data = stringList;
                    change = false;
                }
            }
        };
    }

    public static MessageTextArea builder() {
        return new MessageTextArea();
    }

}
