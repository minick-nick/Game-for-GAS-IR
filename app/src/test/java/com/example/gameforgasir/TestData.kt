package com.example.gameforgasir

import com.example.gameforgasir.data.local.UsState

object TestData {
    val usStates = listOf(
        UsState(
            name = "ALABAMA",
            abbreviation = "AL",
            nickname = "YELLOWHAMMER STATE",
            licensePlate = 1
        ),
        UsState(
            name = "ALASKA",
            abbreviation = "AK",
            nickname = "THE LAST FRONTIER",
            licensePlate = 2
        ),
        UsState(
            name = "ARIZONA",
            abbreviation = "AZ",
            nickname = "GRAND CANYON STATE",
            licensePlate = 3
        ),
        UsState(
            name = "ARKANSAS",
            abbreviation = "AR",
            nickname = "NATURAL STATE",
            licensePlate = 4
        ),
        UsState(
            name = "CALIFORNIA",
            abbreviation = "CA",
            nickname = "GOLDEN STATE",
            licensePlate = 5
        ),
        UsState(
            name = "COLORADO",
            abbreviation = "CO",
            nickname = "CENTENNIAL STATE",
            licensePlate = 6
        ),
        UsState(
            name = "CONNECTICUT",
            abbreviation = "CT",
            nickname = "CONSTITUTION STATE",
            licensePlate = 7
        ),
        UsState(
            name = "DELAWARE",
            abbreviation = "DE",
            nickname = "FIRST STATE",
            licensePlate = 8
        ),
        UsState(
            name = "FLORIDA",
            abbreviation = "FL",
            nickname = "SUNSHINE STATE",
            licensePlate = 9
        ),
        UsState(
            name = "GEORGIA",
            abbreviation = "GA",
            nickname = "PEACH STATE",
            licensePlate = 10
        ),
        UsState(
            name = "HAWAII",
            abbreviation = "HI",
            nickname = "ALOHA STATE",
            licensePlate = 11
        ),
        UsState(
            name = "IDAHO",
            abbreviation = "ID",
            nickname = "GEM STATE",
            licensePlate = 12
        ),
        UsState(
            name = "ILLINOIS",
            abbreviation = "IL",
            nickname = "PRAIRIE STATE",
            licensePlate = 13
        ),
        UsState(
            name = "INDIANA",
            abbreviation = "IN",
            nickname = "HOOSIER STATE",
            licensePlate = 14
        ),
        UsState(
            name = "IOWA",
            abbreviation = "IA",
            nickname = "HAWKEYE STATE",
            licensePlate = 15
        ),
        UsState(
            name = "KANSAS",
            abbreviation = "KS",
            nickname = "SUNFLOWER STATE",
            licensePlate = 16
        ),
        UsState(
            name = "KENTUCKY",
            abbreviation = "KY",
            nickname = "BLUEGRASS STATE",
            licensePlate = 17
        ),
        UsState(
            name = "LOUISIANA",
            abbreviation = "LA",
            nickname = "PELICAN STATE",
            licensePlate = 18
        ),
        UsState(
            name = "MAINE",
            abbreviation = "ME",
            nickname = "PINE TREE STATE",
            licensePlate = 19
        ),
        UsState(
            name = "MARYLAND",
            abbreviation = "MD",
            nickname = "OLD LINE STATE",
            licensePlate = 20
        )
    )

    private val stateAbbreviationMap: Map<String, String> = usStates.associateBy({ it.name }, { it.abbreviation })

    fun getAbbreviation(state: String) = stateAbbreviationMap[state] ?: ""
}