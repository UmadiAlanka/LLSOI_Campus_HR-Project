package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Salary;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    // Define Theme Colors
    private static final Color THEME_RED = new Color(114, 14, 14); // #720e0e
    private static final Color LIGHT_GRAY = new Color(245, 245, 245);

    public byte[] generatePaySlip(Salary salary) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);

        document.open();

        // --- 1. HEADER SECTION ---
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, THEME_RED);
        Paragraph title = new Paragraph("LLSOI CAMPUS", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.GRAY);
        Paragraph subTitle = new Paragraph("Monthly Salary Pay Slip", subTitleFont);
        subTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subTitle);

        document.add(new Paragraph(" ")); // Spacer
        document.add(new Paragraph(" ")); // Spacer

        // --- 2. EMPLOYEE INFO TABLE (No Borders) ---
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);

        infoTable.addCell(getDetailsCell("Employee Name: " + salary.getEmployee().getName()));
        infoTable.addCell(getDetailsCell("Employee ID: " + salary.getEmployee().getEmployeeId()));
        infoTable.addCell(getDetailsCell("Month/Year: " + salary.getMonth() + "/" + salary.getYear()));
        infoTable.addCell(getDetailsCell("Status: " + salary.getStatus()));

        document.add(infoTable);
        document.add(new Paragraph(" "));

        // --- 3. SALARY BREAKDOWN TABLE (Main Table) ---
        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100);
        mainTable.setSpacingBefore(10f);

        // Table Header
        mainTable.addCell(getHeaderCell("Description"));
        mainTable.addCell(getHeaderCell("Amount (LKR)"));

        // Basic Salary Row
        mainTable.addCell(getNormalCell("Basic Salary"));
        mainTable.addCell(getNormalCell("Rs. " + String.format("%,.2f", salary.getBasicSalary())));

        // Allowances (Placeholder for professional look)
        mainTable.addCell(getNormalCell("Allowances / Bonuses"));
        mainTable.addCell(getNormalCell("Rs. 0.00"));

        // Deductions (Placeholder)
        mainTable.addCell(getNormalCell("Deductions (EPF/ETF)"));
        mainTable.addCell(getNormalCell("Rs. 0.00"));

        // Net Salary Row (Highlighted)
        PdfPCell totalLabelCell = new PdfPCell(new Phrase("NET SALARY (PAYABLE)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        totalLabelCell.setBackgroundColor(LIGHT_GRAY);
        totalLabelCell.setPadding(10);
        mainTable.addCell(totalLabelCell);

        PdfPCell totalValCell = new PdfPCell(new Phrase("Rs. " + String.format("%,.2f", salary.getNetSalary()), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, THEME_RED)));
        totalValCell.setBackgroundColor(LIGHT_GRAY);
        totalValCell.setPadding(10);
        totalValCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        mainTable.addCell(totalValCell);

        document.add(mainTable);

        // --- 4. FOOTER SECTION ---
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10, Color.GRAY);
        Paragraph footer = new Paragraph("Thank You!!!", footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);

        String generatedAt = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Paragraph datePara = new Paragraph("Generated on: " + generatedAt, footerFont);
        datePara.setAlignment(Element.ALIGN_CENTER);
        document.add(datePara);

        document.close();
        return out.toByteArray();
    }

    // --- HELPER METHODS FOR BEAUTIFUL CELLS ---

    private PdfPCell getHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE)));
        cell.setBackgroundColor(THEME_RED);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(8);
        return cell;
    }

    private PdfPCell getNormalCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 11)));
        cell.setPadding(8);
        if (text.contains("Rs.")) {
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        }
        return cell;
    }

    private PdfPCell getDetailsCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 11)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(5);
        return cell;
    }
}