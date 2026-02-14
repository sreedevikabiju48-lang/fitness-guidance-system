# fitness-guidance-system

Group Members:
* Moltty Joseph
* Sreedevida Biju
  
A Java-based Fitness Guidance System with a Swing GUI, BMI calculation, and SQLite database integration using JDBC.
This application allows users to enter personal fitness details, calculate BMI, receive fitness guidance, and store records persistently.

## Features:
* User-friendly Swing GUI
* BMI Calculation based on height & weight
* Automatic Fitness Guidance
* Data storage using SQLite
* JDBC connectivity
* View stored user records
* Delete user records
* Auto-creates database & tables
## Technologies Used:
* Java (JDK 8+)
* Java Swing (GUI)
* SQLite
* JDBC (sqlite-jdbc driver)
* VS Code 
## How to Run (VS Code – Windows):
### 1- Install Requirements
* Java JDK (8 or above)
* VS Code
* SQLite JDBC Driver
### 2️- Compile the Program
* Open Command Prompt and navigate to the src folder:

cd C:\Users_molttyyy\Desktop\FitnessGUI\src
Compile:

javac -cp ".;..\lib\sqlite-jdbc-3.51.2.0.jar" FitnessGUI.java

### 3️- Run the Program

* java -cp ".;..\lib\sqlite-jdbc-3.51.2.0.jar" FitnessGUI

#### BMI Formula Used:
BMI = weight (kg) / (height × height)

## BMI Categories:
* < 18.5 → Underweight
* 18.5 – 24.9 → Normal
* 25 – 29.9 → Overweight
* ≥ 30 → Obese
## GUI Details:
### Input Fields:
* Name
* Height (meters)
* Weight (kg)
* Age
### Buttons:
* Calculate & Save
* View Users
### Output area shows:
* BMI value
* Fitness guidance
* Stored records
### Database:
* Database file: fitness.db
* Table: users
* Automatically created when the app runs
