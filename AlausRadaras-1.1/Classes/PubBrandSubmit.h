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
	UIButton *sendBtn;

	Brand *brand;
	NSString *pubId;
	
	NSString *status;
}
@property (nonatomic, retain) Brand *brand;
@property (nonatomic, retain) NSString *pubId;

@property (nonatomic, retain) IBOutlet UISegmentedControl *pubBrandStatusControl;
@property (nonatomic, retain) IBOutlet UILabel *brandLabel;
@property (nonatomic, retain) IBOutlet UIImageView *brandImage;
@property (nonatomic, retain) IBOutlet UIButton *sendBtn;

- (void) sendPubBrandSubmit;

- (IBAction) pubBrandStatusChanged;
- (IBAction) gotoPreviousView;


@end
