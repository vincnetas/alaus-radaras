//
//  SettingsController.h
//  AlausRadaras
//
//  Created by Laurynas R on 2/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SettingsController : UIViewController {
	UISegmentedControl *visibilityControl;
	UISlider *visibleDistanceSlider; 
	UILabel *distanceLabel;
	int visibilityDistance;
}

@property (nonatomic,retain) IBOutlet UISegmentedControl *visibilityControl;
@property (nonatomic, retain) IBOutlet UISlider *visibleDistanceSlider;  
@property (nonatomic, retain) IBOutlet UILabel *distanceLabel;

- (IBAction) visibilityControlIndexChanged;
- (IBAction) visibilityDistanceChanged:(id)sender;  
- (IBAction) gotoPreviousView;

@end
