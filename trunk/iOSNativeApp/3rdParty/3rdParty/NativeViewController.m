//
//  NativeViewController.m
//  3rdParty
//
//  Created by Arjun Ahuja on 01/09/12.
//  Copyright (c) 2012 Arjun Ahuja. All rights reserved.
//

#import "NativeViewController.h"
#import "Native3rdParty.h"

@interface NativeViewController ()

@end

@implementation NativeViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    Native3rdParty *new = [[Native3rdParty alloc]init];
    [new getAll];

    
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

@end
