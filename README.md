# Fetch Rewards Coding Exercise - Software Engineering - Mobile

An Android app written in Kotlin with Jetpack Compose that restrieves data from the specified webpage and then filters, sorts, and displays it as instructed. 
The current version targets API level 35.

## Usage

Overall, this application sticks pretty closely to the given specification. After processing the request response, the items are displayed in a vertical list with 3 columns, one for each field. By default, all item fields from all lists are shown in list ID order followed by name order. The items for a particular list can be viewed by using the selector in the top right hand corner and selecting the desired list ID. 

This application also was made under an assumption that the data in the json file is mostly static, so no automatic polling or additional requests are sent to refresh the display in the event of a change. 


