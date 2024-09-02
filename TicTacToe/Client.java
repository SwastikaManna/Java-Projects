import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client 
{
	Socket socket;
	
	DataInputStream in;
	DataOutputStream out;
	
	boolean running = true;
	
	int[][] field = new int[3][3];
	
	public Client(String ip, int port)
	{
		try 
		{
			socket = new Socket(ip, port);
		} catch (IOException e) {System.out.println("Error while connecting to " + ip + " | " + port);}
		try
		{
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		}catch(Exception e){System.out.println("Error while creating streams");}
		try
		{
			while(running)
			{
				readField();
				writeField();

				int win = in.readInt();
				if(win == 1)
				{
					p("Server won!");
					p("Restarting game");
					p("");
					newGame();
					readField();
					writeField();
				}
				else if(win == 2)
				{
					p("Client won!");
					p("Restarting game");
					p("");
					newGame();
					readField();
					writeField();
				}
				else if(win == 3)
				{
					p("Round draw...");
					p("Restarting game");
					p("");
					newGame();
					readField();
					writeField();
				}
				
				p("Waiting for server...");
				p("");
				
				readField();
				if(field[0][0] == 999 || field[0][1] == 999)
				{
					p("Exit game... (Server)");
					p("");
					System.exit(0);
				}
				else
				{
					writeField();
				}
				
				win = in.readInt();
				if(win == 1)
				{
					p("Server won!");
					p("Restarting game");
					p("");
					newGame();
					readField();
					writeField();
				}
				else if(win == 2)
				{
					p("Client won!");
					p("Restarting game");
					p("");
					newGame();
					readField();
					writeField();
				}
				else if(win == 3)
				{
					p("Round draw...");
					p("Restarting game");
					p("");
					newGame();
					readField();
					writeField();
				}
				
				boolean s1 = false;
				int inX = 0;
				int inY = 0;
				while(s1 == false)
				{
					p("Select a field (-> 'x,y')");
					p("");
					String inString = read();
					p("");
					if(inString.length() > 1)
					{
						if(inString.charAt(0) == '1' || inString.charAt(0) == '2' || inString.charAt(0) == '3' || inString.charAt(2) == '1' || inString.charAt(2) == '1' || inString.charAt(3) == '2')
						{
							inX = (int) inString.charAt(0);
							inY = (int) inString.charAt(2);
							inX -= 49;
							inY -= 49;
							if(field[inY][inX] == 0)
							{
								s1 = true;
							}
							else
							{
								p("The field is already taken");
								p("");
							}
						}
						else if(inString.startsWith("exit"))
						{
							field[0][0] = 999;//twice since it could be overwritten in line:100
							field[0][1] = 999;
							s1 = true;
						}
					}
				}
				field[inY][inX] = 1;
				if(field[0][0] == 999 || field[0][1] == 999)
				{
					out.writeInt(999);
					out.writeInt(999);
					p("Exit game... (Client)");
					p("");
					System.exit(0);
				}
				else
				{
					out.writeInt(inX);
					out.writeInt(inY);
				}
			}
		}catch(Exception e){System.out.println("Error while running the program");e.printStackTrace();}
	}
	
	public void readField() throws Exception
	{
		field[0][0] = in.readInt();
		field[0][1] = in.readInt();
		field[0][2] = in.readInt();
		field[1][0] = in.readInt();
		field[1][1] = in.readInt();
		field[1][2] = in.readInt();
		field[2][0] = in.readInt();
		field[2][1] = in.readInt();
		field[2][2] = in.readInt();
	}
	
	public void writeField()
	{
		System.out.println("[ ]+[1][2][3]");
		System.out.println("+++++++++++++");
		System.out.println("[1]+["+getSFromField(0,0)+"]["+getSFromField(0,1)+"]["+getSFromField(0,2)+"]");
		System.out.println("[2]+["+getSFromField(1,0)+"]["+getSFromField(1,1)+"]["+getSFromField(1,2)+"]");
		System.out.println("[3]+["+getSFromField(2,0)+"]["+getSFromField(2,1)+"]["+getSFromField(2,2)+"]");
		System.out.println("");
	}
	
	public void p(String s)
	{
		System.out.println(s);
	}
	
	public void newGame()
	{
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				field[x][y] = 0;
			}
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
	
	public String getSFromField(int x, int y)
	{
		if(field[x][y] == 0)
		{
			return " ";
		}
		else if(field[x][y] == 1)
		{
			return "X";
		}
		else
		{
			return "O";
		}
	}
}