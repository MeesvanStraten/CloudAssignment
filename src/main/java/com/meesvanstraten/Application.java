package com.meesvanstraten;


import io.micronaut.runtime.Micronaut;

public class Application {
	public static void main(String[] args) {
		try{
			Micronaut.run(Application.class, args);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
}

