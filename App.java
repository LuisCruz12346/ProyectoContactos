package org.example;

import com.microsoft.playwright.*;
import java.nio.file.Paths;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;


public class App {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
		Browser browser = playwright.chromium().launch();
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
		
	    System.out.println("Llegue");
        }catch (Exception e){
        	System.out.println(e);
        }
        
    }
}
