package compta.core.application.template;

import compta.core.domaine.operation.Operation;

public class TrimestreTemplateElement {

	private TrimestreTemplateElementFrequence _freq;

	private int _occurence;

	private Operation _operation;

	public TrimestreTemplateElement() {
		// TODO Auto-generated constructor stub
	}

	public TrimestreTemplateElementFrequence getFreq() {
		return _freq;
	}

	public int getOccurence() {
		return _occurence;
	}

	public Operation getOperation() {
		return _operation;
	}

	public void setFreq(TrimestreTemplateElementFrequence freq) {
		_freq = freq;
	}

	public void setOccurence(int occurence) {
		_occurence = occurence;
	}

	public void setOperation(Operation operation) {
		_operation = operation;
	}

}
