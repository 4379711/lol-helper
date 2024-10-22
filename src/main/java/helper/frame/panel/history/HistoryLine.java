package helper.frame.panel.history;

import helper.bo.GameData;
import helper.frame.bo.LocationBO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 每一行的战绩
 *
 * @author WuYi
 */
public class HistoryLine extends JPanel {
	// 创建用于显示图片的JLabel和用于显示文字的JLabel
	private static final ArrayList<GridBagConstraints> gridList = getGridBagConstraints();

	public HistoryLine(List<LocationBO> list, Color color, Long gameId) {

		//设置不可编辑
		this.setOpaque(true);
		//设置背景颜色
		this.setBackground(color);
		//设置边框为黑色，宽度为5，圆角
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
		//网格布局
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		// 使用GridBagLayout布局管理器
		for (LocationBO locationBO : list) {
			add(locationBO.getJLabel(), locationBO.getGrid());
		}
		this.addMouseListener(new SelectDetailListener(gameId));
	}

	public static HistoryLine builder(ArrayList<Object> objects, Color color, Long gameId) {
		List<LocationBO> list = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++) {
			LocationBO locationBO = new LocationBO();
			locationBO.setJLabel(getJLabel(objects.get(i)));
			locationBO.setGrid(gridList.get(i));
			list.add(locationBO);
		}

		return new HistoryLine(list, color, gameId);
	}

	private static JLabel getJLabel(Object obj) {
		JLabel jLabel = new JLabel();
		if (obj instanceof ImageIcon) {
			jLabel.setIcon((ImageIcon) obj);
		} else if (obj instanceof String) {
			jLabel.setText((String) obj);
		}
		return jLabel;
	}

	/**
	 * 每个组件的布局 需要与{@link MatchPanel#createMatchHistoryBO(GameData)}方法对应
	 */
	private static ArrayList<GridBagConstraints> getGridBagConstraints() {
		ArrayList<GridBagConstraints> arrayList = new ArrayList<>();
		//第一行
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				0, 0,
				// 占2列,占2行
				2, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.WEST,
				// 窗格之间的距离
				new Insets(0, 10, 0, 10),
				// 增加组件的首选宽度和高度
				0, 0
		));
		//英雄头像
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				2, 0,
				// 占2列,占2行
				2, 2,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 10, 0, 10),
				// 增加组件的首选宽度和高度
				0, 0
		));
		//召唤师技能
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				4, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 1, 0, 1),
				// 增加组件的首选宽度和高度
				0, 0
		));
		//基石符文
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				5, 0,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 1, 0, 1),
				// 增加组件的首选宽度和高度
				0, 0
		));
		//KDA
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				6, 0,
				// 占2列,占2行
				4, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 5, 0, 5),
				// 增加组件的首选宽度和高度
				0, 0
		));

		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				9, 0,
				// 占2列,占2行
				4, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 20, 0, 20),
				// 增加组件的首选宽度和高度
				0, 0
		));

		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				13, 0,
				// 占2列,占2行
				2, 2,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 20, 0, 20),
				// 增加组件的首选宽度和高度
				0, 0
		));
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				15, 0,
				// 占2列,占2行
				3, 2,
				//横向占100%长度,纵向占100%长度
				100, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 20, 0, 5),
				// 增加组件的首选宽度和高度
				0, 0
		));

		//第二行
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				0, 1,
				// 占2列,占2行
				2, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.WEST,
				// 窗格之间的距离
				new Insets(0, 10, 0, 10),
				// 增加组件的首选宽度和高度
				0, 0
		));
		//技能
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				4, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 1, 0, 1),
				// 增加组件的首选宽度和高度
				0, 0
		));
		//天赋
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				5, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 1, 0, 1),
				// 增加组件的首选宽度和高度
				0, 0
		));

		//装备
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				6, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(5, 10, 5, 5),
				// 增加组件的首选宽度和高度
				0, 0
		));
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				7, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 3, 0, 3),
				// 增加组件的首选宽度和高度
				0, 0
		));
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				8, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 3, 0, 3),
				// 增加组件的首选宽度和高度
				0, 0
		));
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				9, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 3, 0, 3),
				// 增加组件的首选宽度和高度
				0, 0
		));
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				10, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 3, 0, 3),
				// 增加组件的首选宽度和高度
				0, 0
		));
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				11, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 3, 0, 3),
				// 增加组件的首选宽度和高度
				0, 0
		));
		arrayList.add(new GridBagConstraints(
				// 第(0,0)个格子
				12, 1,
				// 占2列,占2行
				1, 1,
				//横向占100%长度,纵向占100%长度
				0, 0,
				//居中,组件小的话就两边铺满窗格
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				// 窗格之间的距离
				new Insets(0, 3, 0, 3),
				// 增加组件的首选宽度和高度
				0, 0
		));
		return arrayList;
	}

}
