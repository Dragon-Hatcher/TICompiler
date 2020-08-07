package codeGeneration;

public enum Register {
	A, B, C, D, E, F, H, L, AF, BC, DE, HL;
	
	public void assertNotHLWord() throws Exception {
		 if(this != Register.AF && this != Register.BC && this != Register.DE) {
			 throw new Exception("Excpeted non HL word instead got " + this);
		 }
	}
	
	public void assertWord() throws Exception {
		 if(this != Register.AF && this != Register.BC && this != Register.DE && this != Register.HL) {
			 throw new Exception("Excpeted non HL word instead got " + this);
		 }
	}
	
	public void assertHL() throws Exception {
		 if(this != Register.HL) {
			 throw new Exception("Excpeted HL instead got " + this);
		 }		
	}
	
	public boolean isWord() {
		 return this == Register.AF || this == Register.BC || this == Register.DE || this == Register.HL;
	}
	
	public Register high() {
		switch(this) {
		case AF: return Register.A;
		case BC: return Register.B;
		case DE: return Register.D;
		case HL: return Register.H;
		default: return this;
		}
	}

	public Register low() {
		switch(this) {
		case AF: return Register.F;
		case BC: return Register.C;
		case DE: return Register.E;
		case HL: return Register.L;
		default: return this;
		}
	}

}
