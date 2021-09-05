package br.com.phtc.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LabelRow {
	private List<Label> row = new ArrayList<>();

	public LabelRow() {
	}
	
	public LabelRow(Label label, int repetition) {
		for (int i = 0; i < repetition; i++) {
			row.add(label);
		}
	}
	
	public static LabelRow with(Label label, int repetition) {
		return new LabelRow(label, repetition);
	}

	public int size() {
		return row.size();
	}

	public boolean add(Label e) {
		return row.add(e);
	}

	public boolean addAll(Collection<? extends Label> c) {
		return row.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Label> c) {
		return row.addAll(index, c);
	}

	public void clear() {
		row.clear();
	}

	public Label get(int index) {
		return row.get(index);
	}

	public Label set(int index, Label element) {
		return row.set(index, element);
	}

	public void add(int index, Label element) {
		row.add(index, element);
	}

	public Label remove(int index) {
		return row.remove(index);
	}

	@Override
	public String toString() {
		return "LabelRow [row=" + row.toString() + "]";
	}
	
}
