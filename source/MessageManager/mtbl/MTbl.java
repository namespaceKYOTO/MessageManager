/*---------------------------------------------------------------------*/
/*!
 * @brief	.mtbl File
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.io.*;
import java.util.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MTbl
{
	static private String MTBL = ".mtbl";
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public MTbl()
	{
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	.mtbl File Open
	/*---------------------------------------------------------------------*/
	public void open(File file, MesTable mesTable)
	{
		JTable table = mesTable.getTable();
		DefaultTableModel tableModel = mesTable.getTableModel();
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
		
		try
		{
			FileInputStream fileStrem = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileStrem, "UTF-16"));
			
			String line;
			
			// Column comment
			line = bufferedReader.readLine();
			// Column
			line = bufferedReader.readLine();
			String[] column = line.split("\t");
			for(String val : column)
			{
				tableModel.addColumn(val);
			}
			
			// Row comment
			line = bufferedReader.readLine();
			// Row
			while((line = bufferedReader.readLine()) != null)
			{
				String[] rowData = line.split("\t");
				Stack<String>row = new Stack<String>();
				for(String val : rowData)
				{
					row.push(val);
				}
				tableModel.addRow(row);
			}
			
			// redisplay
			int rc = tableModel.getRowCount();
			table.scrollRectToVisible(table.getCellRect(rc, 0, false));
		}
		catch(IOException e)
		{
			System.out.println("Failed File Open : " + e.getMessage());
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	.mtbl File Save
	/*---------------------------------------------------------------------*/
	public void save(File file, MesTable table)
	{
		try
		{
			if(file.getName().endsWith(MTBL) == false)
			{
				File newFile = new File(file.getParent(), file.getName() + MTBL);
				file = newFile;
			}
			file.createNewFile();
			
			OutputStreamWriter outputStream = new OutputStreamWriter( new PrintStream(file), "UTF-16");
			
			// Column Comment
			outputStream.println("# Column Data");
			
			
			outputStream.flush();
			outputStream.close();
		}
		catch(IOException e)
		{
			System.out.println("Fialed File Save : " + e.getMessage());
		}
	}
}
