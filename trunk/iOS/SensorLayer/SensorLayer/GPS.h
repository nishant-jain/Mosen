//
//  GPS.h
//  SensorLayer
//
//  Created by Arjun Ahuja on 09/07/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "SensorObject.h"
#import <CoreLocation/CoreLocation.h>

@interface GPS : SensorObject<CLLocationManagerDelegate>{
    CLLocationManager *manager;
}

- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation;

-(BOOL)get : (int)TimeInSeconds: (int) DelayInMilliseconds;
-(void)deRegister;
-(void)unregisterListener;
-(bool)registerListener: (NSObject<SensorReadingHandler> *) Handler;
-(id)initWithLM: (CLLocationManager *)locationmanagerpassed;
@end
