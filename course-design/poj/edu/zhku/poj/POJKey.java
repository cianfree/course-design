package edu.zhku.poj;


public class POJKey extends edu.zhku.fr.Key {
	public static final POJKey ROLE_STUDENT = new POJKey("student-role", null);
	public static final POJKey ROLE_TEACHER = new POJKey("teacher-role", null);
	
	// ---------------------------------------------------------
	public static final POJKey ROLE_STUDENT_NAME = new POJKey("student-role-name", "学生用户");
	public static final POJKey ROLE_TEACHER_NAME = new POJKey("teacher-role-name", "教师用户");

	protected POJKey(Object key, Object defVal) {
		super(key, defVal);
	}

	protected POJKey(Object key) {
		super(key);
	}
}
