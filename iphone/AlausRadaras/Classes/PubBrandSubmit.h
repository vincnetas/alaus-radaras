//
//  PubBrandSubmit.h
//  AlausRadaras
//
//  Created by Laurynas R on 2/27/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Brand.h";
#import "MBProgressHUD.h";
@interface PubBrandSubmit : UIViewController <MBProgressHUDDelegate> {
	// UI
	UILabel *brandLabel;
	UIImageView *brandImage;
	UISegmentedControl *pubBrandStatusControl;	

	Brand *brand;
	NSString *pubId;
	
	NSString *status;
	
	BOOL enableAllFeatures;
}
@property (nonatomic, retain) Brand *brand;
@property (nonatomic, retain) NSString *pubId;

@property (nonatomic, retain) IBOutlet UISegmentedControl *pubBrandStatusControl;
@property (nonatomic, retain) IBOutlet UILabel *brandLabel;
@property (nonatomic, retain) IBOutlet UIImageView *brandImage;

- (void) sendPubBrandSubmit;

- (IBAction) pubBrandStatusChanged;
- (IBAction) gotoPreviousView;


@end
