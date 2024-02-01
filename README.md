# Website Imager Scheduler

Simple concept to scrape and schedule website images for a remote display device. 

This is useful for a remote display that doesn't need much processing power at the edge and best serves slow moving information. An e-paper display would be a suitable type.

The aggregate AppFlow groups multiple websites and defines how/when each should be displayed.

The DeviceFlow provided to devices contains the AppFlow and a list images to download, via ftp.

# Tests

## Create an AppFlow / Monitor websites Use Case

User selects multiple websites and declares how often the display should toggle between each site.

- (Complete) API - Can create an appflow.
- (Complete) API REST Adapter - Can delete and appflow.
- (Complete) API REST Adapter - Can update an appflow.
- (Complete) Database Adapter - Persists appflows.

## Create a display device

User needs to connect display devices.

- (Complete) API - Can create a device
- (Complete) API REST Adapter - Can delete a device
- (Complete) Database Adapter - Persists devices

## Flow Use Case / Assign a device an AppFlow

User has to state what websites to display on which device. When user has provided this we can begin scraping websites according to the flow.

- (Complete) API - Can create an appflow device assignment.
- (Complete) API REST Adapter - can delete an appflow.
- (Complete) Database Adapter - retrieves appflows.
- (Complete) Input Port - Schedules appflows to trigger event on timer.
- (Complete) Output Port - Retrieve websites when triggered by scheduler timer.
- Config - Reads assignments on startup and schedules current assignments.


## Subscribe to website image stream

A device listens for image/flow updates. A display schedule is returned on successful subscription.

- Database Adapter - persists image retrieval events.
- Database Adapter - retrieves appflows and image events for a device.
- (Complete) Input port - listens for flow updates
- (Complete) API - Device can receive a display schedule.
- (Complete) API - Sends updates async relevant to subscirbed devices.

## Download an image

A device can download

- (complete) FTP Server - Can connect, locate and download.

## Example Application

Run the main application and it'll load the Vaadin Application on the configured port in application.properties.
It demonstrates the full functionality above.




