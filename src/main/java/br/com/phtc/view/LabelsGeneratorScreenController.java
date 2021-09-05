package br.com.phtc.view;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.phtc.domain.LabelData;
import br.com.phtc.domain.LabelRequest;
import br.com.phtc.exception.BusinessException;
import br.com.phtc.module.LabelsGeneratorModule;
import br.com.phtc.utils.FXAlertUtils;
import br.com.phtc.utils.FXListenerUtils;
import br.com.phtc.utils.FXTextFieldUtils;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class LabelsGeneratorScreenController implements Initializable {

	@FXML
	private Button button = new Button();

	@FXML
	private TextField txtPageCount = new TextField();

	@FXML
	private TextField txtLabelCount = new TextField();

	@FXML
	private TextField txtFirstNumber = new TextField();

	@FXML
	private TextField txtLastNumber = new TextField();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FXListenerUtils.addOnlyNumber(txtPageCount);
		FXListenerUtils.addOnlyNumber(txtLabelCount);
		FXListenerUtils.addOnlyNumber(txtFirstNumber);
		FXListenerUtils.addOnlyNumber(txtLastNumber);

		FXListenerUtils.addTextLimiter(txtPageCount, 11);
		FXListenerUtils.addTextLimiter(txtLabelCount, 11);
		FXListenerUtils.addTextLimiter(txtFirstNumber, 11);
		FXListenerUtils.addTextLimiter(txtLastNumber, 11);
	}

	@FXML
	private void onClick(ActionEvent event) {
		Task<JasperPrint> task = createTaskLabelsGenerator();
		task.setOnRunning(getOnRunningLabelsGenerator());
		task.setOnSucceeded(getOnSucceededLabelsGenerator(task));
		task.setOnFailed(getOnFailedLabelsGenerator(task));
		new Thread(task).start();
	}
	
	private Task<JasperPrint> createTaskLabelsGenerator() {
		return new Task<JasperPrint>() {
			@Override
			public JasperPrint call() throws FileNotFoundException, BusinessException, JRException {
				return createReport();
			}
		};
	}
	
	protected JasperPrint createReport() throws FileNotFoundException, BusinessException, JRException {
		return LabelsGeneratorModule.getLabelReportInteractor()
				.execute(LabelRequest.builder()
					.withPageCount(FXTextFieldUtils.toLong(txtPageCount))
					.withLabelCount(FXTextFieldUtils.toLong(txtLabelCount))
					.withFirstNumber(FXTextFieldUtils.toLong(txtFirstNumber))
					.withLastNumber(FXTextFieldUtils.toLong(txtLastNumber))
					.build());
	}

	private EventHandler<WorkerStateEvent> getOnRunningLabelsGenerator() {
		return (e) -> {
			button.setDisable(true);
			button.setText("Gerando etiquetas. Aguarde alguns segundos...");
		};
	}
	
	private EventHandler<WorkerStateEvent> getOnSucceededLabelsGenerator(Task<JasperPrint> task) {
		return (e) -> {
			button.setDisable(false);
			button.setText("Gerar Etiquetas");

			try {
				JasperViewer viewer = new JasperViewer(task.get(), false);
				viewer.setVisible(true);
			} catch (Exception ex) {
				FXAlertUtils.alertError(ex.getMessage());
			}
		};
	}
	
	private EventHandler<WorkerStateEvent> getOnFailedLabelsGenerator(Task<JasperPrint> task) {
		return (e) -> {
			button.setDisable(false);
			button.setText("Gerar Etiquetas");
		
			FXAlertUtils.alertError(task.getException().getMessage());
		};
	}

	@FXML
	private void onTxtTyped(Event event) {
		if (event.getTarget() == txtPageCount) {
			onPageCountChange();
		}

		if (event.getTarget() == txtLabelCount) {
			onLabelCountChange();
		}

		if (event.getTarget() != txtLastNumber && !txtFirstNumber.getText().isEmpty()
				&& !txtLabelCount.getText().isEmpty()) {
			calculateLastNumber();
		}

		if (event.getTarget() == txtLastNumber) {
			onLastNumberChange();
		}
	}

	private void calculateLastNumber() {
		try {
			Long firstNumber = Long.parseLong(txtFirstNumber.getText());
			Long labelCount = Long.parseLong(txtLabelCount.getText());
			txtLastNumber.setText((firstNumber + (labelCount / LabelData.LABELS_PER_ROW) - 1) + "");
		} catch (Exception e) {
		}
	}

	private void onLastNumberChange() {
		try {
			Long lastNumber = Long.parseLong(txtLastNumber.getText());
			Long firstNumber = Long.parseLong(txtFirstNumber.getText());
			Long labelCount = lastNumber - firstNumber + 1L;
			if (labelCount >= 0) {
				txtLabelCount.setText(labelCount + "");
				txtPageCount.setText(Double.valueOf(Math.ceil(labelCount / Double.valueOf(LabelData.LABELS_PER_PAGE))).longValue() + "");
			} else {
				txtLabelCount.setText("");
				txtPageCount.setText("");
			}
		} catch (Exception e) {
		}
	}

	private void onLabelCountChange() {
		try {
			Long labelCount = Long.parseLong(txtLabelCount.getText());
			txtPageCount.setText(Double.valueOf(Math.ceil(labelCount / Double.valueOf(LabelData.LABELS_PER_PAGE))).longValue() + "");
		} catch (Exception e) {
			txtPageCount.setText("");
			txtLabelCount.setText("");
		}
	}

	private void onPageCountChange() {
		try {
			Long pageCount = Long.parseLong(txtPageCount.getText());
			txtLabelCount.setText((pageCount * LabelData.LABELS_PER_PAGE) + "");
		} catch (Exception e) {
			txtPageCount.setText("");
			txtLabelCount.setText("");
		}
	}

}
