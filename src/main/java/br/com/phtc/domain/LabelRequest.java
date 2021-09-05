package br.com.phtc.domain;

public class LabelRequest {
	private Long pageCount;
	private Long firstNumber;
	private Long labelCount;
	private Long lastNumber;

	private LabelRequest(Builder builder) {
		this.pageCount = builder.pageCount;
		this.firstNumber = builder.firstNumber;
		this.labelCount = builder.labelCount;
		this.lastNumber = builder.lastNumber;
	}
	
	public Long getPageCount() {
		return pageCount;
	}

	public Long getFirstNumber() {
		return firstNumber;
	}

	public Long getLabelCount() {
		return labelCount;
	}

	public Long getLastNumber() {
		return lastNumber;
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

		public Builder withFirstNumber(Long firstNumber) {
			this.firstNumber = firstNumber;
			return this;
		}

		public Builder withLabelCount(Long labelCount) {
			this.labelCount = labelCount;
			return this;
		}

		public Builder withLastNumber(Long lastNumber) {
			this.lastNumber = lastNumber;
			return this;
		}

		public LabelRequest build() {
			return new LabelRequest(this);
		}
	}
	
}
