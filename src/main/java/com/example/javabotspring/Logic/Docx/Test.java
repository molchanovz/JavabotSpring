package com.example.javabotspring.Logic.Docx;


import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;

import java.io.File;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        // Create word package
        WordprocessingMLPackage wordPackage = WordprocessingMLPackage.load(new File("УЛ.docx"));
        MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
        List<Object> list = mainDocumentPart.getContent();


        // Create word package
        WordprocessingMLPackage wordPackage2 = WordprocessingMLPackage.createPackage();
// Create main document part
        MainDocumentPart mainDocumentPart2 = wordPackage2.getMainDocumentPart();
// Add Paragraph mainDocumentPart1.addObject(list.get(0));
        for (Object o : list) {
            mainDocumentPart2.addObject(o);
        }

// Save file
        wordPackage2.save(new File("FileFormat.docx"));
    }
}