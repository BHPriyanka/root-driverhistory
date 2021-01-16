import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrivingHistory {

	private static Map<String, List<Trip>> drivers; // hashmap to store list of trips associated with each driver
	private static List<Driver> result; 	// Initialize data structures to store final driver details

	DrivingHistory() {
		drivers = new HashMap<>();
		result = new ArrayList<>();
	}

	/**
	 * readInputFile : method to read the input file using BufferedReader class
	 * @param file
	 */
	public boolean readInputFile(String file) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				populateDriverAndTrips(line);
				// read next line
				line = reader.readLine();

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public int getNumberOfDrivers() {
		return result.size();
	}

	/**
	 * populateDriverAndTrips using each line of input file
	 * @param line
	 */
	private void populateDriverAndTrips(String line) {
		if(line != null) {
			String[] arr = line.split(" ");

			// Check if the line begins with Driver or Trip
			// if Driver, create an entry in HasMap
			// if Trip, add the trip object to the corresponding driver
			if(arr[0].equals("Driver")) {

				if(!drivers.containsKey(arr[0])) {
					drivers.put(arr[1], new ArrayList<Trip>());
				}

			} else if(arr[0].equals("Trip")) {
				Trip trip = new Trip(arr[2], arr[3], arr[4]);

				if(!drivers.containsKey(arr[1])) {
					drivers.put(arr[1], new ArrayList<Trip>());
				}

				drivers.get(arr[1]).add(trip);
			}
		}

	}

	/**
	 * findTimeDiff: method to find the difference between the startTime and stopTime in milliseconds
	 * @param t : a trip object
	 * @return
	 */
	private long findTimeDiff(Trip t) {
		String startTime = t.getStartTime();
		String stopTime = t.getStopTime();

		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		Date date1, date2;
		long difference = 0l;

		try {
			date1 = format.parse(startTime);
			date2 = format.parse(stopTime);
			difference = date2.getTime() - date1.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return difference;
	}


	/**
	 * generateDriverDetails : method to iterate each entry from map,
	 * Compute total miles and total time taken by each driver
	 * Compute average speed 
	 * Create a Driver object with these details and add it to the resule list
	 */
	public void generateDriverDetails() {
		for(Map.Entry<String,List<Trip>> entry : drivers.entrySet()) {
			List<Trip> trips = entry.getValue();
			double totalMiles = 0;
			long totalSeconds = 0l;
			for(Trip t : trips) {
				long time = findTimeDiff(t)/1000;
				double dist = Double.parseDouble(t.getMiles());
				if((3600 * dist) / time > 5 && (3600 * dist) / time < 100) {
					totalSeconds += time;
					totalMiles += dist;
				}
			}

			// for totalSeconds seconds, distance covered is totalMiles
			// so for 1 hour=3600seconds, distance covered =  (3600 * totalMiles ) / totalSeconds
			double speed = (double) (3600 * totalMiles ) / totalSeconds;
			DecimalFormat df = new DecimalFormat("###");

			Driver driverDetails = new Driver(entry.getKey());
			driverDetails.setDistance((int)totalMiles);
			if(totalMiles != 0 || totalSeconds != 0) {
				driverDetails.setSpeed(df.format(speed));
			}
		}
	}


	/**
	 * writeToFile: method to write details of each driver to the output file
	 */
	private void writeToFile() {
		// Sort the driver details based on the total miles travelled in decreasing order
		Collections.sort(result, (a, b) -> {
			if(a.getDistance() > b.getDistance()) return -1;
			if(a.getDistance() < b.getDistance()) return 1;
			return 0;
		});

		try
		{
			String filename= "./DriverReport.txt";
			FileWriter fw = new FileWriter(filename,true); //the true will append the new data

			for(Driver d : result) {
				if(d.getSpeed().equals(""))
					fw.write(d.name + ": " + d.getDistance() + " miles");
				else 
					fw.write(d.name + ": " + d.getDistance() + " miles @ " + d.getSpeed() + " mph\n");
			}

			fw.close();
		}
		catch(IOException ioe)
		{
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public Driver returnDanDetails() {
		for(Driver d : result) {
			if(d.name.equals("Dan"))
				return d;
		}
		return null;
	}

	private void generateDrivingHistory(String file) {
		readInputFile(file);
		generateDriverDetails();
		writeToFile();
	}


	public static void main(String[] args) {
		DrivingHistory d = new DrivingHistory();
		d.generateDrivingHistory("drive_details.txt");
	}



	// inner class Trip to store trip related information
	class Trip {
		String startTime;
		String stopTime;
		String miles;

		Trip(String startTime, String stopTime, String miles) {
			this.startTime = startTime;
			this.stopTime = stopTime;
			this.miles = miles;
		}

		public String getStartTime() {
			return startTime;
		}

		public String getStopTime() {
			return stopTime;
		}

		public String getMiles() {
			return miles;
		}

	}

	// inner Driver class to store Driver related information for final report
	class Driver {
		String name;
		String speed;
		int distance;

		Driver(String n) {
			this.name = n;
			this.speed = "";
			this.distance = 0;
		}

		public void setSpeed(String speed) {
			this.speed = speed;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

		public String getSpeed() {
			return speed;
		}

		public int getDistance() {
			return distance;
		}

	}
}
