package br.com.helpte.controller;


import java.util.List;

import jakarta.persistence.EntityManager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.helpte.dao.HistoricoDao;
import br.com.helpte.dao.UsuarioDao;
import br.com.helpte.dao.impl.UsuarioDaoImpl;
import br.com.helpte.dao.impl.HistoricoDaoImpl;
import br.com.helpte.entity.Historico;
import br.com.helpte.exception.CommitException;
import br.com.helpte.sigleton.EntityManagerFactorySingleton;
import br.com.helpte.exception.EntidadeNaoEcontradaException;

@CrossOrigin(origins = "http://localhost:19006")
@RestController
public class HistoricoController {

	private EntityManager em = EntityManagerFactorySingleton
			.getInstance().createEntityManager();
	
	HistoricoDao dao = new HistoricoDaoImpl(em);
	
	private HistoricoDao historicoDao = new HistoricoDaoImpl(em);	
	private UsuarioDao usuarioDao = new UsuarioDaoImpl(em);

	@GetMapping("/historico/{id}")
	ResponseEntity<List<Historico>> all(@PathVariable Integer id) {
		List<Historico> historicoList = historicoDao.listar();
		return ResponseEntity.ok(historicoList);
	}

	@PostMapping("/historico/{id}")
	public ResponseEntity<Historico> newHistorico(@RequestBody Historico newHistorico, @PathVariable Integer id) {
		try {
			newHistorico.setUsuario(usuarioDao.buscar(id));
			dao.salvar(newHistorico);
			dao.commit();
			newHistorico.setCodigo(historicoDao.findHistorico(newHistorico.getFrase(), newHistorico.getTraducao()).getCodigo());
		} catch (CommitException e) {
			System.out.println(e.getMessage());
		} catch (EntidadeNaoEcontradaException e) {
			System.out.println(e.getMessage());
		}
		return ResponseEntity.ok(newHistorico);
	}

	@PutMapping("/historico/{id}")
	public ResponseEntity<Historico> replaceHistorico(@RequestBody Historico newHistorico, @PathVariable Integer id) {
		
		try {
			newHistorico.setUsuario(usuarioDao.buscar(id));
			dao.salvar(newHistorico);
			dao.commit();
		} catch (CommitException e) {
			System.err.println(e.getMessage());
		} catch (EntidadeNaoEcontradaException e) {
			System.out.println(e.getMessage());
		}
		return ResponseEntity.ok(newHistorico);
	}

	@DeleteMapping("/historico/{id}")
	public ResponseEntity<Historico> deleteHistorico(@PathVariable Integer id) {
		try {
			dao.deletar(id);
			dao.commit();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
}
