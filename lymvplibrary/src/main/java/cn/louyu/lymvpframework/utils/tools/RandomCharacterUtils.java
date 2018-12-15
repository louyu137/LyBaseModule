package cn.louyu.lymvpframework.utils.tools;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomCharacterUtils {

    private final static Character[] digit={'0','1','2','3','4','5','6','7','8','9'};
    private final static Character[] upperCase={'A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private final static Character[] lowerCase={'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private final static Character[] specialCharacter={'~','!','@','#','$','%','^','&','*','(',
            ')','_','-','+','=','|','\\','{','}','[',']',':',';','\'','"',',','<','>','.','?','/'};

    public static String generate(Random r,int length,int type){
       Character c=0;
       if(length<=0){
           return "";
       }
       switch (type){
           case Type.ONLYDIGIT:
               c=randomFetchCharacter(r,digit);
               break;
           case Type.UPPERCASE:
               c=randomFetchCharacter(r,upperCase);
               break;
           case Type.LOWERCASE:
               c=randomFetchCharacter(r,lowerCase);
               break;
           case Type.SPECIALCHARACTER:
               c=randomFetchCharacter(r,specialCharacter);
               break;
           case Type.ONLYDIGIT| Type.UPPERCASE:
               c=randomFetchCharacter(r,digit,upperCase);
               break;
           case Type.ONLYDIGIT| Type.LOWERCASE:
               c=randomFetchCharacter(r,digit,lowerCase);
               break;
           case Type.ONLYDIGIT| Type.SPECIALCHARACTER:
               c=randomFetchCharacter(r,digit,specialCharacter);
               break;
           case Type.UPPERCASE| Type.LOWERCASE:
               c=randomFetchCharacter(r,upperCase,lowerCase);
               break;
           case Type.UPPERCASE| Type.SPECIALCHARACTER:
               c=randomFetchCharacter(r,upperCase,specialCharacter);
               break;
           case Type.LOWERCASE| Type.SPECIALCHARACTER:
               c=randomFetchCharacter(r,lowerCase,specialCharacter);
               break;
           case Type.ONLYDIGIT| Type.UPPERCASE| Type.LOWERCASE:
               c=randomFetchCharacter(r,digit,upperCase,lowerCase);
               break;
           case Type.ONLYDIGIT| Type.UPPERCASE| Type.SPECIALCHARACTER:
               c=randomFetchCharacter(r,digit,upperCase,specialCharacter);
               break;
           case Type.ONLYDIGIT| Type.LOWERCASE| Type.SPECIALCHARACTER:
               c=randomFetchCharacter(r,digit,lowerCase,specialCharacter);
               break;
           case Type.UPPERCASE| Type.LOWERCASE| Type.SPECIALCHARACTER:
               c=randomFetchCharacter(r,upperCase,lowerCase,specialCharacter);
               break;
//           case Type.ONLYDIGIT|Type.UPPERCASE|Type.LOWERCASE|Type.SPECIALCHARACTER:
//               c=randomFetchCharacter(r,digit,upperCase,lowerCase,specialCharacter);
//               break;
           default:
               c=randomFetchCharacter(r,digit,upperCase,lowerCase,specialCharacter);
               break;
       }

       return String.valueOf(c)+generate(r,--length ,type );
   }


    public static String generate(Random r,int length,List<Character> characters){
       if(length<=0) return "";
           return String.valueOf(characters.get(r.nextInt(characters.size())))+generate(r,--length,characters);
    }

   private static Character randomFetchCharacter(Random r,Character[]... chars){
      List<Character> characters=copyOfAll(chars);
      return characters.get(r.nextInt(characters.size()));
   }

   private static <T> List<T> copyOfAll(T[]... characters){
       if(characters==null||characters.length<1){
           throw new IllegalArgumentException("参数错误");
       }

       List<T> list=new ArrayList<T>(Arrays.asList(characters[0]));
       for(int i=0;i<characters.length;i++){
           if (i==0) continue;
           list.addAll(Arrays.asList(characters[i]));
       }
       return list;
   }


   public final static class Type{
       public final static byte ONLYDIGIT=0x1;
       public final static byte UPPERCASE=0x2;
       public final static byte LOWERCASE=0x4;
       public final static byte SPECIALCHARACTER=0x8;
   }
}
