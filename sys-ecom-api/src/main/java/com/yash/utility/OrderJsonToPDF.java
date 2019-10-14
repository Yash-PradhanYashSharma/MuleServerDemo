package com.yash.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class OrderJsonToPDF {

	public byte[] jsontopdf(String filename, String content) throws IOException {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try

		{
			JSONObject jsonObject = new JSONObject(content);

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10f);
			table.setSpacingAfter(10f);
			float[] columnWidths = { 4f, 4f, 4f };
			table.setWidths(columnWidths);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell("ProductName");
			table.addCell("Quantity");
			table.addCell("Price");

			table.setHeaderRows(1);
			PdfPCell[] cells = table.getRow(0).getCells();
			for (int j = 0; j < cells.length; j++) {
				cells[j].setBackgroundColor(BaseColor.GRAY);
			}
			JSONArray arr = jsonObject.getJSONArray("products");
			System.out.print(arr.length());
			for (int i = 0; i < arr.length(); i++) {
				JSONObject json = arr.getJSONObject(i);
				Iterator<String> keys = json.keys();

				while (keys.hasNext()) {
					String key = keys.next();
					System.out.print(key.contains("productName"));
					if (key.contains("productName")) {
						System.out.print(key);
						table.addCell((String) json.get("productName"));
					}
					if (key.contains("quantity")) {
						table.addCell(String.valueOf(json.get("quantity")));
					}
					if (key.contains("price")) {
						table.addCell(String.valueOf(json.get("price")));
					}
				}

			}

			PdfWriter.getInstance(document, out);

			Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.DARK_GRAY);
			Paragraph p = new Paragraph("Thank you for your order! ", f);

			p.setAlignment(Paragraph.ALIGN_LEFT);

			document.open();
			document.add(p);
			document.add(new Paragraph("\n\n\n"));

			document.add(new Paragraph("Dear " + ((String) jsonObject.get("userId"))
					+ ",\nThank you for your order from Yash Website Store.Once your package ships we will send you tracking number.\n\nIf you have any queries about your order, you can email us at yashtech@gmail.com."));

			document.add(new Paragraph("\n\n"));

			Paragraph p1 = new Paragraph("Your Order #" + String.valueOf(jsonObject.get("orderId")), f);
			document.add(p1);

			document.add(table);
			document.add(new Paragraph("\n"));
			document.add(new Paragraph(
					"Thanks for your purchase! \nFor any futher questions do not hesitate to contact us!"));

			document.close();

		} catch (DocumentException e)

		{

			e.printStackTrace();

		}
		System.out.print("DONEEEE");
		return out.toByteArray();

	}

}