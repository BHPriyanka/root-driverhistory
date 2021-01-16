Solution to track driving history of people

Input file: drive_details.txt
Input file location: Same directory as the code

Output file: DriverReport.txt
Output file location: Same directory as the code

As mentioned in the problem statement:
- Each input line in the file is either a Driver entry or a Trip entry
 Driver Dan  
 or
 Trip Dan 07:15 07:45 17.3
- 24 hour clock is used for the time
- Each trip entry has driver name, start and end times, and the distance covered in the trip

Approach:
- Two inner classes named Driver and Trip are defined
- Input file is read line by line
- Each line is then parsed to find if its a Driver related information or Trip related information
- For each new driver entry, a new hashmap entry is added
- For each trip entry, a Trip object is created and added to the list of trips associated with the Driver
- Once the parsing of the input file is done, do the following steps
- For each driver, 
     - initialize total distance and total time variables
     - Get the list of all the trips he/she is associated with
     - For each trip, 
        - compute the total time of the trip 
        - If the speed of each trip is less than 5mph or greater than 100mph, its not considered
        - Otherwise, increment the totaldistance and totaltime 
     - Compute total speed
     - Create a Driver object with total distance and the speed for each driver
     Add the Driver object to the result list
- Write the details to the file
- For each driver details in the result
  - Append a line to the output file
  
 
  
     