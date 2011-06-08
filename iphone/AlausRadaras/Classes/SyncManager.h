//
//  SyncManager.h
//  AlausRadaras
//
//  Created by Laurynas R on 5/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MTStatusBarOverlay.h"

// Manages App Synchronization
@interface SyncManager : NSObject <MTStatusBarOverlayDelegate> {
	NSMutableData *responseData;    
    NSOperationQueue *queue;
    BOOL newAPI;
    BOOL syncInProgressInd;
}

+ (SyncManager*) sharedManager;
+ (id)allocWithZone:(NSZone *)zone;
- (void) initializeManager;

// Custom methods
- (void) doSync;
- (void) syncSuccessful;

@end
