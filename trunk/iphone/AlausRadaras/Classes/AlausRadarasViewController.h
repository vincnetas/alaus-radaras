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
#import "BeerCounterController.h"
#import "LuckyController.h"
#import "SettingsController.h"

@interface AlausRadarasViewController : UIViewController {
	UILabel *pintCountLabel;
	int beerCount;
	
	IBOutlet BeerCounterController *beerCounterController;
	IBOutlet LuckyController *luckyController;
	IBOutlet SettingsController *settingsController;

}

@property(nonatomic, retain) IBOutlet UILabel *pintCountLabel;

-(IBAction) clickPint:(id) sender;
-(IBAction) clickBeers:(id) sender;
-(IBAction) clickPlaces:(id) sender;
-(IBAction) clickLucky:(id) sender;
-(IBAction) clickSettings:(id) sender;


@end

