# Website Imager Scheduler

Simple concept to scrape and schedule website images for a remote display device.

The aggregate appflow groups multiple websites and defines how each should be displayed.

The DisplaySchedule provides the appflow and what/if any images to download via ftp for it to display. 

# Tests

## Create an AppFlow / Monitor websites Use Case

User selects multiple websites and declares how often the display should toggle between each site.

- (Complete) API - Can create an appflow.
- API - Can delete and appflow.
- API - Can update an appflow.

## Create a display device

User needs to connect display devices.

- (Complete) API - Can create a device
- API - Can delete a device

## Assign a device an AppFlow

User has to state what websites to display on which device.

- (Complete) API - Can create an appflow device assignement.
- API - can delete an appflow.
- Process - Schedules websites to image.
- Config - Reads assignments on startup

## Subscribe to website image stream

A device url and listens for image updates. An display schedule is returned on successful subscription.

- API - Device can receive a display schedule.

## Download an image

A device and download




