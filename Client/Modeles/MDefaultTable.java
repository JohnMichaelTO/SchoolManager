package Modeles;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class MDefaultTable<T> extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	
	protected List<T> data = null;
	protected String[] headers;
	
	public MDefaultTable()
	{
		super();
	}

	public int getRowCount()
    {
        return data.size();
    }
 
    public int getColumnCount()
    {
        return headers.length;
    }
 
    public String getColumnName(int columnIndex)
    {
        return headers[columnIndex];
    }
    
    public void add(T object)
    {
        data.add(object);
        super.fireTableRowsInserted(data.size() -1, data.size() -1);
    }
 
    public void remove(int rowIndex)
    {
        data.remove(rowIndex);
        super.fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    public String[] getHeaders()
	{
		return headers;
	}

	public void setHeaders(String[] headers)
	{
		this.headers = headers;
	}

	public T get(int rowIndex)
    {
    	return data.get(rowIndex);
    }
    
    public List<T> getData()
	{
		return data;
	}

    public void setData(List<T> data)
	{
		this.data = data;
		super.fireTableDataChanged();
	}
}
