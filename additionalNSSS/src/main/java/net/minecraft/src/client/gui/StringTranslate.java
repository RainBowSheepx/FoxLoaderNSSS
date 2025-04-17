package net.minecraft.src.client.gui;



import com.mojang.minecraft.Minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


public class StringTranslate {
    public static String langFile;
    private static boolean starting = true;
    public static String splashesFile = "";
    public static String creditsFile = "";
    public static String tipsFile = "";
    private static StringTranslate instance = new StringTranslate();
    private static Properties translateTable;

    private StringTranslate() {
        reloadKeys();
    }

    public static StringTranslate getInstance() {
        return instance;
    }

    public String translateKey(String s) {
        return translateTable.getProperty(s, s);
    }

    public String translateKeyFormat(String s, Object... aobj) {
        String s1 = translateTable.getProperty(s, s);
        return String.format(s1, aobj);
    }

    public String translateNamedKey(String s) {
        return translateTable.getProperty(s + ".name", "");
    }

    public static void reloadKeys() {
        translateTable = new Properties();

        try {
            translateTable.load(new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/en_US.lang"), "UTF-8"));
            System.out.println("Loading \"en_US\" lang table...");
            boolean useDefaultCredits;
            if (starting) {
                File settings = new File(Minecraft.getMinecraftDir(), "optionsNSSS.txt");
                if (!settings.exists()) {
                    if (langFile == null || !langFile.equals("en_US")) {
                        langFile = "en_US";
                        System.err.println("Had trouble finding options.txt, using en_US instead.");
                    }
                } else {
                    BufferedReader bufferedreader = new BufferedReader(new FileReader(settings));
                    useDefaultCredits = false;
                    String s = "";

                    while((s = bufferedreader.readLine()) != null) {
                        String[] as = s.split(":");
                        if (as[0].equals("langFile")) {
                            langFile = as[1];
                            bufferedreader.close();
                            useDefaultCredits = true;
                            break;
                        }
                    }

                    if (!useDefaultCredits) {
                        langFile = "en_US";
                        bufferedreader.close();
                        System.out.println("\"langFile:\" option is missing from options.txt. It will be created on the next save.");
                    }
                }
            }

            starting = false;

            InputStreamReader reader;
            try {
                reader = new InputStreamReader(new FileInputStream(new File(Minecraft.getMinecraftDir(), "/languagepacks/" + langFile + ".lang")), "UTF-8");
            } catch (FileNotFoundException var8) {
                reader = new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/" + langFile + ".lang"), "UTF-8");
            }

            boolean useDefaultSplashes = true;
            useDefaultCredits = true;
            BufferedReader lineReader = new BufferedReader(reader);
            splashesFile = "";
            creditsFile = "";

            for(int i = 0; i < 5; ++i) {
                String line = lineReader.readLine();
                if (line.startsWith("#")) {
                    while(lineReader.readLine().startsWith("#")) {
                    }
                }

                if (line.startsWith("splashes|")) {
                    splashesFile = line.replace("splashes|", "");
                    useDefaultSplashes = false;
                }

                if (line.startsWith("credits|")) {
                    creditsFile = line.replace("credits|", "");
                    useDefaultCredits = false;
                }



                if (splashesFile.equals("")) {
                    useDefaultSplashes = true;
                }

                if (creditsFile.equals("")) {
                    useDefaultCredits = true;
                }


            }

            if (useDefaultSplashes) {
                splashesFile = "jar:/title/splashes.txt";
                if (!langFile.equals("en_US")) {
                    System.out.println("Current lang file has not defined a splashes file. Using the default one.");
                }
            }

            if (useDefaultCredits) {
                creditsFile = "jar:/lang/credits.txt";
                if (!langFile.equals("en_US")) {
                    System.out.println("Current lang file has not defined a credits file. Using the default one.");
                }
            }



            if (!langFile.equals("en_US")) {
                System.out.println("Loading \"" + langFile + "\" lang table...");

                try {
                    reader = new InputStreamReader(new FileInputStream(new File(Minecraft.getMinecraftDir(), "/languagepacks/" + langFile + ".lang")), "UTF-8");
                } catch (FileNotFoundException var7) {
                    reader = new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/" + langFile + ".lang"), "UTF-8");
                }

                translateTable.load(reader);
            }


        } catch (IOException var9) {
            IOException e = var9;
            e.printStackTrace();
        }

    }
}
