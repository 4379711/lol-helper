package helper.frame.panel.client;

import com.alibaba.fastjson2.JSONObject;
import helper.bo.SkinBO;
import helper.cache.AppCache;
import helper.frame.listener.MouseCursorListener;
import helper.frame.panel.base.SquareImageButton;
import helper.frame.utils.FrameTipUtil;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author WuYi
 */
@Slf4j
public class CarouselFrame extends JFrame {
    private ArrayList<SkinBO> skinList;
    private JLabel imageLabel;
    private int currentIndex = 0;

    public CarouselFrame(ArrayList<SkinBO> skinList) {
        this.skinList = skinList;

        JLayeredPane layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane, BorderLayout.CENTER);

        imageLabel = new JLabel();
        updateImage();
        imageLabel.setBounds(0, 0, imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height);
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = FrameTipUtil.optionMsg("是否选择该皮肤为客户端背景", "提示");
                if (i == 0) {
                    try {
                        SkinBO skin = skinList.get(currentIndex);
                        // 设置生涯背景
                        JSONObject body = new JSONObject(2);
                        body.put("key", "backgroundSkinId");
                        body.put("value", skin.getId());
                        AppCache.api.setBackgroundSkin(body.toJSONString());
                        //皮肤增强
                        if (skin.getContentId() != null) {
                            body.put("key", "backgroundSkinAugments");
                            body.put("value", skin.getContentId());
                            AppCache.api.setBackgroundSkin(body.toJSONString());
                        }
                    } catch (IOException ex) {
                        log.error(ex.getMessage());
                    }
                }
            }
        });
        imageLabel.addMouseListener(new MouseCursorListener());

        Image prevImage = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/prev.png"))).getImage();
        Image nextImage = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/next.png"))).getImage();
        Image prevHoverImage = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/prev_hover.png"))).getImage();
        Image nextHoverImage = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/assets/next_hover.png"))).getImage();

        SquareImageButton prevButton = new SquareImageButton(prevImage, prevHoverImage, new Dimension(50, 50));
        prevButton.setBounds(20, imageLabel.getHeight() / 2 - 25, 50, 50); // 左侧垂直居中
        prevButton.addActionListener(e -> {
            currentIndex = (currentIndex - 1 + skinList.size()) % skinList.size();
            updateImage();
        });

        SquareImageButton nextButton = new SquareImageButton(nextImage, nextHoverImage, new Dimension(50, 50));
        nextButton.setBounds(imageLabel.getWidth() - 70, imageLabel.getHeight() / 2 - 25, 50, 50); // 右侧垂直居中
        nextButton.addActionListener(e -> {
            currentIndex = (currentIndex + 1) % skinList.size();
            updateImage();
        });

        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(prevButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(nextButton, JLayeredPane.PALETTE_LAYER);

        layeredPane.setPreferredSize(new Dimension(imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height));

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void updateImage() {
        try {
            Image image = AppCache.api.getImageByPath(skinList.get(currentIndex).getUncenteredSplashPath());
            ImageIcon imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
            imageLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
            setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight() + 50));
            pack();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
