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


