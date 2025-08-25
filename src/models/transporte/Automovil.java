package models.transporte;

import models.paquete.Paquete;

public class Automovil extends Transporte {
	private int limitePaquetes;
	
	
	public Automovil(String patente, int capacidadVolTotal, int precioXViaje, int limitePaquetes) {
		super(patente, capacidadVolTotal, precioXViaje);
		validarLimitePaquetes(limitePaquetes);
		this.limitePaquetes = limitePaquetes;
	}
	
	@Override
	public boolean cargarPaquete(Paquete paq) {
		if (this.esPaqueteValido(paq)) return super.cargarPaquete(paq);
		return false;
	}

	@Override
	public int costoTotalXViaje() {
		return super.getPrecioXViaje();
	}

	@Override
	public boolean esPaqueteValido(Paquete paq) {
		boolean esOrdinario = super.esPaqueteOrdinario(paq);
		boolean volMenor2000 = this.esVolumenValidoPaqOr(paq);
		boolean capacidadMaxima = super.hayEspacio();
		return esOrdinario && volMenor2000 && capacidadMaxima && hayEspacioAutomovil();
	}
	
	public boolean hayEspacioAutomovil() {
		return super.totalPaquetesCargados() <= this.limitePaquetes;
	}
	
	public boolean esVolumenValidoPaqOr(Paquete paq) {
		return paq.getVolumen() < 2000;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Automóvil: ")
		.append(super.toString())
		.append(", Límite de Paquetes: ")
		.append(this.limitePaquetes)
		.append("]");
		return sb.toString();
	}
	
	
	private void validarLimitePaquetes(int limite) {
		if (limite < 1) throw new IllegalArgumentException("El límite de paquetes debe ser mayor a 0.");
	}
	

}
