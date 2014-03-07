//
//  Accelerometer.h
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/06/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SensorObject.h"
#import <UIKit/UIKit.h>
#import <CoreMotion/CoreMotion.h>
@interface AccMeter: SensorObject <UIAccelerometerDelegate> {
    UIAccelerometer *accelerometer;
    CMMotionManager *motionmanager;

}

-(BOOL)get : (int)TimeInSeconds: (int) DelayInMilliseconds;
-(void)deRegister;
-(void)unregisterListener;
-(bool)registerListener: (NSObject<SensorReadingHandler> *) Handler: (int) DelayInMilliseconds;
-(id)initWithMM: (CMMotionManager *)motionmanagerpassed;

@end