package com.wisdomregulation.pdflink;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

/**
 * Created by King6rf on 2017/4/3.
 */
public class SimpleTableCell {
    float width=-1;
    float height=-1;
    SimpleTableCell[][] child=null;
    SimpleTableCell[][] childvalue=null;
    int rowspan=-1;
    int colspan=-1;
    Element element;

    public int getRowspan() {
        return rowspan;
    }

    public SimpleTableCell setRowspan(int rowspan) {
        this.rowspan = rowspan;
        return this;
    }

    public int getColspan() {
        return colspan;
    }

    public SimpleTableCell setColspan(int colspan) {
        this.colspan = colspan;
        return this;
    }

    public Element getElement() {
        return element;
    }

    public SimpleTableCell setElement(Element element) {
        this.element = element;
        return this;
    }
    public SimpleTableCell(String title, Font font){
        this.element=new ParagraphBuilder(title,font).setAlignmentS(Element.ALIGN_CENTER);
    }
    public SimpleTableCell() {
        this.element= null;
    }
    public SimpleTableCell(Element element) {
        this.element= element;
    }
    public SimpleTableCell(float width, float height) {
        this.width = width;
        this.height = height;
    }
    public SimpleTableCell(float width) {
        this.width = width;
    }

    public SimpleTableCell(float width, float height, SimpleTableCell[][] child) {
        this.width = width;
        this.height = height;
        this.child = child;
    }

    public float getWidth() {
        return width;
    }

    public SimpleTableCell setWidth(float width) {
        this.width = width;
        return this;
    }

    public float getHeight() {
        return height;
    }

    public SimpleTableCell setHeight(float height) {
        this.height = height;
        return this;
    }

    public SimpleTableCell[][] getChild() {
        return child;
    }

    public SimpleTableCell setChild(SimpleTableCell[][] child) {
        this.child = child;
        return this;
    }
    public SimpleTableCell setChildValue(SimpleTableCell[][] childvalue) {
        this.childvalue = childvalue;
        return this;
    }
}
