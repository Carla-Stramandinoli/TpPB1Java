package ar.edu.unlam.pb1.dominio;

import java.util.Arrays;

import ar.edu.unlam.pb1.dominio.enums.TipoDeProducto;

public class Bar {
	private String nombre;
	private int cantidadDeMesas;
	private Producto[] producto;
	private Reserva[] reservas;
	private double saldo;
	private int cantidadProductosVendidos;

	public Bar(String nombre, int cantidadDeMesas) {
		this.nombre = nombre;
		this.cantidadDeMesas = cantidadDeMesas;
		this.producto = new Producto[100];
		this.reservas = new Reserva[cantidadDeMesas];
	}

	public Producto obtenerProductoPorCodigo(int codigo) {
		int posicion = 0;
		while (posicion < this.producto.length) {
			if (this.producto[posicion] != null && existe(codigo)) {
				return this.producto[posicion];
			}
		}
		return null;
	}

	public boolean existe(int codigo) {
		for (int i = 0; i < this.producto.length; i++) {
			if (this.producto[i] != null && this.producto[i].getCodigo() == codigo) {
				return true;
			}
		}
		return false;
	}

	public boolean hayStock(int codigo, int cantidadDeProducto) {
		Producto producto = obtenerProductoPorCodigo(codigo);
			if (producto != null && producto.getStock() >= cantidadDeProducto) {
				return true;
		}
		return false;
	}

	public boolean agregarProductoNuevo(int codigo, String nombre, TipoDeProducto tipoDeProducto, double precio,
			int stock) {
		boolean agregado = false;
		int posicion = 0;
		while (posicion < this.producto.length && !agregado) {
			if (this.producto[posicion] == null) {
				this.producto[posicion] = new Producto(codigo, nombre, tipoDeProducto, precio, stock);
				agregado = true;
			}
			posicion++;
		}
		return agregado;
	}

	public Producto[] obtenerProductosDeTipoComidaOrdenados(TipoDeProducto tipoDeProducto) {
		Producto[] comidasOrdenadas = new Producto[this.producto.length];
		for (int a = 0; a < comidasOrdenadas.length; a++) {
			if (this.producto[a] != null && this.producto[a].getTipoDeProducto().equals(tipoDeProducto)) {
				comidasOrdenadas[a] = this.producto[a];
			}
		}
		for (int i = 0; i < comidasOrdenadas.length - 1; i++) {
			for (int j = 0; j < comidasOrdenadas.length - 1 - i; j++) {
				if (comidasOrdenadas[j] != null && comidasOrdenadas[j + 1] != null) {
					if (comidasOrdenadas[j].getCodigo() > comidasOrdenadas[j + 1].getCodigo()) {
						Producto auxiliar = comidasOrdenadas[j];
						comidasOrdenadas[j] = comidasOrdenadas[j + 1];
						comidasOrdenadas[j + 1] = auxiliar;
					}
				}
			}
		}
		return comidasOrdenadas;
	}

	public boolean modificarPrecio(int codigo, double precio) {
		for (int i = 0; i < this.producto.length; i++) {
			if (this.producto[i] != null && existe(codigo)) {
				this.producto[i].setPrecio(precio);
				return true;
			}
		}
		return false;
	}

	public boolean modificarStock(int codigo, int stock) {
		for (int i = 0; i < this.producto.length; i++) {
			if (this.producto[i] != null && existe(codigo)) {
				this.producto[i].setStock(stock);
				return true;
			}
		}
		return false;
	}

	public void eliminarElemento(int codigo) {
		for (int i = 0; i < this.producto.length; i++) {
			if (this.producto[i] != null && obtenerProductoPorCodigo(codigo) != null) {
				this.producto[i] = null;
				return;
			}
		}
	}

	public Reserva agregarReserva(String nombreReserva, int cantidadDePersonas) {
		boolean agregado = false;
		int posicion = 0;
		while (posicion < this.reservas.length && !agregado) {
			if (this.reservas[posicion] == null) {
				Reserva pedido = new Reserva(nombreReserva, cantidadDePersonas);
				this.reservas[posicion] = pedido;
				return pedido;
			}
			posicion++;
		}
		return null;
	}

	public Reserva[] obtenerReservas() {
		Reserva[] todasReservas = new Reserva[this.reservas.length];
		int a = 0;
		for (int i = 0; i < this.reservas.length; i++) {
			if (this.reservas[i] != null) {
				todasReservas[a] = this.reservas[i];
				a++;
				return todasReservas;
			}
		}
		return null;
	}
	
	public void venderProducto(int codigo, int cantidadDeProducto) {
		double precioTotal = 0;
		for(int i = 0; i < this.producto.length; i++) {
			if(existe(codigo) && hayStock(codigo, cantidadDeProducto)) {
				int stockActual = this.producto[i].getStock();
				int stockDespVendido = stockActual - cantidadDeProducto;
				this.producto[i].setStock(stockDespVendido);
				this.cantidadProductosVendidos += cantidadDeProducto;
				precioTotal = cantidadDeProducto * this.producto[i].getPrecio();
				this.saldo += precioTotal;
				return;
			}
		}
	}
	
	public int getCantidadProductosVendidos() {
		return cantidadProductosVendidos;
	}

	public double getSaldo() {
		return saldo;
	}

	public int getCantidadDeMesas() {
		return cantidadDeMesas;
	}

	public void setCantidadDeMesas(int cantidadDeMesas) {
		this.cantidadDeMesas = cantidadDeMesas;
	}

	@Override
	public String toString() {
		return Arrays.toString(producto);
	}
}
