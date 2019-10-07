package com.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;

import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfWriter;

public class JsonToPDF {

	public byte[] jsontopdf(String filename, String content) throws IOException {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();
			document.add(new Paragraph(content));
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
}