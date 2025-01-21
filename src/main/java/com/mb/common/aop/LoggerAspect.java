package com.mb.common.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


/**
 * AOP logger for logging the service, controller and dao all methods before and
 * after execution work.
 * <p>
 * Calculating execution time taken for request.
 * <p>
 * <p>
 * Logging the exception messages thrown by the exception handler
 * <p>
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 */
@Aspect
@Component
public class LoggerAspect {

	/**
	 * Apache log4j logger to log the message
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 */
	private final Logger log = LogManager.getLogger();

	/**
	 * Pointcut expression for executing logs for specified packages
	 */
	private static final String EXPRESSION = "execution(* com.*.*.controller.*.*(..)) || "
			+ "execution(* com.*.*.service.*.*(..)) || execution(* com.*.*.dao.*.*(..))";

	/**
	 * Pointcut expression for logging controller message for specified packages
	 */
	private static final String CONTROLLER_EXPRESSION = "execution(* com.*.*.controller.*.*(..))";

	/**
	 * log name of the method before every method from specified expression package
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param joinPoint
	 */
	@Before(EXPRESSION)
	public void before(JoinPoint joinPoint) {
		log.info("before execution of {} method", joinPoint.getSignature());
	}

	/**
	 * log name of the method after every method from specified expression package
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param joinPoint
	 */
	@After(EXPRESSION)
	public void after(JoinPoint joinPoint) {
		log.info("after execution of {} method", joinPoint.getSignature());
	}

	/**
	 * log the name of exception thrown method and exception message
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param joinPoint
	 * @param e
	 */
	// @AfterThrowing(value = CONTROLLER_EXPRESSION, throwing = "throwable")
	// public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
	// log.warn("exception has been thrown in {}.{} method",
	// joinPoint.getSignature().getDeclaringType(),
	// joinPoint.getSignature().getName());
	// if (throwable instanceof CustomException customException) {
	// if (customException.getHttpStatus() != null
	// && customException.getHttpStatus().equals(HttpStatus.INTERNAL_SERVER_ERROR))
	// {
	// log.error("internal server error :: {}, detail :: {}",
	// customException.getMessage(),
	// customException.getDetail());
	// } else {
	// log.warn("caught exception message :: {}, detail :: {}",
	// customException.getMessage(),
	// customException.getDetail());
	// }
	// } else {
	// log.error("uncaught exception message :: {}", throwable.getMessage());
	// }
	// }

	/**
	 * log the time taken by method to execute
	 * 
	 * @author Mindbowser | rohit.kavthekar@mindbowser.com
	 * @param proceedingJoinPoint
	 * @return {@link Object}
	 * @throws Throwable
	 */
	@Around(CONTROLLER_EXPRESSION)
	public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();

		log.info("execution time of {}.{} :: {} ms", className, methodName, stopWatch.getTotalTimeMillis());

		return result;
	}
}
