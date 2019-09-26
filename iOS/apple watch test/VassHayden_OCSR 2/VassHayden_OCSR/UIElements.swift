//
//  UIElements.swift
//  VassHayden_OCSR
//
//  Created by Hayden Vass on 5/27/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import UIKit


extension ViewController{
    
    func configureUIElements(){
        let white: CGColor = UIColor(red:1.00, green:1.00, blue:1.00, alpha:1.0).cgColor
        detailsView.layer.cornerRadius = 10
        detailsView.layer.masksToBounds = true
        favoriteButton.layer.cornerRadius = 10
        favoriteButton.layer.borderColor = white
        favoriteButton.layer.borderWidth = 2
        dismissButton.layer.cornerRadius = 10
        dismissButton.layer.borderColor = white
        dismissButton.layer.borderWidth = 2

    }
}
