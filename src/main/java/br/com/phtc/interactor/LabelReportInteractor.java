package br.com.phtc.interactor;

import java.io.FileNotFoundException;

import br.com.phtc.domain.LabelRequest;
import br.com.phtc.exception.BusinessException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface LabelReportInteractor {
	
	public JasperPrint execute(LabelRequest request) throws BusinessException, FileNotFoundException, JRException;
}
