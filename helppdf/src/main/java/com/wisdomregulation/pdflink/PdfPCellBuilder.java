package com.wisdomregulation.pdflink;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

/**
 * Created by King6rf on 2017/6/15.
 */
public class PdfPCellBuilder extends PdfPCell{

    public PdfPCellBuilder() {
        super();
    }
    public PdfPCellBuilder(Phrase phrase) {
        super(phrase);
    }
    public PdfPCellBuilder setHorizontalAlignmentS(int horizontalAlignment) {
        this.setHorizontalAlignment(horizontalAlignment);
        return this;

    }
    public PdfPCellBuilder setVerticalAlignmentS(int verticalAlignment) {
        this.setVerticalAlignment(verticalAlignment);
        return this;
    }
}
