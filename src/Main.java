import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {



        List<String> listInput = readAllLines("input5.txt");
        boolean startProgram = true;
        String[] startNumber;

        String firstRow = listInput.get(0);
        int idChar = firstRow.indexOf(":");
        String numbers = firstRow.substring(idChar+2,firstRow.length());
        startNumber = numbers.split("\\s+");
        String result="";
        String[] nazvy= {"seed-to-soil","soil-to-fertilizer","fertilizer-to-water","water-to-light","light-to-temperature","temperature-to-humidity","humidity-to-location"};
        for (int i = 0; i < nazvy.length; i++) {
            if (!startProgram){
                startNumber = result.split("\\s+");
            }

            int zacatekSeedToSoil = najdi(nazvy[i],listInput);
            int konecSeedToSoil = najdi(listInput,zacatekSeedToSoil);
            long [][] pole = nahrajDoPole(listInput,zacatekSeedToSoil,konecSeedToSoil);
            result = vypocti(pole,startNumber);
            //System.out.println(result);
            startProgram = false;
        }

        String[] vysledek = result.split("\\s+");
        long[] vysledekInt =prepisDoPole(vysledek);
        Arrays.sort(vysledekInt);
        System.out.println(vysledekInt[0]);

        System.out.println("\nKonec");
    }
    public static String vypocti(long[][] pole,String[] startNumber){

        long vypocet = 0;
        String result="";
        long hodnota = 0;
        boolean nalezen = false;
        for (int i = 0; i < startNumber.length ; i++) {

            hodnota = Long.parseLong(startNumber[i]);

            for (int j = 0; j < pole.length; j++) {

                long dolniInterval = pole[j][1];
                long horniInterval = pole[j][1]+pole[j][2]-1;

                if ((hodnota>=dolniInterval)&&(hodnota<=horniInterval)){
                    long diference = pole[j][0]-pole[j][1];
                    vypocet = hodnota+diference;
                    nalezen = true;
                    break;
                }

            }
            if (!nalezen){
                //System.out.println(hodnota);
                if (i< startNumber.length-1) {
                    result = result + String.valueOf(hodnota) + " ";
                }else {
                    result = result + String.valueOf(hodnota);
                }
            }else {
                //System.out.println(vypocet);
                if (i< startNumber.length-1){
                    result = result + String.valueOf(vypocet)+" ";
                }else {
                    result = result + String.valueOf(vypocet);
                }
                nalezen = false;
            }
        }
        return result;
    }
    public static long[][] nahrajDoPole(List<String> input,int zacatek,int konec){

        long [][] result = new long[konec-zacatek][3];
        int k = 0;

        for (int i = zacatek+1; i < input.size() ; i++) {
            if (input.get(i).isEmpty()){
                break;
            }
            String[] mapa = input.get(i).split(" ");
            for (int j = 0; j < 3 ; j++) {
                result[k][j] = Long.parseLong(mapa[j]);
            }
            k++;
        }
        return result;
    }

    public static List<String> readAllLines(String resource)throws IOException {
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
            return reader.lines().collect(Collectors.toList());
        }
    }

    public static int najdi(String string, List<String> list){

        String regex = string +"+";

        for (int i = 0; i < list.size(); i++) {

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(list.get(i));

            if (matcher.find()){
                return i;
            }
        }
        return -1;
    }
    public static int najdi(List<String> list,int zacatek){
        for (int i = zacatek; i < list.size(); i++) {
            if (list.get(i).isEmpty()){
                return i-1;
            }

        }

        return list.size()-1;
    }
    public static long[] prepisDoPole(String[] pole){
        long[] longArr = new long[pole.length];
        for (int i = 0; i < pole.length; i++) {
            longArr[i] = Long.parseLong(pole[i]);
        }
        return longArr;
    }
}