package hrs.client.UI.UserUI.HotelInfoUI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import hrs.common.VO.HotelVO;

public class HotelTableModel implements TableModel {
	private List<HotelVO> hotels;

	public HotelTableModel(List<HotelVO> hotels) {
		
		this.hotels = hotels;

	}
	@Override
	public int getRowCount() {
		return hotels.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		String[] l = { "酒店名称", "酒店地址", "星级", "评分"};
		return l[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HotelVO currentInfo = hotels.get(rowIndex);
		List<String> list = new ArrayList<>();
		
		list.add(currentInfo.name);
		list.add(currentInfo.location.name+currentInfo.street);
		list.add(currentInfo.star+"");
		list.add(currentInfo.score+"");
		return list.get(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

}
