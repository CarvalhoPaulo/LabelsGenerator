package br.com.phtc.domain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.phtc.exception.BusinessException;

public class LabelData {

	public static int ROWS_PER_PAGE = 4;
	public static int LABELS_PER_ROW = 4;
	public static int LABELS_PER_PAGE = ROWS_PER_PAGE * LABELS_PER_ROW;
	
	private Long pageCount;
	private Long firstNumber;
	private Long labelCount;
	private Long lastNumber;
	private List<Label> rows = new ArrayList<>();

	private LabelData(Builder builder) throws BusinessException {
		this.pageCount = builder.pageCount;
		this.labelCount = builder.labelCount;
		this.firstNumber = builder.firstNumber;
		this.lastNumber = builder.lastNumber;
		this.loadLabelData();
	}
	
	private void loadLabelData() throws BusinessException {
		if (Objects.isNull(pageCount)) {
			throw new BusinessException("Informa a quantidade de páginas");
		}
		if (Objects.isNull(firstNumber)) {
			throw new BusinessException("Informa o número da primeira etiqueta");
		}
		if (Objects.nonNull(lastNumber) && lastNumber < firstNumber) {
			throw new BusinessException("O numero da primeira etiqueta deve ser maior ou igual o numero da ultima etiqueta");
		}
		if (Objects.nonNull(labelCount) && (labelCount / LABELS_PER_PAGE) > pageCount) {
			throw new BusinessException("A quantidade de etiquetas não cabe na quantidade de paginas informada");
		}
		
		Long firstPageRowNumber = firstNumber;
		Long currentNumber = firstNumber;
		Long currentPageRow = 1L;
		Long currentPage = 1L;
		
		while (rows.size() < getRowCount()) {
			DecimalFormat format = new DecimalFormat("Nº ###,###,###,###");
			rows.add(Label.builder()
					.withNumber(format.format(currentNumber))
					.withBarcode(currentNumber + "")
					.build());
			currentPageRow++;
			currentNumber += this.pageCount;
			if (currentPageRow > ROWS_PER_PAGE && currentPage < pageCount) {
				currentPage++;
				currentPageRow = 1L;
				currentNumber = ++firstPageRowNumber;
			}
		}
	}
	
	private Long getRowCount() {
		return Objects.nonNull(labelCount) ? labelCount / LABELS_PER_ROW : Objects.nonNull(lastNumber) ? (lastNumber - firstNumber) + 1 : pageCount * 4;
	}

	public List<Label> getRows() {
		return rows;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Long pageCount;
		private Long firstNumber;
		private Long labelCount;
		private Long lastNumber;

		private Builder() {
		}

		public Builder withPageCount(Long pageCount) {
			this.pageCount = pageCount;
			return this;
		}

		public Builder withLabelCount(Long labelCount) {
			this.labelCount = labelCount;
			return this;
		}

		public Builder withFirstNumber(Long firstNumber) {
			this.firstNumber = firstNumber;
			return this;
		}
		
		public Builder withLastNumber(Long lastNumber) {
			this.lastNumber = lastNumber;
			return this;
		}

		public LabelData build() throws BusinessException {
			return new LabelData(this);
		}
	}
	
}
