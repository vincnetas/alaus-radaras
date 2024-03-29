//
//  NewPubSubmit.h
//  AlausRadaras
//
//  Created by Laurynas R on 9/3/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "PubAnnotation.h"
#import "MBProgressHUD.h"

@interface NewPubSubmit : UIViewController <MKMapViewDelegate, UITextFieldDelegate>{
    IBOutlet MKMapView *newPubMap;
    IBOutlet UITextField *msgText;
    int msgTextVerticalOffset;

    PubAnnotation *newPubPin;
}


// Map 
@property(nonatomic, retain) IBOutlet MKMapView *newPubMap;
@property(nonatomic, retain) PubAnnotation *newPubPin;
@property(nonatomic, retain) IBOutlet UITextField *msgText;


- (IBAction) gotoPreviousView;
- (IBAction) sendNewPubSubmit;

@end
