package edu.vcu.cyber.dashboard;

import edu.vcu.cyber.dashboard.util.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config
{

	public static boolean showAttackSurfaces = false;
	public static boolean USE_SPEC_GRAPH = true;

	public static final File HIDDEN_DATA = new File("./.cybok/");
	public static final String TEMP_DATA_NAME = "tmp";

	public static final File FILE_AV_GRAPH = new File(HIDDEN_DATA, TEMP_DATA_NAME + "_full_analysis.csv");
	public static final File FILE_FULL_ANALYSIS = new File(HIDDEN_DATA, TEMP_DATA_NAME + "_full_analysis.graphml");
	public static final File FILE_SYS_TOPOLOGY = new File(HIDDEN_DATA, TEMP_DATA_NAME + "_system_topology.graphml");
	public static final File FILE_ATTACK_SURFACE = new File(HIDDEN_DATA, TEMP_DATA_NAME + "_attack_surface_graph.graphml");
	public static final File FILE_ATTACK_SURFACE_EV = new File(HIDDEN_DATA, TEMP_DATA_NAME + "__attack_surface_evidence.csv");


	public static final File FILE_SETTINGS = new File(HIDDEN_DATA, "settings.xml");

	public static final int LAST_SIZE = 3;
	public static File LAST_FILE_LOAD_DIRECTORY = null;
	public static File[] LAST_TOPOLOGY_FILE = null;
	public static File[] LAST_SPEC_FILE = null;
	public static boolean[] LAST_DO_ANALYSIS = null;
	public static boolean showDeletedNodes = false;
	public static boolean showHiddenNodes = false;
	public static boolean showCAPECNodes = true;
	public static boolean showCWENodes = true;
	public static boolean showCVENodes = false;
	public static double AV_NODE_MIN_SIZE = 6;
	public static double AV_NODE_SCALE = 2.0D;
	public static double AV_LAYOUT_WEIGHT_DEF = 0.1D;
	public static double AV_LAYOUT_MIN_SIZE = 2D;

	public static void setup()
	{
		try
		{
			if (!FILE_SETTINGS.exists())
			{
				HIDDEN_DATA.mkdirs();
				FILE_SETTINGS.createNewFile();
			}

			Properties properties = new Properties();
			properties.load(new FileReader(FILE_SETTINGS));

			String s = properties.getProperty("last_file_load", "./");
			LAST_FILE_LOAD_DIRECTORY = new File(s);

			LAST_TOPOLOGY_FILE = new File[LAST_SIZE];
			LAST_SPEC_FILE = new File[LAST_SIZE];
			LAST_DO_ANALYSIS = new boolean[LAST_SIZE];

			for (int i = 0; i < LAST_SIZE; i++)
			{
				s = properties.getProperty("last_top_graph" + (i + 1), "");
				LAST_TOPOLOGY_FILE[i] = new File(s);

				s = properties.getProperty("last_spec_graph" + (i + 1), "");
				LAST_SPEC_FILE[i] = new File(s);

				s = properties.getProperty("last_do_analysis" + (i + 1), "true");
				LAST_DO_ANALYSIS[i] = s.equalsIgnoreCase("true");
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void save()
	{
		try
		{
			if (!FILE_SETTINGS.exists())
			{
				HIDDEN_DATA.mkdirs();
				FILE_SETTINGS.createNewFile();
			}


			Properties properties = new Properties();
			if (LAST_FILE_LOAD_DIRECTORY != null)
				properties.setProperty("last_file_load", LAST_FILE_LOAD_DIRECTORY.toString());
			if (LAST_TOPOLOGY_FILE != null)
			{
				for (int i = 0; i < LAST_SIZE; i++)
				{
					if (LAST_TOPOLOGY_FILE[i] != null)
						properties.setProperty("last_top_graph" + (i + 1), LAST_TOPOLOGY_FILE[i].toString());
				}
			}
			if (LAST_SPEC_FILE != null)
			{
				for (int i = 0; i < LAST_SIZE; i++)
				{
					if (LAST_SPEC_FILE[i] != null)
						properties.setProperty("last_spec_graph" + (i + 1), LAST_SPEC_FILE[i].toString());
				}
			}
			if (LAST_DO_ANALYSIS != null)
				for (int i = 0; i < LAST_SIZE; i++)
					properties.setProperty("last_do_analysis" + (i + 1), "" + LAST_DO_ANALYSIS[i]);

			properties.store(new FileWriter(FILE_SETTINGS), null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}


}
