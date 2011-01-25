//
//  AlausRadarasAppDelegate.h
//  AlausRadaras
//
//  Created by Laurynas R on 1/21/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class AlausRadarasViewController;

@interface AlausRadarasAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    AlausRadarasViewController *viewController;
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet AlausRadarasViewController *viewController;

@end

