package models.transporte;

import models.paquete.Paquete;

public class Camion extends Transporte {
	private int valAdicionalXPaq;
	
	
	
	public Camion(String patente, int capacidadVolTotal, int precioXViaje, int valAdicionalXPaq) {
		super(patente, capacidadVolTotal, precioXViaje);
		validarValorAdicional(valAdicionalXPaq);
		this.valAdicionalXPaq = valAdicionalXPaq;
	}

	@Override
	public int costoTotalXViaje() {
		return (this.valAdicionalXPaq) + super.getPrecioXViaje();
	}

	@Override
	public boolean cargarPaquete(Paquete paq) {
		if (esPaqueteValido(paq)) return super.cargarPaquete(paq);
		return false;
	}
	
	@Override
	public boolean esPaqueteValido(Paquete paq) {
		boolean esEspecial = super.esPaqueteEspecial(paq);
		boolean volMayor2000 = esValidoVolPaqEs(paq);
		boolean capacidadMaxima = super.hayEspacio();
		return esEspecial && volMayor2000 && capacidadMaxima;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Camión: ")
		.append(super.toString())
		.append(", Valor Adicional por Paquete: ")
		.append(this.valAdicionalXPaq)
		.append("]");
		return sb.toString();
	}
	
	private void validarValorAdicional(int val) {
		if (val < 1) throw new IllegalArgumentException("El valor adicional no puede menor o igual a cero.");
	}
	
	private boolean esValidoVolPaqEs(Paquete paq) {
		return paq.getVolumen() > 2000;
	}
}
