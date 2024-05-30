package com.example.gameforgasir.data.local

import com.example.gameforgasir.R
import com.example.gameforgasir.ui.IdentifyAbbreviationOfState.IdentifyAbbreviationOfStatesDestination
import com.example.gameforgasir.ui.IdentifyLicensePlateState.IdentifyLicensePlateStateDestination
import com.example.gameforgasir.ui.IdentifyStateNickname.IdentifyStateNicknameDestination

object LocalGameForGasIrData {
    val gameTypes = listOf(
        GameType(
            title = R.string.identify_abbreviation_of_state,
            icon = R.drawable.us_map_svgrepo_com,
            destinationScreen = IdentifyAbbreviationOfStatesDestination
        ),
        GameType(
            title = R.string.identify_state_nickname,
            icon = R.drawable.select_svgrepo_com,
            destinationScreen = IdentifyStateNicknameDestination
        ),
        GameType(
            title = R.string.identify_license_plate_state,
            icon = R.drawable.license_plate_svgrepo_com,
            destinationScreen = IdentifyLicensePlateStateDestination
        )
    )

    val usStates = listOf(
        UsState(
            name = "ALABAMA",
            abbreviation = "AL",
            nickname = "YELLOWHAMMER STATE",
            licensePlate = R.drawable.alabama_lp
        ),
        UsState(
            name = "ALASKA",
            abbreviation = "AK",
            nickname = "THE LAST FRONTIER",
            licensePlate = R.drawable.alaska_lp
        ),
        UsState(
            name = "ARIZONA",
            abbreviation = "AZ",
            nickname = "GRAND CANYON STATE",
            licensePlate = R.drawable.arizona_lp
        ),
        UsState(
            name = "ARKANSAS",
            abbreviation = "AR",
            nickname = "NATURAL STATE",
            licensePlate = R.drawable.arkansas_lp
        ),
        UsState(
            name = "CALIFORNIA",
            abbreviation = "CA",
            nickname = "GOLDEN STATE",
            licensePlate = R.drawable.california_lp
        ),
        UsState(
            name = "COLORADO",
            abbreviation = "CO",
            nickname = "CENTENNIAL STATE",
            licensePlate = R.drawable.colorado_lp
        ),
        UsState(
            name = "CONNECTICUT",
            abbreviation = "CT",
            nickname = "CONSTITUTION STATE",
            licensePlate = R.drawable.connecticut_lp
        ),
        UsState(
            name = "DELAWARE",
            abbreviation = "DE",
            nickname = "FIRST STATE",
            licensePlate = R.drawable.delaware_lp
        ),
        UsState(
            name = "FLORIDA",
            abbreviation = "FL",
            nickname = "SUNSHINE STATE",
            licensePlate = R.drawable.florida_lp
        ),
        UsState(
            name = "GEORGIA",
            abbreviation = "GA",
            nickname = "PEACH STATE",
            licensePlate = R.drawable.georgia_lp
        ),
        UsState(
            name = "HAWAII",
            abbreviation = "HI",
            nickname = "ALOHA STATE",
            licensePlate = R.drawable.hawaii_lp
        ),
        UsState(
            name = "IDAHO",
            abbreviation = "ID",
            nickname = "GEM STATE",
            licensePlate = R.drawable.idaho_lp
        ),
        UsState(
            name = "ILLINOIS",
            abbreviation = "IL",
            nickname = "PRAIRIE STATE",
            licensePlate = R.drawable.illinois_lp
        ),
        UsState(
            name = "INDIANA",
            abbreviation = "IN",
            nickname = "HOOSIER STATE",
            licensePlate = R.drawable.indiana_lp
        ),
        UsState(
            name = "IOWA",
            abbreviation = "IA",
            nickname = "HAWKEYE STATE",
            licensePlate = R.drawable.iowa_lp
        ),
        UsState(
            name = "KANSAS",
            abbreviation = "KS",
            nickname = "SUNFLOWER STATE",
            licensePlate = R.drawable.kansas_lp
        ),
        UsState(
            name = "KENTUCKY",
            abbreviation = "KY",
            nickname = "BLUEGRASS STATE",
            licensePlate = R.drawable.kentucky_lp
        ),
        UsState(
            name = "LOUISIANA",
            abbreviation = "LA",
            nickname = "PELICAN STATE",
            licensePlate = R.drawable.louisiana_lp
        ),
        UsState(
            name = "MAINE",
            abbreviation = "ME",
            nickname = "PINE TREE STATE",
            licensePlate = R.drawable.maine_lp
        ),
        UsState(
            name = "MARYLAND",
            abbreviation = "MD",
            nickname = "OLD LINE STATE",
            licensePlate = R.drawable.maryland_lp
        ),
        UsState(
            name = "MASSACHUSETTS",
            abbreviation = "MA",
            nickname = "BAY STATE",
            licensePlate = R.drawable.massachusetts_lp
        ),
        UsState(
            name = "MICHIGAN",
            abbreviation = "MI",
            nickname = "GREAT LAKES STATE",
            licensePlate = R.drawable.michigan_lp
        ),
        UsState(
            name = "MINNESOTA",
            abbreviation = "MN",
            nickname = "NORTH STAR STATE",
            licensePlate = R.drawable.minnesota_lp
        ),
        UsState(
            name = "MISSISSIPPI",
            abbreviation = "MS",
            nickname = "MAGNOLIA STATE",
            licensePlate = R.drawable.mississippi_lp
        ),
        UsState(
            name = "MISSOURI",
            abbreviation = "MO",
            nickname = "SHOW-ME STATE",
            licensePlate = R.drawable.missouri_lp
        ),
        UsState(
            name = "MONTANA",
            abbreviation = "MT",
            nickname = "TREASURE STATE",
            licensePlate = R.drawable.montana_lp
        ),
        UsState(
            name = "NEBRASKA",
            abbreviation = "NE",
            nickname = "CORNHUSKER STATE",
            licensePlate = R.drawable.nebraska_lp
        ),
        UsState(
            name = "NEVADA",
            abbreviation = "NV",
            nickname = "SILVER STATE",
            licensePlate = R.drawable.nevada_lp
        ),
        UsState(
            name = "NEW HAMPSHIRE",
            abbreviation = "NH",
            nickname = "GRANITE STATE",
            licensePlate = R.drawable.new_hampshire_lp
        ),
        UsState(
            name = "NEW JERSEY",
            abbreviation = "NJ",
            nickname = "GARDEN STATE",
            licensePlate = R.drawable.new_jersey_lp
        ),
        UsState(
            name = "NEW MEXICO",
            abbreviation = "NM",
            nickname = "LAND OF ENCHANTMENT",
            licensePlate = R.drawable.new_mexico_lp
        ),
        UsState(
            name = "NEW YORK",
            abbreviation = "NY",
            nickname = "EMPIRE STATE",
            licensePlate = R.drawable.new_york_lp
        ),
        UsState(
            name = "NORTH CAROLINA",
            abbreviation = "NC",
            nickname = "TAR HEEL STATE",
            licensePlate = R.drawable.north_carolina_lp
        ),
        UsState(
            name = "NORTH DAKOTA",
            abbreviation = "ND",
            nickname = "PEACE GARDEN STATE",
            licensePlate = R.drawable.north_dakota_lp
        ),
        UsState(
            name = "OHIO",
            abbreviation = "OH",
            nickname = "BUCKEYE STATE",
            licensePlate = R.drawable.ohio_lp
        ),
        UsState(
            name = "OKLAHOMA",
            abbreviation = "OK",
            nickname = "SOONER STATE",
            licensePlate = R.drawable.oklahoma_lp
        ),
        UsState(
            name = "OREGON",
            abbreviation = "OR",
            nickname = "BEAVER STATE",
            licensePlate = R.drawable.oregon_lp
        ),
        UsState(
            name = "PENNSYLVANIA",
            abbreviation = "PA",
            nickname = "KEYSTONE STATE",
            licensePlate = R.drawable.pennsylvania_lp
        ),
        UsState(
            name = "RHODE ISLAND",
            abbreviation = "RI",
            nickname = "OCEAN STATE",
            licensePlate = R.drawable.rhode_island_lp
        ),
        UsState(
            name = "SOUTH CAROLINA",
            abbreviation = "SC",
            nickname = "PALMETTO STATE",
            licensePlate = R.drawable.south_carolina_lp
        ),
        UsState(
            name = "SOUTH DAKOTA",
            abbreviation = "SD",
            nickname = "MOUNT RUSHMORE STATE",
            licensePlate = R.drawable.south_dakota_lp
        ),
        UsState(
            name = "TENNESSEE",
            abbreviation = "TN",
            nickname = "VOLUNTEER STATE",
            licensePlate = R.drawable.tennessee_lp
        ),
        UsState(
            name = "TEXAS",
            abbreviation = "TX",
            nickname = "LONE STAR STATE",
            licensePlate = R.drawable.texas_lp
        ),
        UsState(
            name = "UTAH",
            abbreviation = "UT",
            nickname = "BEEHIVE STATE",
            licensePlate = R.drawable.utah_lp
        ),
        UsState(
            name = "VERMONT",
            abbreviation = "VT",
            nickname = "GREEN MOUNTAIN STATE",
            licensePlate = R.drawable.vermont_lp
        ),
        UsState(
            name = "VIRGINIA",
            abbreviation = "VA",
            nickname = "OLD DOMINION",
            licensePlate = R.drawable.virginia_lp
        ),
        UsState(
            name = "WASHINGTON",
            abbreviation = "WA",
            nickname = "EVERGREEN STATE",
            licensePlate = R.drawable.washington_lp
        ),
        UsState(
            name = "WEST VIRGINIA",
            abbreviation = "WV",
            nickname = "MOUNTAIN STATE",
            licensePlate = R.drawable.west_virginia_lp
        ),
        UsState(
            name = "WISCONSIN",
            abbreviation = "WI",
            nickname = "BADGER STATE",
            licensePlate = R.drawable.wisconsin_lp
        ),
        UsState(
            name = "WYOMING",
            abbreviation = "WY",
            nickname = "EQUALITY STATE",
            licensePlate = R.drawable.wyoming_lp
        )
    )
}