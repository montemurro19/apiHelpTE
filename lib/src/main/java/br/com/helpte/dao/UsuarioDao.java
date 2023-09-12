package br.com.helpte.dao;

import java.util.List;

import br.com.helpte.entity.Usuario;
import br.com.helpte.exception.EntidadeNaoEcontradaException;

public interface UsuarioDao extends GenericDao<Usuario, Integer> {
	List<Usuario> listar();

	Usuario login(String usuario);

	Usuario modificar(Usuario entidade, Integer id) throws EntidadeNaoEcontradaException;
}
