package com.ufu.crokage.to;

import java.io.Serializable;

public class GenericRestTransfer implements Serializable
{
	private static final long serialVersionUID = -2140576335747945587L;
	protected Integer id;
	protected String descricao;
	protected String errorMessage;
	protected String infoMessage;
	

	public GenericRestTransfer(Integer id, String descricao, String infoMessage, String errorMessage)
	{
		this.id = id;
		this.errorMessage = errorMessage;
		this.infoMessage = infoMessage;
		this.descricao = descricao;
		
	}
	
	public GenericRestTransfer() {
		this.descricao=null;
		this.infoMessage=null;
		this.id=null;
		this.errorMessage=null;
	}
	

	public Integer getId() {
		return id;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public String getInfoMessage() {
		return infoMessage;
	}


	public String getDescricao() {
		return descricao;
	}
	
	

}