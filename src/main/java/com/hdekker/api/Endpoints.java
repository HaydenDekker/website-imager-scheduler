package com.hdekker.api;

public class Endpoints {
	
	public static final String DEVICE = "/device";
	public static final String DEVICE_CREATE = "/device/create";
	public static final String DEVICE_CREATE_NAME = "/device/create/{name}";
	public static final String DEVICE_LIST = "/device/list";
	public static final String DEVICE_DELETE = "/device/delete";
	
	public static final String APPFLOW = "/appflow";
	public static final String APPFLOW_CREATE = "/appflow/create";
	public static final String APPFLOW_CREATE_NAME ="/appflow/create/{name}";
	public static final String APPFLOW_LIST = "/appflow/list";
	
	public static final String DEVICE_APPFLOWS = "/device/appflow";
	public static final String DEVICE_APPFLOWS_CREATE = "/device/appflow/create";
	public static final String APPFLOW_UPDATE = "/appflow/update";
	public static final String DEVICE_APPFLOWS_LIST = "/device/appflow/list";
	public static final String DEVICE_APPFLOWS_DELETE = "/device/appflow/delete";
	
	public static final String DEVICEFLOWS_SUBSCRIBE = "/deviceflow/subscribe";
	
	
	
}
