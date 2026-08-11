package org.example;

import com.microsoft.playwright.*;
import java.nio.file.Paths;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
import java.util.Random;
import com.microsoft.playwright.options.BoundingBox;

public class App {

	public static void movimientoAleatorio(Page page, double  x , double  y){  
		Random random = new Random();
		int x_elemento = (int)x ;
		int y_elemento = (int)y ;   
		int x1 = 0, y1 = 0;        
			while (x1 < x_elemento || y1 < y_elemento) { 
			    if (x1 < x_elemento) {
				int step = random.nextInt(10) + 1;         
				x1 = Math.min(x1 + step, x_elemento);                
			    }
			    if (y1 < y_elemento) {
				int step = random.nextInt(10) + 1;
				y1 = Math.min(y1 + step, y_elemento);
			    }

			    page.mouse().move(x1, y1, new Mouse.MoveOptions().setSteps(2));
			    page.waitForTimeout(20 + random.nextInt(30)); 
			}
			
	}
	
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
        	
		Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
		/*
		BrowserContext s = browser.newContext();
		Page page = s.newPage();
		page.navigate("https://www.banxico.org.mx/cep-scl/inicio.do");
		System.out.println(page.title());
		page.locator("#btn_Responsabilidades").click();
		
		Locator mensaje = page.locator(".msg-banxico");
		
		
		for(int i = 0; i < 5; i++)
			mensaje.click();
		
		mensaje.screenshot(
		    new Locator.ScreenshotOptions()
			.setPath(Paths.get("example.png"))
		);
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example2.png")));
		
		Locator enlace = page.locator(".contenido > div:nth-child(1) > div:nth-child(2) > p:nth-child(2) > a:nth-child(1)");
		if (enlace.isVisible()){
			enlace.click();
			page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example3.png")));
		}
		s.close();
		
		try(BrowserContext s2 = browser.newContext( new Browser.NewContextOptions()
        			.setAcceptDownloads(true))){
			Page p2 = s2.newPage();
			p2.navigate("https://www.banxico.org.mx/cep-scl/inicio.do");
			Locator l2 = p2.locator(".disclaimer > p:nth-child(8) > a:nth-child(1)");
			if (l2.textContent().equals("https://transparencia.banxico.org.mx/documentos/%7BCC1169A2-D511-47D6-FA05-FA56CF0156A4%7D.pdf"))
				System.out.println("Si igual");
			if (l2.isVisible()){
				System.out.println("Entre");	
				Download download = p2.waitForDownload(() -> {
    					l2.click();
				});
				
				download.saveAs(Paths.get("aviso.pdf"));
			}
		}
		
		try(BrowserContext s2 = browser.newContext()){
			Page p3 = s2.newPage();
			p3.navigate("https://www.banxico.org.mx/cep-scl/inicio.do");
			p3.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("correo")
				).fill("holaaaa");
			p3.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example5.png")));	
			
		}
		
		try(BrowserContext s2 = browser.newContext()){
			Page p4 = s2.newPage();
			p4.navigate("https://www.banxico.org.mx/cep-scl/inicio.do");
			p4.getByRole(AriaRole.COMBOBOX).nth(0).selectOption("3");
			p4.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example6.png")));	
			
		}
		*/
		
		
		try(BrowserContext s2 = browser.newContext()){
			Random random = new Random();
			Page p5 = s2.newPage();
			p5.navigate("https://www.banxico.org.mx/cep-scl/inicio.do");
			p5.waitForTimeout(3000 + random.nextInt(1000));
			p5.locator("#input-file")
    				.setInputFiles(Paths.get("ejemplo.txt"));
    			p5.waitForTimeout(1000 + random.nextInt(500));
    			//p5.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example7.png")));
    			p5.locator("div.entrada:nth-child(2) > input:nth-child(2)")
    				.pressSequentially("luis.hdz404@gmail.com", 
				        new Locator.PressSequentiallyOptions().setDelay(100));
    			p5.waitForTimeout(1100 + random.nextInt(500));
    			p5.getByRole(AriaRole.COMBOBOX).nth(0).selectOption("2");
    			
    			p5.waitForTimeout(1200 + random.nextInt(500));
    			p5.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example15.png")));
    			
    			/*
    			System.out.println("Valor se: "+ p5.locator("div.entrada:nth-child(3) > select:nth-child(2)").inputValue());
    			
    			System.out.println("Valor co: " + p5.locator("div.entrada:nth-child(2) > input:nth-child(2)").inputValue());
    			System.out.println("Archivo: " + p5.locator("#boton_file").inputValue());
    			
    			System.out.println("Cookies " + s2.cookies());
    			p5.onRequest(request -> {
			    if ("POST".equals(request.method())) {
				System.out.println(request.url());
			    }
			});
    			*/
    			
    			Locator cargarArchivo = p5.locator("#btn_grupo-footer"); 
    			BoundingBox box = cargarArchivo.boundingBox();
    			movimientoAleatorio(p5,box.x,box.y);
    			
    			Response response = p5.waitForResponse(
			    r -> r.request().method().equals("POST")
				 && r.url().contains("/upload"),
			    () -> {
				cargarArchivo.click();
			    }
			);
			
			p5.waitForTimeout(1500 + random.nextInt(500));
			p5.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example16.png")));
			
			/*
			System.out.println("Response");
			System.out.println(response.headers().get("content-type"));
			System.out.println(response.status());
			System.out.println(response.url());
			
			
			System.out.println("Request");
			Request request = response.request();
			
			System.out.println(request.method());
			System.out.println(request.url());
			System.out.println(request.headers());
			System.out.println(request.postData());
			*/
			
			/*
			Locator tokenLocalizacion = p5.locator(".styled > strong:nth-child(6)");
			tokenLocalizacion.click();
			String token = "";
			
			token = tokenLocalizacion.textContent();
			
			System.out.println("Soy el token: " + token);
			
			p5.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example9.png")));
			
			p5.navigate("https://www.banxico.org.mx/cep-scl/inicio2.do");
			
			p5.locator("div.entrada:nth-child(1) > input:nth-child(2)").fill("luis.c.hdz404@gmail.com");
			p5.locator("div.entrada:nth-child(2) > input:nth-child(2)").fill(token);
			
			p5.locator(".footer > input:nth-child(1)").click();
			
			
			
			p5.waitForLoadState(LoadState.LOAD);
			p5.locator(".footer").click();
			
			
			// Revisar las cabeceras de la peticion o cerrar y volver hacerlo con otro contexto
			p5.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example10.png")));
			*/
			
		}
		
		
		/*
		try(BrowserContext s2 = browser.newContext()){
			Random random = new Random();
			
			Page p5 = s2.newPage();
			p5.navigate("https://www.banxico.org.mx/cep-scl/inicio.do");
			p5.waitForTimeout(3000 + random.nextInt(1000));
			p5.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example11.png")));
			
			Locator boton = p5.locator(".principal > p:nth-child(4) > a:nth-child(1)");
			BoundingBox box = boton.boundingBox();
			
			movimientoAleatorio(p5, box.x , box.y);
			
			p5.waitForTimeout(3000 + random.nextInt(1000));
			p5.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example12.png")));
		}
		*/
		
	    System.out.println("Llegue");
        }catch (Exception e){
        	System.out.println(e);
        	
        	// Regresa el "", ya que me dio la pagina de error. 
        }
        
    }
}
