package com.Interface;

import java.io.IOException;

public interface InterfaceBancoDados {
	public void conectar(String db_url, String db_user, String db_password) throws IOException;
	public void desconectar() throws IOException;
	public void consultar(String db_query)throws IOException;
	public int inserirAlterarExcluir(String db_query)throws IOException;
}
