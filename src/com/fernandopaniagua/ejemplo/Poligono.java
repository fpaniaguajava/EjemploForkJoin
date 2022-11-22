package com.fernandopaniagua.ejemplo;

import java.util.ArrayList;
import java.util.List;

public class Poligono {
	private static final int DELAY=1000;
	private static final int NUMERO_POLIGONOS=100;

	private int id;
	public boolean renderizado=false;
	public Poligono(int id) {
		this.id=id;
	}
	public void render() {
		try {
			Thread.sleep(DELAY);
			this.renderizado=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Poligono> getPoligonos() {
		List<Poligono> poligonos = new ArrayList<Poligono>();
		for (int i = 0; i < NUMERO_POLIGONOS; i++) {
			poligonos.add(new Poligono(i));
		}
		return poligonos;
	}
}
