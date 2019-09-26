//
//  ViewController.swift
//  VassHayden_OCSR
//
//  Created by Hayden Vass on 5/19/19.
//  Copyright © 2019 Hayden Vass. All rights reserved.
//

import UIKit
import MapKit
import WatchConnectivity

class ViewController: UIViewController, CLLocationManagerDelegate, MKMapViewDelegate{
    
    @IBOutlet weak var detailsView: UIView!
    @IBOutlet weak var dismissButton: UIButton!
    @IBOutlet weak var favoriteButton: UIButton!
    
    @IBOutlet weak var spotNameLabel: UILabel!
    @IBOutlet weak var shapeLabel: UILabel!
    @IBOutlet weak var sizeLabel: UILabel!
    @IBOutlet weak var swellLabel: UILabel!
    @IBOutlet weak var tideLabel: UILabel!
    @IBOutlet weak var windLabel: UILabel!
    
    @IBOutlet weak var popupConstraint: NSLayoutConstraint!
    @IBOutlet weak var mapView: MKMapView!
    
    var currentSelectedLocation: MKAnnotationView?
    
    var coreLocationManager = CLLocationManager();
    var locationManager: LocationManager!
    var userDefault : UserDefaults? = nil

    
    var allOCSpots: [BeachSpot] = []
    var allFavoriteSpotDetails: [SpotDetails] = []
    var favID: [String] = []

    override func viewDidLoad() {
        super.viewDidLoad()
        userDefault = UserDefaults.standard
      //sets up session
        if (WCSession.isSupported()) {
            let session = WCSession.default
            session.delegate = self
            session.activate()
        }
        
        //configures ui elements
        configureUIElements()
        //registers map
        mapView.register(MKMarkerAnnotationView.self, forAnnotationViewWithReuseIdentifier: MKMapViewDefaultAnnotationViewReuseIdentifier)
        coreLocationManager.delegate = self
        locationManager = LocationManager.sharedInstance
        configureSpotUrl(urlString: "http://api.spitcast.com/api/county/spots/orange-county/")
        let authorizationCode = CLLocationManager.authorizationStatus()
       //resetDefaults()

        //gets authorization to use maps
        if authorizationCode == CLAuthorizationStatus.notDetermined && coreLocationManager.responds(to: #selector(CLLocationManager.requestAlwaysAuthorization)) ||
            coreLocationManager.responds(to: Selector(("requestWhenInUseAuthroization"))){
            if (Bundle.main.object(forInfoDictionaryKey: "NSLocationAlwaysUsageDescription") != nil){
                coreLocationManager.requestAlwaysAuthorization();
            }else{
                print("no descriptiong provided")
            }
        }else{
          
        }
        getSavedDefaults()
    }
    
    // MapView functions
    func pointSpotPlots(){
        // loop through all spots returned from async task and create CL location cooridante objects from their lats / longs
        //turn them into point annotations and add them to the list
        var allSpotAnnotations: [MKPointAnnotation] = []
        for spot in allOCSpots{
            let annotation: MKPointAnnotation = spot
            let savedCoord = CLLocationCoordinate2D(latitude: spot.latitude ?? 0.0, longitude: spot.longitude ?? 0.0)
            annotation.coordinate = savedCoord
            annotation.title = spot.name
            annotation.subtitle = String(spot.spotID ?? 0)
            allSpotAnnotations.append(annotation)
        }
        
        mapView.showAnnotations(allSpotAnnotations, animated: true)
    }
    
    //annotation selection
    func mapView(_ mapView: MKMapView, didSelect view: MKAnnotationView) {
        //move details onto screen
        animateDetails(duration: 0.1, xAxisConstant: -450)
        animateDetails(duration: 0.3, xAxisConstant: 0)
        //gets beach ID to pull against API
        if let id = (view.annotation as? BeachSpot)?.spotID{
            let strId = String(id)
            configureForecast(urlString: "http://api.spitcast.com/api/spot/forecast/" + strId + "/")
        }
        currentSelectedLocation = view
    }
    
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        if let beachAnnotation = mapView.dequeueReusableAnnotationView(withIdentifier: MKMapViewDefaultAnnotationViewReuseIdentifier) as? MKMarkerAnnotationView{
            beachAnnotation.animatesWhenAdded = true
            beachAnnotation.tintColor = .blue
            beachAnnotation.titleVisibility = .adaptive
            beachAnnotation.canShowCallout = true
            return beachAnnotation
        }
        return nil
    }
    
    // adds beach to favorite
    @IBAction func favoritePressed(_ sender: Any) {
        let url = "http://api.spitcast.com/api/spot/forecast/" + (currentSelectedLocation?.annotation?.subtitle!)!
        + "/"
        configureFavSpotForecast(urlString: url)
        favID.append(((currentSelectedLocation?.annotation?.subtitle)!)!)
        userDefault?.set(favID, forKey: "favIDS")
        
    }
    
    //dismisses detail view
    @IBAction func dismissPopOver(_ sender: Any) {
        animateDetails(duration: 0.3, xAxisConstant: -450)
    }
    
    // animates details screen when selecting a beach pin
    func animateDetails(duration: Double, xAxisConstant: CGFloat){
        popupConstraint.constant = xAxisConstant
        UIView.animate(withDuration: duration) {
            self.view.layoutIfNeeded()
        }
    }
    
    //gets current hour to check agains the API
    func getCurrentHour() -> String{
        let formatter = DateFormatter()
        formatter.dateFormat = "hha"
        var str = formatter.string(from: Date())
        if str.prefix(1) == "0"{
            str = String(str.dropFirst(1))
        }
        return str
    }
    //takes in user defaults to get saved favorite beaches
    func getSavedDefaults(){
        let savedArray = userDefault?.stringArray(forKey: "favIDS") ?? [String]()
        favID = savedArray
        for id in savedArray{
            configureFavSpotForecast(urlString: "http://api.spitcast.com/api/spot/forecast/" + id + "/")
        }
    }
}

// watch sesson delegate - takes favorited beaches from phone and passes
// id to watch to be parsed for quick checking
extension ViewController: WCSessionDelegate{
    func session(_ session: WCSession, activationDidCompleteWith activationState: WCSessionActivationState, error: Error?) { }
    func sessionDidBecomeInactive(_ session: WCSession) {}
    func sessionDidDeactivate(_ session: WCSession) {}
    // sends watch IDs to watch. My original game plan was to contain the data in an object on the phone
    // and pass the custom object to the watch, but due to time constraints sending the ID and running another
    // api call from the phone was the solution i had to go with
    func sendToWatch() {
        let session = WCSession.default
        var spotIDArray : [String] = []
        for spot in allFavoriteSpotDetails{
            spotIDArray.append(spot.spotID ?? "na")
        }
        spotIDArray.removeDuplicates()
        if session.activationState == .activated {
            let appDictionary = ["message": spotIDArray]
            do {
                try session.updateApplicationContext(appDictionary)
                print("updating context from phone")
            } catch {
                print("error")
            }
        }
    }
    //functionality functions
    func resetDefaults() {
        let defaults = UserDefaults.standard
        let dictionary = defaults.dictionaryRepresentation()
        dictionary.keys.forEach { key in
            defaults.removeObject(forKey: key)
        }
    }
}
extension Array where Element: Hashable {
    func removingDuplicates() -> [Element] {
        var bubbleDictionary = [Element: Bool]()
        return filter {
            bubbleDictionary.updateValue(true, forKey: $0) == nil
        }
    }
    mutating func removeDuplicates() {
        self = self.removingDuplicates()
    }
}

