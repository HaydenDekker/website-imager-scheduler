# Website Imager Scheduler

Simple concept to scrape and schedule website images for a remote display device. It's kinda just a file scheduler but specific is always best first up.

I'm using it to update a remote display that doesn't need much processing power at the edge as is serves slow moving information, approx 10 times a day.

The aggregate AppFlow groups multiple websites and defines how/when each should be displayed.

The DeviceFlow provided to devices contains the AppFlow and a list images to download, via ftp.

# Tests

## Create an AppFlow / Monitor websites Use Case

User selects multiple websites and declares how often the display should toggle between each site.

- (Complete) API - Can create an appflow.
- (Complete) API REST Adapter - Can delete and appflow.
- (Complete) API REST Adapter - Can update an appflow.
- (Complete) Database Adapter - Persists appflows.
- (Complete) Database Adapter - retrieves appflows.

### Scheduling process

- (Complete) Input Port - Schedules appflows to trigger event on timer.
- (Complete) Output Port - Retrieve websites when triggered by scheduler timer.
- (Complete) Service - Periodically add more flowtimers such that the number of current timer is minimised while the timers are continually refreshed.
- (Complete) Service - Update timers when appFlows are updated.
- (Complete) Config - Reads and schedules appFlows on startup.
- (Complete) MockWebsiteAdapter - provides a static image in response to all requests.

## Create a display device

User needs to connect display devices.

- (Complete) API - Can create a device
- (Complete) API REST Adapter - Can delete a device
- (Complete) Database Adapter - Persists devices

## Assign a device to an AppFlow

User has to state what websites to display on which device.

- (Complete) API - Can create an appflow device assignment.
- (Complete) API REST Adapter - can delete an appflow.

## Subscribe to website image stream

A device listens for image/flow updates. A display schedule is returned on successful subscription.

- (Complete) Database Adapter - persists image retrieval events.
- (Complete) Database Adapter - retrieves appflows and image events for a device.
- (Complete) Input port - listens for flow updates
- (Complete) API - Device can receive a display schedule.
- (Complete) API - Sends updates async relevant to subscribed devices.
- (Complete) API - Can subscribe to appflow updates for device.

## Download an image

A device can download

- (complete) FTP Server - Can connect, locate and download.
- HTTP, Can retrieve image via HTTP.
- Can read image from file system at configured location.

## Example Application

Run the main application and it'll load the Vaadin Application on the configured port in application.properties.
It demonstrates the full functionality above.

### Mock Image Interface for Integration Testing
Use the profile "mock_image_if" and set the properties to instruct the image interface to respond to all requests using a static image.




