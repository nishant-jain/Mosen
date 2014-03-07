//
//  GyroScope.m
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/07/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "GyroScope.h"
#import "SensorObject.h"
#import "SensorLayer.h"

@implementation GyroScope : SensorObject

-(bool)registerListener:(NSObject<SensorReadingHandler> *)Handler :(int)DelayInMilliseconds{
    
    
    DelayInMilliseconds = DelayInMilliseconds/1000;
    
    if(Listener!=NULL)
    {
        motionmanager.GyroUpdateInterval = DelayInMilliseconds;
        NSOperationQueue *a;
        a = [[NSOperationQueue alloc]init];
        
        Listener = Handler;
        
        CMGyroHandler GyroHandler = ^ (CMGyroData *motion, NSError *error)
        {
            NSMutableArray *ReadingTemp = [[NSMutableArray alloc] init];
            [ReadingTemp addObject:[NSNumber numberWithDouble:motion.rotationRate.x]];
            [ReadingTemp addObject:[NSNumber numberWithDouble:motion.rotationRate.y]];
            [ReadingTemp addObject:[NSNumber numberWithDouble:motion.rotationRate.z]];
            
            SensorReading *Reading = [[SensorReading alloc] initWithValues:TYPE_GYROSCOPE :motion.timestamp:ReadingTemp];
            
            [Listener SensorReadingChanged:Reading];
        };
        
        [motionmanager startGyroUpdatesToQueue:a withHandler:GyroHandler];
        return true;
    }
    else
    {
        return false;
    }
    
}



-(BOOL)get : (int)TimeInSeconds: (int) DelayInMilliseconds{
    
    
    DelayInMilliseconds = DelayInMilliseconds/1000;
    
    motionmanager.gyroUpdateInterval = DelayInMilliseconds;
    NSOperationQueue *a;
    a = [[NSOperationQueue alloc]init];
    
        [self InitializeFile:@"Gyroscope.txt"];
    
    
    CMGyroHandler getHandler = ^ (CMGyroData *rotation, NSError *error)
    {
        
        NSMutableString *value = [[NSMutableString alloc]init];
        value = [NSMutableString stringWithFormat:@"x= %g@, y= %g@, z= %g",rotation.rotationRate.x,rotation.rotationRate.y,rotation.rotationRate.z];
        [self WriteToFile:value];
        NSLog(@"displaying rotation rate : %@",value);
        
    };
    
    [motionmanager startGyroUpdatesToQueue:a withHandler:getHandler];
    
    //Scheduling the timer
    [self scheduleTimer:TimeInSeconds];
    
    return true;
}

-(void)unregisterListener{
    Listener = NULL;
    [motionmanager stopGyroUpdates];
}

-(id)initWithMM: (CMMotionManager *)motionmanagerpassed{
    self =[super init];
    motionmanager = motionmanagerpassed;
    SensorType=TYPE_GYROSCOPE;
    return self;
}


-(void)deRegister
{
    [motionmanager stopGyroUpdates];
    [self closeFile];
    
}
@end
