package com.himanshu.BusLook.ActivityClasses;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.BusLook.DataClasses.Bus;
import com.himanshu.BusLook.DataClasses.Bus_Journey;
import com.himanshu.BusLook.LoginAndSignUp.LoginActivity;
import com.himanshu.BusLook.R;

import java.util.ArrayList;


public class BusActivity extends AppCompatActivity{
    public static String[][] buses = { { "1", "Todi", "Harmara", "Nindar Mod", "Jodla", "Vki Road No. 14", "Vki Road No. 12", "Vki Road No. 9", "Vki Road No. 8", "Vki Road No. 5", "Vki Road No. 1", "Alka Cinema", "Vidyadhar Nagar Bus Depot", "Khetan Hospital", "Dahar Ke Balaji", "Bhawani Niketan", "Chomu Puliya", "Ambabari", "Panipech", "TB Hospital", "Doodh Mandi", "Pital Factory", "Chandpol", "ML Quarters", "Gpo", "Panch Batti", "Ajmeri Gate", "Sanganeri Gate" }, { "1A", "Vki Road No. 17", "Vki Road No. 14", "Vki Road No. 9", "Vki Road No. 8", "Vki Road No. 5", "Vki Road No. 1", "Alka Cinema", "Vidyadhar Nagar Bus Depot", "Khetan Hospital", "Dahar Ke Balaji", "Bhawani Niketan", "Chomu Puliya", "Ambabari", "Panipech", "TB Hospital", "Doodh Mandi", "Pital Factory", "Chandpol", "ML Quarters", "Gpo", "Panch Batti", "Ajmeri Gate", "Sanganeri Gate" }, { "3", "Dwarikapuri", "NRI Circle", "Pratap Nagar Sector-16", "Bhairon Mandir", "Pratap Nagar Sector-11", "Pratap Nagar Sector-10", "Pratap Nagar RSRTC Bus Stand", "Kumbha Marg", "Pratap Nagar Sector-8", "Haldigati Gate", "Pratap Nagar Sector-5", "Pratap Nagar Sector-3", "Gaushala", "Shiv Nagar", "Sanganer Thana", "datti vada", "Kalyan Nagar/Ram Mandir", "Taron Ki Koot/surya nagar", "City Plex", "Jawahar Circle", "Fortis Hospital", "Genpact", "Durgapura", "Mahaveer Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Choti Chopar", "Badi Chopar" }, { "3A", "Sanganer Town", "Sanganer Stadium", "Kundan Nagar", "Sanganer Thana", "datti vada", "Kalyan Nagar/Ram Mandir", "Taron Ki Koot/surya nagar", "City Plex", "Jawahar Circle", "Fortis Hospital", "Genpact", "Durgapura", "Mahaveer Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Choti Chopar" }, { "3B", "Pannadhai Circle", "Shyopur", "Shypur Road", "Gaushala", "Shiv Nagar", "Sanganer Thana", "datti vada", "Kalyan Nagar/Ram Mandir", "Taron Ki Koot/surya nagar", "City Plex", "Jawahar Circle", "Fortis Hospital", "Genpact", "Durgapura", "Mahaveer Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Choti Chopar", "Badi Chopar", "Chandi Ki Taksal", "Shubhash Chowk", "Joravar Singh Gate", "Govind Nagar", "Ramgarh Mod", "Shahpura Bag", "Jal Mahal", "Airforce Office", "Mavata", "Amer Fort", "Amer", "Amer Stadium", "Morta", "Narad Ka Bagh", "Kunda " }, { "6A", "Jawahar Circle", "Malviya Nagar Sector-10", "Malviya Nagar Sector-11", "Malviya Nagar Sector-15", "Malviya Nagar Sector-3", "Malviya Nagar Sector-2(Satkar)", "Calgary Hospital Mod", "Hari Marg", "Pradhan Marg", "Saras Diary", "Mnit", "Bhaskar", "Bajaj Nagar Enclave", "Gandhinagar Station", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Panch Batti", "Gpo", "Khasa Kothi", "Collectrate Circle", "Chinkara Canteen", "Panipech", "Ambabari", "Chomu Puliya", "Lata Cinema", "Jothwara Pankha", "Vishnu Marg", "Boring Road", "Jothwara Kanta", "Ram Nagar(Jothwara)", "Lal Mandir", "Khirni Phatak" }, { "7", "Panchayala Puliya", "Bajri Mandi", "Karani Palace", "Sundar Nagar", "New Transport Nagar", "Dharam Kanta", "Dharam kanta chauraha", "Mansarovar Metro Station", "Ganga Jamuna", "Gurjar Ki Thadi", "Mahesh Nagar", "Ridhi Sidhi", "Mahesh Nagar Mod", "Triveni Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "Trimurti Circle", "Moti Dungri Mandir Circle", "Rajapark", "Barafkhana", "Pagalkhana", "Transport Nagar Mod", "Transport Nagar" }, { "8A", "Jagatpura Phatak", "Jagatpura Railway Station", "Modal Town", "Dak Colony/Balaji Mod", "Haldia Garden", "Apex Circle", "Kv No.3", "Baiji Ki Kothi", "Rto Office", "Gandhi Circle", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Panch Batti", "GPO", "Sindhi Camp", "Khasa Kothi", "Jaipur Railway Junction", "Collectrate Circle", "Chinkara Canteen", "Panipech", "Ambabari", "Chomu Puliya" }, { "8B", "Alka Cinema", "Parsuram Circle", "Vidhyadhar Nagar Thana", "Mandir Mod", "Vidhyadhar Nagar", "Ram Nagar(vidhyadhar nagar)", "Hari Nagar", "Kawatiya Circle", "Subhas colony", "Shashtri Nagar Circle", "Pital Factory", "Pareek College", "Mayank Cinema", "Chandpol", "ML Quarters", "Gpo", "Government Hostel", "Shalimar", "Hathroi", "Civil Lines Chauraha", "Civil Lines Metro Station", "Majdoor Nagar", "Esi Hospital", "Sodala", "Shayam Nagar", "Sushil Pura", "Purani Chungi", "Kings Road Nirman Nagar", "Ganga Jamuna", "Ganga Jamuna Petrol Pump", "Kaveri Path", "Swarn Path", "Varun Path", "Kiran Path", "Rajat Path", "Hira Path", "Akbar Vt Road", "Aravali Marg", "Kv No. 5", "Market Road", "Patel Marg", "Meera Marg", "Vijay Path", "Thadi Market", "Agarwal Farm", "Suresh Nagar Mod", "Ricco", "Ricco Chauraha", "Sanganer Petrol Pump", "Sanganer Stadium", "Kundan Nagar", "Sanganer Thana", "datti vada", "Kalyan Nagar/Ram Mandir", "Taron Ki Koot/surya nagar", "City Plex", "Jawahar Circle", "Gold Sukh", "North West Railway Office", "Jagatpura Phatak" }, { "9", "Agarwal Farm", "Thadi Market", "Vijay Path", "Meera Marg", "Patel Marg", "Market Road", "Kv No. 5", "Aravali Marg", "Akbar Vt Road", "Hira Path", "Rajat Path", "Kiran Path", "Varun Path", "Swarn Path", "Kaveri Path", "Ganga Jamuna Petrol Pump", "Ganga Jamuna", "Gurjar Ki Thadi", "Mahesh Nagar", "Ridhi Sidhi", "Mahesh Nagar Mod", "Triveni Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Panch Batti", "GPO", "Sindhi Camp", "Khasa Kothi”,”Jaipur Railway Junction", "Collectrate Circle", "Chinkara Canteen", "Panipech", "Ambabari", "Chomu Puliya", "Lata Cinema", "Jothwara Pankha", "Vishnu Marg", "Boring Road", "Jothwara Kanta", "Joshi Marg", "Rawan Gate", "Govindpura" }, { "9A", "Agarwal Farm", "Thadi Market", "Vijay Path", "Patel Marg Crossing", "Maharani Farm", "Durgapura", "Mahaveer Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Sanganeri Gate", "Badi Chopar", "Tripoliya Bajar", "Choti Chopar", "Chandpol", "Mayank Cinema", "Pareek College", "Pital Factory", "Shashtri Nagar Circle", "Subhas colony", "Kawatiya Circle", "Hari Nagar", "Ram Nagar(vidhyadhar nagar)", "Vidhyadhar Nagar", "Mandir Mod", "Vidhyadhar Nagar Thana", "Parsuram Circle", "Alka Cinema", "Vki Road No. 1", "Murlipura Thana", "Kedia Palace", "Dadi Ka Phatak" }, { "9B", "Mahatma Gandhi Hospital", "BOSCH Limited", "Goner Railway Phatak", "Chatrala Circle", "Genpact Sitapura", "Sitapura Circle", "India Gate", "Pratap Nagar RSRTC Bus Stand", "Kumbha Marg", "Pratap Nagar Sector-8", "Haldigati Gate", "Pratap Nagar Sector-5", "Pratap Nagar Sector-3", "Gaushala", "Shiv Nagar", "Sanganer Thana", "Kundan Nagar", "Sanganer Stadium", "Sanganer Petrol Pump", "Ricco Chauraha", "Ricco", "Suresh Nagar Mod", "Agarwal Farm", "Thadi Market", "Vijay Path", "Meera Marg", "Patel Marg", "Market Road", "Kv No. 5", "Aravali Marg", "Akbar Vt Road", "Hira Path", "Rajat Path", "Kiran Path", "Varun Path", "Swarn Path", "Kaveri Path", "Ganga Jamuna Petrol Pump", "Ganga Jamuna", "Mansarovar Metro Station", "Dharam kanta chauraha", "Dharam Kanta" }, { "10", "Niwaru", "34 No. Bus Stand", "Dayal Nursing Home", "Vaidhji Ka Chauraha", "Ashok Marg", "Niwaru Marg", "Netaji Chowki", "Laxmi Nagar", "Ramneshpuri", "Shalimar Factory", "Shivaji Nagar chauraha", "Jothwara Kanta", "Boring Road", "Jothwara Pankha", "Lata Cinema", "Chomu Puliya", "Ambabari", "Panipech", "TB Hospital", "Doodh Mandi", "Pital Factory", "Power House", "Chandpol", "Mayank Cinema", "Sindhi Camp", "Polo Victory", "Central Railway Hospital", "Jaipur Club", "Hasanpura", "Esi No.4 Dispensary", "Hatwada", "Esi Hospital", "Sodala Metro", "Ram Nagar(Sodala)", "Nandpuri", "Ram Mandir", "Sahkari Bhawan / 22 Godam", "High Court Circle", "Rambagh Circle", "Jda Circle", "Lbs College Mod", "Panchwati Circle", "Pulia No 1", "Behlad", "Jawahar Nagar", "Satsai College", "Transport Nagar", "Galta Gate" }, { "10A", "Jaipur Railway Junction", "Polo Victory", "Sindhi Camp", "Mayank Cinema", "Chandpol", "Choti Chopar", "Tripoliya Bajar", "Badi Chopar", "Ramganj Chopar", "Surajpole", "Galta Gate", "Lal Masjid Churaha", "Khole Ke Hanuman Ji" }, { "11", "Goner", "Vidhani", "Mahatma Gandhi Hospital", "BOSCH Limited", "Goner Mod", "12 Mill", "India Gate", "Pratap Nagar RSRTC Bus Stand", "Kumbha Marg", "Pratap Nagar Sector-8", "Haldigati Gate", "Pratap Nagar Sector-5", "Pratap Nagar Sector-3", "Gaushala", "Shiv Nagar", "Sanganer Thana", "datti vada", "Kalyan Nagar/Ram Mandir", "Taron Ki Koot/surya nagar", "City Plex", "Jawahar Circle", "Fortis Hospital", "Genpact", "Durgapura", "Mahaveer Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Choti Chopar", "Chandpol", "Mayank Cinema", "Sindhi Camp", "Polo Victory", "Central Railway Hospital", "Jaipur Club", "Hasanpura", "Kv No.4", "Khatipura Mod", "Panchawala ", "Meenawala", "Siwar" }, { "12", "Jagatpura Phatak", "CBI Phatak", "New Rto Office", "Kho-Nagorian", "Rahim Nagar", "Karim Nagar", "Madina Nagar", "Khaniya", "Chulgiri", "Transport Nagar", "Ghat Gate", "Sanganeri Gate", "Ram Niwas Garden Parking", "Ajmeri Gate", "Maharani College", "SMS Hospital", "Narayan Singh Circle", "Rambagh Circle", "Lal Kothi", "Gandhinagar Mod", "Gandhi Circle", "Rto Office", "Baiji Ki Kothi", "Kv No.3", "Apex Circle", "Haldia Garden", "Dak Colony/Balaji Mod", "Model Town", "Jagatpura Railway Station" }, { "12", "Jagatpura Phatak", "Jagatpura Railway Station", "Model Town", "Dak Colony/Balaji Mod", "Haldia Garden", "Apex Circle", "Kv No.3", "Baiji Ki Kothi", "Rto Office", "Gandhi Circle", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Ram Niwas Garden Parking", "Sanganeri Gate", "Ghat Gate", "Transport Nagar", "Chulgiri", "Khaniya", "Madina Nagar", "Karim Nagar", "Rahim Nagar", "Kho-Nagorian", "New Rto Office", "CBI Phatak" }, { "AC1", "Sanganer Town", "Sanganer Stadium", "Kundan Nagar", "Sanganer Thana", "datti vada", "Kalyan Nagar/Ram Mandir", "Taron Ki Koot/surya nagar", "City Plex", "Jawahar Circle", "Fortis Hospital", "Genpact", "Durgapura", "Mahaveer Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Sanganeri Gate", "Badi Chopar", "Chandi Ki Taksal", "Shubhash Chowk", "Joravar Singh Gate", "Govind Nagar", "Ramgarh Mod", "Shahpura Bag", "Jal Mahal", "Airforce Office", "Mavata", "Amer Fort", "Amer", "Amer Stadium", "Morta", "Narad Ka Bagh", "Kunda ", "Check Post Amer", "Kukas" }, { "AC5", "Agarwal Farm", "Thadi Market", "Vijay Path", "Meera Marg", "Patel Marg", "Market Road", "Kv No. 5", "Aravali Marg", "Akbar Vt Road", "Hira Path", "Rajat Path", "Kiran Path", "Varun Path", "Swarn Path", "Kaveri Path", "Ganga Jamuna Petrol Pump", "Ganga Jamuna", "Gurjar Ki Thadi", "Mahesh Nagar", "Ridhi Sidhi", "Mahesh Nagar Mod", "Triveni Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Sanganeri Gate", "Badi Chopar", "Chandi Ki Taksal", "Shubhash Chowk", "Joravar Singh Gate", "Govind Nagar", "Ramgarh Mod", "Shahpura Bag", "Jal Mahal", "Airforce Office", "Mavata", "Amer Fort", "Amer" }, { "AC2", "Mahatma Gandhi Hospital", "BOSCH Limited", "Goner Railway Phatak", "Chatrala Circle", "Genpact Sitapura", "Sitapura Circle", "India Gate", "Pratap Nagar RSRTC Bus Stand", "Kumbha Marg", "Pratap Nagar Sector-8", "Haldigati Gate", "Pratap Nagar Sector-5", "Pratap Nagar Sector-3", "Gaushala", "Shiv Nagar", "Sanganer Thana", "datti vada", "Kalyan Nagar/Ram Mandir", "Taron Ki Koot/surya nagar", "City Plex", "Jawahar Circle", "Fortis Hospital", "Genpact", "Durgapura", "Mahaveer Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Sanganeri Gate", "Badi Chopar", "Tripoliya Bajar", "Choti Chopar", "Chandpol", "Mayank Cinema", "Pareek College", "Pital Factory", "Doodh Mandi", "TB Hospital", "Panipech", "Ambabari", "Chomu Puliya", "Lata Cinema", "Jothwara Pankha", "Vishnu Marg", "Boring Road", "Jothwara Kanta", "Joshi Marg" }, { "14", "Jothwara Kanta", "Boring Road", "Jothwara Pankha", "Lata Circle", "Khatipura Mod", "Chand Bihari Nagar", "Jasvant Nagar", "Khatipura", "Khatipura Mod", "Parivahan Nagar Khatipura", "Khatipura Road", "Kings Road Nirman Nagar", "Gandhi Path", "Gandhi Path Mod", "Purani Chungi", "Sushil Pura", "Shayam Nagar", "Sodala", "Sodala Metro", "Ram Nagar(Sodala)", "Nandpuri", "Ram Mandir", "Sahkari Bhawan / 22 Godam", "High Court Circle", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Ram Niwas Garden Parking", "Sanganeri Gate", "Ghat Gate", "Transport Nagar", "Khaniya", "Prem Nagar Pulliya", "Balti Factory", "Paldimina", "Bawadi", "Purani Chungi(Bawadi)", "Hanuman Ji Mandir", "Babrana", "Kanota", "Dayaram Pura", "Vinoda Mod", "Ricco", "Bassi Mod", "Bassi" }, { "15", "Chomu", "Birla Hospital", "Jaitpura", "Rampura", "Motu Ka Was", "Rajawas", "Todi", "Harmara", "Nindar Mod", "Jodla", "Vki Road No. 14", "Vki Road No. 12", "Vki Road No. 9", "Vki Road No. 8", "Vki Road No. 5", "Vki Road No. 1", "Alka Cinema", "Vidyadhar Nagar Bus Depot", "Khetan Hospital", "Dahar Ke Balaji", "Bhawani Niketan", "Chomu Puliya", "Ambabari", "Panipech", "TB Hospital", "Doodh Mandi", "Pital Factory", "Chandpol" }, { "16", "Chaksu", "Shitala", "Yarlipura", "Shivdaspura", "Nagaliya", "Bilwa", "Goner Mod", "12 Mill", "India Gate", "Pratap Nagar RSRTC Bus Stand", "Kumbha Marg", "Pratap Nagar Sector-8", "Haldigati Gate", "Pratap Nagar Sector-5", "Pratap Nagar Sector-3", "Gaushala", "Shiv Nagar", "Sanganer Thana", "datti vada", "Kalyan Nagar/Ram Mandir", "Taron Ki Koot/surya nagar", "City Plex", "Jawahar Circle", "Fortis Hospital", "Genpact", "Durgapura", "Mahaveer Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate" }, { "26", "Bagru", "Dhayami Balaji", "Dhayami Kala Mod", "Debliya", "Thikaria Mod", "Bade Ke Balaji", "Ramachandra Pura", "Mahapura", "Bhankrota", "Keshupura", "Chaudhary Nagar", "Kamla Nehru Nagar", "Dhawas Girdharipura Mode", "Power House(hirapura)", "Hirapura Bypass", "Tagore Nagar", "DCM", "Kings Road Nirman Nagar", "Purani Chungi", "Sushil Pura", "Shayam Nagar", "Sodala", "Esi No.4 Dispensary", "Majdoor Nagar", "Civil Lines Metro Station", "Civil Lines Chauraha", "Ajmer Pulia", "Hathroi", "Shalimar", "Government Hostel", "Gpo", "ML Quarters", "Chandpol" }, { "27A", "Vatika", "Bhatta Wala", "Kalbada", "Asha Wala", "12 Mill", "India Gate", "Pratap Nagar RSRTC Bus Stand", "Kumbha Marg", "Pratap Nagar Sector-8", "Haldigati Gate", "Pratap Nagar Sector-5", "Pratap Nagar Sector-3", "Gaushala", "Shiv Nagar", "Sanganer Thana", "datti vada", "Kalyan Nagar/Ram Mandir", "Taron Ki Koot/surya nagar", "City Plex", "Jawahar Circle", "Fortis Hospital", "Genpact", "Durgapura", "Mahaveer Nagar", "Mahaveer Nagar Mod", "Gopalpura Mod", "Sayeed Ka Gatta", "Tonk Phatak", "Laxmi Mandir", "Nehru Place", "Gandhinagar Mod", "Lal Kothi", "Rambagh Circle", "Narayan Singh Circle", "SMS Hospital", "Maharani College", "Ajmeri Gate", "Ram Niwas Garden Parking", "Sanganeri Gate", "Ghat Gate", "Transport Nagar", "Khaniya", "Muchh Ki Pipli", "Loniyawas", "Datli", "Siroli", "Goner" }};
    public String[] bus;
    private TextView journeyBusNumber, journeyEngineNumber, journeyChassisNumber;
    private EditText driverName, conductorName, fromJourney, toJourney, journeyMobile;
    private Spinner spinner;
    String[] buser1;
    ArrayList<String> buser = new ArrayList<>();
    private String bb, fromJourney1, toJourney1, driverName1, conductorName1, mobile1, local_Bus_Number;
    DatabaseReference mDatabaseReference;
    ArrayList<String> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        Toolbar toolbar = findViewById(R.id.busToolbar);
        setSupportActionBar(toolbar);
        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        bb = bundle.getString("KEY3");
        setTitle("Plan Journey");
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://buslook-5966f.firebaseio.com/BusList/"+ bb);
        journeyBusNumber = findViewById(R.id.journeyBusNumber);
        journeyEngineNumber = findViewById(R.id.journeyEngineNumber);
        journeyChassisNumber = findViewById(R.id.journeyChassisNumber);
        spinner = findViewById(R.id.busNumberSpinner);
        driverName = findViewById(R.id.driverName);
        conductorName = findViewById(R.id.conductorName);
        fromJourney = findViewById(R.id.fromJourney);
        toJourney = findViewById(R.id.toJourney);
        journeyMobile = findViewById(R.id.journeyMobile);
        Button start = findViewById(R.id.journeyButton);
        data.clear();       data.add("Choose Bus Number");
        for(int i =0 ; i<buses.length; i++){
                data.add(buses[i][0]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(BusActivity.this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                local_Bus_Number = adapterView.getItemAtPosition(i).toString();
                if(!local_Bus_Number.equals("Choose Bus Number")){
                    for(int k = 0; k< buses.length; k++){
                        if(buses[k][0].equals(local_Bus_Number)){
                            fromJourney.setText(buses[k][1]);
                            toJourney.setText(buses[k][buses[k].length-1]);
                            for(int y = 0; y < buses[k].length; y++){
                                buser.add(buses[k][y]);
                            }
                        }
                    }
                    buser1 = new String[buser.size()];
                    buser1 = buser.toArray(buser1);
                }
                else{
                    fromJourney.setText("");
                    toJourney.setText("");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromJourney1 = fromJourney.getText().toString();
                toJourney1 = toJourney.getText().toString();
                driverName1 = driverName.getText().toString();
                conductorName1 = conductorName.getText().toString();
                mobile1 = journeyMobile.getText().toString();
                mDatabaseReference.child("Bus_Journey").setValue(new Bus_Journey(bb, fromJourney1, toJourney1, driverName1, conductorName1, mobile1, local_Bus_Number));
                startActivity(new Intent(BusActivity.this, Bus_MainActivity.class).putExtra("KEY9", buser1).putExtra("KEY10", bb));
            }
        });
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Bus bus = dataSnapshot.getValue(Bus.class);
                if (bus != null) {
                    journeyBusNumber.append(bus.getBusNumber());
                    journeyEngineNumber.append(bus.getEngineNumber());
                    journeyChassisNumber.append(bus.getChassisNumber());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.bus_signOut){
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(BusActivity.this);
            localBuilder.setTitle("Confirm");
            localBuilder.setMessage("Do you want sign out");
            DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {}
            };
            localBuilder.setNegativeButton("No", local1);
            localBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    mDatabaseReference.child("Bus_Journey").child("status").setValue(false);
                    startActivity(new Intent(BusActivity.this, LoginActivity.class).putExtra("KEY1", 1));
                    Toast.makeText(BusActivity.this, "Sign Out Successfully", Toast.LENGTH_SHORT).show();
                }
            });
            localBuilder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void swapStation(View view) {
        String fromJourney1 = fromJourney.getText().toString();
        String toJourney1 = toJourney.getText().toString();
        toJourney.setText(fromJourney1);
        fromJourney.setText(toJourney1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(LoginActivity.progressDialog != null){
            LoginActivity.progressDialog.dismiss();
        }
    }
}

