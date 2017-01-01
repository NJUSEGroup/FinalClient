package hrs.client.UI.HotelUI.RoomUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import hrs.client.UI.HotelUI.Components.RoomTableModel;
import hrs.client.UI.HotelUI.RoomUI.Listener.AddListener;
import hrs.client.util.ControllerFactory;
import hrs.client.util.HMSBlueButton;
import hrs.client.util.UIConstants;
import hrs.common.Controller.HotelController.IRoomController;
import hrs.common.Exception.RoomService.RoomNotFoundException;
import hrs.common.VO.HotelVO;
import hrs.common.VO.RoomVO;
import hrs.common.util.type.RoomType;

public class RoomUIPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3459435356780911369L;
	private JPanel jpRoom;
	private JPanel jpButton;
	private JScrollPane jspRoom;
	private JTable jtRoom;
	private JTableHeader jthOrderList;
	private RoomTableModel roomTableModel;
	private HMSBlueButton jbAdd;
	private JLabel jlTotal;
	private JLabel jlNum;
	private JLabel jlRecord;
	private HotelVO theHotel;
	private IRoomController roomController;
	private AddListener addListener;
	private List<RoomVO> rooms;
	private Color panelColor;
	private Color tableHeadColor;
	private Font font;
	
	/**
	 * 初始化录入客房界面面板
	 */
	public RoomUIPanel(HotelVO theHotel){
		init(theHotel);
	}
	
	public void init(HotelVO theHotel){
		this.theHotel = theHotel;
		this.setSize(1080, 722);
		this.setLayout(null);
		
		this.setFontAndColor();
		this.setPanel();
		this.setRoomPanel();
		this.setButtonPanel();
	}
	
	public void setFontAndColor(){
		panelColor = UIConstants.JFRAME;
		tableHeadColor = UIConstants.JTABLEHEADER_COLOR;
		font = UIConstants.FONT_16;
	}
	
	/**
	 * 设置面板
	 */
	public void setPanel(){
		jpRoom = new JPanel();
		jpRoom.setBounds(0, 0, 1080, 642);
		jpRoom.setBackground(panelColor);
		jpRoom.setLayout(null);
		
		jpButton = new JPanel();
		jpButton.setBounds(0, 642, 1080, 80);
		jpButton.setBackground(panelColor);
		jpButton.setLayout(null);
		
		this.add(jpRoom);
		this.add(jpButton);
	}
	
	/**
	 * 设置房间信息面板
	 */
	public void setRoomPanel(){
		roomController = ControllerFactory.getRoomController();
		
		rooms = new ArrayList<RoomVO>();
		
		try {
			rooms= roomController.findByHotelID(theHotel.id);
		} catch (RoomNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "您的酒店尚未录入客房！", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
		
		roomTableModel = new RoomTableModel(rooms);
		
		jtRoom = new JTable(roomTableModel);
		jtRoom.setBackground(Color.WHITE);
		jtRoom.setFont(font);
		jtRoom.setRowHeight(40);
		jtRoom.setShowVerticalLines(false);
		
		jthOrderList = jtRoom.getTableHeader(); 
		jthOrderList.setPreferredSize(new Dimension(jtRoom.getWidth(),40)); 
		jthOrderList.setBackground(tableHeadColor);
		jthOrderList.setEnabled(false);
		jthOrderList.setBorder(new EmptyBorder(0,0,0,0));
		jthOrderList.setFont(font);
		
		jspRoom = new JScrollPane(jtRoom);
		jspRoom.setBounds(10, 10, 1060, 622);
		jspRoom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jspRoom.setOpaque(false);
		jspRoom.getViewport().setOpaque(false);
		jspRoom.setBackground(Color.WHITE);
		
		jpRoom.add(jspRoom);
	}
	
	/**
	 * 设置按钮面板
	 */
	public void setButtonPanel(){
		jlTotal = new JLabel();
		jlTotal.setBounds(15, 15, 35, 20);
		jlTotal.setFont(font);
		jlTotal.setText("共有");
		
		jlNum = new JLabel();
		jlNum.setBounds(50, 15, 25, 20);
		jlNum.setFont(font);
		
		jlRecord = new JLabel();
		jlRecord.setBounds(80, 15, 50, 20);
		jlRecord.setFont(font);
		jlRecord.setText("条记录");
		
		addListener = new AddListener(this);
		
		jbAdd = new HMSBlueButton("添加");
		jbAdd.setBounds(736, 13, 90, 40);
		jbAdd.addMouseListener(addListener);
		
		jpButton.add(jbAdd);
		jpButton.add(jlTotal);
		jpButton.add(jlNum);
		jpButton.add(jlRecord);
	}
	
	/**
	 * 点击添加按钮，弹出添加房间对话框
	 */
	public void add(){
		List<RoomType> notAddedRoom = roomController.findNotAddedRoomType(theHotel.id);
		
		AddRoomDialog addRoomDialog = new AddRoomDialog(notAddedRoom, this);
	}
	
	/**
	 * 添加房间
	 * @param newRoom
	 */
	public void addRoom(RoomVO newRoom) {
		newRoom.hotel = theHotel;
		roomController.addRoom(newRoom);
	}
	
	/**
	 * 获取在表格中被选中的房间类型
	 * @return
	 */
	public String getSelectedRoomType(){
		int row = jtRoom.getSelectedRow();
		String roomType = (String) jtRoom.getValueAt(row, 0);
		
		return roomType;
	}
	
	/**
	 * 刷新房间列表
	 */
	public void refreshRoomList(){
		try {
			rooms= roomController.findByHotelID(theHotel.id);
		} catch (RoomNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "您的酒店尚未录入客房！", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
		roomTableModel = new RoomTableModel(rooms);
		jtRoom.setModel(roomTableModel);
		jlNum.setText(Integer.toString(rooms.size()));
	}

}
