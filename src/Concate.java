import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Concate {
	public static void main(String[] args) throws IOException {
		
		List<String> agentList = new ArrayList<String>();
		agentList.add("Alarm");
		agentList.add("Contacts");
		agentList.add("HFD");
		agentList.add("Launch Apps");
		agentList.add("Memo");
		agentList.add("Music");
		agentList.add("Navigation");
		agentList.add("News");
		agentList.add("POI");
		agentList.add("Settings");
		agentList.add("System");
		agentList.add("Weather");
		agentList.add("WebSearch");
		
		for (int idx = 0; idx < agentList.size(); idx ++) {
			String agentName = agentList.get(idx);
			String originalCardPath = "";
			String segmentedCommandPath = "";
			String originalCommandPath = "";
			String newSegedCommandPath = "";
			originalCardPath = "CARDs/" + agentName + "zh-CN0627.csv";
			segmentedCommandPath = "seged/" + agentName + ".txt";
			originalCommandPath = "original/" + agentName + ".txt";
			newSegedCommandPath = "newseged/" + agentName + ".txt";
			
			BufferedReader command = new BufferedReader(new FileReader(segmentedCommandPath));
			List<String> commands = new ArrayList<String>();
			String commandLine = "";
			while ((commandLine = command.readLine()) != null) {
				commandLine = commandLine.replaceAll(" < ", "<");
				commandLine = commandLine.replaceAll(" > ", ">");
				commandLine = commandLine.replaceAll("[\\s]+", " ");
				commands.add(commandLine);
			}
			command.close();
			
			BufferedReader br = new BufferedReader(new FileReader(originalCardPath));
			PrintStream newCard = new PrintStream("segedCards/" + agentName + "-zh-CN.csv");
			PrintStream originalCommands = new PrintStream(originalCommandPath);
			PrintStream newSegedCommands = new PrintStream(newSegedCommandPath);
			String line = "";
			int lineNum = 0;
			while ((line = br.readLine()) != null) {
			    String[] tokens = line.split(",");
			    originalCommands.println(tokens[8].replaceAll("\"", ""));
			    if (lineNum != 0) {
			    	tokens[8] = commands.get(lineNum);
			    }
			    if (lineNum == 0) {
			    	newSegedCommands.println("Command Variations");
			    } else {
			    	newSegedCommands.println(commands.get(lineNum));
			    }
			    newCard.println(arrayToString(tokens, ","));
			    lineNum ++;
			}
			br.close();
			newCard.close();
			originalCommands.close();
			newSegedCommands.close();
      }
	}
	
	public static String arrayToString(String[] tokens, String separator) {
		String result = "";
		if (tokens.length > 0) {
			result = tokens[0];
			for (int i = 1; i < tokens.length; i ++) {
				result += separator + tokens[i];
			}
		}
		return result;
	}
}