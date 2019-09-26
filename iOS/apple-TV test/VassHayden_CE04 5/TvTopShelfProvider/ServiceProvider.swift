//
//  ServiceProvider.swift
//  TvTopShelfProvider
//
//  Created by Hayden Vass on 5/23/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
import TVServices

class ServiceProvider: NSObject, TVTopShelfProvider {
    
    override init() {
        super.init()
    }
    var topShelfStyle: TVTopShelfContentStyle {
        return .sectioned
    }
    
    var topShelfItems: [TVContentItem] {
        //loops through each category and adds two images from each to the top shelf
        let cID = TVContentIdentifier(identifier: "tsImages", container: nil)
        let countrySection = TVContentItem(contentIdentifier: cID)
        countrySection.title = "Country"
        countrySection.topShelfItems = getSections(img1: "johnny", img2: "waylon", sectionTitle: "Country")

        let bID = TVContentIdentifier(identifier: "tsImages", container: nil)
        let bluesSection = TVContentItem(contentIdentifier: bID)
        bluesSection.title = "Blues"
        bluesSection.topShelfItems = getSections(img1: "muddy", img2: "RL", sectionTitle: "Blues")

        let rID = TVContentIdentifier(identifier: "tsImages", container: nil)
        let rockSection = TVContentItem(contentIdentifier: rID)
        rockSection.title = "Rock"
        rockSection.topShelfItems = getSections(img1: "kay", img2: "ozzy", sectionTitle: "Rock")

        return [countrySection, bluesSection, rockSection]

        
    }
    
    //method to loop through and create the TVcontentItems
    func getSections(img1: String, img2: String, sectionTitle: String)-> [TVContentItem]{
        let id = TVContentIdentifier(identifier: "tsImages", container: nil)
        let section = TVContentItem(contentIdentifier: id)
        section.title = sectionTitle
        section.topShelfItems = []
        let imgResource = [img1, img2]
        section.topShelfItems = []
        for (i, _) in imgResource.enumerated(){
            let identifier = TVContentIdentifier(identifier: imgResource[i], container: nil)
            let imgContentItem = TVContentItem(contentIdentifier:identifier)
            let imgURL = Bundle.main.url(forResource: imgResource[i], withExtension: "jpg")
            imgContentItem.setImageURL(imgURL, forTraits: .userInterfaceStyleLight)
            section.topShelfItems?.append(imgContentItem)
        }
        return section.topShelfItems!
    }
    
}


