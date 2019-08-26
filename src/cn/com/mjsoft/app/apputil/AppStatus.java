package cn.com.mjsoft.app.apputil;

public class AppStatus {
	private Boolean mustTok;

	private Boolean mustEnc;

	private Boolean mustSecTok;

	private Boolean postMode;

	public Boolean getMustEnc() {
		return mustEnc;
	}

	public void setMustEnc(Boolean mustEnc) {
		this.mustEnc = mustEnc;
	}

	public Boolean getMustSecTok() {
		return mustSecTok;
	}

	public void setMustSecTok(Boolean mustSecTok) {
		this.mustSecTok = mustSecTok;
	}

	public Boolean getMustTok() {
		return mustTok;
	}

	public void setMustTok(Boolean mustTok) {
		this.mustTok = mustTok;
	}

	public Boolean getPostMode() {
		return postMode;
	}

	public void setPostMode(Boolean postMode) {
		this.postMode = postMode;
	}

	@Override
	public String toString() {
		return "mustTok:" + this.mustTok + " mustEnc:" + this.mustEnc
				+ " mustSecTok:" + this.mustSecTok + " postMode:"
				+ this.postMode;
	}

}
