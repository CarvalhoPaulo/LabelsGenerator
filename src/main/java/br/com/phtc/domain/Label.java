package br.com.phtc.domain;

public class Label {
	private String number;
	private String barcode;

	private Label(Builder builder) {
		this.number = builder.number;
		this.barcode = builder.barcode;
	}
	
	public String getNumber() {
		return number;
	}
	public String getBarcode() {
		return barcode;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String number;
		private String barcode;

		private Builder() {
		}

		public Builder withNumber(String number) {
			this.number = number;
			return this;
		}

		public Builder withBarcode(String barcode) {
			this.barcode = barcode;
			return this;
		}

		public Label build() {
			return new Label(this);
		}
	}

	@Override
	public String toString() {
		return "Label [number=" + number + ", barcode=" + barcode + "]";
	}
}
