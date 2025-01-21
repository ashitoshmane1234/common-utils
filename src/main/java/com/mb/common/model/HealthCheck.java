package com.mb.common.model;

import lombok.Builder;
import lombok.Data;

/**
 * Health check response model for returning application and version details
 * 
 * @author Mindbowser | rohit.kavthekar@mindbowser.com
 *
 */
@Data
@Builder
public class HealthCheck {

	private String appName;

	private String appVersion;

	private String groupId;

	private String artifactId;

	private String javaVersion;

	private String springBootVersion;

}
