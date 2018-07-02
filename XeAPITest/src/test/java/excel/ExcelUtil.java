package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import excel.Common;
import excel.Util;
import excel.ReqParams;

public class ExcelUtil {    
    /**
     * read the Excel file
     * @throws IOException
     */
    public List<ReqParams> readExcel(String path) throws IOException {
        if (path == null || Common.EMPTY.equals(path)) {
            return null;
        } else {
            String postfix = Util.getPostfix(path);
            if (!Common.EMPTY.equals(postfix)) {
                if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path);
                }
            } else {
                System.out.println(path + Common.NOT_EXCEL_FILE);
            }
        }
        return null;
    }

    /**
     * Read the Excel 2010
     * @throws IOException
     */
    public List<ReqParams> readXlsx(String path) throws IOException {
        System.out.println(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        ReqParams param = null;
        List<ReqParams> list = new ArrayList<ReqParams>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                	param = new ReqParams();
                	XSSFCell noRow=xssfRow.getCell(0);
                    XSSFCell interfaceChName = xssfRow.getCell(1);                   
                    XSSFCell caseNo = xssfRow.getCell(3);
                    XSSFCell caseName = xssfRow.getCell(4);
                    XSSFCell domainName = xssfRow.getCell(5);
                    XSSFCell upath=xssfRow.getCell(6);
                    XSSFCell requestParam=xssfRow.getCell(9);
                    XSSFCell expResult=xssfRow.getCell(10);
                    param.setNoRow(getValue(noRow));
                    param.setInterfaceChName(getValue(interfaceChName));
                    param.setCaseNo(getValue(caseNo));
                    param.setCaseName(getValue(caseName));
                    param.setDomainName(getValue(domainName));
                    param.setUpath(getValue(upath));
                    param.setRequestParam(getValue(requestParam));
                    param.setExpResult(getValue(expResult));
                    list.add(param);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    //Write the Excel
    public static void writeCell(String path,int x,int y,String value) {
		try {
			//����Excel�Ĺ������ Workbook,��Ӧ��һ��excel�ĵ�
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(path));
			XSSFSheet sheet=wb.getSheetAt(0);
			XSSFRow row=sheet.getRow(x);
			XSSFCell cell=row.getCell((short) y);
			cell.setCellValue(value);
			FileOutputStream os;
			os = new FileOutputStream(path);
			wb.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}