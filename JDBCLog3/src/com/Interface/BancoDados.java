package com.Interface;
import java.sql.*;
import java.io.IOException;


public class BancoDados implements InterfaceBancoDados {
		private Connection c;
		private Log meuLogger; 
	    
	    public BancoDados() throws IOException {
	        meuLogger = new Log("Log.txt");
	    }
	    
	    
	    public static void main(String[] args) throws IOException {
	        String db_url = "jdbc:mysql://localhost:3306/reuniao";
	        String db_user = "root";
	        String db_password = "";
	        BancoDados bd = new BancoDados();
	        bd.conectar(db_url, db_user, db_password);
	        
	        String tabela = "pessoa"; 
	        bd.inserirAlterarExcluir("INSERT INTO pessoa (nome, email, cargo) values ('William','william@yahoo.com','Aluno'),('Maria','maria@yahoo.com','Diretora')");
	        
	        bd.consultar("SELECT * FROM " + tabela);
	        
	        bd.desconectar();
	    }
	        
	    @Override
		public void conectar(String db_url, String db_user, String db_password) throws IOException{
		    try {
		        c = DriverManager.getConnection(db_url, db_user, db_password);
		        meuLogger.logger.info("\n Conectado ao banco de dados");
		    } catch (SQLException e) {
		    	meuLogger.logger.warning("Erro ao conectar o banco de dados: " + e.getMessage());
		    }
		}

	    @Override
		public void desconectar() throws IOException{
		    try {
		    	meuLogger.logger.info("Desconectado do Banco de Dados");
		        c.close();
		    } catch (SQLException e) {
		        meuLogger.logger.warning("Erro ao desconectar do banco de dados: " + e.getMessage());
		    }
		}

	    @Override
		public void consultar(String db_query) throws IOException{
		    try {
		    	PreparedStatement ps = c.prepareStatement(db_query);
		        ResultSet rs = ps.executeQuery(db_query);
		        while (rs.next()) {
		        	System.out.println("ID: " + rs.getString(1)+"\tNome: "+rs.getString(2) + "\tEmail: "+rs.getString(3) + "\tCargo: " + rs.getString(4));
		        }
		        ps.close();
		        rs.close();
		    } catch (SQLException e) {
		        meuLogger.logger.warning("Erro ao consultar" + e.getMessage());
		    }
		}

	    @Override
		public int inserirAlterarExcluir(String db_query) throws IOException{
		    int numLinhasAfetadas = 0;
		    try {
		    	PreparedStatement ps = c.prepareStatement(db_query);
		        numLinhasAfetadas = ps.executeUpdate();
			    meuLogger.logger.info("Operação realizada com sucesso! " + numLinhasAfetadas + " linhas foram afetadas.");
		        ps.close();
		    } catch (SQLException e) {
		        meuLogger.logger.warning("Erro ao realizar a operação " + e.getMessage());
		    }
		    return numLinhasAfetadas;
		}

}
