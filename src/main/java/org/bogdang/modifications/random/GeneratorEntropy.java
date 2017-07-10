package org.bogdang.modifications.random;
import java.io.*;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.bogdang.modifications.random.*;
 
 /**
 * GeneratorEntropy by Bogdan-G
 * 16.06.2017 - version 0.0.3
 * info: generate more values for more sample
 */
public class GeneratorEntropy implements java.io.Serializable {

    private static final long serialVersionUID = 8218727692528454918L;

    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;
    private static final long GAMMA = 0x9e3779b97f4a7c15L;
    private static final int PROBE_INCREMENT = 0x9e3779b9;
    private static final long SEEDER_INCREMENT = 0xbb67ae8584caa73bL;
    private static final double DOUBLE_UNIT = 0x1.0p-53;  // 1.0  / (1L << 53)
    private static final long DOUBLE_UNIT_LONG = (long)0x1.0p-53;
    private static final float FLOAT_UNIT  = 0x1.0p-24f; // 1.0f / (1 << 24)
    private static final long FLOAT_UNIT_LONG  = (long)0x1.0p-24f;
    
    private static final String path_cache = "."+File.separator+"cache2"+File.separator;
    
    private static final RandomModified rm = new RandomModified();
    private static final SecureRandom sr = new SecureRandom();
    private static final XSTR xstr = new XSTR();
    private static final TLR tlr = new TLR();
    private static final MersenneTwisterFast mtf = new MersenneTwisterFast();

    public GeneratorEntropy() {
    }
    
    public GeneratorEntropy(boolean flag) {
        if (flag) Gen();
    }

    
    private static long seed_combined = 1L;
    private static AtomicLong seedUniquifier = new AtomicLong(1L);

    private static long seedUniquifier() {
        for (;;) {
            long current = seedUniquifier.get();
            long next = current * seed_combined;
            if (seedUniquifier.compareAndSet(current, next)) {
                return next;
            }
        }
    }

    public static long getSeed() {
        String data_s="";
        try {
        final File point = new File(path_cache+"GeneratorEntropy_cache_data_modGraveStone");
        byte[] data = new byte[(int)point.length()];
        InputStream fis  = new BufferedInputStream(new FileInputStream(point));
        fis.read(data);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedReader br = new BufferedReader(new InputStreamReader(bis));
        String line;
        while((line = br.readLine()) != null) {data_s=line;}
        } catch (Exception e) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.WARN, (Throwable)e, "GeneratorEntropy stacktrace: %s", (Throwable)e);}
        String[] source = data_s.split("");
        String[] data_str_array;
        if (source[0].length()==0) {
            data_str_array = new String[source.length-1];
            System.arraycopy(source, 1, data_str_array, 0, source.length-1);
        } else {
            data_str_array = source;
        }
        long[] Long_array = new long[data_str_array.length/15+1];
        int j=0;
        for (int i=0;i<data_str_array.length;i++) {
        StringBuilder sb = new StringBuilder();
        int k=5;
        for (;i<data_str_array.length && k!=0;i++) {
            if (xstr.nextInt(10)==0) {
            sb.append(data_str_array[i]);i++;
            if (i<data_str_array.length) { 
                sb.append(data_str_array[i]);i++;
                if (i<data_str_array.length) { 
                    sb.append(data_str_array[i]);i++;
                }
            }
            k--;
            }
        }
        String temp = String.valueOf(sb);
        if (temp.length()!=0) Long_array[j]=Long.parseLong(temp, 16);
        else Long_array[j]=Long.parseLong("0", 16);
        j++;
        }
        long rl = Long_array[xstr.nextInt(Long_array.length)];
        return rl==0?xstr.getSeed():rl;
    }

    public static void reGen() {
        Gen();
    }

    public static void Gen() {
        float startT = System.nanoTime();
        float startT0 = startT;
        float elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy start func Gen().");
        startT = System.nanoTime();
        File point = new File(path_cache);
        if (!point.exists()) point.mkdir();
        point = new File(path_cache+"GeneratorEntropy_cache_data_modGraveStone");
        boolean file_find = false;
        try {
        if (!point.exists()) {
            point.createNewFile();
            elapsed = System.nanoTime() - startT;
            cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy file created.- "+elapsed/1000000+" ms");
            startT = System.nanoTime();
        } else file_find = true;
        } catch (Exception e) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.WARN, (Throwable)e, "GeneratorEntropy stacktrace: %s", (Throwable)e);}
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy file check - "+elapsed/1000000+" ms");
        startT = System.nanoTime();
        
        String data_s="";
        try {
        byte[] data = new byte[(int)point.length()];
        InputStream fis  = new BufferedInputStream(new FileInputStream(point));
        fis.read(data);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedReader br = new BufferedReader(new InputStreamReader(bis));
        String line;
        while((line = br.readLine()) != null) {data_s=line;}
        } catch (Exception e) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.WARN, (Throwable)e, "GeneratorEntropy stacktrace: %s", (Throwable)e);}
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy data of file load - "+elapsed/1000000+" ms");
        startT = System.nanoTime();
        
        byte[] seed_array_byte = sr.generateSeed(1000);
        long values_sab_combined=1;
        for (int i=0;;) {
            if (i<seed_array_byte.length) {values_sab_combined=values_sab_combined*seed_array_byte[i];i++;} else break;
            if (i<seed_array_byte.length) {values_sab_combined=values_sab_combined*seed_array_byte[i];i++;} else break;
            if (i<seed_array_byte.length) {values_sab_combined=values_sab_combined*seed_array_byte[i];i++;} else break;
            if (i<seed_array_byte.length) {values_sab_combined=values_sab_combined*seed_array_byte[i];i++;} else break;
            if (i<seed_array_byte.length) {values_sab_combined=values_sab_combined*seed_array_byte[i];i++;} else break;
        }
        seed_array_byte=null;
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy combined values sab - "+elapsed/1000000+" ms");
        //startT = System.nanoTime();
        double[] Z1234 = new double[4000];
        for (int jj=0;jj<1000000;jj++) {
            //small warm-up
            int temp = rm.nextInt()*xstr.nextInt()*tlr.current().nextInt()*mtf.nextInt();
        }
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy rnd\'s small warm-up - "+elapsed/1000000+" ms");
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy start gen array\'s types.");
        startT = System.nanoTime();
        //block 1
        {
        try {
        seed_combined = rm.getSeed()*xstr.getSeed()*tlr.current().getSeed()*mtf.getSeed()*values_sab_combined;
        } catch (Throwable e) {
            //cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.WARN, e, "GeneratorEntropy stacktrace: %s", e);
            //MC crash java.lang.NoSuchMethodError: org.bogdang.modifications.random.TLR.getSeed()J , but out MC class TLR normal work
            seed_combined = rm.getSeed()*xstr.getSeed()*mtf.getSeed()*values_sab_combined;
        }
        seedUniquifier.set(seed_combined);
        double[] D = new double[6000];
        //double[] Z = new double[1000];
        int j = 0;
        for (int i=0;i<D.length;i++) {
            if (j<1000) D[i]=rm.nextLong()*seedUniquifier();
            else if (j<2000) D[i]=rm.nextInt()*seedUniquifier();
            else if (j<3000) D[i]=rm.nextInt()*seedUniquifier();
            else if (j<4000) D[i]=rm.nextInt()*seedUniquifier();
            else if (j<5000) D[i]=rm.nextFloat()*seedUniquifier();
            else if (j<6000) D[i]=rm.nextDouble()*seedUniquifier();
            j++;
        }
        for (int i=0;i<1000;i++) {
            int the_number_fell = rm.nextInt(6);
            Z1234[i]= the_number_fell==0 ? D[rm.nextInt(1000)] : the_number_fell==1 ? D[rm.nextInt(1000)+1000] : the_number_fell==2 ? D[rm.nextInt(1000)+2000] : the_number_fell==3 ? D[rm.nextInt(1000)+3000] : the_number_fell==4 ? D[rm.nextInt(1000)+4000] : D[rm.nextInt(1000)+5000];
        }
        //Z1=Z; - not work?
        //System.arraycopy(Z, 0, Z1234, 0, 1000);
        }
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy block 1 - "+elapsed/1000000+" ms");
        startT = System.nanoTime();
        //block 2
        {
        try {
        seed_combined = rm.getSeed()*xstr.getSeed()*tlr.current().getSeed()*mtf.getSeed()*values_sab_combined;
        } catch (Throwable e) {
            //cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.WARN, e, "GeneratorEntropy stacktrace: %s", e);
            //MC crash java.lang.NoSuchMethodError: org.bogdang.modifications.random.TLR.getSeed()J , but out MC class TLR normal work
            seed_combined = rm.getSeed()*xstr.getSeed()*mtf.getSeed()*values_sab_combined;
        }
        seedUniquifier.set(seed_combined);
        double[] D = new double[6000];
        //double[] Z = new double[1000];
        int j = 0;
        for (int i=0;i<D.length;i++) {
            if (j<1000) D[i]=xstr.nextLong()*seedUniquifier();
            else if (j<2000) D[i]=xstr.nextInt()*seedUniquifier();
            else if (j<3000) D[i]=xstr.nextInt()*seedUniquifier();
            else if (j<4000) D[i]=xstr.nextInt()*seedUniquifier();
            else if (j<5000) D[i]=xstr.nextFloat()*seedUniquifier();
            else if (j<6000) D[i]=xstr.nextDouble()*seedUniquifier();
            j++;
        }
        for (int i=1000;i<2000;i++) {
            int the_number_fell = xstr.nextInt(6);
            Z1234[i]= the_number_fell==0 ? D[xstr.nextInt(1000)] : the_number_fell==1 ? D[xstr.nextInt(1000)+1000] : the_number_fell==2 ? D[xstr.nextInt(1000)+2000] : the_number_fell==3 ? D[xstr.nextInt(1000)+3000] : the_number_fell==4 ? D[xstr.nextInt(1000)+4000] : D[xstr.nextInt(1000)+5000];
        }
        //Z2=Z;
        //System.arraycopy(Z, 0, Z1234, 1000, 1000);
        }
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy block 2 - "+elapsed/1000000+" ms");
        startT = System.nanoTime();
        //block 3
        {
        try {
        seed_combined = rm.getSeed()*xstr.getSeed()*tlr.current().getSeed()*mtf.getSeed()*values_sab_combined;
        } catch (Throwable e) {
            //cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.WARN, e, "GeneratorEntropy stacktrace: %s", e);
            //MC crash java.lang.NoSuchMethodError: org.bogdang.modifications.random.TLR.getSeed()J , but out MC class TLR normal work
            seed_combined = rm.getSeed()*xstr.getSeed()*mtf.getSeed()*values_sab_combined;
        }
        seedUniquifier.set(seed_combined);
        double[] D = new double[6000];
        //double[] Z = new double[1000];
        int j = 0;
        for (int i=0;i<D.length;i++) {
            if (j<1000) D[i]=tlr.current().nextLong()*seedUniquifier();
            else if (j<2000) D[i]=tlr.current().nextInt()*seedUniquifier();
            else if (j<3000) D[i]=tlr.current().nextInt()*seedUniquifier();
            else if (j<4000) D[i]=tlr.current().nextInt()*seedUniquifier();
            else if (j<5000) D[i]=tlr.current().nextFloat()*seedUniquifier();
            else if (j<6000) D[i]=tlr.current().nextDouble()*seedUniquifier();
            j++;
        }
        for (int i=2000;i<3000;i++) {
            int the_number_fell = tlr.current().nextInt(6);
            Z1234[i]= the_number_fell==0 ? D[tlr.current().nextInt(1000)] : the_number_fell==1 ? D[tlr.current().nextInt(1000)+1000] : the_number_fell==2 ? D[tlr.current().nextInt(1000)+2000] : the_number_fell==3 ? D[tlr.current().nextInt(1000)+3000] : the_number_fell==4 ? D[tlr.current().nextInt(1000)+4000] : D[tlr.current().nextInt(1000)+5000];
        }
        //Z3=Z;
        //System.arraycopy(Z, 0, Z1234, 2000, 1000);
        }
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy block 3 - "+elapsed/1000000+" ms");
        startT = System.nanoTime();
        //block 4
        {
        try {
        seed_combined = rm.getSeed()*xstr.getSeed()*tlr.current().getSeed()*mtf.getSeed()*values_sab_combined;
        } catch (Throwable e) {
            //cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.WARN, e, "GeneratorEntropy stacktrace: %s", e);
            //MC crash java.lang.NoSuchMethodError: org.bogdang.modifications.random.TLR.getSeed()J , but out MC class TLR normal work
            seed_combined = rm.getSeed()*xstr.getSeed()*mtf.getSeed()*values_sab_combined;
        }
        seedUniquifier.set(seed_combined);
        double[] D = new double[6000];
        //double[] Z = new double[1000];
        int j = 0;
        for (int i=0;i<D.length;i++) {
            if (j<1000) D[i]=mtf.nextLong()*seedUniquifier();
            else if (j<2000) D[i]=mtf.nextInt()*seedUniquifier();
            else if (j<3000) D[i]=mtf.nextInt()*seedUniquifier();
            else if (j<4000) D[i]=mtf.nextInt()*seedUniquifier();
            else if (j<5000) D[i]=mtf.nextFloat()*seedUniquifier();
            else if (j<6000) D[i]=mtf.nextDouble()*seedUniquifier();
            j++;
        }
        for (int i=3000;i<4000;i++) {
            int the_number_fell = mtf.nextInt(6);
            Z1234[i]= the_number_fell==0 ? D[mtf.nextInt(1000)] : the_number_fell==1 ? D[mtf.nextInt(1000)+1000] : the_number_fell==2 ? D[mtf.nextInt(1000)+2000] : the_number_fell==3 ? D[mtf.nextInt(1000)+3000] : the_number_fell==4 ? D[mtf.nextInt(1000)+4000] : D[mtf.nextInt(1000)+5000];
        }
        //Z4=Z;
        //System.arraycopy(Z, 0, Z1234, 3000, 1000);
        }
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy block 4 - "+elapsed/1000000+" ms");
        startT = System.nanoTime();
        for (int i=0;i<1000;i++) {
            if (seedUniquifier()>10000000000L) {
            Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)];Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+2000];Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+3000];
            Z1234[xstr.nextInt(1000)+1000]=Z1234[xstr.nextInt(1000)];Z1234[xstr.nextInt(1000)+1000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+1000]=Z1234[xstr.nextInt(1000)+2000];Z1234[xstr.nextInt(1000)+1000]=Z1234[xstr.nextInt(1000)+3000];
            } else if (seedUniquifier()<-10000000000L) {
            Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)];Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+2000];Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+3000];
            Z1234[xstr.nextInt(1000)+3000]=Z1234[xstr.nextInt(1000)];Z1234[xstr.nextInt(1000)+3000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+3000]=Z1234[xstr.nextInt(1000)+2000];Z1234[xstr.nextInt(1000)+3000]=Z1234[xstr.nextInt(1000)+3000];
            } else {
            Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+1000];Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+3000];Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+3000];
            Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+1000];Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+3000];Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+3000];
            }
        }
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy blending array\'s values #1 - "+elapsed/1000000+" ms");
        startT = System.nanoTime();
        for (int i=0;i<1000;i++) {
            int which_generator = xstr.nextInt(4);
            int which_type = xstr.nextInt(6);
            if (which_generator==0) {
                if (which_type==0) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~(((long)Z1234[x1]^rm.nextLong())>>PROBE_INCREMENT*2L)^GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))^8L*900L)+(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.log10(Z1234[xstr.nextInt(1000)])*addend/mask-(long)Math.scalb((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))>>DOUBLE_UNIT_LONG, 2))>>SEEDER_INCREMENT)^1^2^8^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^rm.nextLong())|PROBE_INCREMENT|2L)*(long)Math.log(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L*900L)+(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.log10(Z1234[xstr.nextInt(1000)])*addend/mask-(long)Math.ulp((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))<<DOUBLE_UNIT_LONG))*SEEDER_INCREMENT)|10^1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]^rm.nextLong())<<2L|8)*(long)Math.sqrt((long)Z1234[xstr.nextInt(1000)+1000]>>8L)+(long)Math.ceil((long)Z1234[xstr.nextInt(1000)+2000]<<1L))|1L/32L)-(long)Math.rint(Z1234[xstr.nextInt(1000)+3000])/200L)/1+(long)Math.signum(Z1234[xstr.nextInt(1000)]*128L)>>(long)Math.sinh(FLOAT_UNIT_LONG*xstr.nextInt(10)|DOUBLE_UNIT_LONG))^SEEDER_INCREMENT)|10);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^rm.nextLong()))*(long)Math.getExponent(Z1234[xstr.nextInt(1000)+1000]*5L)<<(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 16))/1010101L)+(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend*mask-(long)Math.scalb(2L*xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))<<SEEDER_INCREMENT)^16);
                } else if (which_type==1) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~(((long)Z1234[x1]|(byte)rm.nextInt())>>PROBE_INCREMENT*2L)|GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))|8L*900L)^(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])*addend-mask-(long)Math.copySign((long)(FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))>>DOUBLE_UNIT_LONG, 2))>>SEEDER_INCREMENT)|1|2|8|2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]|(byte)rm.nextInt())|PROBE_INCREMENT|2L)*(long)Math.log(Z1234[xstr.nextInt(1000)+1000])*(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))|8L*900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])*addend-mask-(long)Math.ulp((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))*DOUBLE_UNIT))*SEEDER_INCREMENT)|10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]|(byte)rm.nextInt())*2L|8)*(long)Math.sqrt((long)Z1234[xstr.nextInt(1000)+1000]>>8L)^(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000]*1L, 2D))|1L-32L)-(long)Math.sqrt(Z1234[xstr.nextInt(1000)+3000])-15L)-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128L)>>(long)Math.cosh(FLOAT_UNIT_LONG*xstr.nextInt(10)|DOUBLE_UNIT_LONG))|SEEDER_INCREMENT)|10);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]|(byte)rm.nextInt()))*(long)Math.IEEEremainder((long)Z1234[xstr.nextInt(1000)+1000]<<2L, 2)*(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend*mask-(long)Math.copySign(2L*xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))*SEEDER_INCREMENT)|11);
                } else if (which_type==2) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((-(((long)Z1234[x1]^rm.nextInt())+PROBE_INCREMENT<<2L)^GAMMA)<<(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend*mask/1+(long)Math.copySign(FLOAT_UNIT/1+xstr.nextInt(1000000)-DOUBLE_UNIT, 2))-SEEDER_INCREMENT)^1^2^8^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^rm.nextInt())^PROBE_INCREMENT^2L)<<(long)Math.log(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend-mask-(long)Math.atan2(FLOAT_UNIT_LONG^1+xstr.nextInt(1000000)+DOUBLE_UNIT_LONG, 2))|SEEDER_INCREMENT)>>10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]^rm.nextInt())<<2L^8)<<(long)Math.sqrt(Z1234[xstr.nextInt(1000)+1000]-8L)^(long)Math.hypot((long)Z1234[xstr.nextInt(1000)+2000]<<1L, 2D))^1L)-(long)Math.sqrt(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)-(long)Math.atan(xstr.nextInt(10)))^SEEDER_INCREMENT));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^rm.nextInt()))+(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]-2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend<<mask-(long)Math.copySign(2L<<xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))<<SEEDER_INCREMENT)^11);
                } else if (which_type==3) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((-(((long)Z1234[x1]^rm.nextInt())+PROBE_INCREMENT<<2L)^GAMMA)<<(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))>>5L)^(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend*mask/1+(long)Math.copySign(FLOAT_UNIT/1+xstr.nextInt(1000000)-DOUBLE_UNIT, 2))-SEEDER_INCREMENT)<<1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^rm.nextInt())^PROBE_INCREMENT^2L)<<(long)Math.log(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend-mask-(long)Math.copySign(FLOAT_UNIT_LONG^1+xstr.nextInt(1000000)+DOUBLE_UNIT_LONG, 2))|SEEDER_INCREMENT)>>10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=((((((((long)Z1234[x1]^rm.nextInt())<<2L^8)<<(long)Math.cos(Z1234[xstr.nextInt(1000)+1000]-8L)^(long)Math.scalb(Z1234[xstr.nextInt(1000)+2000]*256/1000, 2)^1L)-(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)-(long)Math.atan(xstr.nextInt(10)))>>2L));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^rm.nextInt()))+(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]-2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.cos(Z1234[xstr.nextInt(1000)])/addend^mask+(long)Math.copySign(2L<<xstr.nextInt(100), 8))<<SEEDER_INCREMENT)^11);
                } else if (which_type==4) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~((long)(Z1234[x1]*rm.nextFloat())+PROBE_INCREMENT*2L)>>GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))>>5L)>>(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])*addend-mask+(long)Math.copySign((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))^DOUBLE_UNIT_LONG, 2))^SEEDER_INCREMENT)*1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=((((((((long)(Z1234[x1]*rm.nextFloat())>>PROBE_INCREMENT^2L)*(long)Math.exp(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))>>1L*2L)>>(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])^(long)Math.sqrt(FLOAT_UNIT*1+xstr.nextInt(1000000)+DOUBLE_UNIT))|SEEDER_INCREMENT)<<1/2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((long)(Z1234[x1]*rm.nextFloat())*2L>>8)*(long)Math.cos((long)Z1234[xstr.nextInt(1000)+1000]^1L)>>(long)Math.scalb(Z1234[xstr.nextInt(1000)+2000]/128, 2)>>1L)^(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)^(long)Math.atan(xstr.nextInt(10)))^2L));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((long)(Z1234[x1]*rm.nextFloat()))+(long)Math.IEEEremainder((long)Z1234[xstr.nextInt(1000)+1000]^2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 2))^1010101L)>>(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))*addend*SEEDER_INCREMENT)^2);
                } else if (which_type==5) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~((long)(Z1234[x1]-rm.nextDouble()))>>GAMMA)*(long)Math.asin(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))|5L)-(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])*addend-mask^(long)Math.nextAfter(xstr.nextInt(10000)^DOUBLE_UNIT_LONG, 2))^SEEDER_INCREMENT)*1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=((((((((long)(Z1234[x1]-rm.nextDouble())-PROBE_INCREMENT^2L)*(long)Math.exp(Z1234[xstr.nextInt(1000)+1000])^(long)Math.sqrt(Z1234[xstr.nextInt(1000)+2000]))*2L)-(long)Math.ceil(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])^(long)Math.sqrt(FLOAT_UNIT_LONG-1+xstr.nextInt(1000000)^DOUBLE_UNIT_LONG))|SEEDER_INCREMENT)*1*2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=((((((((long)(Z1234[x1]-rm.nextDouble())*2L-8)*(long)Math.cos((long)Z1234[xstr.nextInt(1000)+1000]^1L)-(long)Math.round(Z1234[xstr.nextInt(1000)+2000]))-1L)^(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.signum(Z1234[xstr.nextInt(1000)]-1L)^(long)Math.nextUp(xstr.nextInt(10)))));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((long)(Z1234[x1]-rm.nextDouble()))^(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]*128, 2)^(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 2))^1010101L)-(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))*addend-SEEDER_INCREMENT)^2);
                }
            
        } else if (which_generator==1) {
        if (which_type==0) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~(((long)Z1234[x1]^xstr.nextLong())>>PROBE_INCREMENT*2L)^GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))^8L*900L)+(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.log10(Z1234[xstr.nextInt(1000)])*addend/mask-(long)Math.scalb((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))>>DOUBLE_UNIT_LONG, 2))>>SEEDER_INCREMENT)^1^2^8^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^xstr.nextLong())|PROBE_INCREMENT|2L)*(long)Math.log(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L*900L)+(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.log10(Z1234[xstr.nextInt(1000)])*addend/mask-(long)Math.ulp((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))<<DOUBLE_UNIT_LONG))*SEEDER_INCREMENT)|10^1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]^xstr.nextLong())<<2L|8)*(long)Math.sqrt((long)Z1234[xstr.nextInt(1000)+1000]>>8L)+(long)Math.ceil((long)Z1234[xstr.nextInt(1000)+2000]<<1L))|1L/32L)-(long)Math.rint(Z1234[xstr.nextInt(1000)+3000])/200L)/1+(long)Math.signum(Z1234[xstr.nextInt(1000)]*128L)>>(long)Math.sinh(FLOAT_UNIT_LONG*xstr.nextInt(10)|DOUBLE_UNIT_LONG))^SEEDER_INCREMENT)|10);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^xstr.nextLong()))*(long)Math.getExponent(Z1234[xstr.nextInt(1000)+1000]*5L)<<(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 16))/1010101L)+(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend*mask-(long)Math.scalb(2L*xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))<<SEEDER_INCREMENT)^16);
                } else if (which_type==1) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~(((long)Z1234[x1]|(byte)xstr.nextInt())>>PROBE_INCREMENT*2L)|GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))|8L*900L)^(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])*addend-mask-(long)Math.copySign((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))>>DOUBLE_UNIT_LONG, 2))>>SEEDER_INCREMENT)|1|2|8|2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]|(byte)xstr.nextInt())|PROBE_INCREMENT|2L)*(long)Math.log(Z1234[xstr.nextInt(1000)+1000])*(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))|8L*900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])*addend-mask-(long)Math.ulp((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))*DOUBLE_UNIT))*SEEDER_INCREMENT)|10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]|(byte)xstr.nextInt())*2L|8)*(long)Math.sqrt((long)Z1234[xstr.nextInt(1000)+1000]>>8L)^(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000]*1L, 2D))|1L-32L)-(long)Math.sqrt(Z1234[xstr.nextInt(1000)+3000])-15L)-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128L)>>(long)Math.cosh(FLOAT_UNIT_LONG*xstr.nextInt(10)|DOUBLE_UNIT_LONG))|SEEDER_INCREMENT)|10);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]|(byte)xstr.nextInt()))*(long)Math.IEEEremainder((long)Z1234[xstr.nextInt(1000)+1000]<<2L, 2)*(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend*mask-(long)Math.copySign(2L*xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))*SEEDER_INCREMENT)|11);
                } else if (which_type==2) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((-(((long)Z1234[x1]^xstr.nextInt())+PROBE_INCREMENT<<2L)^GAMMA)<<(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend*mask/1+(long)Math.copySign(FLOAT_UNIT/1+xstr.nextInt(1000000)-DOUBLE_UNIT, 2))-SEEDER_INCREMENT)^1^2^8^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^xstr.nextInt())^PROBE_INCREMENT^2L)<<(long)Math.log(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend-mask-(long)Math.atan2(FLOAT_UNIT_LONG^1+xstr.nextInt(1000000)+DOUBLE_UNIT_LONG, 2))|SEEDER_INCREMENT)>>10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]^xstr.nextInt())<<2L^8)<<(long)Math.sqrt(Z1234[xstr.nextInt(1000)+1000]-8L)^(long)Math.hypot((long)Z1234[xstr.nextInt(1000)+2000]<<1L, 2D))^1L)-(long)Math.sqrt(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)-(long)Math.atan(xstr.nextInt(10)))^SEEDER_INCREMENT));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^xstr.nextInt()))+(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]-2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend<<mask-(long)Math.copySign(2L<<xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))<<SEEDER_INCREMENT)^11);
                } else if (which_type==3) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((-(((long)Z1234[x1]^xstr.nextInt())+PROBE_INCREMENT<<2L)^GAMMA)<<(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))>>5L)^(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend*mask/1+(long)Math.copySign(FLOAT_UNIT/1+xstr.nextInt(1000000)-DOUBLE_UNIT, 2))-SEEDER_INCREMENT)<<1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^xstr.nextInt())^PROBE_INCREMENT^2L)<<(long)Math.log(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend-mask-(long)Math.copySign(FLOAT_UNIT_LONG^1+xstr.nextInt(1000000)+DOUBLE_UNIT_LONG, 2))|SEEDER_INCREMENT)>>10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=((((((((long)Z1234[x1]^xstr.nextInt())<<2L^8)<<(long)Math.cos(Z1234[xstr.nextInt(1000)+1000]-8L)^(long)Math.scalb(Z1234[xstr.nextInt(1000)+2000]*256/1000, 2)^1L)-(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)-(long)Math.atan(xstr.nextInt(10)))>>2L));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^xstr.nextInt()))+(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]-2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.cos(Z1234[xstr.nextInt(1000)])/addend^mask+(long)Math.copySign(2L<<xstr.nextInt(100), 8))<<SEEDER_INCREMENT)^11);
                } else if (which_type==4) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~((long)(Z1234[x1]*xstr.nextFloat())+PROBE_INCREMENT*2L)>>GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))>>5L)>>(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])*addend-mask+(long)Math.copySign((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))^DOUBLE_UNIT_LONG, 2))^SEEDER_INCREMENT)*1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=((((((((long)(Z1234[x1]*xstr.nextFloat())>>PROBE_INCREMENT^2L)*(long)Math.exp(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))>>1L*2L)>>(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])^(long)Math.sqrt(FLOAT_UNIT*1+xstr.nextInt(1000000)+DOUBLE_UNIT))|SEEDER_INCREMENT)<<1/2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((long)(Z1234[x1]*xstr.nextFloat())*2L>>8)*(long)Math.cos((long)Z1234[xstr.nextInt(1000)+1000]^1L)>>(long)Math.scalb(Z1234[xstr.nextInt(1000)+2000]/128, 2)>>1L)^(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)^(long)Math.atan(xstr.nextInt(10)))^2L));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((long)(Z1234[x1]*xstr.nextFloat()))+(long)Math.IEEEremainder((long)Z1234[xstr.nextInt(1000)+1000]^2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 2))^1010101L)>>(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))*addend*SEEDER_INCREMENT)^2);
                } else if (which_type==5) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~((long)(Z1234[x1]-xstr.nextDouble()))>>GAMMA)*(long)Math.acos(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))|5L)-(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])*addend-mask^(long)Math.nextAfter(xstr.nextInt(10000)^DOUBLE_UNIT_LONG, 2))^SEEDER_INCREMENT)*1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=((((((((long)(Z1234[x1]-xstr.nextDouble())-PROBE_INCREMENT^2L)*(long)Math.exp(Z1234[xstr.nextInt(1000)+1000])^(long)Math.sqrt(Z1234[xstr.nextInt(1000)+2000]))*2L)-(long)Math.ceil(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])^(long)Math.sqrt(FLOAT_UNIT_LONG-1+xstr.nextInt(1000000)^DOUBLE_UNIT_LONG))|SEEDER_INCREMENT)*1*2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=((((((((long)(Z1234[x1]-xstr.nextDouble())*2L-8)*(long)Math.cos((long)Z1234[xstr.nextInt(1000)+1000]^1L)-(long)Math.round(Z1234[xstr.nextInt(1000)+2000]))-1L)^(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.signum(Z1234[xstr.nextInt(1000)]-1L)^(long)Math.nextUp(xstr.nextInt(10)))));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((long)(Z1234[x1]-xstr.nextDouble()))^(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]*128, 2)^(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 2))^1010101L)-(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))*addend-SEEDER_INCREMENT)^2);
                }
        } else if (which_generator==2) {
        if (which_type==0) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~(((long)Z1234[x1]^tlr.current().nextLong())>>PROBE_INCREMENT*2L)^GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))^8L*900L)+(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.log10(Z1234[xstr.nextInt(1000)])*addend/mask-(long)Math.scalb((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))>>DOUBLE_UNIT_LONG, 2))>>SEEDER_INCREMENT)^1^2^8^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^tlr.current().nextLong())|PROBE_INCREMENT|2L)*(long)Math.log(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L*900L)+(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.log10(Z1234[xstr.nextInt(1000)])*addend/mask-(long)Math.ulp((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))<<DOUBLE_UNIT_LONG))*SEEDER_INCREMENT)|10^1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]^tlr.current().nextLong())<<2L|8)*(long)Math.sqrt((long)Z1234[xstr.nextInt(1000)+1000]>>8L)+(long)Math.ceil((long)Z1234[xstr.nextInt(1000)+2000]<<1L))|1L/32L)-(long)Math.rint(Z1234[xstr.nextInt(1000)+3000])/200L)/1+(long)Math.signum(Z1234[xstr.nextInt(1000)]*128L)>>(long)Math.sinh(FLOAT_UNIT_LONG*xstr.nextInt(10)|DOUBLE_UNIT_LONG))^SEEDER_INCREMENT)|10);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^tlr.current().nextLong()))*(long)Math.getExponent(Z1234[xstr.nextInt(1000)+1000]*5L)<<(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 16))/1010101L)+(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend*mask-(long)Math.scalb(2L*xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))<<SEEDER_INCREMENT)^16);
                } else if (which_type==1) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~(((long)Z1234[x1]|(byte)tlr.current().nextInt())>>PROBE_INCREMENT*2L)|GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))|8L*900L)^(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])*addend-mask-(long)Math.copySign((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))>>DOUBLE_UNIT_LONG, 2))>>SEEDER_INCREMENT)|1|2|8|2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]|(byte)tlr.current().nextInt())|PROBE_INCREMENT|2L)*(long)Math.log(Z1234[xstr.nextInt(1000)+1000])*(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))|8L*900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])*addend-mask-(long)Math.ulp((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))*DOUBLE_UNIT))*SEEDER_INCREMENT)|10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]|(byte)tlr.current().nextInt())*2L|8)*(long)Math.sqrt((long)Z1234[xstr.nextInt(1000)+1000]>>8L)^(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000]*1L, 2D))|1L-32L)-(long)Math.sqrt(Z1234[xstr.nextInt(1000)+3000])-15L)-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128L)>>(long)Math.cosh(FLOAT_UNIT_LONG*xstr.nextInt(10)|DOUBLE_UNIT_LONG))|SEEDER_INCREMENT)|10);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]|(byte)tlr.current().nextInt()))*(long)Math.IEEEremainder((long)Z1234[xstr.nextInt(1000)+1000]<<2L, 2)*(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend*mask-(long)Math.copySign(2L*xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))*SEEDER_INCREMENT)|11);
                } else if (which_type==2) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((-(((long)Z1234[x1]^tlr.current().nextInt())+PROBE_INCREMENT<<2L)^GAMMA)<<(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend*mask/1+(long)Math.copySign(FLOAT_UNIT/1+xstr.nextInt(1000000)-DOUBLE_UNIT, 2))-SEEDER_INCREMENT)^1^2^8^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^tlr.current().nextInt())^PROBE_INCREMENT^2L)<<(long)Math.log(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend-mask-(long)Math.atan2(FLOAT_UNIT_LONG^1+xstr.nextInt(1000000)+DOUBLE_UNIT_LONG, 2))|SEEDER_INCREMENT)>>10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]^tlr.current().nextInt())<<2L^8)<<(long)Math.sqrt(Z1234[xstr.nextInt(1000)+1000]-8L)^(long)Math.hypot((long)Z1234[xstr.nextInt(1000)+2000]<<1L, 2D))^1L)-(long)Math.sqrt(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)-(long)Math.atan(xstr.nextInt(10)))^SEEDER_INCREMENT));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^tlr.current().nextInt()))+(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]-2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend<<mask-(long)Math.copySign(2L<<xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))<<SEEDER_INCREMENT)^11);
                } else if (which_type==3) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((-(((long)Z1234[x1]^tlr.current().nextInt())+PROBE_INCREMENT<<2L)^GAMMA)<<(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))>>5L)^(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend*mask/1+(long)Math.copySign(FLOAT_UNIT/1+xstr.nextInt(1000000)-DOUBLE_UNIT, 2))-SEEDER_INCREMENT)<<1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^tlr.current().nextInt())^PROBE_INCREMENT^2L)<<(long)Math.log(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend-mask-(long)Math.copySign(FLOAT_UNIT_LONG^1+xstr.nextInt(1000000)+DOUBLE_UNIT_LONG, 2))|SEEDER_INCREMENT)>>10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=((((((((long)Z1234[x1]^tlr.current().nextInt())<<2L^8)<<(long)Math.cos(Z1234[xstr.nextInt(1000)+1000]-8L)^(long)Math.scalb(Z1234[xstr.nextInt(1000)+2000]*256/1000, 2)^1L)-(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)-(long)Math.atan(xstr.nextInt(10)))>>2L));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^tlr.current().nextInt()))+(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]-2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.cos(Z1234[xstr.nextInt(1000)])/addend^mask+(long)Math.copySign(2L<<xstr.nextInt(100), 8))<<SEEDER_INCREMENT)^11);
                } else if (which_type==4) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~((long)(Z1234[x1]*tlr.current().nextFloat())+PROBE_INCREMENT*2L)>>GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))>>5L)>>(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])*addend-mask+(long)Math.copySign((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))^DOUBLE_UNIT_LONG, 2))^SEEDER_INCREMENT)*1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=((((((((long)(Z1234[x1]*tlr.current().nextFloat())>>PROBE_INCREMENT^2L)*(long)Math.exp(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))>>1L*2L)>>(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])^(long)Math.sqrt(FLOAT_UNIT*1+xstr.nextInt(1000000)+DOUBLE_UNIT))|SEEDER_INCREMENT)<<1/2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((long)(Z1234[x1]*tlr.current().nextFloat())*2L>>8)*(long)Math.cos((long)Z1234[xstr.nextInt(1000)+1000]^1L)>>(long)Math.scalb(Z1234[xstr.nextInt(1000)+2000]/128, 2)>>1L)^(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)^(long)Math.atan(xstr.nextInt(10)))^2L));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((long)(Z1234[x1]*tlr.current().nextFloat()))+(long)Math.IEEEremainder((long)Z1234[xstr.nextInt(1000)+1000]^2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 2))^1010101L)>>(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))*addend*SEEDER_INCREMENT)^2);
                } else if (which_type==5) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~((long)(Z1234[x1]-tlr.current().nextDouble()))>>GAMMA)*(long)Math.floor(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))|5L)-(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])*addend-mask^(long)Math.nextAfter(xstr.nextInt(10000)^DOUBLE_UNIT_LONG, 2))^SEEDER_INCREMENT)*1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=((((((((long)(Z1234[x1]-tlr.current().nextDouble())-PROBE_INCREMENT^2L)*(long)Math.exp(Z1234[xstr.nextInt(1000)+1000])^(long)Math.sqrt(Z1234[xstr.nextInt(1000)+2000]))*2L)-(long)Math.ceil(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])^(long)Math.sqrt(FLOAT_UNIT_LONG-1+xstr.nextInt(1000000)^DOUBLE_UNIT_LONG))|SEEDER_INCREMENT)*1*2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=((((((((long)(Z1234[x1]-tlr.current().nextDouble())*2L-8)*(long)Math.cos((long)Z1234[xstr.nextInt(1000)+1000]^1L)-(long)Math.round(Z1234[xstr.nextInt(1000)+2000]))-1L)^(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.signum(Z1234[xstr.nextInt(1000)]-1L)^(long)Math.nextUp(xstr.nextInt(10)))));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((long)(Z1234[x1]-tlr.current().nextDouble()))^(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]*128, 2)^(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 2))^1010101L)-(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))*addend-SEEDER_INCREMENT)^2);
                }
        } else if (which_generator==3) {
        if (which_type==0) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~(((long)Z1234[x1]^mtf.nextLong())>>PROBE_INCREMENT*2L)^GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))^8L*900L)+(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.log10(Z1234[xstr.nextInt(1000)])*addend/mask-(long)Math.scalb((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))>>DOUBLE_UNIT_LONG, 2))>>SEEDER_INCREMENT)^1^2^8^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^mtf.nextLong())|PROBE_INCREMENT|2L)*(long)Math.log(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L*900L)+(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.log10(Z1234[xstr.nextInt(1000)])*addend/mask-(long)Math.ulp((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))<<DOUBLE_UNIT_LONG))*SEEDER_INCREMENT)|10^1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]^mtf.nextLong())<<2L|8)*(long)Math.sqrt((long)Z1234[xstr.nextInt(1000)+1000]>>8L)+(long)Math.ceil((long)Z1234[xstr.nextInt(1000)+2000]<<1L))|1L/32L)-(long)Math.rint(Z1234[xstr.nextInt(1000)+3000])/200L)/1+(long)Math.signum(Z1234[xstr.nextInt(1000)]*128L)>>(long)Math.sinh(FLOAT_UNIT_LONG*xstr.nextInt(10)|DOUBLE_UNIT_LONG))^SEEDER_INCREMENT)|10);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^mtf.nextLong()))*(long)Math.getExponent(Z1234[xstr.nextInt(1000)+1000]*5L)<<(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 16))/1010101L)+(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend*mask-(long)Math.scalb(2L*xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))<<SEEDER_INCREMENT)^16);
                } else if (which_type==1) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~(((long)Z1234[x1]|(byte)mtf.nextInt())>>PROBE_INCREMENT*2L)|GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))|8L*900L)^(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])*addend-mask-(long)Math.copySign((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))>>DOUBLE_UNIT_LONG, 2))>>SEEDER_INCREMENT)|1|2|8|2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]|(byte)mtf.nextInt())|PROBE_INCREMENT|2L)*(long)Math.log(Z1234[xstr.nextInt(1000)+1000])*(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))|8L*900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])*addend-mask-(long)Math.ulp((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))*DOUBLE_UNIT))*SEEDER_INCREMENT)|10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]|(byte)mtf.nextInt())*2L|8)*(long)Math.sqrt((long)Z1234[xstr.nextInt(1000)+1000]>>8L)^(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000]*1L, 2D))|1L-32L)-(long)Math.sqrt(Z1234[xstr.nextInt(1000)+3000])-15L)-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128L)>>(long)Math.cosh(FLOAT_UNIT_LONG*xstr.nextInt(10)|DOUBLE_UNIT_LONG))|SEEDER_INCREMENT)|10);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]|(byte)mtf.nextInt()))*(long)Math.IEEEremainder((long)Z1234[xstr.nextInt(1000)+1000]<<2L, 2)*(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend*mask-(long)Math.copySign(2L*xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))*SEEDER_INCREMENT)|11);
                } else if (which_type==2) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((-(((long)Z1234[x1]^mtf.nextInt())+PROBE_INCREMENT<<2L)^GAMMA)<<(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend*mask/1+(long)Math.copySign(FLOAT_UNIT/1+xstr.nextInt(1000000)-DOUBLE_UNIT, 2))-SEEDER_INCREMENT)^1^2^8^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^mtf.nextInt())^PROBE_INCREMENT^2L)<<(long)Math.log(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend-mask-(long)Math.atan2(FLOAT_UNIT_LONG^1+xstr.nextInt(1000000)+DOUBLE_UNIT_LONG, 2))|SEEDER_INCREMENT)>>10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((((long)Z1234[x1]^mtf.nextInt())<<2L^8)<<(long)Math.sqrt(Z1234[xstr.nextInt(1000)+1000]-8L)^(long)Math.hypot((long)Z1234[xstr.nextInt(1000)+2000]<<1L, 2D))^1L)-(long)Math.sqrt(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)-(long)Math.atan(xstr.nextInt(10)))^SEEDER_INCREMENT));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^mtf.nextInt()))+(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]-2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.sqrt(Z1234[xstr.nextInt(1000)])-addend<<mask-(long)Math.copySign(2L<<xstr.nextInt(100)*DOUBLE_UNIT_LONG, 8))<<SEEDER_INCREMENT)^11);
                } else if (which_type==3) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((-(((long)Z1234[x1]^mtf.nextInt())+PROBE_INCREMENT<<2L)^GAMMA)<<(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])<<(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))>>5L)^(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend*mask/1+(long)Math.copySign(FLOAT_UNIT/1+xstr.nextInt(1000000)-DOUBLE_UNIT, 2))-SEEDER_INCREMENT)<<1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=(((((((((long)Z1234[x1]^mtf.nextInt())^PROBE_INCREMENT^2L)<<(long)Math.log(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))^8L<<900L)^(long)Math.log1p(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.log(Z1234[xstr.nextInt(1000)])<<addend-mask-(long)Math.copySign(FLOAT_UNIT_LONG^1+xstr.nextInt(1000000)+DOUBLE_UNIT_LONG, 2))|SEEDER_INCREMENT)>>10|1);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=((((((((long)Z1234[x1]^mtf.nextInt())<<2L^8)<<(long)Math.cos(Z1234[xstr.nextInt(1000)+1000]-8L)^(long)Math.scalb(Z1234[xstr.nextInt(1000)+2000]*256/1000, 2)^1L)-(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))-(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)-(long)Math.atan(xstr.nextInt(10)))>>2L));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((((long)Z1234[x1]^mtf.nextInt()))+(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]-2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 11))-1010101L)^(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))/1+(long)Math.cos(Z1234[xstr.nextInt(1000)])/addend^mask+(long)Math.copySign(2L<<xstr.nextInt(100), 8))<<SEEDER_INCREMENT)^11);
                } else if (which_type==4) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~((long)(Z1234[x1]*mtf.nextFloat())+PROBE_INCREMENT*2L)>>GAMMA)*(long)Math.cbrt(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))>>5L)>>(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])*addend-mask+(long)Math.copySign((FLOAT_UNIT_LONG*1+xstr.nextInt(1000000))^DOUBLE_UNIT_LONG, 2))^SEEDER_INCREMENT)*1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=((((((((long)(Z1234[x1]*mtf.nextFloat())>>PROBE_INCREMENT^2L)*(long)Math.exp(Z1234[xstr.nextInt(1000)+1000])+(long)Math.expm1(Z1234[xstr.nextInt(1000)+2000]))>>1L*2L)>>(long)Math.expm1(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])^(long)Math.sqrt(FLOAT_UNIT*1+xstr.nextInt(1000000)+DOUBLE_UNIT))|SEEDER_INCREMENT)<<1/2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=(((((((long)(Z1234[x1]*mtf.nextFloat())*2L>>8)*(long)Math.cos((long)Z1234[xstr.nextInt(1000)+1000]^1L)>>(long)Math.scalb(Z1234[xstr.nextInt(1000)+2000]/128, 2)>>1L)^(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.signum(Z1234[xstr.nextInt(1000)]*128)^(long)Math.atan(xstr.nextInt(10)))^2L));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((long)(Z1234[x1]*mtf.nextFloat()))+(long)Math.IEEEremainder((long)Z1234[xstr.nextInt(1000)+1000]^2L, 2)+(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 2))^1010101L)>>(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))*addend*SEEDER_INCREMENT)^2);
                } else if (which_type==5) {
                    int x1 = xstr.nextInt(1000);
                    Z1234[x1]=(((((((~((long)(Z1234[x1]-mtf.nextDouble()))>>GAMMA)*(long)Math.floor(Z1234[xstr.nextInt(1000)+1000])*(long)Math.exp(Z1234[xstr.nextInt(1000)+2000]))|5L)-(long)Math.exp(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])*addend-mask^(long)Math.nextAfter(xstr.nextInt(10000)^DOUBLE_UNIT_LONG, 2))^SEEDER_INCREMENT)*1L^2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+1000]=((((((((long)(Z1234[x1]-mtf.nextDouble())-PROBE_INCREMENT^2L)*(long)Math.exp(Z1234[xstr.nextInt(1000)+1000])^(long)Math.sqrt(Z1234[xstr.nextInt(1000)+2000]))*2L)-(long)Math.ceil(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.exp(Z1234[xstr.nextInt(1000)])^(long)Math.sqrt(FLOAT_UNIT_LONG-1+xstr.nextInt(1000000)^DOUBLE_UNIT_LONG))|SEEDER_INCREMENT)*1*2);
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+2000]=((((((((long)(Z1234[x1]-mtf.nextDouble())*2L-8)*(long)Math.cos((long)Z1234[xstr.nextInt(1000)+1000]^1L)-(long)Math.round(Z1234[xstr.nextInt(1000)+2000]))-1L)^(long)Math.cos(Z1234[xstr.nextInt(1000)+3000]))^(long)Math.signum(Z1234[xstr.nextInt(1000)]-1L)^(long)Math.nextUp(xstr.nextInt(10)))));
                    x1 = xstr.nextInt(1000);
                    Z1234[x1+3000]=(((((((long)(Z1234[x1]-mtf.nextDouble()))^(long)Math.IEEEremainder(Z1234[xstr.nextInt(1000)+1000]*128, 2)^(long)Math.hypot(Z1234[xstr.nextInt(1000)+2000], 2))^1010101L)-(long)Math.atan(Z1234[xstr.nextInt(1000)+3000]))*addend-SEEDER_INCREMENT)^2);
                }
        }
        }
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy amplification by mathematical formulas - "+elapsed/1000000+" ms");
        startT = System.nanoTime();
        for (int i=0;i<1000;i++) {
            if (seedUniquifier()>10000000000L) {
            Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)];Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+2000];Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+3000];
            Z1234[xstr.nextInt(1000)+1000]=Z1234[xstr.nextInt(1000)];Z1234[xstr.nextInt(1000)+1000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+1000]=Z1234[xstr.nextInt(1000)+2000];Z1234[xstr.nextInt(1000)+1000]=Z1234[xstr.nextInt(1000)+3000];
            } else if (seedUniquifier()<-10000000000L) {
            Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)];Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+2000];Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+3000];
            Z1234[xstr.nextInt(1000)+3000]=Z1234[xstr.nextInt(1000)];Z1234[xstr.nextInt(1000)+3000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+3000]=Z1234[xstr.nextInt(1000)+2000];Z1234[xstr.nextInt(1000)+3000]=Z1234[xstr.nextInt(1000)+3000];
            } else {
            Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+1000];Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+3000];Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+3000];
            Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+1000];Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+1000];
            Z1234[xstr.nextInt(1000)+2000]=Z1234[xstr.nextInt(1000)+3000];Z1234[xstr.nextInt(1000)]=Z1234[xstr.nextInt(1000)+3000];
            }
        }
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy blending array\'s values #2 - "+elapsed/1000000+" ms");
        startT = System.nanoTime();
        byte[] data_finish;
        if (file_find) {
            char[] data_char = data_s.toCharArray();
            for (int i=0;i<data_char.length;i++) {
                if (xstr.nextInt(10)==0) {
                    char[] Z1ca = Long.toString((long)Math.abs(Z1234[xstr.nextInt(1000)]), 16).toCharArray();
                    for (int j=0;i<Z1ca.length;j++) {
                        data_char[i]=Z1ca[j];
                        i++;
                    }
                }
            }
            for (int i=0;i<data_char.length;i++) {
                if (xstr.nextInt(10)==0) {
                    char[] Z1ca = Long.toString((long)Math.abs(Z1234[xstr.nextInt(1000)+1000]), 16).toCharArray();
                    for (int j=0;i<Z1ca.length;j++) {
                        data_char[i]=Z1ca[j];
                        i++;
                    }
                }
            }
            for (int i=0;i<data_char.length;i++) {
                if (xstr.nextInt(10)==0) {
                    char[] Z1ca = Long.toString((long)Math.abs(Z1234[xstr.nextInt(1000)+2000]), 16).toCharArray();
                    for (int j=0;i<Z1ca.length;j++) {
                        data_char[i]=Z1ca[j];
                        i++;
                    }
                }
            }
            for (int i=0;i<data_char.length;i++) {
                if (xstr.nextInt(10)==0) {
                    char[] Z1ca = Long.toString((long)Math.abs(Z1234[xstr.nextInt(1000)+3000]), 16).toCharArray();
                    for (int j=0;i<Z1ca.length;j++) {
                        data_char[i]=Z1ca[j];
                        i++;
                    }
                }
            }
            StringBuilder sb = new StringBuilder(data_char.length);
            for (int i=0;i<data_char.length;i++) sb.append(data_char[i]);
            data_finish=(String.valueOf(sb)).getBytes();
            elapsed = System.nanoTime() - startT;
            cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy combined old & new data, put finish data in byte array - "+elapsed/1000000+" ms");
            startT = System.nanoTime();
        } else {
            StringBuilder sb = new StringBuilder(4000);
            for (int i=0;i<Z1234.length;i++) sb.append(Long.toString((long)Math.abs(Z1234[i]), 16));
            data_finish = (String.valueOf(sb)).getBytes();
            //cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy finish data byte array length - "+data_finish.length);
            //cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy finish data byte array: "+Arrays.toString(data_finish));
            elapsed = System.nanoTime() - startT;
            cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy put finish data in byte array - "+elapsed/1000000+" ms");
            startT = System.nanoTime();
        }
        try {
        OutputStream fos = new BufferedOutputStream(new FileOutputStream(point));
        fos.write(data_finish);
        fos.flush();
        } catch (Exception e) {cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.WARN, (Throwable)e, "GeneratorEntropy stacktrace: %s", (Throwable)e);}
        elapsed = System.nanoTime() - startT;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy write data in file to disk - "+elapsed/1000000+" ms");
        elapsed = System.nanoTime() - startT0;
        cpw.mods.fml.common.FMLLog.log(org.apache.logging.log4j.Level.INFO, "GeneratorEntropy end func Gen(). Total time: "+elapsed/1000000+" ms");
        
    }

}