package ar.edu.unlam.pb1.interfaz;

import java.util.Scanner;

import ar.edu.unlam.pb1.dominio.Bar;
import ar.edu.unlam.pb1.dominio.Reserva;
import ar.edu.unlam.pb1.dominio.Producto;
import ar.edu.unlam.pb1.dominio.enums.TipoDeProducto;
import ar.edu.unlam.pb1.interfaz.enums.MenuPrincipal;

public class GestionDelBar {
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {
		String nombreDelBar = ingresarString("Ingrese el nombre del bar: ");
		int cantidadDeMesas;
		do {
			cantidadDeMesas = ingresarNumeroEntero("Ingrese la cantidad de mesas disponibles: ");
			if (cantidadDeMesas <= 0) {
				mostrarPorPantalla("Ingrese al menos 1 mesa");
			}
		} while (cantidadDeMesas <= 0);

		Bar nuevoBar = new Bar(nombreDelBar, cantidadDeMesas);
		MenuPrincipal opcionMenuPrincipal = null;
		do {
			opcionMenuPrincipal = ingresarOpcionDelMenuPrincipal();

			switch (opcionMenuPrincipal) {
			case AGREGAR_PRODUCTO_A_LA_CARTA:
				agregarProductoALaCarta(nuevoBar);
				break;
			case MOSTRAR_CARTA_COMIDAS_ORDENADAS:
				mostrarCartaComidasOrdenadas(nuevoBar);
				break;
			case MOSTRAR_CARTA_BEBIDAS_ORDENADAS:
				mostrarCartaBebidasOrdenadas(nuevoBar);
				break;
			case MODIFICAR_PRECIO_PRODUCTO:
				modificarPrecioProducto(nuevoBar);
				break;
			case MODIFICAR_STOCK_PRODUCTO:
				modificarStockProducto(nuevoBar);
				break;
			case ELIMINAR_PRODUCTO_DE_LA_CARTA:
				eliminarProductoDeLaCarta(nuevoBar);
				break;
			case AGREGAR_RESERVA:
				agregarReserva(nuevoBar);
				break;
			case VENDER_PRODUCTOS:
				venderProductos(nuevoBar);
				break;
			case VER_DISPONIBILIDAD_DE_MESAS:
				mostrarMesasDisponibles(nuevoBar);
				break;
			case MOSTRAR_RESUMEN_RESERVAS_Y_SALIR:
				mostrarResumenSalir(nuevoBar);
				break;
			}

		} while (opcionMenuPrincipal != MenuPrincipal.MOSTRAR_RESUMEN_RESERVAS_Y_SALIR);
	}

	private static void mostrarMesasDisponibles(Bar bar) {
		mostrarPorPantalla("Mesas disponibles: " + bar.getCantidadDeMesas());
	}

	private static void agregarProductoALaCarta(Bar bar) {
		int codigo;
		do {
			codigo = ingresarNumeroEntero("Ingresar el codigo (numerico) del producto: ");
			if (bar.existe(codigo)) {
				mostrarPorPantalla("El codigo ingresado ya fue utilizado, intente con otro.");
			}
		} while (bar.existe(codigo));
		String nombre = ingresarString("Ingresar el nombre del producto: ");
		TipoDeProducto tipoDeProducto = ingresarTipoDeProducto();
		double precio = ingresarDouble("Ingrese el precio de venta: ");
		int stock;
		do {
			stock = ingresarNumeroEntero("Ingrese el stock actual del producto (debe ser mayor a cero): ");
		} while (stock <= 0);

		if (bar.agregarProductoNuevo(codigo, nombre, tipoDeProducto, precio, stock)) {
			mostrarPorPantalla("Agregado correctamente!");
		} else {
			mostrarPorPantalla("El producto no pudo ser agregado.");
		}
	}

	private static void mostrarCartaComidasOrdenadas(Bar bar) {
		mostrarPorPantalla("Carta comidas: ");
		mostrarArrayDeProductos(bar.obtenerProductosDeTipoComidaOrdenados(TipoDeProducto.COMIDA));
	}

	private static void mostrarCartaBebidasOrdenadas(Bar bar) {
		mostrarPorPantalla("Carta bebidas: ");
		mostrarArrayDeProductos(bar.obtenerProductosDeTipoComidaOrdenados(TipoDeProducto.BEBIDA));
	}

	private static void modificarPrecioProducto(Bar bar) {
		int codigo;
		do {
			codigo = ingresarNumeroEntero("Ingrese el codigo del producto que quiere modificar: ");
			if (!bar.existe(codigo)) {
				mostrarPorPantalla("El codigo ingresado no es valido.");
			}
		} while (!bar.existe(codigo));

		if (bar.obtenerProductoPorCodigo(codigo) != null) {
			double precio = ingresarDouble("Ingrese el precio nuevo: ");
			boolean modificado = bar.modificarPrecio(codigo, precio);
			if (modificado) {
				mostrarPorPantalla("El precio fue modificado!");
			} else {
				mostrarPorPantalla("El precio no pudo ser modificado.");
			}
		}

	}

	private static void modificarStockProducto(Bar bar) {
		int codigo;
		do {
			codigo = ingresarNumeroEntero("Ingrese el codigo del producto que quiere modificar: ");
			if (!bar.existe(codigo)) {
				mostrarPorPantalla("El codigo ingresado no es valido.");
			}
		} while (!bar.existe(codigo));

		if (bar.obtenerProductoPorCodigo(codigo) != null) {
			int stock = ingresarNumeroEntero("Ingrese el stock nuevo: ");
			boolean modificado = bar.modificarStock(codigo, stock);
			if (modificado) {
				mostrarPorPantalla("El stock fue modificado!");
			} else {
				mostrarPorPantalla("El stock no pudo ser modificado.");
			}
		}
	}

	private static void eliminarProductoDeLaCarta(Bar bar) {
		int codigo = ingresarNumeroEntero("Ingrese el codigo del producto que desea eliminar: ");
		confirmar(bar, codigo);
	}

	private static boolean confirmar(Bar bar, int codigo) {
		String confirmar = "n";
		boolean eliminado = false;
		mostrarPorPantalla("Seguro que desea eliminar el elemento?");
		confirmar = ingresarString("Ingrese 's' para confirmar o 'n' para cancelar.").toLowerCase();
		if (bar.existe(codigo) && confirmar.equals("s")) {
			bar.eliminarElemento(codigo);
			mostrarPorPantalla("El elemento se elimino.");
			eliminado = true;
		} else {
			mostrarPorPantalla("Operacion cancelada!.");
		}
		return eliminado;

	}

	private static void agregarReserva(Bar bar) {
		String nombreReserva = ingresarString("Ingrese el nombre del cliente para la reserva: ");
		int cantidadPax = ingresarNumeroEntero("Ingrese la cantidad de personas que asistiran: ");
		Reserva reserva = bar.agregarReserva(nombreReserva, cantidadPax);
		if (reserva != null && reserva.cantidadDeMesasQueOcupan(cantidadPax, bar) <= bar.getCantidadDeMesas()) {
			mostrarPorPantalla("La reserva se realizo con exito!");
		} else {
			mostrarPorPantalla("La reserva no pudo realizarse, consultar disponibilidad de mesas!");
		}
	}

	private static void venderProductos(Bar bar) {
		int codigo;
		do {
			mostrarCartaComidasOrdenadas(bar);
			mostrarCartaBebidasOrdenadas(bar);
			codigo = ingresarNumeroEntero("\nIngrese el codigo del producto que desea vender: ");
			if (!bar.existe(codigo)) {
				mostrarPorPantalla("\nEl codigo ingresado no es valido.");
			}
		} while (!bar.existe(codigo));
		int cantidadDeProducto = ingresarNumeroEntero("Ingrese la cantidad de ese producto que va a vender: ");
		if (bar.hayStock(codigo, cantidadDeProducto)) {
			bar.venderProducto(codigo, cantidadDeProducto);
			mostrarPorPantalla("Producto vendido exitosamente");
		} else if (bar.hayStock(codigo, cantidadDeProducto) == false) {
			mostrarPorPantalla("El stock no es suficiente");
		}
	}

	private static void mostrarResumenSalir(Bar bar) {
		mostrarPorPantalla("Reservas: ");
		if (bar.obtenerReservas() == null) {
			mostrarPorPantalla("No hay reservas.");
		} else {
			mostrarArrayDeReservas(bar.obtenerReservas());
		}
		mostrarPorPantalla("Saldo acumulado: $" + bar.getSaldo() + "\nCantidad de productos vendidos: "
				+ bar.getCantidadProductosVendidos());
	}

	private static TipoDeProducto ingresarTipoDeProducto() {
		int opcion = 0;
		do {
			mostrarMenuTipoProducto();
			opcion = ingresarNumeroEntero("\nSeleccione el tipo de producto: ");
		} while (opcion < 1 || opcion > TipoDeProducto.values().length);

		TipoDeProducto menuTipoProducto = TipoDeProducto.values()[opcion - 1];
		return menuTipoProducto;
	}

	private static void mostrarMenuTipoProducto() {
		for (int i = 0; i < TipoDeProducto.values().length; i++) {
			mostrarPorPantalla((i + 1) + ". " + TipoDeProducto.values()[i]);
		}
	}

	private static MenuPrincipal ingresarOpcionDelMenuPrincipal() {
		int opcion = 0;
		do {
			mostrarMenuPrincipal();
			opcion = ingresarNumeroEntero("\nIngrese una opcion del menu: ");
		} while (opcion < 1 || opcion > MenuPrincipal.values().length);

		MenuPrincipal menuPrincipal = MenuPrincipal.values()[opcion - 1];
		return menuPrincipal;
	}

	private static void mostrarMenuPrincipal() {
		for (int i = 0; i < MenuPrincipal.values().length; i++) {
			mostrarPorPantalla((i + 1) + ". " + MenuPrincipal.values()[i]);
		}
	}

	private static void mostrarArrayDeProductos(Producto[] producto) {
		for (int i = 0; i < producto.length; i++) {
			if (producto[i] != null) {
				mostrarPorPantalla(producto[i].toString());
			}
		}
	}

	private static void mostrarArrayDeReservas(Reserva[] reservas) {
		for (int i = 0; i < reservas.length; i++) {
			if (reservas[i] != null) {
				mostrarPorPantalla(reservas[i].toString());
			}
		}
	}

	private static int ingresarNumeroEntero(String mensaje) {
		mostrarPorPantalla(mensaje);
		return teclado.nextInt();
	}

	private static String ingresarString(String mensaje) {
		mostrarPorPantalla(mensaje);
		return teclado.next();
	}

	private static double ingresarDouble(String mensaje) {
		mostrarPorPantalla(mensaje);
		return teclado.nextDouble();
	}

	private static void mostrarPorPantalla(String mensaje) {
		System.out.println(mensaje);
	}
}
