package br.com.phtc.module;

import br.com.phtc.interactor.LabelReportInteractor;
import br.com.phtc.interactor.LabelReportInteractorImpl;

public class LabelsGeneratorModule {

	public static LabelReportInteractor getLabelReportInteractor() {
		return new LabelReportInteractorImpl();
	}
	
}
