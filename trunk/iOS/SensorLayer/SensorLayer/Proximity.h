//
//  Proximity.h
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/07/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "SensorObject.h"
#import <UIKit/UIKit.h>

@interface Proximity : SensorObject{

}

-(BOOL)get : (int)TimeInSeconds;
-(void)deRegister;
-(void)unregisterListener;
-(bool)registerListener: (NSObject<SensorReadingHandler> *) Handler;

@end
