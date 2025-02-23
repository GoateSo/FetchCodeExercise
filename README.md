# Fetch Rewards Coding Exercise - Software Engineering - Mobile

An Android app written in Kotlin with Jetpack Compose that restrieves data from the specified webpage and then filters, sorts, and displays it as instructed. 
The current version targets API level 35.

## Setup

This project can be set up by cloning this repository, opening in Android studio, and then running using the emulator. 

## Usage

Overall, this application sticks pretty closely to the given specification. After processing the request response, the items are displayed in a vertical list with 3 columns, one for each field. From the outset, all item fields from all lists are shown in list ID order followed by name order. The items for a particular list can be viewed by using the selector in the top right hand corner and selecting the desired list ID. 

This application also was made under an assumption that the data in the json file is mostly static, so no automatic polling or additional requests are sent to refresh the display in the event of a change. However, a refresh button is included as the left action button on the topbar, allowing the user to manually refresh data or retry a request in case of failure. 

## Design considerations

1. List type -- Overall, I felt the simple, spreadsheet-like tabular structure was the easiest in terms of readability, especially in portrait mode, but something like a card grid or card list might work, but sacrifices the compactness of a tabular structure

2. Grouping style -- I settled on a menu based selector for the method of displaying individual groups, but a stack of collapsible sections might also work well. However, out of a past negative experience with trying to keep track of collapsible sections in articles (and programming language documentation), i decided against it