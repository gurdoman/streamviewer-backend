package com.adrianrossino.model;

public class TokenInfo {
	
	private String tokenId;
	private String source;
	
	
	
	public TokenInfo() {
		super();
	}
	public TokenInfo(String tokenId, String source) {
		super();
		this.tokenId = tokenId;
		this.source = source;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
