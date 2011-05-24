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
	
	UIColor *background;
	/* iOS3.1 Support */
	if (enableAllFeatures) {
		background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background.png"]];	
	} else {
		background = [[UIColor alloc] initWithPatternImage:[UIImage imageNamed:@"black-background-small.png"]];
		visibilityControl.segmentedControlStyle = UISegmentedControlStyleBar;	
		
		CGRect rect = CGRectMake(0, 0, 320, 260);
		UIButton *button=[[UIButton alloc] initWithFrame:rect];
		[button setFrame:rect];
		[button setBackgroundColor:[UIColor clearColor]];
		//	[button setBackgroundImage:buttonImageNormal forState:UIControlStateNormal];
		[button setContentMode:UIViewContentModeCenter];
		[button addTarget:self action:@selector(hideSettings:) forControlEvents:UIControlEventTouchUpInside];
		[self.view addSubview:button];
		[button release];
	}
	
	self.view.backgroundColor = background;
	[background release];
    self.view.opaque = NO;
	
	if (standardUserDefaults) {
		BOOL visibilityControlled = [standardUserDefaults boolForKey:@"VisibilityControlled"];
		visibilityDistance = [standardUserDefaults integerForKey:@"VisibilityDistance"];
		[[LocationManager sharedManager]setVisibilityControlled:visibilityControlled];
		
		if (visibilityControlled) {
			visibilityControl.selectedSegmentIndex = 1;
		} else {
			visibilityControl.selectedSegmentIndex = 0;
		}
		visibleDistanceSlider.value = visibilityDistance;
	}
	if (visibilityDistance == 0) {
		visibilityDistance = 16;
	}
	
	[[LocationManager sharedManager]setDistance:visibilityDistance];
	[self visibilityControlIndexChanged];
	
    [standardUserDefaults synchronize];

	NSLog(@"SettingsController viewDidLoad");
}

-(IBAction) hideSettings:(id) sender {
	[self.view removeFromSuperview];
	self.view.alpha = 0.0;
	[self viewDidDisappear:NO];
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

- (IBAction) gotoPreviousView {
	NSLog(@"gotoPreviousView");
	[self.parentViewController dismissModalViewControllerAnimated:YES];	
}

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