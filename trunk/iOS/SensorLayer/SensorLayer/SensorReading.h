//
//  SensorReading.h
//  SensorLayer
//
//  Created by Arjun Ahuja on 03/08/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SensorReading : NSObject{

int SensorType;
long TimeStamp;
NSMutableArray *Readings;
    
}
-(id)initWithValues: (int)SensorTypeP : (long)TimeStampP : (NSMutableArray *) Values;
-(NSMutableArray *)getReadings;

@end
