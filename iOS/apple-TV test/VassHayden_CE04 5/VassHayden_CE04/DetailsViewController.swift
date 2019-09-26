//
//  DetailsViewController.swift
//  VassHayden_CE04
//
//  Created by Hayden Vass on 5/23/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit

class DetailsViewController: UIViewController {

    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var htLabel: UILabel!
    @IBOutlet weak var catLabel: UILabel!
    
    @IBOutlet weak var aImageView: UIImageView!
    @IBOutlet weak var descriptionTV: UITextView!
    
    var receicvedArtist: Musician?
    // fills ui elements
    override func viewDidLoad() {
        super.viewDidLoad()
        nameLabel.text = receicvedArtist?.name
        htLabel.text = receicvedArtist?.homeTown
        catLabel.text = receicvedArtist?.category
        descriptionTV.text = receicvedArtist?.descrip
        aImageView.image = receicvedArtist?.img
    }
    //dismisses view on back button press
    @IBAction func backButtonPressed(_ sender: Any) {
        navigationController?.popViewController(animated: true)
    }
    
}
