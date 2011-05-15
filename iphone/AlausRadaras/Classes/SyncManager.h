//
//  SyncManager.h
//  AlausRadaras
//
//  Created by Laurynas R on 5/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

// Manages App Synchronization
@interface SyncManager : NSObject {
	NSMutableData *responseData;
	UIWindow *topWindow;
    
    NSOperationQueue *queue;
}

+ (SyncManager*) sharedManager;
+ (id)allocWithZone:(NSZone *)zone;
- (void) initializeManager;

// Custom methods
- (void) doSync;
- (void) showSyncInd;
- (void) removeSyncInd;


@end
