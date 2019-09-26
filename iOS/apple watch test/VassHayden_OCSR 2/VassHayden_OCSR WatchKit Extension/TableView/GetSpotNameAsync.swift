//
//  GetSpotNameAsync.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/28/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import Foundation
//takes in ids from phone and gets spot name to present in table view
extension InterfaceController{
    func getSpotNames(urlString: String){
        var spotID: (String?, Int?) = (nil, nil)
        let config = URLSessionConfiguration.default
        let session = URLSession(configuration: config)
        if let validURL = URL (string: urlString){
            print (validURL)
            let task = session.dataTask(with: validURL) { (data, response, error) in
                if error != nil {return}
                guard let response = response as? HTTPURLResponse,
                    response.statusCode == 200,
                    let data = data
                    else {return}
                do{
                    if let json = try JSONSerialization.jsonObject(with: data) as? [Any]
                    {
                        for data in json{
                            if let secondLevel = data as? [String: Any]{
                                let name = secondLevel["spot_name"] as? String
                                let id = secondLevel["spot_id"] as? Int
                                spotID = (name, id)
                            }
                        }
                    }
                }catch{
                    print ("Error: \(error.localizedDescription)")
                }
                DispatchQueue.main.async {
                    self.favoritedIds.append(spotID as! (String, Int))
                }
            }
            task.resume()
        }
    }
}

