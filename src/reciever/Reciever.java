package reciever;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Reciever {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String serverName = args[0];
		String p = null;
		int port = Integer.parseInt(args[1]);
		try {
			System.out.println("Connecting to " + serverName + " on port "
					+ port);
			Socket client = new Socket(serverName, port);
			System.out.println("Just connected to "
					+ client.getRemoteSocketAddress() + "\n\n\n");
			do {

				OutputStream outToServer = client.getOutputStream();
				DataOutputStream out = new DataOutputStream(outToServer);
				System.out.print("Client : ");

				p = s.nextLine();
				if (p.equalsIgnoreCase("exit")) {
					client.close();
					break;
				}
				p = encrypt(p);
				out.writeUTF(p);
				InputStream inFromServer = client.getInputStream();
				DataInputStream in = new DataInputStream(inFromServer);
				String test = in.readUTF();
				test = decrypt(test);
				System.out.println("Server : " + test);
			} while (true);

		} catch (IOException e) {
			System.out.println("\n\n server says goodbye!! ");
			// e.printStackTrace();
		}

	}

	static public String decrypt(String str) {

		// String str ;
		char x;
		String result = new String();
		String letter = new String();
		// Scanner s = new Scanner(System.in);

		// str=s.nextLine();

		char[] charArray = str.toCharArray();

		int i = -1;
		double num = 0;
		i = 0;
		while (i < charArray.length) {
			letter = "";
			for (; i < charArray.length; i++) {
				if (charArray[i] == '#')
					break;
				letter += charArray[i];
			}
			i++;
			try {
				num = Double.parseDouble(letter);
			} catch (NumberFormatException e) {
				System.out.println("not a number");
			}

			num = 99999989 / num;
			num = (int) num % 1000;
			num = num / 10;
			num = num - 9;
			num += (int) 23;

			x = (char) num;
			result = result + x;

		}

		return result;

	}

	public static String encrypt(String str) {

		// Scanner s = new Scanner(System.in);
		Random r = new Random();

		// System.out.print("Enter Data to be Sent : ");
		// String str= s.nextLine();
		double x;
		str = str.toUpperCase();

		char[] charArray = str.toCharArray();
		List<Double> intArray = new ArrayList<Double>();

		for (int i = 0; i < str.length(); i++) {
			x = (int) charArray[i];
			x -= 23;

			x += 9;
			x *= 10;
			x += (r.nextInt(9) * 1000) + r.nextInt(9);
			x = 99999989 / x;
			intArray.add(x);
		}

		// System.out.println("\n------------------ENCRYPTED KEY-----------------\n\n\n\n");

		String result = new String();

		int i = 0;
		while (i < intArray.size()) {
			result = result + intArray.get(i).toString();
			result += "#";
			i++;
		}

		// System.out.println(result+"\n\nSending Data........\n\n\n");

		return result;
	}
}
