//
//  NativeAppDelegate.h
//  3rdParty
//
//  Created by Arjun Ahuja on 01/09/12.
//  Copyright (c) 2012 Arjun Ahuja. All rights reserved.
//

#import <UIKit/UIKit.h>

@class NativeViewController;

@interface NativeAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) NativeViewController *viewController;

@end
