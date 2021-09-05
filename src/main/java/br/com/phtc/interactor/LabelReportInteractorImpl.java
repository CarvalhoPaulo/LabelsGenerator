package br.com.phtc.interactor;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import br.com.phtc.domain.LabelData;
import br.com.phtc.domain.LabelRequest;
import br.com.phtc.exception.BusinessException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class LabelReportInteractorImpl implements LabelReportInteractor {

	@Override
	public JasperPrint execute(LabelRequest request) throws BusinessException, FileNotFoundException, JRException  {
		LabelData reportData = LabelData.builder()
				.withFirstNumber(request.getFirstNumber())
				.withLabelCount(request.getLabelCount())
				.withLastNumber(request.getLastNumber())
				.withPageCount(request.getPageCount())
				.build();
		
		InputStream jasperTemplate = this.getClass().getResourceAsStream("label-report.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperTemplate);
		return JasperFillManager.fillReport(jasperReport, new HashMap<>(), new JRBeanCollectionDataSource(reportData.getRows()));
	}

}
