package cubo_java;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class cubo extends HttpServlet {
	int[][][] cubo;
	int N;
	int suma = 0;
	String x, y, z, w, x1, x2, y1, y2, z1, z2;
	ArrayList<String> resultado = new ArrayList<String>();
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String dimension = request.getParameter("dimension");
		String peticiones = request.getParameter("peticiones");
		PrintWriter writer = response.getWriter();
		resultado.add("No tiene resutado");
		if (crearcubo(dimension)) {
			writer.println("Dimension cubo:" + dimension);
			if (procesar_peticiones(peticiones)) {
				writer.println("Procesamiento de peticiones " + peticiones);
			} 
		}
		
		Iterator<String> it = resultado.iterator();
		while (it.hasNext()){
			String out = it.next();
			System.out.println("Salida" + out);
			resultado = null;
			writer.println(out);
		}
		writer.flush();


	}

	public boolean crearcubo(String dimension) {
		int d = 0;
		d = Integer.parseInt(dimension);
		if (1 <= d && d <= 100) {
			cubo = new int[d + 1][d + 1][d + 1];
			//cubo = new int[1][1][1];
			this.N = d;
			//d = N;
			return true;
		} else {
			return false;
		}
	}

	public boolean update(String x, String y, String z, String w) {
		int ix = Integer.parseInt(x);
		int iy = Integer.parseInt(y);
		int iz = Integer.parseInt(z);
		int iw = Integer.parseInt(w);
		{
			// Validar coordenadas 1 <= x,y,z <= N
			if (!(1 <= ix && ix <= this.N && 1 <= iy && iy <= this.N && 1 <= iz && iz <= this.N)) {
				return false;
			}

			// Validar valor
			if (!(Math.pow(-10, 9) <= iw && iw <= Math.pow(10, 9))) {
				return false;
			}
			System.out.println("x->"+ix+" y->"+iy+" z->"+iz+" w->"+ iw);
			this.cubo[ix][iy][iz] = iw;
			System.out.println("Cubo" + cubo[ix][iy][iz]);
			return true;
		}
	}

	public boolean query(String x1, String y1, String z1, String x2, String y2, String z2) {
		int ix1 = Integer.parseInt(x1);
		int iy1 = Integer.parseInt(y1);
		int iz1 = Integer.parseInt(z1);
		int ix2 = Integer.parseInt(x2);
		int iy2 = Integer.parseInt(y2);
		int iz2 = Integer.parseInt(z2);
		
		if (!(1 <= ix1 && ix1 <= ix2 && ix2 <= this.N && 1 <= iy1 && iy1 <= iy2 && iy2 <= this.N && 1 <= iz1
				&& iz1 <= iz2 && iz2 <= this.N)) {
			return false;
		}
		int suma = 0;
		System.out.println("x1->"+ix1+" y1->"+iy1+" z1->"+iz1+" x2->"+ix2+" y2->"+iy2+" z2->"+iz2);
		for (int i = ix1; i <= ix2; i++) {
			for (int j = iy1; j <= iy2; j++) {
				for (int k = iz1; k <= iz2; k++) {
					suma += this.cubo[i][j][k];
					//suma = this.cubo[i][j][k];
				}
			}
		}
		System.out.println("suma: " + suma);
		String Ssuma = String.valueOf(suma);
		resultado.add(Ssuma);
		return true;
	}

	private boolean procesar_peticiones(String peticiones) {
		// String cadena = "UPDATE 2 2 2 4,QUERY 1 1 1 1 4 4,UPDATE 2 2 2 5";
		try {
			peticiones = peticiones.substring(0,  peticiones.length() -1);
			peticiones =peticiones.substring(1 ,peticiones.length());
			String[] peticion = peticiones.split(",");
			String[] variables = null;
			for (int i = 0; i <= peticion.length - 1; i++) {
				int t =  peticion[i].length();
				//System.out.println("cadenas " + peticion[i] + " tamaño " + t);
				variables = peticion[i].split(" ");
				if (variables[0].equals("UPDATE")) {
					if (!variables[1].isEmpty() && !variables[2].isEmpty() && !variables[3].isEmpty() && !variables[4].isEmpty()) {
						//System.out.println("x->"+variables[1]+"y->"+variables[2]+"z->"+variables[3]+"w->"+ variables[4]);
						if (update(variables[1], variables[2], variables[3], variables[4])){
							System.out.println("UPDATE OK");
						}
					}
				} else if (variables[0].equals("QUERY")) {
					if (!variables[1].isEmpty() && !variables[2].isEmpty() && !variables[3].isEmpty() && !variables[4].isEmpty() && !variables[5].isEmpty() && !variables[6].isEmpty()) {
						if (query(variables[1], variables[2], variables[3], variables[4], variables[5], variables[6])){
							System.out.println("QUERY OK");
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error : " + e );
			return false;
		}
		return true;
	}
}
