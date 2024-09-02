import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TTT 
{	
	boolean server = false;
	String ip;
	int port;
	
	Server s;
	Client c;
	
	public TTT()
	{
		p(""); //Start/Get port/ip
		p("Tic Tac Toe - 2 Player");
		p("");
		boolean s1 = false;
		while(s1 == false)
		{
			p("Do you want to create a server?");
			p("[1] Yes");
			p("[2] No");
			p("");
			String i1 = read();
			p("");
			if(i1.length()>0)
			{
				if(i1.charAt(0) == '1')
				{
					server = true;
					s1 = true;
				}
				else if(i1.charAt(0) == '2')
				{
					server = false;
					s1 = true;
				}
			}
		}
		if(server)
		{
			p("");
			boolean s2 = false;
			while(s2 == false)
			{
				p("Port:");
				p("");
				String i2 = read();
				p("");
				int portInt = 0;
				try
				{
					portInt = Integer.parseInt(i2);
					port = portInt;
					s2 = true;
				}
				catch(Exception e){}
			}
			s = new Server(port);
		}
		else
		{
			p("");
			p("IP:");
			p("");
			String IPString = read();
			p("");
			ip = IPString;
			boolean s3 = false;
			while(s3 == false)
			{
				p("Port:");
				p("");
				String i4 = read();
				p("");
				int PortInt2 = 0;
				try
				{
					PortInt2 = Integer.parseInt(i4);
					port = PortInt2;
					s3 = true;
				}catch(Exception e){}
			}
			c = new Client(ip, port);
		}
	}
	
	public String read()
	{
		String i = "";
		try{
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		i = bReader.readLine();
		}catch(Exception e){p("Error while reading");}
		return i;
	}
	
	public void p(String s)
	{
		System.out.println(s);
	}

	public static void main(String[] args)
	{
		new TTT();
	}
}