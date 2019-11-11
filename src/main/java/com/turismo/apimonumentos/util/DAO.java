package com.turismo.apimonumentos.util;

import java.util.List;

public interface DAO <T> {
	
	public boolean persist(T t);
	
	public List<T> getAll();
	
	public List<T> getByNombre(String nombre);
	
	public void update(T t);
	
	public void delete(int id);

}
