package org.example;


import java.nio.file.Paths;
import org.apache.poi.xwpf.usermodel.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class App {
    
// Lo que puedo hacer un constructor donde se descargue la plantilla y cada metodo hacer cosas particulares
//  por ejemplo escribir un parrafo en vez de poner todo lo de abajo. 
    public static void generarDocumentoPorCaso(byte[] plantillaBytes, String nombreCaso, String contenido) throws IOException {
        try (InputStream is = new ByteArrayInputStream(plantillaBytes);
            XWPFDocument document = new XWPFDocument(is)) {

            /*
            XWPFParagraph saltoPagina = document.createParagraph();
            XWPFRun runSalto = saltoPagina.createRun();
            runSalto.addBreak(BreakType.PAGE);
            */
            
            XWPFParagraph titulo = document.createParagraph();
            titulo.setStyle("CasoNombre"); 
            XWPFRun runTitulo = titulo.createRun();
            runTitulo.setText(nombreCaso);

            XWPFParagraph cuerpo = document.createParagraph();
            cuerpo.setStyle("EstiloParrafo"); 
            XWPFRun runCuerpo = cuerpo.createRun();
            runCuerpo.setText(contenido);

            XWPFParagraph saltoPagina = document.createParagraph();
            XWPFRun runSalto = saltoPagina.createRun();
            runSalto.addBreak(BreakType.PAGE);

            XWPFParagraph cuerpo2 = document.createParagraph();
            cuerpo2.setStyle("EstiloParrafo"); 
            XWPFRun runCuerpo2 = cuerpo2.createRun();
            runCuerpo2.setText(contenido);
            

            // 3. Guardar como archivo independiente para este caso
            try (OutputStream out = new FileOutputStream("caso_" + nombreCaso + ".docx")) {
                document.write(out);
            }
        }
    }
    public static void main(String[] args) {

        try{        
            byte[] plantillaBytes = Files.readAllBytes(Paths.get("C:\\Users\\miris\\OneDrive\\Documentos\\servi\\java\\automatizacion\\plantillasWord\\plantilla.docx"));
            generarDocumentoPorCaso(plantillaBytes, "casoDeUso1", "Soy la descripcion");
        }catch(Exception e){
            System.out.println(e);
        }
        

    }
}