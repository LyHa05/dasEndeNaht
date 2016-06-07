package Aufgabe_09;

public class Literal {
	public char bezeichner;
	public boolean wahrheitswert;
	
	public Literal(char bezeichner, boolean wahrheitswert) {
		this.bezeichner = bezeichner;
		this.wahrheitswert = wahrheitswert;
	}
	
	public Literal() {
		
	}
	
	public Literal(Literal lit) {
		this(lit.getBezeichner(), lit.getWahrheitswert());
	}

	public char getBezeichner() {
		return bezeichner;
	}

	public void changeWahrheitswert() {
		wahrheitswert = !wahrheitswert;
	}
	
	public boolean getWahrheitswert() {
		return wahrheitswert;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bezeichner;
		result = prime * result + (wahrheitswert ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Literal other = (Literal) obj;
		if (bezeichner != other.bezeichner) {
			return false;
		}
		if (wahrheitswert != other.wahrheitswert) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (wahrheitswert == false) {
			builder.append('!');
		}
		builder.append(bezeichner);
		return builder.toString();
	}
}