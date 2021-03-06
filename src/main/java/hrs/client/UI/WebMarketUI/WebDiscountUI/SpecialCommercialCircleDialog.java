package hrs.client.UI.WebMarketUI.WebDiscountUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import hrs.client.UI.WebMarketUI.WebDiscountUI.WebDiscountListener.CancelModifySpecialCommercialListener;
import hrs.client.UI.WebMarketUI.WebDiscountUI.WebDiscountListener.ConfirmModifySpecialCommercialListener;
import hrs.client.util.ControllerFactory;
import hrs.client.util.HMSBlueButton;
import hrs.client.util.RegExpHelper;
import hrs.client.util.UIConstants;
import hrs.common.Controller.WebMarketController.IWebDiscountController;
import hrs.common.VO.CommercialCircleVO;
import hrs.common.VO.LocationVO;
import hrs.common.VO.WebDiscountVO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpecialCommercialCircleDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7143214906969942501L;
	private final JPanel contentPanel = new JPanel();
	private JTextField jtextDiscount;
	private WebDiscountPanel webDiscountPanel;
	private WebDiscountVO webDiscountVO;
	private JLabel jlLocation;
	private JLabel jlCommercialCircle;
	private JComboBox<Object> jcomboBoxCommercialCircle;
	private JComboBox<Object> jcomboBoxLocation;
	private HMSBlueButton jbConfirmModify, jbCancelModify;
	private JLabel jlDiscount;
	private LocationVO location;
	private List<LocationVO> locs;
	private List<LocationVO> locations;
	private List<CommercialCircleVO> commercialCircles;
	private List<CommercialCircleVO> commercialCircleList;
	private int locationIndex;
	private IWebDiscountController controller = ControllerFactory.getWebDiscountController();
	private ConfirmModifySpecialCommercialListener listener;
	private CancelModifySpecialCommercialListener cancelListener;
	private JLabel jlDisc;

	public SpecialCommercialCircleDialog(WebDiscountPanel webDiscountPanel) {
		this.webDiscountPanel = webDiscountPanel;
		init();
	}

	/**
	 * Create the dialog.
	 */
	public void init() {
		webDiscountVO = webDiscountPanel.getSelected();

		getContentPane().setBackground(UIConstants.JFRAME);
		this.setTitle("特定商圈专属折扣修改");
		this.setBounds(100, 100, 420, 300);
		this.setResizable(false);
		this.setModal(true);
		this.setLocationRelativeTo(null);

		getContentPane().setLayout(null);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		jlLocation = new JLabel("城市");
		jlLocation.setFont(UIConstants.FONT_16);
		jlLocation.setBounds(99, 30, 82, 29);
		getContentPane().add(jlLocation);

		jlCommercialCircle = new JLabel("商圈");
		jlCommercialCircle.setFont(UIConstants.FONT_16);
		jlCommercialCircle.setBounds(99, 87, 82, 29);
		getContentPane().add(jlCommercialCircle);

		jlDiscount = new JLabel("折扣信息");
		jlDiscount.setFont(UIConstants.FONT_16);
		jlDiscount.setBounds(99, 141, 82, 29);
		getContentPane().add(jlDiscount);

		jbConfirmModify = new HMSBlueButton("确认修改");
		jbConfirmModify.setFont(UIConstants.FONT_16);
		jbConfirmModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		jbConfirmModify.setBounds(120, 199, 84, 29);
		getContentPane().add(jbConfirmModify);
		listener = new ConfirmModifySpecialCommercialListener(webDiscountPanel, this);
		jbConfirmModify.addMouseListener(listener);

		jbCancelModify = new HMSBlueButton("取消修改");
		jbCancelModify.setFont(UIConstants.FONT_16);
		jbCancelModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		jbCancelModify.setBounds(228, 199, 84, 29);
		getContentPane().add(jbCancelModify);
		cancelListener = new CancelModifySpecialCommercialListener(this);
		jbCancelModify.addMouseListener(cancelListener);

		jcomboBoxLocation = new JComboBox<Object>();
		jcomboBoxLocation.setBounds(195, 33, 130, 27);
		jcomboBoxLocation.setFont(UIConstants.FONT_16);
		getContentPane().add(jcomboBoxLocation);
		for (int i = 0; i < locToName().length; i++) {
			jcomboBoxLocation.addItem(locToName()[i]);
		}
		jcomboBoxLocation.setSelectedItem(webDiscountVO.location.name);

		jcomboBoxCommercialCircle = new JComboBox<Object>();
		jcomboBoxCommercialCircle.setFont(UIConstants.FONT_16);
		jcomboBoxCommercialCircle.setBounds(195, 90, 130, 27);
		getContentPane().add(jcomboBoxCommercialCircle);

		commercialCircleList = controller.findCircleByLoc(webDiscountVO.location.id);
		Object[] circleName = new Object[commercialCircleList.size()];

		for (int i = 0; i != commercialCircleList.size(); ++i) {
			circleName[i] = commercialCircleList.get(i).name;
		}

		DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(circleName);
		jcomboBoxCommercialCircle.setModel(model);

		// jcomboBoxCommercialCircle.setSelectedItem(webDiscountVO.commercialCircle.name);

		jcomboBoxLocation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				locationIndex = jcomboBoxLocation.getSelectedIndex();
				location = locs.get(locationIndex);
				commercialCircleList = controller.findCircleByLoc(location.id);
				Object[] circleName = new Object[commercialCircleList.size()];
				for (int i = 0; i != commercialCircleList.size(); ++i) {
					circleName[i] = commercialCircleList.get(i).name;
				}
				DefaultComboBoxModel<Object> model = new DefaultComboBoxModel<>(circleName);
				jcomboBoxCommercialCircle.setModel(model);
			}
		});

		jtextDiscount = new JTextField();
		jtextDiscount.setFont(UIConstants.FONT_17);
		jtextDiscount.setBounds(213, 141, 112, 26);
		getContentPane().add(jtextDiscount);
		jtextDiscount.setColumns(10);

		jlDisc = new JLabel("0.");
		jlDisc.setBounds(195, 141, 31, 26);
		jlDisc.setFont(UIConstants.FONT_17);
		getContentPane().add(jlDisc);
	}

	public WebDiscountVO getModifyVO() {
		locations = controller.findAllLocations();
		int j = 0, k = 0;
		double newDiscount = 0;

		if (!RegExpHelper.matchOnlyNum(jtextDiscount.getText())) {
			JOptionPane.showMessageDialog(null, "折扣信息中不能包含非数字字符！", "Error", JOptionPane.WARNING_MESSAGE);
		} else if (jtextDiscount.getText().length() > 2) {
			JOptionPane.showMessageDialog(null, "折扣信息中只能包含一位或两位有效数字！", "Error", JOptionPane.WARNING_MESSAGE);
		} else if (Integer.valueOf(jtextDiscount.getText()) == 0) {
			JOptionPane.showMessageDialog(null, "促销策略不为0！", "Error", JOptionPane.WARNING_MESSAGE);
		} else {
			if (jtextDiscount.getText().length() == 1) {
				newDiscount = Double.valueOf(jtextDiscount.getText()) / 10;
			} else {
				newDiscount = Double.valueOf(jtextDiscount.getText()) / 100;
			}

		}

		String newLocation = (String) jcomboBoxLocation.getSelectedItem();
		String newCommercialCircle = (String) jcomboBoxCommercialCircle.getSelectedItem();
		for (j = 0; j < locations.size(); j++) {
			if (locations.get(j).name.equals(newLocation))
				break;
		} // 找到新的位置
		webDiscountVO.location = locations.get(j);

		commercialCircles = controller.findCircleByLoc(j + 1);
		System.out.println(commercialCircles.size());
		for (k = 0; k < commercialCircles.size(); k++) {
			if (commercialCircles.get(k).name.equals(newCommercialCircle))
				break;
		} // 找到新的商圈
		webDiscountVO.commercialCircle = commercialCircles.get(k);

		webDiscountVO.discount = newDiscount;

		return webDiscountVO;
	}// 转成vo，而不仅仅是string；否则id没发生变化

	public double getNewDiscount() {
		return getModifyVO().discount;
	}

	private Object[] locToName() {
		locs = controller.findAllLocations();
		Object[] names = new Object[locs.size()];
		for (int i = 0; i != names.length; ++i) {
			names[i] = locs.get(i).name;
		}
		return names;
	}
}
