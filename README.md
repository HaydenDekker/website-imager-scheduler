# Website Imager Scheduler

Simple concept to scrape and schedule website images for a remote display device.

The aggregate appflow groups multiple websites and defines how each should be displayed.

The DisplaySchedule provided to devices contains the appflow and list images to download, via ftp.

# Tests

## Create an AppFlow / Monitor websites Use Case

User selects multiple websites and declares how often the display should toggle between each site.

- (Complete) API - Can create an appflow.
- API - Can delete and appflow.
- API - Can update an appflow.
- Database - Persists appflows.

## Create a display device

User needs to connect display devices.

- (Complete) API - Can create a device
- API - Can delete a device
- Database - Persists devices

## Flow Use Case / Assign a device an AppFlow

User has to state what websites to display on which device. When user has provided this we can begin scraping websites according to the flow.

- (Complete) API - Can create an appflow device assignement.
- API - can delete an appflow.
- Database - retrieves appflows.
- (Complete) Input Port - Schedules appflows to trigger event on timer.
- (Complete) Output Port - Retrieve websites when triggered by scheduler timer.
- Config - Reads assignments on startup and schedules current assignments.
- Database - persists flow schedule events.

## Subscribe to website image stream

A device listens for image/flow updates. A display schedule is returned on successful subscription.

- Database - retrieves device flows and flow updates
- Input port - listens for flow updates
- API - Device can receive a display schedule.
- API - Sends updates async relevant to subscirbed devices.

## Download an image

A device can download

- FTP Server - Can connect, locate and download.




