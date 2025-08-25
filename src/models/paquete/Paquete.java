package models.paquete;

import java.util.Objects;

public abstract class Paquete {
	private static int proxIdPaq = 1;
	private int idPaquete;
	private int volumen;
	private int precio;
	private boolean entregado;
	
	public Paquete(int volumen, int precio) {
		validarVolumen(volumen);
		validarPrecio(precio);
		this.idPaquete = proxIdPaq++;
		this.volumen = volumen;
		this.precio = precio;
		this.entregado = false;
	}
	
	public abstract int totalPrecio();
	
	public boolean estaEntregado() {
		return this.entregado;
	}
	
	public void marcarComoEntregado() {
		if (!this.entregado) this.entregado = true;
		else throw new RuntimeException("El paquete ya fue entregado");
	}
	
	public int getVolumen() {
		return this.volumen;
	}
	
	public int getIdPaquete() {
		return this.idPaquete;
	}
	
	public int getPrecio() {
		return this.precio;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.idPaquete)
		.append(", Volumen: ")
		.append(this.volumen)
		.append(", Precio: ")
		.append(this.precio)
		.append(", Entregado: ")
		.append(this.entregado);
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || this.getClass() != other.getClass()) return false;
		Paquete paq = (Paquete) other;
		return this.volumen == paq.volumen && this.precio == paq.precio;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.volumen, this.precio);
	}
	
	private void validarPrecio(int precio) {
		if (precio <= 0)  new IllegalArgumentException("El precio no puede ser menor igual a cero.");
	}
	
	private void validarVolumen(int vol) {
		if (vol <= 0) new IllegalArgumentException("El voluvmen no puede ser menor o igual a cero");
	}
}
