package br.com.helpte.dao;

import java.util.List;

import br.com.helpte.entity.Historico;

public interface HistoricoDao extends GenericDao<Historico, Integer> {
	List<Historico> listar(Integer id);
	Historico findHistorico(String frase, String traducao);
}
