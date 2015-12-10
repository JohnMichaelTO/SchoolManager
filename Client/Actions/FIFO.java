package Actions;

import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class FIFO extends SwingWorker<Object, Object>
{
	private SwingWorker swing1;
	private SwingWorker swing2;
	
	public FIFO(SwingWorker swing1, SwingWorker swing2)
	{
		this.swing1 = swing1;
		this.swing2 = swing2;
	}
	
	@Override
	protected Object doInBackground() throws Exception
	{
		swing1.execute();
		return null;
	}
	
	@Override
	protected void done()
	{
		try
		{
			swing1.get();
			swing2.execute();
		}
		catch (InterruptedException | ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}