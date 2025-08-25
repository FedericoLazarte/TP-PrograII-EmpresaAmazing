package models.transporte;

import models.paquete.Paquete;

public class Utilitario extends Transporte {
	private int valorExtra;
	
	
	
	public Utilitario(String patente, int capacidadVolTotal, int precioXViaje, int valorExtra) {
		super(patente, capacidadVolTotal, precioXViaje);
		validarValExtra(valorExtra);
		this.valorExtra = valorExtra;
	}

	@Override
	public int costoTotalXViaje() {
		if (super.totalPaquetesCargados() > 3) return super.getPrecioXViaje() + this.valorExtra;
		return super.getPrecioXViaje();
	}
	
	@Override
	public boolean cargarPaquete(Paquete paq) {
		if (esPaqueteValido(paq)) return super.cargarPaquete(paq);
		return false;
	}

	@Override
	public boolean esPaqueteValido(Paquete paq) {
		boolean capacidadMaxima = super.hayEspacio();
		if (super.esPaqueteEspecial(paq)) return this.esValidoVolPaqEs(paq) && capacidadMaxima;
		return esValidoVolPaqOr(paq) && capacidadMaxima;
	}
	
	public boolean esValidoVolPaqEs(Paquete paq) {
		return paq.getVolumen() <= 2000;
	}
	
	public boolean esValidoVolPaqOr(Paquete paq) {
		return paq.getVolumen() >= 2000;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Utilitario: ")
		.append(super.toString())
		.append(", Valor Extra: ")
		.append(this.valorExtra)
		.append("]");
		return sb.toString();
	}
	
	private void validarValExtra(int val) {
		if (val < 1) throw new IllegalArgumentException("El valor extra debe ser mayor a 0.");
	}
}
