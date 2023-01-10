
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TabAlignment;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReceiptGenerator {
    public static final String DEST = "./target/sandbox/objects/rent_receipt.pdf";

    private static String tenantName;
    private static String landlordName;
    private static String rentAmount;
    private static String address;
    private static Date fromDate;
    private static Date toDate;
    private static String panNumber;

    private static String receiptMonth;
    private static String dateOfRentPayment;
    private static List<String> months = new ArrayList<>();

    private static PdfFont font;
    private static PdfFont bold;

    private static final int HEADER_FONT_SIZE = 22;
    private static final int TEXT_FONT_SIZE = 14;
    private static final DateFormat monthInputParser = new SimpleDateFormat("MM-yyyy");
    private static final DateFormat monthOutputFormatter = new SimpleDateFormat("MMM-yyyy");
    private static final SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

        final Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Receipt Generator!!!!");
        System.out.println("Please select one of the below options");
        System.out.println("1. Pre-filled Rent Receipt Generator");
        System.out.println("2. Empty Rent Receipt Generator");
        String option = scanner.nextLine();

        switch (option) {
            case "1":
                System.out.println("Executing Case 1");
                System.out.println("Please enter the name of the tenant : ");
                tenantName = scanner.nextLine();

                System.out.println("Please enter the name of the owner : ");
                landlordName = scanner.nextLine();

                System.out.println("Please enter the rent amount : ");
                rentAmount = scanner.nextLine();

                System.out.println("Please enter the address of the property : ");
                address = scanner.nextLine();

                System.out.println("Please enter the entire rental period : ");
                try {
                    System.out.println("From Date (MM-YYYY) : ");
                    fromDate = monthInputParser.parse(scanner.nextLine());
                    System.out.println("To Date (MM-YYYY) : ");
                    toDate = monthInputParser.parse(scanner.nextLine());
                    monthsBetween(fromDate, toDate);
                } catch (ParseException pe) {
                    System.out.println(pe.getMessage());
                }

                System.out.println("Please enter landlord's PAN Number : ");
                panNumber = scanner.nextLine();
                new ReceiptGenerator().createRentReceipt(DEST);
                break;
            case "2":
                System.out.println("Executing Case 2");
                if (months.size() == 0) {
                    months.add(null);
                }
                new ReceiptGenerator().createRentReceipt(DEST);
                break;
            default:
                System.out.println("Executing the Default Case");
                System.out.println("Sorry we don't have that option. Please select from the ones provided.");
        }
    }

    public void createRentReceipt(String destination) throws IOException {
        System.out.println("Creating Rent Receipt");
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destination));
        Document document = new Document(pdfDoc);
        document.setFont(font);
        document.setFontSize(TEXT_FONT_SIZE);
        Rectangle pageSize = pdfDoc.getDefaultPageSize();
        float width = pageSize.getWidth() - document.getLeftMargin() - document.getRightMargin();
        Calendar dateOfRent;

        for (String month : months) {
            System.out.println("Executing for " + months.size() + " months");
            receiptMonth = month;

            // Calculate and populate receipt month and date of receipt basis the current month
            if (receiptMonth != null) {
                dateOfRent = Calendar.getInstance();
                dateOfRent.add(Calendar.MONTH, 1);
                dateOfRent.set(Calendar.DAY_OF_MONTH, 1);
                dateOfRentPayment = fullDateFormat.format(dateOfRent.getTime());
            }

            addTitle(document, width);
            addSubTitle(document, width);
            addBody(document, width);
            addSignature(document, width);
            addPan(document, width);
            drawBorder(document);
        }

        document.close();
    }

    private static void addTitle(Document document, float width) {
        List<TabStop> tabStops = new ArrayList<>();

        // Create a TabStop at the middle of the page
        tabStops.add(new TabStop(width / 2, TabAlignment.LEFT));

        // Create a TabStop at the end of the page
        tabStops.add(new TabStop(width, TabAlignment.RIGHT));

        Paragraph paragraph = new Paragraph().addTabStops(tabStops);
        paragraph.add("RENT RECEIPT").setFont(bold).setFontSize(HEADER_FONT_SIZE);
        paragraph.add(new Tab()).add(new Tab());

        Text receiptNumberText = new Text("Receipt Number : ____").setFont(font).setFontSize(TEXT_FONT_SIZE);
        paragraph.add(receiptNumberText).setHorizontalAlignment(HorizontalAlignment.LEFT);
        document.add(paragraph);
    }

    private static void addSubTitle(Document document, float width) {
        List<TabStop> tabStops = new ArrayList<>();

        // Create a TabStop at the middle of the page
        tabStops.add(new TabStop(width / 2, TabAlignment.LEFT));

        // Create a TabStop at the end of the page
        tabStops.add(new TabStop(width, TabAlignment.RIGHT));

        Paragraph paragraph = new Paragraph().addTabStops(tabStops);
        paragraph.add(receiptMonth == null ? "____/________" : receiptMonth);
        paragraph.add(new Tab()).add(new Tab());
        paragraph.add("Date: " + (dateOfRentPayment == null ? "____/_____/__________" : dateOfRentPayment));
        document.add(paragraph);
    }

    private static void addBody(Document document, float width) {
        List<TabStop> tabStops = new ArrayList<>();

        // Create a TabStop at the middle of the page
        tabStops.add(new TabStop(width / 2, TabAlignment.CENTER));

        // Create a TabStop at the end of the page
        tabStops.add(new TabStop(width, TabAlignment.LEFT));

        Paragraph paragraph = new Paragraph().addTabStops(tabStops);
        String body = "Received sum of Rs." + rentAmount
                + ", paid by Mr./Ms. " + (tenantName == null ? "____________________________ (Tenant Name)" : tenantName)
                + " towards the rent of property located at the address : " + (address == null ? "________________________________________________________ (Address of Property)" : address)
                + " for the period from " + (fromDate == null ? "____/_____/__________ " : fromDate)
                + " to " + (toDate == null ? " ____/_____/__________ " : toDate);

        paragraph.add(body);
        document.add(new Paragraph());
        document.add(new Paragraph());
        document.add(paragraph);
    }

    private static void addSignature(Document document, float width) {
        List<TabStop> tabStops = new ArrayList<>();

        // Create a TabStop at the middle of the page
        tabStops.add(new TabStop(width / 2, TabAlignment.LEFT));

        // Create a TabStop at the end of the page
        tabStops.add(new TabStop(width, TabAlignment.RIGHT));

        Paragraph paragraph = new Paragraph().addTabStops(tabStops);
        paragraph.add((landlordName == null ? "____________________________" : landlordName) + " (Landlord Name)");

        // Add Image
//        document.add(new Paragraph());
//        document.add(new Paragraph());
//        document.add(new Paragraph());
//        document.add(new Paragraph());
//        final Image image = addRevenueStamp();
//        paragraph.add(image);

        document.add(paragraph);
    }

    private static void addPan(Document document, float width) {
        List<TabStop> tabStops = new ArrayList<>();

        // Create a TabStop at the middle of the page
        tabStops.add(new TabStop(width / 2, TabAlignment.CENTER));

        // Create a TabStop at the end of the page
        tabStops.add(new TabStop(width, TabAlignment.RIGHT));

        Paragraph paragraph = new Paragraph().addTabStops(tabStops);
        paragraph.add("PAN No.: " + (panNumber == null ? "____________________________" : panNumber));
        document.add(paragraph);
    }

    private static void monthsBetween(Date fromDate, Date toDate) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(fromDate);

        Calendar finishCalendar = Calendar.getInstance();
        finishCalendar.setTime(toDate);

        while (beginCalendar.before(finishCalendar)) {
            // add one month to date per loop
            String date = monthOutputFormatter.format(beginCalendar.getTime()).toUpperCase();
            months.add(date);
            System.out.println(date);
            beginCalendar.add(Calendar.MONTH, 1);
        }
    }

    private static void drawBorder(Document document) {
        PdfCanvas canvas = new PdfCanvas(document.getPdfDocument().getFirstPage());
        Rectangle pageSize = document.getPdfDocument().getDefaultPageSize();
        float leftX = pageSize.getLeft() + document.getLeftMargin() - 15;
        float topY = pageSize.getTop() - document.getTopMargin() + 10;
        float rightX = pageSize.getRight() - document.getLeftMargin() + 15;
        float bottomY = topY - 260;

        // Create a 100% Magenta color
        Color magentaColor = new DeviceCmyk(0.f, 1.f, 0.f, 0.f);
        canvas.setStrokeColor(magentaColor)
                .moveTo(leftX, topY)
                .lineTo(rightX, topY)
                .closePathStroke();

        // Left edge
        canvas.setStrokeColor(magentaColor)
                .moveTo(leftX, topY)
                .lineTo(leftX, bottomY).closePathStroke();

        // Bottom edge
        canvas.setStrokeColor(magentaColor)
                .moveTo(leftX, bottomY)
                .lineTo(rightX, bottomY).closePathStroke();

        // Right edge
        canvas.setStrokeColor(magentaColor)
                .moveTo(rightX, topY)
                .lineTo(rightX, bottomY).closePathStroke();
    }
}
