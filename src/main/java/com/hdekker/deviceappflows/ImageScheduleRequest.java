package com.hdekker.deviceappflows;

import com.hdekker.appflow.AppFlow;
import com.hdekker.device.Device;

public record ImageScheduleRequest(Device device, AppFlow appFlow) {}
