//
//  InterfaceController.swift
//  VassHayden_OCSR WatchKit Extension
//
//  Created by Hayden Vass on 5/19/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import WatchKit
import Foundation
import WatchConnectivity


class InterfaceController: WKInterfaceController, WCSessionDelegate {
    
    var swellCall: [SwellReport] = []
    var favsFromPhone: [String] = []
    var favoritedIds: [(String, Int)] = []
    fileprivate let session: WCSession? = WCSession.isSupported() ? WCSession.default : nil
    @available(watchOS 2.2, *)
    public func session(_ session: WCSession, activationDidCompleteWith activationState: WCSessionActivationState, error: Error?) {
    }
    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        if (WCSession.isSupported()) {
            let session = WCSession.default
            session.delegate = self
            session.activate()
        }
        
    }
    override func willActivate() {
        // This method is called when watch view controller is about to be visible to user
        super.willActivate()
    }
    override func didDeactivate() {
        // This method is called when watch view controller is no longer visible
        super.didDeactivate()
    }
    //updates favorites with new context sent from phone
    func session(_ session: WCSession, didReceiveApplicationContext applicationContext: [String : Any]) {
        //array of favorited spot ids
        let idArray = applicationContext["message"] as? [String] ?? []
        favoritedIds.removeAll()
        let urlBeginning = "http://api.spitcast.com/api/spot/forecast/"
        let urlEnd = "/"
        for id in idArray{
            print(urlBeginning + id + urlEnd)
            getSpotNames(urlString: urlBeginning + id + urlEnd)
        }
        
    }
    //Button presses / API calls
    @IBAction func surfPressed() {
        configureURL(urlString: "http://api.spitcast.com/api/county/swell/orange-county/")
    }
    
    @IBAction func windsPressed() {
        configureWindURL(urlString: "http://api.spitcast.com/api/county/wind/orange-county/")
    }
    
    @IBAction func tidePressed() {
        configureTideURL(urlString: "http://api.spitcast.com/api/county/tide/orange-county/")

    }
    @IBAction func tempPressed() {
        configureTempURL(urlString: "http://api.spitcast.com/api/county/water-temperature/orange-county/")
    }
    
    @IBAction func spotForecastPressed() {
        self.pushController(withName: "tableView", context: favoritedIds)
    }
    
    //get time methods to check against API
    static func getCurrentHour() -> String{
        let formatter = DateFormatter()
        formatter.dateFormat = "hha"
        var str = formatter.string(from: Date())
        if str.prefix(1) == "0"{
            str = String(str.dropFirst(1))
        }
        return str
    }
    
    func getCurrentMinutes() -> String{
        let formatter = DateFormatter()
        formatter.dateFormat = "mm"
        var str = formatter.string(from: Date())
        str = String(str.dropLast(1))
        return str
    }
    
}
