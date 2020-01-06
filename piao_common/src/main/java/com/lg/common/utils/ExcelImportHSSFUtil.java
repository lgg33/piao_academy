package com.lg.common.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author L
 * @version 1.0
 * @ClassName: u
 * @date: 2020/1/6 15:09
 * @since JDK 1.8
 */
public class ExcelImportHSSFUtil {

    private HSSFFormulaEvaluator formulaEvaluator;
    private HSSFSheet sheet;
    private String pattern;// 日期格式

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public HSSFFormulaEvaluator getFormulaEvaluator() {
        return formulaEvaluator;
    }

    public void setFormulaEvaluator(HSSFFormulaEvaluator formulaEvaluator) {
        this.formulaEvaluator = formulaEvaluator;
    }

    public HSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(HSSFSheet sheet) {
        this.sheet = sheet;
    }

    public ExcelImportHSSFUtil() {
        super();
    }

    public ExcelImportHSSFUtil(InputStream is) throws IOException {
        this(is, 0, true);
    }

    public ExcelImportHSSFUtil(InputStream is, int seetIndex) throws IOException {
        this(is, seetIndex, true);
    }

    public ExcelImportHSSFUtil(InputStream is, int seetIndex, boolean evaluateFormular) throws IOException {
        super();
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        this.sheet = workbook.getSheetAt(seetIndex);
        if (evaluateFormular) {
            this.formulaEvaluator = new HSSFFormulaEvaluator(workbook);
        }
    }

    public String getCellValue(Cell cell, int cellType) throws Exception {

        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC://0

                if (HSSFDateUtil.isCellDateFormatted(cell)) {//日期
                    Date date = cell.getDateCellValue();
                    if (pattern != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        return sdf.format(date);
                    } else {
                        return date.toString();
                    }
                } else {
                    // 不是日期格式，则防止当数字过长时以科学计数法显示
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    return cell.toString();
                }

            case Cell.CELL_TYPE_STRING://1
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_FORMULA://2

                if (this.formulaEvaluator == null) {//得到公式
                    return cell.getCellFormula();
                } else {//计算公式
                    CellValue evaluate = this.formulaEvaluator.evaluate(cell);
                    cellType = evaluate.getCellType();
                    return String.valueOf(this.getCellValue(cell, cellType));
                }
            case Cell.CELL_TYPE_BLANK://3
                //注意空和没有值不一样，从来没有录入过内容的单元格不属于任何数据类型，不会走这个case
                return "";
            case Cell.CELL_TYPE_BOOLEAN://4
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_ERROR:
                throw new Exception("数据类型错误");
            default:
                throw new Exception("未知数据类型:" + cellType);
        }
    }
}