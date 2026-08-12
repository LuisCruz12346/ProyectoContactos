// Esta es la clase principal y va a asignar el comportamiento de las herencia de la clases
//  el comportamiento que heredaran las clases de herencia es de que va a asignar un Browser unico para la clase
// Otra idea que tengo es dejar un static como constructor, y dentro del controlador llamar a la funcion para cerrar
//  el navegador antes de cerrar. 

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestFixtures {
  // Shared between all tests in the class.
  Playwright playwright;
  Browser browser;

  @BeforeAll
  void launchBrowser() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch();
  }

  @AfterAll
  void closeBrowser() {
    playwright.close();
  }

  // New instance for each test method.
  BrowserContext context;
  Page page;

  @BeforeEach
  void createContextAndPage() {
    context = browser.newContext();
    page = context.newPage();
  }

  @AfterEach
  void closeContext() {
    context.close();
  }
}

class Test1 extends CasosDeUso {
  @Test
  void casoDeUso1_6() {
    Solicitud paginaSolicitud = new Solicitud(page);
    paginaSolicitud.probarEnlaceUno();
    //paginaSolicitud.probarEnlaces([2,3]); // Un metodo donde se seleccione el metodo a realizar segun el numero un switch
  }

  @Test
  void casoDeUso7_8() {
    Solicitud paginaSolicitud = new Solicitud(page);
    paginaSolicitud.probarEnlaceSiete();
    paginaSolicitud.probarEnlaceOcho();
    //paginaSolicitud.probarEnlaces();
  }

  @Test
  void casoDeUso9_10() {
    Consulta paginaConsulta = new Consulta(page);
    paginaConsulta.probarEnlaceUno();
    paginaConsulta.probarEnlaceDos();
  }
  
  @Test
  void casoDeUso11() {
    Solicitud paginaSolicitud = new Solicitud(page);
    paginaSolicitud.realizarSolicitud();
  }
  
  @Test
  void casoDeUso12() {
    Solicitud paginaSolicitud = new Solicitud(page);
    String token = paginaSolicitud.realizarSolicitud();
    Consulta paginaConsulta = new Consulta(page);
  }
  
}

import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class CrearWord {
    public static void main(String[] args) throws IOException {
        XWPFDocument documento = new XWPFDocument();

        // Título
        XWPFParagraph titulo = documento.createParagraph();
        titulo.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun runTitulo = titulo.createRun();
        runTitulo.setText("Informe de Ejemplo");
        runTitulo.setBold(true);
        runTitulo.setFontSize(18);

        // Párrafo normal
        XWPFParagraph parrafo = documento.createParagraph();
        XWPFRun run = parrafo.createRun();
        run.setText("Este es un párrafo generado con Apache POI.");
        run.setFontFamily("Calibri");
        run.setFontSize(12);

        // Guardar archivo
        try (FileOutputStream out = new FileOutputStream("documento.docx")) {
            documento.write(out);
        }
        documento.close();
        System.out.println("Documento creado correctamente");
    }
}

import java.io.FileInputStream;

XWPFParagraph pImg = documento.createParagraph();
XWPFRun runImg = pImg.createRun();
try (FileInputStream img = new FileInputStream("logo.png")) {
    runImg.addPicture(img, XWPFDocument.PICTURE_TYPE_PNG, "logo.png", 
        Units.toEMU(150), Units.toEMU(150)); // ancho, alto en puntos
}
