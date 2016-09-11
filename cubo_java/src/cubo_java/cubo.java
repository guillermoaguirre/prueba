package cubo_java;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class cubo extends HttpServlet {
	int [][][] cubo;
	int N;
	int suma = 0;
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			String dimension = request.getParameter("dimension");
			String x = request.getParameter("x");
			String y = request.getParameter("y");
			String z = request.getParameter("z");
			String w = request.getParameter("w");
			String x1 = request.getParameter("x1");
			String y1 = request.getParameter("y1");
			String z1 = request.getParameter("z1");
			String x2 = request.getParameter("x2");
			String y2 = request.getParameter("y2");
			String z2 = request.getParameter("z2");
			System.out.println(w);
			PrintWriter writer = response.getWriter();
			String resultado = "Si ningun resultado";
			writer.println("Dimension :" + dimension );
			if (crearcubo(dimension)){
				writer.println("x :" + x );
				writer.println("y :" + y );
				writer.println("z :" + z );
				writer.println("w :" + w );
				if (!x.isEmpty() && !y.isEmpty() && !z.isEmpty() && !w.isEmpty()){
					if (update(x,y,z,w)){
						writer.println("UPDATE OK");
					}
				}
				writer.println("x1 :" + x1 );
				writer.println("y1 :" + y1 );
				writer.println("z1 :" + z1 );
				writer.println("x2 :" + x2 );
				writer.println("y2 :" + y2 );
				writer.println("z3 :" + z2 );
				if (!x1.isEmpty() && !y1.isEmpty() && !z1.isEmpty() && !x2.isEmpty() && !y2.isEmpty() && !z2.isEmpty()){
					if (query(x1,y1,z1,x2,y2,z2)){
						writer.println("OK Query");
						resultado = String.valueOf(suma);	
					}
				}
			} 
			writer.println("Resultado :" + resultado );
			writer.flush();
	}
	
	public boolean crearcubo(String dimension){
		int d = 0;
		d = Integer.parseInt(dimension);
		if (1<=d && d<=100){
			cubo = new int[d+1][d+1][d+1];
			N = d;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean update(String x, String y, String z, String w){
		int ix =Integer.parseInt(x);
		int iy =Integer.parseInt(y);
		int iz =Integer.parseInt(z);
		int iw =Integer.parseInt(w);
		{
	        // Validar coordenadas 1 <= x,y,z <= N 
	        if(!(1<=ix && ix<=this.N && 1<=iy && iy<=this.N && 1<=iz && iz<=this.N)){
	           return false;
	        } 
	        
	        // Validar valor
	        if(!(Math.pow(-10,9)<= iw && iw<=Math.pow(10,9))){
	            return false;
	        }
	        
	        this.cubo[ix][iy][iz] = iw;
	        return true;
	    }
	}
	
	   public boolean query(String x1, String y1, String z1, String x2, String y2, String z2){
		   	int ix1 =Integer.parseInt(x1);
			int iy1 =Integer.parseInt(y1);
			int iz1 =Integer.parseInt(z1);
			int ix2 =Integer.parseInt(x2);
			int iy2 =Integer.parseInt(y2);
			int iz2 =Integer.parseInt(z2);
		   
	        if(!(1<=ix1 && ix1<= ix2 && ix2<=this.N && 1<=iy1 && iy1<=iy2 && iy2<=this.N && 1<=iz1 && iz1<=iz2 && iz2<=this.N)){
	            return false;
	        }
	        
	        
	        
	        for(int i= ix1; i<=ix2; i++){
	            for(int j=iy1; j<=iy2; j++){
	                for(int k=iz1; k<=iz2; k++){
	                    suma+= this.cubo[i][j][k];
	                }
	            }
	        }
	        return true;
	    }
}
