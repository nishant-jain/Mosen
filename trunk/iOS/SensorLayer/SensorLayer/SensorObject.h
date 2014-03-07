//
//  SensorObject.h
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/06/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SensorReading.h"

@protocol SensorReadingHandler <NSObject>

@required
-(void)SensorReadingChanged : (SensorReading *) Reading; 

@end


@interface SensorObject : NSObject
{
       NSMutableData *data;
    
@private
    NSString *documentsDir;
    NSFileHandle *FileManager;
    

@protected
    int MinDelay,Version,SensorType;
    BOOL Writable;
    float Power, MaximumRange, Resolution;
	NSString *Name, *Vendor;
    NSObject<SensorReadingHandler> *Listener;

@public
    bool fileInitialized;

}
-(bool)isFileInitialized;
-(bool)isListening;
-(void) scheduleTimer: (int)TimeInSeconds;

-(BOOL) InitializeFile :(NSString*)Filename ;

-(BOOL) WriteToFile : (NSString*)toWrite;
-(void) closeFile;
-(void) scheduleTimer:(int)TimeInSeconds;
-(void) deRegister;

-(bool)registerListener: (NSObject<SensorReadingHandler> *) Handler: (int) DelayInMilliseconds;
-(void)unregisterListener ;
@end
