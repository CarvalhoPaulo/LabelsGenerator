package br.com.phtc.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.junit.Test;

import br.com.phtc.exception.BusinessException;

public class LabelDataTest {

	@Test
	public void should_buildThrowException_whenPageCountIsNull() {
		Exception exception = assertThrows(Exception.class, () -> LabelData.builder().build());
		assertThat("Should be instance of BusinessException", exception instanceof BusinessException);
		assertThat(exception.getMessage(), equalTo("Informa a quantidade de páginas"));
	}
	
	@Test
	public void should_buildThrowException_whenFirstNumberIsNull() {
		Exception exception = assertThrows(Exception.class, () -> LabelData.builder()
				.withPageCount(4L)
				.build());
		assertThat("Should be instance of BusinessException", exception instanceof BusinessException);
		assertThat(exception.getMessage(), equalTo("Informa o número da primeira etiqueta"));
	}
	
	@Test
	public void should_buildThrowException_whenPageCountNotMatchWithLabelCount() {
		Exception exception = assertThrows(Exception.class, () -> LabelData.builder()
				.withFirstNumber(10000L)
				.withPageCount(1L)
				.withLabelCount(32L)
				.build());
		assertThat("Should be instance of BusinessException", exception instanceof BusinessException);
		assertThat(exception.getMessage(), equalTo("A quantidade de etiquetas não cabe na quantidade de paginas informada"));
	}
	
	@Test
	public void should_buildThrowException_whenFirstNumberGreateumber() {
		Exception exception = assertThrows(Exception.class, () -> LabelData.builder()
				.withPageCount(4L)
				.withFirstNumber(2000L)
				.withLastNumber(1000L)
				.build());
		assertThat("Should be instance of BusinessException", exception instanceof BusinessException);
		assertThat(exception.getMessage(), equalTo("O numero da primeira etiqueta deve ser maior ou igual o numero da ultima etiqueta"));
	}
	
	@Test
	public void should_build4Rows_whenPageCountIs1() throws BusinessException {
		List<Label> rows = LabelData.builder()
			.withFirstNumber(10000L)
			.withPageCount(1L)
			.build().getRows();
		
		assertThat(rows.size(), equalTo(4));
	}
	
	@Test
	public void should_build8Rows_whenPageCountIs2() throws BusinessException {
		List<Label> rows = LabelData.builder()
			.withFirstNumber(10000L)
			.withPageCount(2L)
			.build().getRows();
		
		assertThat(rows.size(), equalTo(8));
	}
	
	@Test
	public void should_buildCorrectLabels_whenFirstNumberAndLastNumberIsEqual() throws BusinessException {
		List<Label> rows = LabelData.builder()
			.withFirstNumber(10000L)
			.withPageCount(1L)
			.withLastNumber(10000L)
			.build().getRows();
		
		assertThat(rows.size(), equalTo(1));
		assertRow(rows.get(0), "Nº 10.000", "10000");
	}
	
	@Test
	public void should_buildCorrectLabels_whenFirstNumberIs10000AndLastNumberIs10003() throws BusinessException {
		List<Label> rows = LabelData.builder()
			.withFirstNumber(10000L)
			.withPageCount(1L)
			.withLastNumber(10003L)
			.build().getRows();
		
		assertThat(rows.size(), equalTo(4));
		
		assertRow(rows.get(0), "Nº 10.000", "10000");
		assertRow(rows.get(1), "Nº 10.001", "10001");
		assertRow(rows.get(2), "Nº 10.002", "10002");
		assertRow(rows.get(3), "Nº 10.003", "10003");
	}
	
	@Test
	public void should_buildCorrectLabels_whenRequest3Pages() throws BusinessException {
		List<Label> rows = LabelData.builder()
			.withFirstNumber(10000L)
			.withPageCount(3L)
			.build().getRows();
		
		assertThat(rows.size(), equalTo(12));
		
		assertRow(rows.get(0), "Nº 10.000", "10000");
		assertRow(rows.get(1), "Nº 10.003", "10003");
		assertRow(rows.get(2), "Nº 10.006", "10006");
		assertRow(rows.get(3), "Nº 10.009", "10009");
		
		assertRow(rows.get(4), "Nº 10.001", "10001");
		assertRow(rows.get(5), "Nº 10.004", "10004");
		assertRow(rows.get(6), "Nº 10.007", "10007");
		assertRow(rows.get(7), "Nº 10.010", "10010");
		
		assertRow(rows.get(8), "Nº 10.002", "10002");
		assertRow(rows.get(9), "Nº 10.005", "10005");
		assertRow(rows.get(10), "Nº 10.008", "10008");
		assertRow(rows.get(11), "Nº 10.011", "10011");
	}
	
	@Test
	public void should_buildCorrectLabels_whenRequest15Labels() throws BusinessException {
		List<Label> rows = LabelData.builder()
			.withFirstNumber(10000L)
			.withPageCount(4L)
			.withLabelCount(60L)
			.build().getRows();
		
		assertThat(rows.size(), equalTo(15));
		
		assertRow(rows.get(0), "Nº 10.000", "10000");
		assertRow(rows.get(1), "Nº 10.004", "10004");
		assertRow(rows.get(2), "Nº 10.008", "10008");
		assertRow(rows.get(3), "Nº 10.012", "10012");
		
		assertRow(rows.get(4), "Nº 10.001", "10001");
		assertRow(rows.get(5), "Nº 10.005", "10005");
		assertRow(rows.get(6), "Nº 10.009", "10009");
		assertRow(rows.get(7), "Nº 10.013", "10013");
		
		assertRow(rows.get(8), "Nº 10.002", "10002");
		assertRow(rows.get(9), "Nº 10.006", "10006");
		assertRow(rows.get(10), "Nº 10.010", "10010");
		assertRow(rows.get(11), "Nº 10.014", "10014");
		
		assertRow(rows.get(12), "Nº 10.003", "10003");
		assertRow(rows.get(13), "Nº 10.007", "10007");
		assertRow(rows.get(14), "Nº 10.011", "10011");
	}
	
	private void assertRow(Label label, String number, String barcode) {
		assertThat(label.getNumber(), equalTo(number));
		assertThat(label.getNumber(), equalTo(number));
		assertThat(label.getNumber(), equalTo(number));
		assertThat(label.getNumber(), equalTo(number));
		
		assertThat(label.getBarcode(), equalTo(barcode));
		assertThat(label.getBarcode(), equalTo(barcode));
		assertThat(label.getBarcode(), equalTo(barcode));
		assertThat(label.getBarcode(), equalTo(barcode));
	}
}
