package edu.zhku.fr.dao;

public class DaoRunntimeException extends RuntimeException {
	private static final long serialVersionUID = -4540905897567970087L;

	public DaoRunntimeException() {
		super();
	}

	public DaoRunntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoRunntimeException(String message) {
		super(message);
	}

	public DaoRunntimeException(Throwable cause) {
		super(cause);
	}

}
