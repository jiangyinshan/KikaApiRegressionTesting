package Util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvMalformedLineException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class CsvAction {
    final String CsvFilePath = "/Users/xm20190901/Downloads/接口自动化.csv";
    final int responseCodeColumn = 9;//响应码所在列数
    final int errorCodeColumn = 10;//errorCode所在列数
    final int errorMsgColumn = 11;//errorMsg所在列数
    final int dataColumn = 12;//响应中data字段的内容的所在列数
    final int conclusionColumn = 13;//接口是否可用的结果所在列数
    final int logicConclusionColumn = 14;//业务是否可用的结果所在列数
    public static Log log = LogFactory.getLog(CsvAction.class.getName());
    private volatile static CsvAction instance;

    public static CsvAction getInstance() {
        if (instance == null) {
            synchronized (CsvAction.class) {
                if (instance == null) {
                    instance = new CsvAction();
                }
            }
        }
        return instance;
    }

    public static List<String[]> allRecords;

    /**
     * 读取csv文件，每一列数据为字符串数组String[]，返回数组列表List<String[]>
     **/
    public List<String[]> getCSVDataList() throws CsvMalformedLineException {
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(CsvFilePath), Charset.forName("UTF-8"));
            CSVReader reader = new CSVReader(in);
            allRecords = reader.readAll();
            reader.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (CsvException csvException) {
            csvException.printStackTrace();
        }
        return allRecords;
    }

    public void writeCSV(List<String[]> csvData) {
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(
                    new OutputStreamWriter(new FileOutputStream(CsvFilePath), Charset.forName("utf-8")));
            writer.writeAll(csvData);
            writer.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param line   要修改的单元格的行数,注意是行数的index，如果line=1，则对应csv文件第2行
     * @param column 要修改的单元格的列数，注意是列数的index，如果line=1，则对应csv文件第2列
     * @param value  要修改的单元格的值
     **/
    public void SetCellValue(int line, int column, String value) throws CsvMalformedLineException {
        if (allRecords == null) {
            CsvAction.getInstance().getCSVDataList();
        }
        if (line > allRecords.size() || column > allRecords.get(line).length) {
            log.fatal("行数或列数错误，超出最大范围");
        }
        allRecords.get(line)[column] = value;
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(
                    new OutputStreamWriter(new FileOutputStream(CsvFilePath), Charset.forName("utf-8")));
            writer.writeAll(allRecords);
            writer.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RecordResponseCode(int line, String responseCode) throws CsvMalformedLineException {
        CsvAction.getInstance().SetCellValue(line, responseCodeColumn, responseCode);
    }

    public void RecordErrorCode(int line, String errorCode) throws CsvMalformedLineException {
        SetCellValue(line, errorCodeColumn, errorCode);
    }

    public void RecordErrorMsg(int line, String errorMsg) throws CsvMalformedLineException {
        SetCellValue(line, errorMsgColumn, errorMsg);
    }

    public void RecordData(int line, String data) throws CsvMalformedLineException {
        SetCellValue(line, dataColumn, data);
    }

    public void RecordConclusion(int line, boolean b) throws CsvMalformedLineException {
        if (b == true) {
            SetCellValue(line, conclusionColumn, "通过");
        } else {
            SetCellValue(line, conclusionColumn, "失败");
        }
    }

    public void RecordLogicConclusion(int line, boolean b) {
        try {
            if (b == true) {
                SetCellValue(line, logicConclusionColumn, "通过");
            } else {
                SetCellValue(line, logicConclusionColumn, "失败");
            }
        } catch (CsvMalformedLineException e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]) throws CsvMalformedLineException {
        CsvAction.getInstance().SetCellValue(1, 13, "测试");
    }
}
