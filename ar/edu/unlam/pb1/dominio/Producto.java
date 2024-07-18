package ar.edu.unlam.pb1.dominio;

import ar.edu.unlam.pb1.dominio.enums.TipoDeProducto;

public class Producto {
	private int codigo;
	private String nombre;
	private TipoDeProducto tipoDeProducto;
	private double precio;
	private int stock;

	public Producto(int codigo, String nombre, TipoDeProducto tipoDeProducto, double precio, int stock) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.tipoDeProducto = tipoDeProducto;
		this.precio = precio;
		this.stock = stock;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public TipoDeProducto getTipoDeProducto() {
		return tipoDeProducto;
	}

	@Override
	public String toString() {
		return "codigo= " + codigo + ", nombre= " + nombre + ", tipoDeProducto= " + tipoDeProducto + ", precio= $"
				+ precio + ", stock= " + stock;
	}

	
	
}
