package edu.zhku.fr.expections;

/**
 * 所依赖的模块没有被配置异常，如果一个模块的部署需呀依赖于别的模块，而所依赖的模块并没有配置，那么就会抛出该异常
 * 
 * @author XJQ
 * @since 2013-3-23
 */
public class DependenceModuleInactiveException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DependenceModuleInactiveException() {
		super("Sorry, Dependence module is Inactive, Reasons[1, No configed; 2, Configed but deploy is false]");
	}

	public DependenceModuleInactiveException(String message) {
		super(message);
	}

}
