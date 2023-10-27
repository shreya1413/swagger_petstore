package swagger.io.swaggerpetstoreservice.common.response;

public class ResponseCodes {
	public static final int SUCCESS = 200;
	public static final int BAD_REQUEST = 400;
	public static final int USER_NOT_FOUND = 404;
	public static final int ALREADY_EXIST = 409;
	
	public static ErrorResponse getErrorResponse(int code ,String message) {
		return new ErrorResponse(code, message);
	}
	
	public static SuccessResponse getSuccessResponse(int code ,String message) {
		return new SuccessResponse(code, message);
	}
}
