//
//  Native3rdParty.h
//  3rdParty
//
//  Created by Arjun Ahuja on 01/09/12.
//  Copyright (c) 2012 Arjun Ahuja. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import <CoreMotion/CoreMotion.h>
#import <CoreLocation/CoreLocation.h>

@interface Native3rdParty : NSObject<CLLocationManagerDelegate> {
@private
    NSString *documentsDir;
    NSFileHandle *FileManager1,*FileManager2,*FileManager3,*FileManager4;
    CMMotionManager *motionmanager;
    CLLocationManager *manager;
    NSString *file1,*file2,*file3,*file4;
}
-(void) getAll;
-(void) scheduleTimer:(int)TimeInSeconds;
-(void) stopAll;
- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation;
@end
