package models.transporte;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Iterator;

import models.paquete.*;

public abstract class Transporte {
	private String patente;
	private int capacidadVolTotal;
	private int precioXViaje;
	private List<Paquete> paquetes;
	private int volumenCargado;
	
	public Transporte(String patente, int capacidadVolTotal, int precioXViaje) {
		validarPatente(patente);
		validarCapacidadVolumenTotal(capacidadVolTotal);
		validarPrecioXViaje(precioXViaje);
		this.patente = patente;
		this.capacidadVolTotal = capacidadVolTotal;
		this.precioXViaje = precioXViaje;
		this.paquetes = new ArrayList<>();
		this.volumenCargado = 0;
	}
	
	public abstract int costoTotalXViaje();
	
	public abstract boolean esPaqueteValido(Paquete paq);
	
	public boolean cargarPaquete(Paquete paq) {
		if (estaPaquete(paq.getIdPaquete())) {
			throw new RuntimeException("El paquete ya se encuentra cargado");
		}
		if (!paq.estaEntregado()) {
			paq.marcarComoEntregado();
			this.volumenCargado += paq.getVolumen();
			return this.paquetes.add(paq);
		}
		return false;
	}
	
	public void quitarPaquete(int idPaq) {
		if (estaPaquete(idPaq)) {
			Iterator<Paquete> iterator = this.paquetes.iterator();
			while (iterator.hasNext()) {
				Paquete paq = iterator.next();
				if (paq.getIdPaquete() == idPaq) {
					this.volumenCargado -= paq.getVolumen();
					iterator.remove();
				}
			}
		} else {
			throw new RuntimeException("El pauqete no se encuentra en el cargamento.");
		}
	}
	
	public boolean esPaqueteOrdinario(Paquete paq) {
		return paq instanceof PaqueteOrdinario;
	}
	
	public boolean esPaqueteEspecial(Paquete paq) {
		return paq instanceof PaqueteEspecial;
	}
	
	
	public String getPatente() {
		return patente;
	}

	public int getPrecioXViaje() {
		return precioXViaje;
	}

	public int totalPaquetesCargados() {
		return this.paquetes.size();
	}
	
	public  boolean hayEspacio() {
		return volumenCargamento() <= this.capacidadVolTotal;
	}
	
	public boolean estaCargado() {
		return !this.paquetes.isEmpty();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Patente: ")
		.append(this.patente)
		.append(", Capacidad de volument total: ")
		.append(this.capacidadVolTotal)
		.append(", Precio x Viaje: ")
		.append(this.precioXViaje)
		.append(", Paquetes Cargados: [")
		.append(paquetes)
		.append("]");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || this.getClass() != other.getClass()) return false;
		Transporte t = (Transporte) other;
		if (this.paquetes.size() == 0 || t.paquetes.size() == 0) return false;
		return this.paquetes.equals(t.paquetes);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.paquetes);
	}
	

	private void validarPrecioXViaje(int precio) {
		if (precio < 1)
			throw new IllegalArgumentException("El precio por viaje debe ser mayor a cero.");
		
	}

	private void validarCapacidadVolumenTotal(int capVol) {
		if (capVol < 1)
			throw new IllegalArgumentException("La capacidda de volumen del traspporte debe ser mayor a 0");
	}

	private void validarPatente(String patente) {
		if (patente == null || patente.length() == 0)
			throw new IllegalArgumentException("La pantente no puede estar vacía ni ser nula.");
		
	}
	
	private boolean estaPaquete(int idPaq) {
		for (Paquete paq : this.paquetes) {
			if (paq.getIdPaquete() == idPaq)
				return true;
		}
		return false;
	}
	
	private int volumenCargamento() {
		return this.volumenCargado;
	}
}
