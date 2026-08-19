package org.example;


import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.ooxml.POIXMLRelation;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;


public class App {

    public static void CrearDocumento(String hipervinculo) throws IOException{
        XWPFDocument document = new XWPFDocument();
        
        XWPFParagraph paragraph = document.createParagraph();

        XWPFHyperlinkRun hyperlinkRun = paragraph.createHyperlinkRun(hipervinculo);
        hyperlinkRun.setText("otro_documento.docx");

        // Crear un estilo de la plantilla y referenciarlo aqui
        hyperlinkRun.setColor("0000FF");
        hyperlinkRun.setUnderline(UnderlinePatterns.SINGLE);

        Path rutaDocumento = Paths.get(System.getProperty("user.home"), "Downloads", "prue", "documento_con_hipervinculo.docx");
        // Guardar
        try (FileOutputStream out = new FileOutputStream(rutaDocumento.toString())) {
            document.write(out);
        }catch(Exception e){
            System.out.println(e);
        }
        document.close();

        System.out.println("Documento generado con éxito.");
    }
    
    public static void main(String[] args) {

        String zipPath = "C:\\Users\\miris\\Downloads\\prue\\A01097BC47.zip";
        String destino;
        List<String> hipervinculos = new ArrayList<>();

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entrada;
            
            // Creamos la carpeta que tendra la descompresion
            Path rutaCarpeta = Paths.get(System.getProperty("user.home"), "Downloads", "prue", "descomprension");
            Files.createDirectories(rutaCarpeta);
            destino = rutaCarpeta.toString();

            // Guardamos cada uno de los archivos, revisando las etiquetas. 
            while ((entrada = zis.getNextEntry()) != null) {
                Path rutaSalida = Paths.get(destino, entrada.getName());
                hipervinculos.add(rutaSalida.toString());
                if (entrada.isDirectory()) {
                    Files.createDirectories(rutaSalida);
                } else {
                    
                    Files.createDirectories(rutaSalida.getParent());

                    try (OutputStream os = Files.newOutputStream(rutaSalida)) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            os.write(buffer, 0, len);
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }
                zis.closeEntry();
            }
            CrearDocumento(new File(hipervinculos.get(0)).toURI().toString());
        }catch(Exception e){
            System.out.println(e);
        }
        
        System.out.println("¡Descomprimido con éxito!");
        
    }
}