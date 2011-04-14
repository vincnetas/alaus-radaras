//
//  AlausRadarasAppDelegate.m
//  AlausRadaras
//
//  Created by Laurynas R on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "AlausRadarasAppDelegate.h"
#import "SQLiteManager.h"
#import "LocationManager.h"
#import "DataPublisher.h"

@implementation AlausRadarasAppDelegate

@synthesize window;
@synthesize viewController;
//@synthesize navigationController;

- (void)dealloc {
	//[db release];
//	[navigationController release];
    [viewController release];
    [window release];
    [super dealloc];
}


#pragma mark -
#pragma mark Application lifecycle

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {

//	Creates database using scripts in beer-radar-schema.sqlite
//	[[SQLiteManager sharedManager] createNewDatabase];
	
	[[SQLiteManager sharedManager] initializeDatabase];
	
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	if (standardUserDefaults) {
		double dbVersion = [standardUserDefaults doubleForKey:@"DatabaseVersion"];
		
		NSLog(@"Database Version: %.2f", dbVersion);
		double appVersion = [[[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleVersion"]doubleValue];
		NSLog(@"Application Version: %.2f", appVersion);
		if (appVersion != dbVersion) {
			NSLog(@"Updating Database");
			
			[[SQLiteManager sharedManager] recreateDatabase];
		
			[standardUserDefaults setDouble:appVersion forKey:@"DatabaseVersion"];
			[standardUserDefaults synchronize];
		}
		[[SQLiteManager sharedManager] copyDatabaseIfNotExists];
	}

    [[SQLiteManager sharedManager]refresh];	
	
	// Add the view controller's view to the window and display.
    [self.window addSubview:viewController.view];
	//[window addSubview:[navigationController view]];
    [self.window makeKeyAndVisible];
	//[navigationController setNavigationBarHidden:YES];
	
	[[DataPublisher sharedManager]initializeManager];
	 
    return YES;
}


#pragma mark -
#pragma mark Memory management

- (void)applicationDidReceiveMemoryWarning:(UIApplication *)application {
	/*
     Free up as much memory as possible by purging cached data objects that can be recreated (or reloaded from disk) later.
     */
	NSLog(@"AlausRadarasAppDelegate: Recieved a Memory Warning... Oooops..");
	[[SQLiteManager sharedManager]refresh];
}


#pragma mark -
#pragma mark Other methods



- (void)applicationWillResignActive:(UIApplication *)application {
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}


- (void)applicationDidEnterBackground:(UIApplication *)application {
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, called instead of applicationWillTerminate: when the user quits.
     */
}


- (void)applicationWillEnterForeground:(UIApplication *)application {
    /*
     Called as part of  transition from the background to the inactive state: here you can undo many of the changes made on entering the background.
     */
}


- (void)applicationDidBecomeActive:(UIApplication *)application {
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}


- (void)applicationWillTerminate:(UIApplication *)application {
    /*
     Called when the application is about to terminate.
     See also applicationDidEnterBackground:.
     */
}






@end

