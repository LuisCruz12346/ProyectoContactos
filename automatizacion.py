import os
import random
import traceback
from playwright.sync_api import sync_playwright
from seleniumbase import sb_cdp


URL_SOLICITUD = "https://www.banxico.org.mx/cep-scl/inicio.do"
URL_CONSULTA = "https://www.banxico.org.mx/cep-scl/inicio2.do"


CARPETA_CAPTURAS = "capturas"
os.makedirs(CARPETA_CAPTURAS, exist_ok=True)


def espera_humana(base_ms, variacion_ms):
    """Encapsula el patron de espera con jitter para no repetirlo en cada llamada."""
    return base_ms + random.randint(1, variacion_ms)


def ruta_captura(nombre):
    """Se añade la ruta donde se guardaran las capturas"""
    return os.path.join(CARPETA_CAPTURAS, nombre)


def llenar_archivo(page, selector, nombre_archivo_entrada, nombre_archivo_captura=None):
    """Se localiza el elemento y se añade el archivo """
    elemento_archivo = page.locator(selector)
    if not elemento_archivo.is_visible():
        raise Exception(f"El input de archivo '{selector}' no es visible")

    elemento_archivo.set_input_files(nombre_archivo_entrada)
    if nombre_archivo_captura:
        page.screenshot(path=ruta_captura(nombre_archivo_captura))


def llenar_texto(page, entrada_de_texto, nombre_campo, nombre_archivo_captura=None):
    """Se localiza el elemento de casilla de texto y se añade el string """
    if not entrada_de_texto:
        raise ValueError(f"El valor para el campo '{nombre_campo}' esta vacio")

    elemento_texto = page.get_by_role("textbox", name=nombre_campo)
    if not elemento_texto.is_visible():
        raise Exception(f"El campo de texto '{nombre_campo}' no es visible")

    elemento_texto.press_sequentially(entrada_de_texto, delay=80)
    if nombre_archivo_captura:
        page.screenshot(path=ruta_captura(nombre_archivo_captura))


def llenar_select(page, selector, seleccion, nombre_archivo_captura=None):
    """Se localiza el elemento de seleccion y se selecciona la casilla """
    elemento_select = page.locator(selector)
    if not elemento_select.is_visible():
        raise Exception(f"El campo select '{selector}' no es visible")

    elemento_select.select_option(seleccion)
    if nombre_archivo_captura:
        page.screenshot(path=ruta_captura(nombre_archivo_captura))


def realizar_solicitud(page, archivo_entrada, correo, seleccion):
    """Se llenan los rubros de una solicitud (archivo,correo y sel) y se envia la solicitud"""
    page.goto(URL_SOLICITUD)
    page.wait_for_timeout(espera_humana(3000, 500))

    llenar_archivo(page, "#input-file", archivo_entrada)
    page.wait_for_timeout(espera_humana(1000, 1000))

    llenar_texto(page, correo, "correo")
    page.wait_for_timeout(espera_humana(500, 1000))

    llenar_select(page, "#tuFormulario > div:nth-child(3) > select", seleccion)
    page.wait_for_timeout(espera_humana(1200, 500))

    page.screenshot(path=ruta_captura("solicitud_temp1.png"))
    page.wait_for_timeout(espera_humana(500, 500))

    page.locator("#btn_grupo-footer").click()
    page.wait_for_timeout(espera_humana(2000, 500))

    page.screenshot(path=ruta_captura("solicitud_temp2.png"))

    elemento_token = page.locator(".styled > strong:nth-child(6)")

    if elemento_token.is_visible():
        texto = elemento_token.text_content()
        return texto.strip() if texto else ""

    return ""


def realizar_consulta(page, token, correo):
    """Se llenan los rubros para la consulta(correo y token) y se envia la consulta """
    page.goto(URL_CONSULTA)
    page.wait_for_timeout(espera_humana(3000, 500))

    llenar_texto(page, correo, "correo")
    page.wait_for_timeout(espera_humana(1000, 500))

    llenar_texto(page, token, "token")
    page.wait_for_timeout(espera_humana(1200, 500))

    page.screenshot(path=ruta_captura("consulta_temp1.png"))

    page.locator("#tuFormulario > div.footer > input[type=button]").click()
    page.wait_for_timeout(espera_humana(2000, 500))

    page.screenshot(path=ruta_captura("consulta_temp2.png"))


def realizar_solicitud_consulta(page, archivo_entrada, correo_solicitud, seleccion, correo_consulta):
    """Se combinan los procesos de solicitud y consulta"""
    token = realizar_solicitud(
        page=page,
        archivo_entrada=archivo_entrada,
        correo=correo_solicitud,
        seleccion=seleccion,
    )

    if token:
        print(f"Token obtenido: {token}")
        realizar_consulta(page=page, token=token, correo=correo_consulta)

        btn_descarga_cep = page.locator("#tuFormulario > div.footer > input[type=button]")

        if btn_descarga_cep.is_visible():
            with page.expect_download() as download_info:
                btn_descarga_cep.click()
            download = download_info.value
            download.save_as(download.suggested_filename)
            print(f"Archivo descargado: {download.suggested_filename}")
        else:
            print("Hubo un error en la consulta: no se encontro el boton de descarga")
            page.screenshot(path=ruta_captura("error_consulta.png"))
    else:
        print("No se encontró el token en la solicitud")
        page.screenshot(path=ruta_captura("error_no_token.png"))


def main():
    sb = sb_cdp.Chrome(guest=True)
    endpoint_url = sb.get_endpoint_url()

    try:
        with sync_playwright() as p:
            browser = p.chromium.connect_over_cdp(endpoint_url)
            page = browser.contexts[0].pages[0]

            realizar_solicitud_consulta(
                page=page,
                archivo_entrada="ejemplo.txt",
                correo_solicitud="luis.c.hdz404@gmail.com",
                seleccion="3",
                correo_consulta="luis.c.hdz404@gmail.com",
            )

            browser.close()

    except Exception as e:
        print("Error al realizar el procedimiento")
        print(e)
        
        traceback.print_exc()

    finally:
        
        try:
            sb.driver.quit()
        except Exception:
            pass


if __name__ == "__main__":
    main()