//
//  AlausRadarasViewController.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BrandsViewController.h"
#import "MapViewController.h"

@interface AlausRadarasViewController : UIViewController {
	UILabel *pintCountLabel;
	NSInteger pintCount;
}

@property(nonatomic, retain) IBOutlet UILabel *pintCountLabel;

-(IBAction) clickPint:(id) sender;
-(IBAction) clickAlus:(id) sender;
-(IBAction) clickVietos:(id) sender;

@end

