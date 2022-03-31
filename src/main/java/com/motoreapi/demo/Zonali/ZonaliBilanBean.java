package com.motoreapi.demo.Zonali;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class ZonaliBilanBean {
	
	@JsonProperty("DATA_ORA")
	@Expose
	public String DATA_ORA;
	
	@JsonProperty("ORA")
	@Expose
	public String ORA;
	
	@JsonProperty("ZONA_DA")
	@Expose
	public String ZONA_DA;
	
	@JsonProperty("ZONA_A")
	@Expose
	public String ZONA_A;
	
	@JsonProperty("SCAMBIO")
	@Expose
	public float SCAMBIO;
	
	@JsonProperty("NAZIONE")
	@Expose
	public String NAZIONE;
	
	@JsonProperty("SALDO")
	@Expose
	public float SALDO;

	
	public float getSALDO() {
		return SALDO;
	}

	public void setSALDO(float sALDO) {
		SALDO = sALDO;
	}

	public String getNAZIONE() {
		return NAZIONE;
	}

	public void setNAZIONE(String nAZIONE) {
		NAZIONE = nAZIONE;
	}

	public String getDATA_ORA() {
		return DATA_ORA;
	}

	public void setDATA_ORA(String dATA_ORA) {
		DATA_ORA = dATA_ORA;
	}

	public String getORA() {
		return ORA;
	}

	public void setORA(String oRA) {
		ORA = oRA;
	}

	public String getZONA_DA() {
		return ZONA_DA;
	}

	public void setZONA_DA(String zONA_DA) {
		ZONA_DA = zONA_DA;
	}

	public String getZONA_A() {
		return ZONA_A;
	}

	public void setZONA_A(String zONA_A) {
		ZONA_A = zONA_A;
	}

	public float getSCAMBIO() {
		return SCAMBIO;
	}

	public void setSCAMBIO(float sCAMBIO) {
		SCAMBIO = sCAMBIO;
	}

	@Override
	public String toString() {
		return "ZonaliBilanBean [DATA_ORA=" + DATA_ORA + ", ORA=" + ORA + ", ZONA_DA=" + ZONA_DA + ", ZONA_A=" + ZONA_A
				+ ", SCAMBIO=" + SCAMBIO + "]";
	}

}
