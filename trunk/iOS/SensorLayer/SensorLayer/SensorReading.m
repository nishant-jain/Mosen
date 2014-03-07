//
//  SensorReading.m
//  SensorLayer
//
//  Created by Arjun Ahuja on 03/08/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "SensorReading.h"

@implementation SensorReading{
}
-(id)initWithValues: (int)SensorTypeP : (long)TimeStampP : (NSMutableArray *) Values{
    Readings = Values;
    SensorType = SensorTypeP;
    TimeStamp = TimeStampP;
    
    return self;
}
-(NSMutableArray *)getReadings{
    return Readings;
}
@end
