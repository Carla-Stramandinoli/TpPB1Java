package ar.edu.unlam.pb1.dominio;

import java.util.Arrays;

public class Reserva {

	private String nombreReserva;
	private int cantidadDePersonas;

	public Reserva(String nombreReserva, int cantidadDePersonas) {
		this.nombreReserva = nombreReserva;
		this.cantidadDePersonas = cantidadDePersonas;
	}

	public int cantidadDeMesasQueOcupan(int cantidadDePersonas, Bar bar) {
		int intCantidadDeMesasQueOcupan = 0;
		while (cantidadDePersonas > 0) {
			intCantidadDeMesasQueOcupan++;
			cantidadDePersonas -= 2;
		}
		bar.setCantidadDeMesas(bar.getCantidadDeMesas() - intCantidadDeMesasQueOcupan);
		return intCantidadDeMesasQueOcupan;
	}

	@Override
	public String toString() {
		return "nombreReserva= " + nombreReserva + ", cantidadDePersonas= " + cantidadDePersonas;
	}

}
