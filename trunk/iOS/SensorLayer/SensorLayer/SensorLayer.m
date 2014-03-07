//
//  SensorLayer.m
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/06/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "SensorLayer.h"

@implementation SensorLayer


-(GPS*)getGPS{
    GPS *temp = [[GPS alloc]initWithLM:LocationManager];
    [SensorsReturned addObject:temp];
    return temp;
}
-(AccMeter*)getAccMeter{
    AccMeter *temp = [[AccMeter alloc]initWithMM:MotionManager];
    [SensorsReturned addObject:temp];
    return temp;
}
-(GyroScope*)getGyroScope{
    GyroScope *temp = [[GyroScope alloc]initWithMM:MotionManager];
    [SensorsReturned addObject:temp];
    return temp;
}

-(Proximity*)getProximity{
    Proximity *temp = [[Proximity alloc]init];
    [SensorsReturned addObject:temp];
    return temp;
}

-(void)closeAll{
    NSEnumerator *e = [SensorsReturned objectEnumerator];
    id object;
    SensorObject *Sobj;
    while(object = [e nextObject]){
        Sobj = (SensorObject*)object;
        if([Sobj isFileInitialized]){
            [Sobj deRegister];
        }
        if([Sobj isListening]){
            [Sobj unregisterListener];
        }
    }
    
}

-(id)init{
    SensorsReturned = [[NSMutableArray alloc]init];
    MotionManager = [[CMMotionManager alloc]init];
    LocationManager = [[CLLocationManager alloc]init];
    return self;
}
@end

