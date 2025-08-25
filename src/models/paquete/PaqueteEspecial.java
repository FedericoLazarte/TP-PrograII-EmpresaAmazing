package models.paquete;

import java.util.Objects;

public class PaqueteEspecial extends Paquete {
	private int porcentaje;
	private int adicional;
	
	
	
	public PaqueteEspecial(int volumen, int precio, int porcentaje, int adicional) {
		super(volumen, precio);
		validarPorcentaje(porcentaje);
		validarAdicional(adicional);
		this.porcentaje = porcentaje;
		this.adicional = adicional;
	}

	@Override
	public int totalPrecio() {
		int sumaPorcentaje = super.getPrecio() + (super.getPrecio() * this.porcentaje / 100);
		if (super.getVolumen() >= 5000) return sumaPorcentaje + (this.adicional * 2);
		if (super.getVolumen() >= 3000) return sumaPorcentaje + this.adicional;
		return sumaPorcentaje;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Paquete Especial: [")
		.append(super.toString())
		.append(", Porcentaje: ")
		.append(this.porcentaje)
		.append(", Adicional: ")
		.append(this.adicional)
		.append("]");
		return sb.toString();
	}
	
	@Override 
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || this.getClass() != other.getClass()) return false;
		if (!super.equals(other)) return false;
		PaqueteEspecial paqEs = (PaqueteEspecial) other;
		return this.porcentaje == paqEs.porcentaje && this.adicional == paqEs.adicional;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), this.porcentaje, this.adicional);
	}
	
	private void validarAdicional(int ad) {
		if (ad <= 0) throw new RuntimeException("El adicional no puede ser menor o igual a cero.");
	}
	
	private void validarPorcentaje(int porcentaje) {
		if (porcentaje <= 0) throw new RuntimeException("El porcentaje no puede ser menor o igual a cero.");
	}
}
