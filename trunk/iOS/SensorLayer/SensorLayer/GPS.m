//
//  GPS.m
//  SensorLayer
//
//  Created by Arjun Ahuja on 09/07/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "GPS.h"
#import "SensorLayer.h"
#import "SensorObject.h"


@implementation GPS : SensorObject

-(bool)registerListener:(NSObject<SensorReadingHandler> *)Handler :(int)DelayInMilliseconds{
    
    
    DelayInMilliseconds = DelayInMilliseconds/1000;
    Listener = Handler;
    [manager startUpdatingLocation];
    return true;
}

- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation 
{
    if(Listener!=NULL)
    {
        NSMutableArray *ReadingTemp = [[NSMutableArray alloc] init];
        [ReadingTemp addObject:[NSNumber numberWithDouble:newLocation.coordinate.latitude]];
        [ReadingTemp addObject:[NSNumber numberWithDouble:newLocation.coordinate.longitude]];
        [ReadingTemp addObject:[NSNumber numberWithDouble:newLocation.horizontalAccuracy]];
        
        
        SensorReading *Reading = [[SensorReading alloc] initWithValues:TYPE_GPS :0 : ReadingTemp];
        
        [Listener SensorReadingChanged:Reading];
    }
    
    if(fileInitialized)
    {
        NSMutableString *value = [[NSMutableString alloc]init];
        value = [NSMutableString stringWithFormat:@"location coordinates: %@",[newLocation description]];
        NSLog(@"%@",[newLocation description]);
        [self WriteToFile:value];
    }
    
   
    
}

-(BOOL)get : (int)TimeInSeconds: (int) DelayInMilliseconds{
    
    
    DelayInMilliseconds = DelayInMilliseconds/1000;
    [self InitializeFile:@"GPS.txt"];

    [manager startUpdatingLocation];

    [self scheduleTimer:TimeInSeconds];
    
    return true;
}




-(void)unregisterListener{
    Listener = NULL;
    [manager stopUpdatingLocation];
 
}
-(void) deRegister{
    
    [manager stopUpdatingLocation];
    
    [self closeFile];

}
-(id)initWithLM: (CLLocationManager *)locationmanagerpassed{
    
    self = [super init];
    manager = locationmanagerpassed;
    manager.delegate = self;
    
    Listener = NULL;
    
    return self;
}
@end
