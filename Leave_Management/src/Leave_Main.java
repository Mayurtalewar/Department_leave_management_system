import java.util.ArrayList;
import java.util.Scanner;
public class Leave_Main {
	ArrayList<Dean> Dean_details = new ArrayList<>();
	ArrayList<Faculty> Faculty_details = new ArrayList<>();
	public static void main(String[] args) {
		Leave_Main leave = new Leave_Main();
		leave.Display();
	}
	void Display() {
		Scanner sc = new Scanner(System.in);
		String name, password;
		int a = 0, total = 0;
		System.out.println("******* DEPARTMENT LEAVE MANAGEMENT SYSTEM*******");
		System.out.println("Select any one for login procedure\n" + "1.Admin \n" + "2.Dean \n" + "3.Faculty");
		try {
			a = sc.nextInt();
			System.out.println("Enter Username");
			name = sc.next();
			System.out.println("Enter Password");
			password = sc.next();
			switch (a) {
				case 1:
					checkadmin(name,password);
					while (true) {
						System.out.println("Enter 1 to ADD Dean or Faculty" +"\nEnter 2 to View Report of Registered Dean" + "\n"
								+ "Enter 3 to View Report of Registered Faculty" + "\n" + "Enter 4 to LOGOUT");
						a = sc.nextInt();
						switch (a) {
							case 1:
								System.out.println("Enter 1 to add Dean OR Enter 2 to add Faculty");
								a = sc.nextInt();
								if (a == 1) { // Creates Dean
									System.out.println("How many Dean you want to Add?");
									total = sc.nextInt();
									Admin.AddDean(Dean_details, total);
									continue;
								}
								else if (a == 2) { // Creates Faculty
									System.out.println("How many Faculty you want to Add?");
									total = sc.nextInt();
									Admin.AddFaculty(Faculty_details, Dean_details, total);
									continue;
								} else {

									System.out.println("Enter valid choice!");
									continue;
								}
							case 2: // View report of Dean
								Admin.viewdean(Dean_details);
								continue;
							case 3: // View report of Faculty
								Admin.viewfaculty(Faculty_details);
								continue;
							case 4: // Display is called for logout
								Display();
							default:
								continue;
						}
					}
				case 2:
					// Login validation for Dean
					Dean rlogin = Matchdean(name, password, Dean_details);
					if (rlogin != null) {
						System.out.println("Welcome " + name+"!");
					} else {
						Display();
					}
					while (true) {
						int c = 0;
						System.out.println("1.View Report\n2.View Requests\n3.Grant\n4.Logout");
						c = sc.nextInt();
						if (c == 1) {
							rlogin.view(Faculty_details);
						} else if (c == 2) { // View request of  allocated Faculty
							rlogin.requests(Faculty_details);
						} else if (c == 3) { // Calling Grant function for Accepting or Rejecting or Pending request
							rlogin.grant(Faculty_details);
						} else if (c == 4)
							Display();
					}
				case 3:
					Faculty e = Matchfaculty(name, password, Faculty_details); // Login validation for Faculty
					if (e != null) {
						System.out.println("Welcome " + name);
						while (true) {
							int b = 0, req = 0,n=0;
							System.out.println("1. View  Leaves" + "\n" + "2. Apply for leave application" + "\n" + "3. Logout");
							b = sc.nextInt();
							switch (b) {
								case 1: // Viewing available leave
									e.viewleave();
									break;
								case 2: //Requesting leave
									System.out.println("Your total CL are:" + e.Getfacleavecl());
									System.out.println("Your total Sl are:" + e.Getfacleavesl());
									System.out.println("Your total EL are:" + e.Getfacleaveel());
									System.out.println("Which leave you want to opt for:"+"\n1.CL\n2.SL\n3.EL");
									n=sc.nextInt();
									switch(n){
										case 1:
											System.out.println("\nHow many CL do you want?");
											String z="CL";
											req = sc.nextInt();
											e.reqleave(req,z);
											break;
										case 2:
											System.out.println("\nHow many SL do you want?");
											String d="SL";
											req = sc.nextInt();
											e.reqleave(req,d);
											break;
										case 3:
											System.out.println("\nHow many EL do you want?");
											String f="EL";
											req = sc.nextInt();
											e.reqleave(req,f);
											break;
									}
								case 3:
									Display();
									break;
								default:
									break;
							}
						}
					}
				default:
					System.out.println("Invalid Credentials!");
					Display();
			}
		} catch (Exception e) {
			System.out.println("Exception Occured!");
			Display();
		}
		sc.close();
	}
	public void checkadmin (String name, String password) {
		if (name.equals("Admin") && password.equals("123") ){
			System.out.println("Welcome  " + name+"!" );
		}
		else {
			System.out.println("Invalid Credentials");
			Display();
		}
	}
	public Dean Matchdean(String name, String password, ArrayList<Dean> Dean_details) {
		for (Dean getd : Dean_details) {
			if (name.equalsIgnoreCase(getd.GetNameDean()) && password.equals(getd.GetPassDean())) {
				return getd;
			}
		}
		return null;
	}
	public Faculty Matchfaculty(String name, String password, ArrayList<Faculty> Faculty_details) {
		for (Faculty getd : Faculty_details) {
			if (name.equalsIgnoreCase(getd.GetnameFac()) && password.equals(getd.GetpassFac())) {
				return getd;
			}
		}
		return null;
	}
}
class Admin {
	// it adds Dean
	public static void AddDean(ArrayList<Dean> Dean_details, int total) {
		String scanname, scanpassword;
		for (int i = 0; i < total; i++) {
			System.out.println("\t\t\tDEAN");
			System.out.println("Enter name:");
			Scanner sc = new Scanner(System.in);
			scanname = sc.next();
			boolean v = validatedean(scanname, Dean_details);
			if (v != true) {
				System.out.println("Enter Password:");
				scanpassword = sc.next();
				Dean_details.add(new Dean(scanname, scanpassword));
				System.out.println("Dean Created\n");
			} else {
				System.out.println("Username Already Exixts ");
				break;
			}
		}
	}
	static boolean validatedean(String scanname, ArrayList<Dean> Dean_details) {
		for (Dean getd : Dean_details) {
			if (scanname.equalsIgnoreCase(getd.GetNameDean()))
			{
				System.out.println("Dean already exists please Enter again\n");
				return true;
			}
		}
		return false;
	}
	public static void AddFaculty(ArrayList<Faculty> Faculty_details, ArrayList<Dean> Dean_details, int total) {
		String scanname, scanpassword;
		for (int i = 0; i < total; i++) {
			System.out.println("\t\t\tFaculty");
			System.out.println("Enter name:");
			Scanner sc = new Scanner(System.in);
			scanname = sc.next();
			System.out.println("Enter Password:");
			scanpassword = sc.next();
			boolean v = validatefaculty(scanname, scanpassword, Faculty_details);
			if (v == true) {
				break;
			}
			else {
				int leavecl = 10;
				int leavesl=8;
				int leaveel=30;
				int usedcl = 0;
				int usedsl=0;
				int usedel=0;
				int requestedcl = 0;
				int requestedsl = 0;
				int requestedel = 0;
				System.out.println("\t\t\tDEAN LIST \n");
				for (Dean getd : Dean_details) {
					if (getd.GetNameDean() != null) {
						System.out.println("Dean Name:" + getd.GetNameDean());
					}
				}
				System.out.println("Assign Dean to the faculty:");
				String Dean;
				Scanner scc=new Scanner(System.in);
				String De_an = scc.next();
				for (Dean getd : Dean_details) {
					if (De_an.equalsIgnoreCase(getd.GetNameDean())) {
						Dean= getd.GetNameDean();
						Faculty_details.add(new Faculty(scanname, scanpassword,leavecl,leavesl,leaveel ,usedcl,usedsl,usedel,requestedcl,requestedsl,requestedel, Dean));
						System.out.println("Faculty Added\n");
					}
				}
			}
		}
	}
	static boolean validatefaculty(String scanname, String scanpassword, ArrayList<Faculty> Faculty_details) {
		for (Faculty getd: Faculty_details) {
			if (scanname.equalsIgnoreCase(getd.GetnameFac()) && scanpassword.equalsIgnoreCase(getd.GetpassFac())) {
				System.out.println("Faculty already exists please Enter again with different id or password\n");
				return true;
			}
		}
		return false;
	}
	public static void viewdean(ArrayList<Dean> Dean_details) {
		System.out.println("\t\t\tDEAN LIST WITH PASSWORD\n");
		for (Dean getd : Dean_details) {
			System.out.println("Dean Name: " + getd.GetNameDean() + "\t\tPassword: " + getd.GetPassDean());
		}
	}
	public static void viewfaculty(ArrayList<Faculty> Faculty_details) {
		System.out.println("\t\t\tFACULTY LIST WITH PASSWORD\n");
		for (Faculty getd : Faculty_details) {
			System.out.println("Faculty Name: " + getd.GetnameFac() + "\t\tPassword: " + getd.GetpassFac() + "\t\tDean: " + getd.GETDean());
		}
	}
}
class Faculty {
	String facname, facpass, Dean;
	int facleavecl,facleavesl,facleaveel, usedleavecl,usedleavesl,usedleaveel, requestcl, requestsl, requestel;
	public Faculty(String scanname, String scanpassword, int leavecl,int leavesl,int leaveel ,int usedcl,int usedsl,int usedel,
				   int requestedcl,int requestedsl,int requestedel, String Dean)
	{
		this.facname = scanname;
		this.facpass = scanpassword;
		this.facleavecl = leavecl;
		this.facleavesl= leavesl;
		this.facleaveel=leaveel;
		this.usedleavecl = usedcl;
		this.usedleavesl=usedsl;
		this.usedleaveel=usedel;
		this.requestcl = requestedcl;
		this.requestsl = requestedsl;
		this.requestel = requestedel;
		this.Dean = Dean;

	}
	// Displays available leaves
	public void viewleave() {
		System.out.println("Your Casual Leaves(CL) are:" + Getfacleavecl());
		System.out.println("Your Sick Leaves(SL) are:" + Getfacleavesl());
		System.out.println("Your Earn Leaves(EL) are:" + Getfacleaveel());
	}
	// function for requesting leave
	public void reqleave(int request, String s) { // it checks if requested no. of leaves are available or not
		if (request > Getfacleavecl() && s=="CL") {
			System.out.println("You don't have sufficient Casual leaves\n");
		}
		else if(request>Getfacleavesl() && s=="SL") {
			System.out.println("You don't have sufficient Sick leaves\n");
		}
		else if(request>Getfacleaveel() && s=="EL") {
			System.out.println("You don't have sufficient Earn leaves\n");
		}
		else {
			System.out.println("Requested for leave of " + request + " days");
		}
		if(s=="CL"){
			setreq1(request);
		} else if (s=="SL") {
			setreq2(request);
		}
		else if(s=="EL"){
			setreq3(request);
		}
	}
	// set the value of total leaves of Faculty
	public void setreq1(int request) {
		this.requestcl = request;
	}
	public void setreq2(int request) {
		this.requestsl = request;
	}
	public void setreq3(int request) {
		this.requestel = request;
	}
	// returns name of Faculty
	public String GetnameFac() {
		return this.facname;
	}
	// returns password of Faculty
	public String GetpassFac() {
		return this.facpass;
	}
	// returns remaining leaves of Faculty
	public int Getfacleavecl() {
		return this.facleavecl;
	}
	public int Getfacleavesl() {
		return this.facleavesl;
	}
	public int Getfacleaveel() {
		return this.facleaveel;
	}
	// returns used leaves of Faculty
	public int Getusedleavecl() {
		return this.usedleavecl;
	}
	public int Getusedleavesl() {
		return this.usedleavesl;
	}
	public int Getusedleaveel() {
		return this.usedleaveel;
	}
	// setting the value of used leaves of Faculty
	public void setusedleavecl(int used) {
		this.usedleavecl = used;
	}
	public void setusedleavesl(int used) {
		this.usedleavesl = used;
	}
	public void setusedleaveel(int used) {
		this.usedleaveel = used;
	}
	// returns no. of requesting leave
	public int GETrequestedleavecl() {
		return this.requestcl;
	}
	public int GETrequestedleavesl() {
		return this.requestsl;
	}
	public int GETrequestedleaveel() {
		return this.requestel;
	}
	// updating the value of total leaves of regular Faculty
	public void updateleavecl(int update) {
		this.facleavecl = update;
	}
	public void updateleavesl(int update) {
		this.facleavesl = update;
	}
	public void updateleaveel(int update) {
		this.facleaveel = update;
	}
	// returns the Dean name of Faculty
	public String GETDean() {
		return this.Dean;
	}
}
class Dean {
	String name, deanid;
	Scanner sc = new Scanner(System.in);
	public Dean(String name, String password) {
		this.name = name;
		this.deanid = password;
	}
	// returns name of Dean
	public String GetNameDean() {
		return this.name;
	}
	// returns password of Dean
	public String GetPassDean() {
		return this.deanid;
	}
	public void view(ArrayList<Faculty> Faculty_details) {    //print all Faculty name, available leaves and used leaves
		for (Faculty getd : Faculty_details) {
			if (GetNameDean().equalsIgnoreCase(getd.GETDean())) {
				System.out.println("Faculty Name:" + getd.GetnameFac() + "\t\tAvailable CL:" + getd.Getfacleavecl() + "\t\tUsed CL:" + getd.Getusedleavecl() + "\n");
				System.out.println("Faculty Name:" + getd.GetnameFac() + "\t\tAvailable SL:" + getd.Getfacleavesl() + "\t\tUsed SL:" + getd.Getusedleavesl() + "\n");
				System.out.println("Faculty Name:" + getd.GetnameFac() + "\t\tAvailable EL:" + getd.Getfacleaveel() + "\t\tUsed EL:" + getd.Getusedleaveel() + "\n");
			}
		}
	}
	public void requests(ArrayList<Faculty> Faculty_details) {    // print all the requests of Faculty who are under the logged in Dean
		for (Faculty getd : Faculty_details) {
			if (GetNameDean().equalsIgnoreCase(getd.GETDean())) {
				if(getd.GETrequestedleavecl()>0)
				{
					System.out.println("Faculty Name:" + getd.GetnameFac() + "\t\tRequesting CL for " + getd.GETrequestedleavecl() + " Days \n");
				}
				if(getd.GETrequestedleavesl()>0)
				{
					System.out.println("Faculty Name:" + getd.GetnameFac() + "\t\tRequesting SL for " + getd.GETrequestedleavesl() + " Days \n");
				}
				if(getd.GETrequestedleaveel()>0)
				{
					System.out.println("Faculty Name:" + getd.GetnameFac() + "\t\tRequesting EL for " + getd.GETrequestedleaveel() + " Days \n");
				}
			}
		}
	}
	// Confirmation for leave to be given or not
	public void grant(ArrayList<Faculty> Faculty_details) {
		for (Faculty getd : Faculty_details) {
			if (getd.GETDean().equalsIgnoreCase(GetNameDean())) {
				if(getd.GETrequestedleavecl()>0)
				{
					System.out.println("Faculty Name:" + getd.GetnameFac() + "\t\tRequesting CL for " + getd.GETrequestedleavecl() + " Days \n");
				}
				if(getd.GETrequestedleavesl()>0)
				{
					System.out.println("Faculty Name:" + getd.GetnameFac() + "\t\tRequesting SL for " + getd.GETrequestedleavesl() + " Days \n");
				}
				if(getd.GETrequestedleaveel()>0)
				{
					System.out.println("Faculty Name:" + getd.GetnameFac() + "\t\tRequesting EL for " + getd.GETrequestedleaveel() + " Days \n");
				}
				int a;
				System.out.println("1.Confirm " + "\n" + "2.Reject" + "\n" + "3.Don't change");
				a = sc.nextInt();
				switch (a) {
					// if Dean confirms the request
					case 1:
						int cl = getd.Getfacleavecl();
						int sl = getd.Getfacleavesl();
						int el = getd.Getfacleaveel();
						int cl1 = getd.GETrequestedleavecl();
						int sl1 = getd.GETrequestedleavesl();
						int el1= getd.GETrequestedleaveel();
						if(sl1>0)
						{
							int deduct = getd.GETrequestedleavesl();
							sl -= deduct;
							getd.updateleavesl(sl);
							int used = getd.Getusedleavesl() + getd.GETrequestedleavesl();
							getd.setusedleavesl(used);
							getd.setreq1(0);
							getd.setreq2(0);
							getd.setreq3(0);
							System.out.println("Leaves granted\n");
						}
						else if(cl1>0)
						{
							int deduct = getd.GETrequestedleavecl();
							cl -= deduct;
							getd.updateleavecl(cl);
							int used = getd.Getusedleavecl() + getd.GETrequestedleavecl();
							getd.setusedleavecl(used);
							getd.setreq1(0);
							getd.setreq2(0);
							getd.setreq3(0);
							System.out.println("Leaves granted\n");
						}
						else if(el1>0)
						{
							int deduct = getd.GETrequestedleaveel();
							el -= deduct;
							getd.updateleaveel(el);
							int used = getd.Getusedleaveel() + getd.GETrequestedleaveel();
							getd.setusedleaveel(used);
							getd.setreq1(0);
							getd.setreq2(0);
							getd.setreq3(0);
							System.out.println("Leaves granted\n");
						}
						break;
						// if dean rejects the request
					case 2:
						System.out.println("Leaves Rejected\n");
						getd.setreq1(0);
						getd.setreq2(0);
						getd.setreq3(0);
						break;
					// if dean selects pending option
					case 3:
						System.out.println("Leaves are as it is\n");
						break;
					default:
						break;
				}
			}
		}
	}
}