//
//  Accelerometer.m
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/06/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "AccMeter.h"
#import "SensorObject.h"
#import "SensorLayer.h"

@implementation AccMeter : SensorObject



-(bool)registerListener: (NSObject<SensorReadingHandler> *) Handler: (int) DelayInMilliseconds
{
    if(Listener==NULL)
    {
        
        DelayInMilliseconds = DelayInMilliseconds/1000;
        motionmanager.accelerometerUpdateInterval = DelayInMilliseconds;
        NSOperationQueue *a;
        a = [[NSOperationQueue alloc]init];
        
        Listener = Handler;
        
        CMAccelerometerHandler AccHandler = ^ (CMAccelerometerData *motion, NSError *error)
        {
            NSMutableArray *ReadingTemp = [[NSMutableArray alloc] init];
            [ReadingTemp addObject:[NSNumber numberWithDouble:motion.acceleration.x]];
            [ReadingTemp addObject:[NSNumber numberWithDouble:motion.acceleration.y]];
            [ReadingTemp addObject:[NSNumber numberWithDouble:motion.acceleration.z]];
            
            SensorReading *Reading = [[SensorReading alloc] initWithValues:TYPE_ACCELEROMETER :motion.timestamp:ReadingTemp];
            
            [Listener SensorReadingChanged:Reading];
        };
        
        [motionmanager startAccelerometerUpdatesToQueue:a withHandler:AccHandler];
        return true;
    }
    else
    {
        return false;
    }
    
}
-(void)unregisterListener{
    Listener=NULL;
    [motionmanager stopAccelerometerUpdates];
}



-(BOOL)get : (int)TimeInSeconds: (int) DelayInMilliseconds{
    
    DelayInMilliseconds = DelayInMilliseconds/1000;
    motionmanager.accelerometerUpdateInterval = DelayInMilliseconds;
    NSOperationQueue *a;
    a = [[NSOperationQueue alloc]init];
    
    [self InitializeFile:@"Accelerometer.txt"];
    
    
    CMAccelerometerHandler getHandler = ^ (CMAccelerometerData *motion, NSError *error)
    {
        
        NSMutableString *value = [[NSMutableString alloc]init];
        value = [NSMutableString stringWithFormat:@"x= %g@, y= %g@, z= %g",motion.acceleration.x,motion.acceleration.y,motion.acceleration.z];
        [self WriteToFile:value];
        //NSLog(@"displaying acceleration : %@",value);
    };
    
    
    
    [motionmanager startAccelerometerUpdatesToQueue:a withHandler:getHandler];
    
    //Scheduling the timer
    [self scheduleTimer:TimeInSeconds];
    
    return true;
}


-(id)initWithMM: (CMMotionManager *)motionmanagerpassed
{   self = [super init];
    motionmanager = motionmanagerpassed;
    SensorType=TYPE_ACCELEROMETER;
    return self;
    
}

-(void)deRegister
{
    [motionmanager stopAccelerometerUpdates];
    [self closeFile];
}
@end
