package org.example.dto;

import java.util.Objects;

public class Token{
	private String value;

	@Override
	public String toString() {
		return "Token [value=" + value + "]";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		return Objects.equals(value, other.value);
	}
	
	
	
}
