//
//  Native3rdParty.m
//  3rdParty
//
//  Created by Arjun Ahuja on 01/09/12.
//  Copyright (c) 2012 Arjun Ahuja. All rights reserved.
//

#import "Native3rdParty.h"

@implementation Native3rdParty{

}//

-(void) scheduleTimer:(int)TimeInSeconds
{
    [NSTimer scheduledTimerWithTimeInterval:TimeInSeconds target:self selector:@selector(stopAll) userInfo:nil repeats:NO];
}

- (void)locationManager:(CLLocationManager *)manager didUpdateToLocation:(CLLocation *)newLocation fromLocation:(CLLocation *)oldLocation
{
    NSMutableString *value3 = [[NSMutableString alloc]init];
    value3 = [NSMutableString stringWithFormat:@"location coordinates: %@",[newLocation description]];
    NSLog(@"%@",[newLocation description]);
    [FileManager3 seekToEndOfFile];
    NSData *dToWrite = [value3 dataUsingEncoding:NSUnicodeStringEncoding];
    [FileManager3 writeData:dToWrite];
}

-(void)getAll{
    int DelayInMilliseconds = 1000;
    int TimeInSeconds = 20;
    DelayInMilliseconds = DelayInMilliseconds/1000;
    motionmanager.accelerometerUpdateInterval = DelayInMilliseconds;
    motionmanager.gyroUpdateInterval = DelayInMilliseconds;
    file1 = @"Accelerometer.txt";
    file2 = @"Gyroscope.txt";
    file3 = @"GPS.txt";
    //file4 = @"Proximity.txt";
    NSFileManager *m,*n,*o,*p;
    p = [[NSFileManager alloc]init];
    o = [[NSFileManager alloc]init];
    n = [[NSFileManager alloc]init];
    m = [[NSFileManager alloc]init];
    [m createFileAtPath:[documentsDir stringByAppendingFormat:@"/%@",file1] contents:nil attributes:nil];
    [n createFileAtPath:[documentsDir stringByAppendingFormat:@"/%@",file2] contents:nil attributes:nil];
    [o createFileAtPath:[documentsDir stringByAppendingFormat:@"/%@",file3] contents:nil attributes:nil];
    [p createFileAtPath:[documentsDir stringByAppendingFormat:@"/%@",file4] contents:nil attributes:nil];
    FileManager1 = [NSFileHandle fileHandleForUpdatingAtPath:[[NSString alloc] initWithFormat:@"%@", [documentsDir stringByAppendingFormat:@"/%@",file1]]];
    FileManager2 = [NSFileHandle fileHandleForUpdatingAtPath:[[NSString alloc] initWithFormat:@"%@", [documentsDir stringByAppendingFormat:@"/%@",file2]]];
    FileManager3 = [NSFileHandle fileHandleForUpdatingAtPath:[[NSString alloc] initWithFormat:@"%@", [documentsDir stringByAppendingFormat:@"/%@",file3]]];
   // FileManager4 = [NSFileHandle fileHandleForUpdatingAtPath:[[NSString alloc] initWithFormat:@"%@", [documentsDir stringByAppendingFormat:@"/%@",file4]]];

    
    NSOperationQueue *a;
    a = [[NSOperationQueue alloc]init];
    
    NSOperationQueue *b;
    b = [[NSOperationQueue alloc]init];
    
    CMAccelerometerHandler getHandler1 = ^ (CMAccelerometerData *motion, NSError *error)
    {
        
        NSMutableString *value1 = [[NSMutableString alloc]init];
        value1 = [NSMutableString stringWithFormat:@"x= %g@, y= %g@, z= %g",motion.acceleration.x,motion.acceleration.y,motion.acceleration.z];
        [FileManager1 seekToEndOfFile];
        NSData *dToWrite = [value1 dataUsingEncoding:NSUnicodeStringEncoding];
        [FileManager1 writeData:dToWrite];

        NSLog(@"displaying acceleration : %@",value1);
    };
    
    
    CMGyroHandler getHandler2 = ^ (CMGyroData *rotation, NSError *error)
    {
        
        NSMutableString *value2 = [[NSMutableString alloc]init];
        value2 = [NSMutableString stringWithFormat:@"x= %g@, y= %g@, z= %g",rotation.rotationRate.x,rotation.rotationRate.y,rotation.rotationRate.z];
        [FileManager2 seekToEndOfFile];
        NSData *dToWrite = [value2 dataUsingEncoding:NSUnicodeStringEncoding];
        [FileManager2 writeData:dToWrite];
        
        NSLog(@"displaying rotation rate : %@",value2);
        
    };

   [manager startUpdatingLocation];
    

    [motionmanager startAccelerometerUpdatesToQueue:a withHandler:getHandler1];
    [motionmanager startGyroUpdatesToQueue:b withHandler:getHandler2];
    
    [self scheduleTimer:TimeInSeconds];



}


-(void) stopAll{
    [motionmanager stopGyroUpdates];
    [motionmanager stopAccelerometerUpdates];
    [manager stopUpdatingLocation];
    [FileManager1 closeFile];
    [FileManager2 closeFile];
    [FileManager3 closeFile];
 //   [FileManager4 closeFile];
    FileManager1 = NULL;
    FileManager2 = NULL;
    FileManager3 = NULL;
   // FileManager4 = NULL;
}



-(id)init{
    
    self = [super init];
    FileManager1 = NULL;
    FileManager2 = NULL;
    FileManager3 = NULL;
 //   FileManager4 = NULL;
    NSArray *documentPath = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    documentsDir = [documentPath objectAtIndex:0];

    manager = [[CLLocationManager alloc]init];
    manager.delegate = self;
    motionmanager = [[CMMotionManager alloc]init];
    return self;
}

@end
