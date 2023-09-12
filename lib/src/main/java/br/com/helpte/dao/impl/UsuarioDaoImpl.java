package br.com.helpte.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

import br.com.helpte.dao.UsuarioDao;
import br.com.helpte.entity.Usuario;
import br.com.helpte.exception.EntidadeNaoEcontradaException;

public class UsuarioDaoImpl extends GenericDaoImpl<Usuario, Integer> implements UsuarioDao {

	public UsuarioDaoImpl(EntityManager em) {
		super(em);
	}

	public List<Usuario> listar() {
		TypedQuery<Usuario> query = em.createQuery("FROM Usuario", Usuario.class);
		return query.getResultList();
	}

	public Usuario login(String usuario) {
		return em.createQuery("from Usuario u where u.usuario = :usuario", Usuario.class)
				.setParameter("usuario", usuario)
				.getSingleResult();
	}

	public Usuario modificar(Usuario entidade, Integer id) throws EntidadeNaoEcontradaException {
		Usuario usuarioExistente = em.find(Usuario.class, id);

		if (usuarioExistente == null) {
			throw new EntidadeNaoEcontradaException("usuario nao encontrado");
		}

		usuarioExistente.setIdade(entidade.getIdade());
		usuarioExistente.setNome(entidade.getNome());
		usuarioExistente.setSenha(entidade.getSenha());
		usuarioExistente.setUsuario(entidade.getUsuario());

		em.getTransaction().begin();
		em.merge(usuarioExistente);
		em.getTransaction().commit();

		return usuarioExistente;
	}
}
