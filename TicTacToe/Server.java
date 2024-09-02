import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
	ServerSocket ssocket;
	Socket socket;
	
	DataInputStream in;
	DataOutputStream out;
	
	boolean running = true;
	
	int[][] field = new int[3][3]; //0->empty , 1->server, 2->client
	
	public Server(int port)
	{
		try
		{
			ssocket = new ServerSocket(port);
		}catch(Exception e){System.out.println("Error while starting server on " + port);}
		try
		{
			socket = ssocket.accept();
		}catch(Exception e){System.out.println("Error while accepting socket");}
		try
		{
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		}catch(Exception e){System.out.println("Error while creating streams");}
		try
		{
			while(running)
			{
				sendField();
				writeField();
				
				int win = checkAndWriteWin();
				if(win == 1)
				{
					p("Server won!");
					p("Restarting game");
					p("");
					newGame();
					sendField();
					writeField();
					
				}
				else if(win == 2)
				{
					p("Client won!");
					p("Restarting game");
					p("");
					newGame();
					sendField();
					writeField();
				}
				else if(win == 3)
				{
					p("Round draw...");
					p("Restarting game");
					p("");
					newGame();
					sendField();
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
				
				sendField();
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

				win = checkAndWriteWin();
				if(win == 1)
				{
					p("Server won!");
					p("Restarting game");
					p("");
					newGame();
					sendField();
					writeField();
				}
				else if(win == 2)
				{
					p("Client won!");
					p("Restarting game");
					p("");
					newGame();
					sendField();
					writeField();
				}
				else if(win == 3)
				{
					p("Round draw...");
					p("Restarting game");
					p("");
					newGame();
					sendField();
					writeField();
				}
				
				p("Waiting for client...");
				p("");

				int clientX = in.readInt();
				int clientY = in.readInt();
				if(clientX == 999 || clientY == 999)
				{
					p("Exit game... (Client)");
					p("");
					System.exit(0);
				}
				else
				{
					field[clientY][clientX] = 2;
				}
			}
		}catch(Exception e){System.out.println("Error while running program"); e.printStackTrace();}
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
	
	public void sendField() throws Exception
	{
		out.writeInt(field[0][0]);
		out.writeInt(field[0][1]);
		out.writeInt(field[0][2]);
		out.writeInt(field[1][0]);
		out.writeInt(field[1][1]);
		out.writeInt(field[1][2]);
		out.writeInt(field[2][0]);
		out.writeInt(field[2][1]);
		out.writeInt(field[2][2]);
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
	
	public boolean fieldFilled()
	{
		boolean filled = true;
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 3; y++)
			{
				if(field[x][y] == 0)
				{
					filled = false;
				}
			}
		}
		return filled;
	}
	
	public int checkAndWriteWin() throws Exception
	{
		int win = 0; //0-e, 1-s, 2-c
		for(int i = 0; i < 3; i++) // TODO: making it better.........
		{
			if(field[i][0] == 1 && field[i][1] == 1 && field[i][2] == 1)
			{
				win = 1;
			}
			if(field[i][0] == 2 && field[i][1] == 2 && field[i][2] == 2)
			{
				win = 2;
			}
			if(field[0][i] == 1 && field[1][i] == 1 && field[2][i] == 1)
			{
				win = 1;
			}
			if(field[0][i] == 2 && field[1][i] == 2 && field[2][i] == 2)
			{
				win = 2;
			}
		}
		if(field[0][0] == 1 && field[1][1] == 1 && field[2][2] == 1)
		{
			win = 1;
		}
		if(field[0][0] == 2 && field[1][1] == 2 && field[2][2] == 2)
		{
			win = 2;
		}
		if(field[0][2] == 1 && field[1][1] == 1 && field[2][0] == 1)
		{
			win = 1;
		}
		if(field[0][2] == 2 && field[1][1] == 2 && field[2][0] == 2)
		{
			win = 2;
		}
		if(fieldFilled() && win == 0)
		{
			win = 3;
		}
		out.writeInt(win);
		return win;
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
}