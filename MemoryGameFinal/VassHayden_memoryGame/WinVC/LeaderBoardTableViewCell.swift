//
//  LeaderBoardTableViewCell.swift
//  VassHayden_memoryGame
//
//  Created by Hayden Vass on 2/22/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit

class LeaderBoardTableViewCell: UITableViewCell {

    // cell ui elements here
    
    @IBOutlet var playerDateLabel: UILabel!
    @IBOutlet var playerInitalsLabel: UILabel!
    @IBOutlet var playerTimeLabel: UILabel!
    @IBOutlet var playerClickLabel: UILabel!
    @IBOutlet var placeLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
