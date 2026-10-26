// Va a ser un documento externo con una variable en memoria de token
import { getToken, setToken, clearToken } from "./tokenService";

const BASE_URL = "/api";
let refreshPromise = null;

// La funcion para hacer el refresh 
async function refreshToken() {
  const res = await fetch(`${BASE_URL}/auth/refreshToken`, {
    method: "POST",
    credentials: "include",
  });
  if (!res.ok) throw new Error("Refresh fallido");
  const data = await res.json();
  setToken(data.accessToken);
  return data.accessToken;
}

// cada peticion tendra una funcion general
async function request(path, { body, headers, ...rest } = {}) {
  const token = getToken();
  return fetch(`${BASE_URL}${path}`, {
    credentials: "include",
    ...rest,
    headers: {
      "Content-Type": "application/json",
      ...(token && { Authorization: `Bearer ${token}` }),
      ...headers,
    },
    body: body ? JSON.stringify(body) : undefined,
  });
}

// Esta funcion es la que llamaran las demas funciones con el path correspondiente y su funciones
export async function api(path, options = {}) {
  let res = await request(path, options);
	// Esta condicion esta en proceso, casi no se ejecutara, con el if
  if (res.status === 401 && path !== "/auth/refresh") {
    try {
      // Si varias peticiones fallan a la vez, comparten un solo refresh
      refreshPromise ??= refreshToken().finally(() => (refreshPromise = null));
      await refreshPromise;
      res = await request(path, options); // reintento con el token nuevo
    } catch {
      clearToken();
      throw new Error("Sesión expirada");
    }
  }
  // Regresar el estado de la peticion, voy a regresar null si falla y asi manejar el resultado dentro de la peticion
  //  con res || []  o res || {} y asi 
  if (!res.ok) {
    const error = new Error(`HTTP ${res.status}`);
    error.status = res.status;
    error.data = await res.json().catch(() => null);
    throw error;
  }

  return res.status === 204 ? null : res.json();
}
