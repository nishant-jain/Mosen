//
//  Proximity.m
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/07/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "Proximity.h"
#import "SensorLayer.h"
#import "SensorObject.h"

@implementation Proximity

-(bool)registerListener: (NSObject<SensorReadingHandler> *) Handler
{
    Listener = Handler;
    [[UIDevice currentDevice] setProximityMonitoringEnabled:YES];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(SensorStateChange:) name:@"UIDeviceProximityStateChange"object:nil];
    return true;
    
}
-(void)SensorStateChange :(NSNotificationCenter *)notification{
    
    NSMutableArray *ReadingTemp = [[NSMutableArray alloc] init];
    
   
    if(Listener !=NULL){
        
    if ([[UIDevice currentDevice] proximityState] == YES) {
        [ReadingTemp addObject:[NSNumber numberWithDouble:0]];
        
        NSLog(@"0");
    }
        
    else
    {
        [ReadingTemp addObject:[NSNumber numberWithDouble:1]];
        
    NSLog(@"1");
    }
    SensorReading *Reading = [[SensorReading alloc] initWithValues:TYPE_PROXIMITY :0:ReadingTemp];
    
    [Listener SensorReadingChanged:Reading];
    }
    if (fileInitialized) {
        
        if([[UIDevice currentDevice] proximityState] == YES){
            [self WriteToFile:@"0"];
            NSLog(@"0");
            
        }
        else [self WriteToFile:@"1"];
        
        NSLog(@"0");
    }

}

-(void)unregisterListener{
    Listener=NULL;
    [[UIDevice currentDevice] setProximityMonitoringEnabled:NO];
}



-(BOOL)get : (int)TimeInSeconds{
    
    //DelayInMilliseconds = DelayInMilliseconds/1000;
    [[UIDevice currentDevice] setProximityMonitoringEnabled:YES];
    
    [self InitializeFile:@"Proximity.txt"];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(SensorStateChange:) name:@"UIDeviceProximityStateChange"object:nil];
    
    
    //Scheduling the timer
    [self scheduleTimer:TimeInSeconds];
    
    return true;
}


-(id)init
{   self = [super init];
    SensorType=TYPE_PROXIMITY;
    return self;
    
}

-(void)deRegister
{
    [[UIDevice currentDevice] setProximityMonitoringEnabled:NO];
    [self closeFile];
}

@end
