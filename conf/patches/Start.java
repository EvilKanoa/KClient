import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

import net.minecraft.client.main.Main;

public class Start
{
    public static void main(String[] args)
    {
        // start minecraft game application
        // --version is just used as 'launched version' in snoop data and is required
        // Working directory is used as gameDir if not provided
        Main.main(concat(new String[]{"--version", "mcp", "--accessToken", "0", "--assetsDir", "assets/virtual/legacy"}, args));
    }
    
    public static <T> T[] concat(T[] first, T[] second)
    {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}