package swagger.io.swaggerpetstoreservice.common.response;

public class SuccessResponse implements CommonResponse {

	private int code;
	private String message;
	
	public SuccessResponse() {
	}

	public SuccessResponse(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public void setCode(int code) {
		this.code = code;

	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;

	}

}
