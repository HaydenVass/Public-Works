//
//  makeObjs.swift
//  1702TVTest
//
//  Created by Hayden Vass on 5/23/19.
//  Copyright © 2019 FullSail. All rights reserved.
//

import Foundation
import UIKit
extension ViewController{
    
    //creats muscian data objects for project
    
    func makeArtist()->[[Musician]]{
        //categoreis
        let categoreis = ["Country","Blues","Rock"]
        //names
        var namesCountry = ["Johnny Cash","Waylon Jennings","Willie Nelson","Merle Haggard","Hank Williams Jr."]
        var namesBlues = ["BB King","Robert Johnson","RL Burnside","Muddy Waters","Ligtnin Hopkins"]
        var namesRock = ["Robert Plant","John Fogerty","John Kay","Eric Clapton", "Ozzy Osbourne"]
        //home towns
        var htCountry = ["Kingsland, Arkansas", "Littlefield, Texas", "Abbott, Texas", "Oildale, California", "Shreveport, Louisiana"]
        var htBlues = ["Itta Bena, Mississippi", "Hazlehurst, Mississippi", "Harmontown, Mississippi", "Issaquena County, Mississippi", "Houston, Texas"]
        var htRock = ["West Bromwich, Staffordshire", "Berkeley, California", "Tilsit, East Prussia", "Ripley, Surrey", "Aston, Birmingham"]
        //images
        var imagesCountry = [UIImage.init(named:"johnny"), UIImage.init(named: "waylon"), UIImage.init(named: "willie"), UIImage.init(named: "merele"), UIImage.init(named: "hank")]
        var imagesBlues = [UIImage.init(named: "bbking"),UIImage.init(named: "robert"), UIImage.init(named: "RL"), UIImage.init(named: "muddy"),UIImage.init(named: "lightnin")]
        var imagesRock = [UIImage.init(named: "plant"), UIImage.init(named: "fogerty"),UIImage.init(named: "kay"),UIImage.init(named: "clapton"),UIImage.init(named: "ozzy")]
        
        //descriptions
        var cDescriptions = [
            "Johnny Cash (born J.R. Cash; February 26, 1932 – September 12, 2003) was an American singer-songwriter, guitarist, actor, and author. He is one of the best-selling music artists of all time, having sold more than 90 million records worldwide. Although primarily remembered as a country music icon, his genre-spanning songs and sound embraced rock and roll, rockabilly, blues, folk, and gospel. This crossover appeal won Cash the rare honor of being inducted into the Country Music, Rock and Roll, and Gospel Music Halls of Fame.",
            "Waylon Arnold Jennings (June 15, 1937 – February 13, 2002) was an American singer, songwriter, and musician. In 1958, Buddy Holly arranged Jennings's first recording session, and hired him to play bass. Jennings gave up his seat on the ill-fated flight in 1959 that crashed and killed Holly, J. P. The Big Bopper Richardson and Ritchie Valens. During the 1970s, Jennings was instrumental in the inception of Outlaw country movement, and recorded country music's first platinum album, Wanted! The Outlaws with Willie Nelson, Tompall Glaser, and Jessi Colter.",
            "Willie Hugh Nelson (born April 29, 1933) is an American singer, songwriter, musician, actor, producer, author, poet, and activist. The critical success of the album Shotgun Willie (1973), combined with the critical and commercial success of Red Headed Stranger (1975) and Stardust (1978), made Nelson one of the most recognized artists in country music. He was one of the main figures of outlaw country, a subgenre of country music that developed in the late 1960s as a reaction to the conservative restrictions of the Nashville sound. Nelson has acted in over 30 films, co-authored several books, and has been involved in activism for the use of biofuels and the legalization of marijuana.",
            "Haggard was born in Oildale, California, during the Great Depression. His childhood was troubled after the death of his father, and he was incarcerated several times in his youth. After being released from San Quentin State Prison in 1960, he managed to turn his life around and launch a successful country music career, gaining popularity with his songs about the working class that occasionally contained themes contrary to the prevailing anti-Vietnam War sentiment of much popular music of the time. Between the 1960s and the 1980s, he had 38 number-one hits on the US country charts, several of which also made the Billboard all-genre singles chart. Haggard continued to release successful albums into the 2000s.",
            "Randall Hank Williams (born May 26, 1949), known professionally as Hank Williams Jr., or alternatively as “Bocephus,” is an American singer-songwriter and musician. His musical style is often considered a blend of Southern rock, blues, and traditional country. He is the son of country music singer Hank Williams and the father of Hank Williams III and Holly Williams.Williams began his career following in his famed father's footsteps, covering his father's songs and imitating his father's style. Williams' first television appearance was in a 1964 episode of ABC's The Jimmy Dean Show, in which at age fourteen he sang several songs associated with his father. Later that year, he was a guest star on ABC's Shindig!."]
        
        var bDescriptions = [
            "Riley B. King (September 16, 1925 – May 14, 2015), known professionally as B.B. King, was an American blues singer, electric guitarist, songwriter, and record producer. King introduced a sophisticated style of soloing based on fluid string bending and shimmering vibrato that influenced many later electric blues guitarists.",
            "Robert Leroy Johnson (May 8, 1911 – August 16, 1938) was an American blues singer, songwriter and musician. His landmark recordings in 1936 and 1937 display a combination of singing, guitar skills, and songwriting talent that has influenced later generations of musicians. Johnson's poorly documented life and death have given rise to much legend. The one most closely associated with his life is that he sold his soul to the devil at a local crossroads to achieve musical success. He is now recognized as a master of the blues, particularly as a progenitor of the Delta blues style.",
            "R. L. Burnside (November 23, 1926 – September 1, 2005) was an American blues singer, songwriter, and guitarist. He played music for much of his life but received little recognition before the early 1990s. In the latter half of that decade, Burnside recorded and toured with Jon Spencer, garnering crossover appeal and introducing his music to a new fan base in the punk and garage rock scenes.",
            "McKinley Morganfield (April 4, 1913  – April 30, 1983), known professionally as Muddy Waters, was an American blues singer-songwriter and musician who is often cited as the father of modern Chicago blues, and an important figure on the post-war blues scene.",
            "Samuel John Lightnin Hopkins (March 15, 1912 – January 30, 1982) was an American country blues singer, songwriter, guitarist and occasional pianist, from Centerville, Texas. Rolling Stone magazine ranked him number 71 on its list of the 100 greatest guitarists of all time. The musicologist Robert Mack McCormick opined that Hopkins is the embodiment of the jazz-and-poetry spirit, representing its ancient form in the single creator whose words and music are one act."
        ]
        
        var rDescriptions = [
            "Robert Anthony Plant CBE (born 20 August 1948) is an English singer, songwriter, and musician, best known as the lead singer and lyricist of the rock band Led Zeppelin. Plant is regarded as one of the greatest vocalists in the history of rock music.",
            "John Cameron Fogerty (born May 28, 1945) is an American musician, singer, and songwriter. Together with Doug Clifford, Stu Cook, and his brother Tom Fogerty, he founded the band Creedence Clearwater Revival, for which he was the lead singer, lead guitarist and principal songwriter. The group had nine top-ten singles and eight gold albums between 1968 and 1972, and was inducted into the Rock and Roll Hall of Fame in 1993.",
            "John Kay (born Joachim Fritz Krauledat, 12 April 1944) is a German-Canadian rock singer, songwriter, and guitarist known as the frontman of Steppenwolf.",
            "Eric Patrick Clapton, CBE (born 30 March 1945) is an English rock and blues guitarist, singer, and songwriter. He is the only three-time inductee to the Rock and Roll Hall of Fame: once as a solo artist and separately as a member of the Yardbirds and of Cream. Clapton has been referred to as one of the most important and influential guitarists of all time. Clapton ranked second in Rolling Stone magazine's list of the 100 Greatest Guitarists of All Time and fourth in Gibson's Top 50 Guitarists of All Time. He was also named number five in Time magazine's list of The 10 Best Electric Guitar Players in 2009",
            "John Michael Ozzy Osbourne (born 3 December 1948)[2] is an English singer, songwriter, actor and reality television star who rose to prominence during the 1970s as the lead vocalist of the heavy metal band Black Sabbath, during which he adopted the nickname The Prince of Darkness. Osbourne was fired from the band in 1979 due to alcohol and drug problems, but went on to have a successful solo career, releasing eleven studio albums, the first seven of which were all awarded multi-platinum certifications in the United States."
        ]
        
        var countryMusicians: [Musician] = []
        var bluesMusicians: [Musician] = []
        var rockMusicians: [Musician] = []

        //loops through each array to make ojbects
        for (index, _) in namesCountry.enumerated(){
            let cMA = Musician(_name: namesCountry[index], _description: cDescriptions[index], _img: imagesCountry[index]!,
                               _category: categoreis[0], _hT: htCountry[index])
            let bMA = Musician(_name: namesBlues[index], _description: bDescriptions[index], _img: imagesBlues[index]!,
                               _category: categoreis[1], _hT: htBlues[index])
            let rMA = Musician(_name: namesRock[index], _description: rDescriptions[index], _img: imagesRock[index]!,
                               _category: categoreis[2], _hT: htRock[index])
            
            countryMusicians.append(cMA)
            bluesMusicians.append(bMA)
            rockMusicians.append(rMA)
        }
        
        return [countryMusicians, bluesMusicians, rockMusicians]
    }

}
