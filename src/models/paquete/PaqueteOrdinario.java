package models.paquete;

import java.util.Objects;

public class PaqueteOrdinario extends Paquete {
	private int costoEnvio;
	
	

	public PaqueteOrdinario(int volumen, int precio, int costoEnvio) {
		super(volumen, precio);
		validarCosto(costoEnvio);
		this.costoEnvio = costoEnvio;
	}

	@Override
	public int totalPrecio() {
		// TODO Auto-generated method stub
		return super.getPrecio() + this.costoEnvio;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Paquete Ordinario: [")
		.append(super.toString())
		.append(", Costo de Envío: ")
		.append(this.costoEnvio)
		.append("]");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || this.getClass() != other.getClass()) return false;
		if (!super.equals(other)) return false;
		PaqueteOrdinario parOr = (PaqueteOrdinario) other;
		return this.costoEnvio == parOr.costoEnvio;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), this.costoEnvio);
	}
	
	private void validarCosto(int costo) {
		if (costo < 1) throw new IllegalArgumentException("El costo de envío no puede ser menor o igual a 0");
	}

}
