//
//  NewPubBrandSubmit.h
//  AlausRadaras
//
//  Created by Laurynas R on 3/2/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface NewPubBrandSubmit : UIViewController {
	// Add UI
	UITextView *msgTextView;
	UISegmentedControl *submitControl;	

	NSString *pubId;
}

@property (nonatomic, retain) IBOutlet UITextView *msgTextView;
@property (nonatomic, retain) IBOutlet UISegmentedControl *submitControl;

@property (nonatomic, retain) NSString *pubId;

- (void) sendNewPubBrandSubmit;
- (IBAction) actionSelected;

@end
