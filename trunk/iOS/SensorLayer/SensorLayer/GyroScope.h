//
//  GyroScope.h
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/07/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "SensorObject.h"
#import <UIKit/UIKit.h>
#import <CoreMotion/CoreMotion.h>

@interface GyroScope : SensorObject {
    CMMotionManager *motionmanager;
}

 
-(BOOL)get : (int)TimeInSeconds: (int) DelayInMilliseconds;
-(void)deRegister;
-(void)unregisterListener;
-(bool)registerListener: (NSObject<SensorReadingHandler> *) Handler;
-(id)initWithMM: (CMMotionManager *)motionmanagerpassed;

@end