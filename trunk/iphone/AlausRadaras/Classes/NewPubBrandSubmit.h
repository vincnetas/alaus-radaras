//
//  NewPubBrandSubmit.h
//  AlausRadaras
//
//  Created by Laurynas R on 3/2/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Pub.h"

@interface NewPubBrandSubmit : UIViewController <UITextViewDelegate> {
	// Add UI
	UITextView *msgTextView;
	UIBarButtonItem *submitBtn;
    Pub *pub;
}

@property (nonatomic, retain) IBOutlet UITextView *msgTextView;
@property (nonatomic, retain) IBOutlet UIBarButtonItem *submitBtn;

@property (nonatomic, retain) Pub *pub;


- (void) sendNewPubBrandSubmit;
- (IBAction) actionSelected;//Deprecated - remove
- (IBAction) gotoPreviousView;
- (IBAction) sendNewPubBrandSubmit;

@end
