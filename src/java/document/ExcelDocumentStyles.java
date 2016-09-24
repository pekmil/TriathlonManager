/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author pekmil
 */
public final class ExcelDocumentStyles {
    
    private final XSSFWorkbook wb;
    
    private static final XSSFColor borderColor = new XSSFColor(new java.awt.Color(155, 194, 230));
    private static final XSSFColor headerLevel1FgColor = new XSSFColor(new java.awt.Color(47, 117, 181));
    private static final XSSFColor headerLevel1FtColor = new XSSFColor(new java.awt.Color(255, 255, 255));
    private static final XSSFColor headerLevel2FgColor = borderColor;
    private static final XSSFColor headerLevel2FtColor = new XSSFColor(new java.awt.Color(31, 78, 120));
    private static final XSSFColor headerLevel3FgColor = new XSSFColor(new java.awt.Color(214, 220, 228));
    private static final XSSFColor headerLevel3FtColor = new XSSFColor(new java.awt.Color(51, 63, 79));
    private static final XSSFColor headerLevel4FgColor = new XSSFColor(new java.awt.Color(220, 230, 241));
    
    private XSSFFont boldH1;
    private XSSFFont boldH2;
    private XSSFFont boldH3;
    private XSSFFont boldItalicFont;
    private XSSFFont italicFont;
    
    private XSSFCellStyle headerLevel1;
    private XSSFCellStyle headerLevel2;
    private XSSFCellStyle headerLevel3;
    private XSSFCellStyle headerLevel4;
    private XSSFCellStyle headerLevel4Medalist;
    private XSSFCellStyle boldItalic;
    private XSSFCellStyle bordered;
    private XSSFCellStyle italic;
    
    public ExcelDocumentStyles(XSSFWorkbook wb){
        this.wb = wb;
        initFonts();
    }
    
    private void withBorder(XSSFCellStyle style){
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(borderColor);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(borderColor);
    }
    
    private void initFonts(){
        boldH1 = initFont(true, false, headerLevel1FtColor);
        boldH2 = initFont(true, false, headerLevel2FtColor);
        boldH3 = initFont(true, false, headerLevel3FtColor);
        boldItalicFont = initFont(true, true, null);
        italicFont = initFont(false, true, null);
    }
    
    private XSSFFont initFont(boolean bold, boolean italic, XSSFColor color){
        XSSFFont font = wb.createFont();
        if(bold) fontBold(font);
        if(italic) fontItalic(font);
        if(color != null) fontColor(font, color);
        return font;
    }
    
    private void fontBold(XSSFFont font){
        font.setBold(true);
    }
    
    private void fontItalic(XSSFFont font){
        font.setItalic(true);
    }
    
    private void fontColor(XSSFFont font, XSSFColor color){
        font.setColor(color);
    }
    
    private void styleAligment(XSSFCellStyle style, short s){
        style.setAlignment(s);
    }
    
    private void styleForeground(XSSFCellStyle style, XSSFColor color){
        style.setFillForegroundColor(color);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
    }

    public XSSFCellStyle getHeaderLevel1() {
        if(headerLevel1 == null){
            headerLevel1 = wb.createCellStyle();
            styleAligment(headerLevel1, CellStyle.ALIGN_CENTER);
            styleForeground(headerLevel1, headerLevel1FgColor);
            headerLevel1.setFont(boldH1);
        }
        return headerLevel1;
    }
    
    public XSSFCellStyle getHeaderLevel2() {
        if(headerLevel2 == null){
            headerLevel2 = wb.createCellStyle();
            styleAligment(headerLevel2, CellStyle.ALIGN_CENTER);
            styleForeground(headerLevel2, headerLevel2FgColor);
            headerLevel2.setFont(boldH2);
        }
        return headerLevel2;
    }
    
    public XSSFCellStyle getHeaderLevel3() {
        if(headerLevel3 == null){
            headerLevel3 = wb.createCellStyle();
            styleAligment(headerLevel3, CellStyle.ALIGN_CENTER);
            styleForeground(headerLevel3, headerLevel3FgColor);
            headerLevel3.setFont(boldH3);
        }
        return headerLevel3;
    }
    
    public XSSFCellStyle getHeaderLevel4() {
        if(headerLevel4 == null){
            headerLevel4 = wb.createCellStyle();
            styleAligment(headerLevel4, CellStyle.ALIGN_CENTER);
            styleForeground(headerLevel4, headerLevel4FgColor);
        }
        return headerLevel4;
    }
    
    public XSSFCellStyle getHeaderLevel4Medalist() {
        if(headerLevel4Medalist == null){
            headerLevel4Medalist = wb.createCellStyle();
            styleAligment(headerLevel4Medalist, CellStyle.ALIGN_CENTER);
            styleForeground(headerLevel4Medalist, headerLevel4FgColor);
            headerLevel4Medalist.setFont(italicFont);
        }
        return headerLevel4Medalist;
    }
    
    public XSSFCellStyle getBoldItalic() {
        if(boldItalic == null){
            boldItalic = wb.createCellStyle();
            boldItalic.setFont(boldItalicFont);
            withBorder(boldItalic);
        }
        return boldItalic;
    }
    
    public XSSFCellStyle getBordered() {
        if(bordered == null){
            bordered = wb.createCellStyle();
            withBorder(bordered);
        }
        return bordered;
    }
    
    public XSSFCellStyle getItalic() {
        if(italic == null){
            italic = wb.createCellStyle();
            italic.setFont(italicFont);
        }
        return italic;
    }
    
}
