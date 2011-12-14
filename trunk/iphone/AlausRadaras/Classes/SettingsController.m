    //
//  SettingsController.m
//  AlausRadaras
//
//  Created by Laurynas R on 2/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SettingsController.h"
#import "LocationManager.h"
#import "SyncManager.h"

@implementation SettingsController

@synthesize visibilityControl, visibleDistanceSlider, distanceLabel;

- (void)dealloc {
	[visibilityControl release];
	[visibleDistanceSlider release];
	[distanceLabel release];
    [super dealloc];
}

- (void)viewDidLoad {
	/* Application setup */
    [super viewDidLoad];

    NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	
	[[LocationManager sharedManager]initializeManager];
	BOOL enableAllFeatures = TRUE;
	
	/* Determine if awesome features are enabled */
	NSString *currSysVer = [[UIDevice currentDevice] systemVersion];
	NSLog(@"iOS version: %@", currSysVer);
	NSString *reqSysVer = @"4.0";
	if ([currSysVer compare:reqSysVer options:NSNumericSearch] != NSOrderedAscending) {
		NSLog(@"Version >= 4.0 - Enabling awesome features");
		enableAllFeatures = TRUE;
	} else {
		NSLog(@"Version < 4.0 - Disabling awesome features");
		enableAllFeatures = FALSE;
	}	
	[standardUserDefaults setBool:enableAllFeatures forKey:@"EnableAllFeatures"];
	[[LocationManager sharedManager]setEnableAllFeatures:enableAllFeatures];
	
	UIColor *background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background.png"]];	
	
	self.view.backgroundColor = background;
//	[background release];
//    self.view.opaque = NO;
	
	UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hideSettings:)];
    tap.numberOfTapsRequired = 1;
    tap.numberOfTouchesRequired = 1;
    tap.delegate = self;          
    [self.view addGestureRecognizer:tap];
	
    /* ****** VISIBILITY SETTINGS ******* */
    
	if (standardUserDefaults) {
		BOOL visibilityControlled = [standardUserDefaults boolForKey:@"VisibilityControlled"];
        visibilityDistance = [standardUserDefaults integerForKey:@"VisibilityDistance"];

        // if first run
        if (visibilityDistance == 0) {
            NSLog(@"SYSTEM: Defaulting visibility to 10km");
            visibilityControlled = YES;
            visibilityDistance = 10;
        }
        
		[[LocationManager sharedManager]setVisibilityControlled:visibilityControlled];
		
		if (visibilityControlled) {
			visibilityControl.selectedSegmentIndex = 1;
		} else {
			visibilityControl.selectedSegmentIndex = 0;
		}
		visibleDistanceSlider.value = visibilityDistance;
	}
	
	[[LocationManager sharedManager]setDistance:visibilityDistance];
	[self visibilityControlIndexChanged];
	
    /* ****** END VISIBILITY SETTINGS ******* */

    
    [standardUserDefaults synchronize];

	NSLog(@"SettingsController viewDidLoad");
}

- (BOOL)gestureRecognizer:(UIGestureRecognizer *)gestureRecognizer shouldReceiveTouch:(UITouch *)touch{
    //change it to your condition    
    if ([touch.view isKindOfClass:[UISegmentedControl class]] || [touch.view isKindOfClass:[UISlider class]]) {      
		return NO;
    }
    return YES;
}

-(IBAction) hideSettings:(id) sender {
	[self dismissModalViewControllerAnimated:YES];
}

-(IBAction) visibilityControlIndexChanged {
	switch (self.visibilityControl.selectedSegmentIndex) {
		case 0:
			NSLog(@"Setting Infinity selected.");
			visibleDistanceSlider.enabled = NO;
			visibleDistanceSlider.value = 30;
			distanceLabel.text = @"∞";
			break;
		case 1:
			NSLog(@"Setting Radius selected.");

			//TODO: check if location services are enabled

			visibleDistanceSlider.enabled = YES;
			visibleDistanceSlider.value = visibilityDistance;
			distanceLabel.text =  [[NSString alloc]initWithFormat:@"%i", visibilityDistance];
			break;
			
		default:
			break;
	}
}


- (IBAction) visibilityDistanceChanged:(id)sender {
	UISlider *visibilitySlider = (UISlider *) sender;
	visibilityDistance = (int) visibilitySlider.value;
	distanceLabel.text =  [[NSString alloc]initWithFormat:@"%i", visibilityDistance];
}

//- (IBAction) gotoPreviousView {
//	NSLog(@"gotoPreviousView");
//	[self dismissModalViewControllerAnimated:YES];	
//}

- (void) viewDidDisappear:(BOOL)animated {
	NSLog(@"SettingsController viewDidDisappear");
	
	NSUserDefaults *standardUserDefaults = [NSUserDefaults standardUserDefaults];
	if (standardUserDefaults) {
		[standardUserDefaults setBool:visibleDistanceSlider.enabled forKey:@"VisibilityControlled"];
		[standardUserDefaults setInteger:visibilityDistance forKey:@"VisibilityDistance"];
		[standardUserDefaults synchronize];
	}
	[[LocationManager sharedManager]setDistance:visibilityDistance];
	[[LocationManager sharedManager]setVisibilityControlled:visibleDistanceSlider.enabled ];
}

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc. that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


@end