package org.telluriumsource.test.ddt.mapping.io

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.telluriumsource.ui.Const
import org.telluriumsource.ui.Const

/**
 * The implementation for the field set reader to read excel files
 *
 * @author Ajay Ravichandran (ajay.ravichandran@gmail.com)
 *
 * Date: Jul 23, 2008
 *
 */
class ExcelDataReader implements DataReader{


		protected HSSFSheet workSheet = null
		protected int currentRowCounter = 0

		public void setupDataStream(FileInputStream input)
		{
			workSheet = new HSSFWorkbook(new POIFSFileSystem(input)).getSheetAt(0)
		}
		public int getReaderType()
		{
			return Const.EXCEL_TYPE
		}
		public List readLineFromDataStream()
		{
			List<String, String> lst = new ArrayList<String, String>()
			boolean lineRead = false
			int maxRows = workSheet.getPhysicalNumberOfRows()
			for(int i = currentRowCounter; i < maxRows; i++) {
				HSSFRow currentRow = workSheet.getRow(i)
				Iterator cellIter = currentRow.cellIterator();
	        	while(cellIter.hasNext()){
	        		  HSSFCell cell = (HSSFCell) cellIter.next();
	        		  if(cell !=null && cell.toString() !=null && cell.toString().length() > 0)
	        		  {
	        			  lst.add(cell.toString())
		                  lineRead = true
	        		  }
	        	}
	        	currentRowCounter++
		        if(lineRead)
		        {
		        	break;
		        }
			}
			return lst;
		}

	    public List readLine(BufferedReader reader) {
	    	return null
		}
}