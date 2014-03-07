//
//  SensorLayer.h
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/06/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SensorObject.h"
#import "AccMeter.h"
#import "GPS.h"
#import "Proximity.h"
#import "GyroScope.h"

#define TYPE_ACCELEROMETER 1
#define TYPE_GPS 2
#define TYPE_GYROSCOPE 3
#define TYPE_PROXIMITY 4

@interface SensorLayer : NSObject{
    NSMutableArray *SensorsReturned ;
    CMMotionManager *MotionManager;
    CLLocationManager *LocationManager;
}


-(AccMeter*)getAccMeter;
-(GPS*)getGPS;
-(GyroScope*)getGyroScope;
-(Proximity*)getProximity;
-(void)closeAll;
@end
