package hrs.client.UI.UserUI.UserInfoUI.UserInfoListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import hrs.client.UI.UserUI.UserInfoUI.UserInfoLabel;
import hrs.client.UI.UserUI.UserInfoUI.UserInfoPanel;
import hrs.client.util.ControllerFactory;
import hrs.common.Controller.UserController.IUserController;
import hrs.common.VO.UserVO;

/**
 * 
 * 用户信息界面确认按钮事件监听
 * 
 * @author 涵
 *
 */
public class ConfirmListener implements ActionListener {
	
	private UserInfoPanel panel;
	

	public ConfirmListener(UserInfoPanel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		panel.update();

	}

}
