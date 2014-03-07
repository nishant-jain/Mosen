//
//  SensorObject.m
//  SensorLayer
//
//  Created by Arjun Ahuja on 20/06/12.
//  Copyright (c) 2012 arjunahuja@iiitd.com. All rights reserved.
//

#import "SensorObject.h"

@implementation SensorObject

-(void) scheduleTimer:(int)TimeInSeconds
{
    [NSTimer scheduledTimerWithTimeInterval:TimeInSeconds target:self selector:@selector(deRegister) userInfo:nil repeats:NO];
}
-(BOOL)InitializeFile:(NSString *)Filename
{
    NSFileManager *m = [[NSFileManager alloc]init];
    [m createFileAtPath:[documentsDir stringByAppendingFormat:@"/%@",Filename] contents:nil attributes:nil];
    
    FileManager = [NSFileHandle fileHandleForUpdatingAtPath:[[NSString alloc] initWithFormat:@"%@", [documentsDir stringByAppendingFormat:@"/%@",Filename]]];
    NSLog(@"The filepath is: ");
    NSLog(@"%@",[documentsDir stringByAppendingFormat:@"/%@",Filename]);
    
    if ([m isWritableFileAtPath:[documentsDir stringByAppendingFormat:@"/%@",Filename]]==YES)
    {
        NSLog(@"File is writable");
    }
    if(FileManager==NULL)
        return false;
    else
    {
        fileInitialized = true;
        return true;
    }
        
}

-(BOOL)WriteToFile:(NSString *)toWrite
{
    [FileManager seekToEndOfFile];
    NSData *dToWrite = [toWrite dataUsingEncoding:NSUnicodeStringEncoding];
    [FileManager writeData:dToWrite];
    return true;
}

-(void)closeFile
{
    [FileManager closeFile];
    FileManager = NULL;
    fileInitialized = false;
}

-(id)init
{   //self = [super init];
    fileInitialized = false;
    FileManager = NULL;
    Listener = NULL;
    NSArray *documentPath = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    
    documentsDir = [documentPath objectAtIndex:0];
    NSLog(@"The documents dir is: ");
    NSLog(@"/%@",documentsDir);
    
    return self;
}

-(bool)isFileInitialized{
    return fileInitialized;
    
}
-(bool)isListening{
    return Listener!=NULL;
    
}

@end



