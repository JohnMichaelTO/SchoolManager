package Modeles;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

public class MComboBox<T> extends DefaultComboBoxModel<T>
{
	private static final long serialVersionUID = 1L;
	
	private List<T> data = null;

	public MComboBox()
	{
		super();
		data = new ArrayList<T>();
	}

	public MComboBox(List<T> data)
	{
		super();
		this.setData(data);
	}

	public List<T> get()
	{
		return data;
	}

	@Override
	public T getElementAt(int index)
	{
		return data.get(index);
	}

	@Override
	public int getSize()
	{
		return data.size();
	}

	@Override
	public int getIndexOf(Object element)
	{
		return data.indexOf(element);
	}
	
	public List<T> getData()
	{
		return data;
	}

	public void setData(List<T> data)
	{
		this.data = data;
	}
}
