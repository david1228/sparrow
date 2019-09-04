package com.zoo.sparrow.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import java.util.List;

/**
 * @author liudewei
 * @date 2019/5/30
 */
public class SplitterTest {
  static final Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();
  public static void main(String[] args) {

//    String s = " s,,,,1,";
//    System.out.println(splitter.splitToList(s));

//    String string = "com.letv.tv_2.9.24_100-Dalvik/2.1.0 (Linux; U; Android 7.0; Q5?\\u009B\\u009B??? Build/HMD-2.0.3)";
    // com.letv.tv_2.9.34_284-Dalvik/2.1.0 (Linux; U; Android 5.1.1; ?????????????????? Build/LMY48P
//    String string = "com.letv.tv_2.9.24_100-Dalvik/2.1.0 (Linux; U; Android 6.0; ????????? Build/MRA58K)";
    //com.letv.tv_2.9.34_284-Dalvik/2.1.0 (Linux; U; Android 5.1.1; ?\u0095??\u0099??\u0099??\u0083??\u0094???\u0086 Build/LMY48P)
    //
    String string = "com.letv.tv_2.9.24_100-Dalvik/2.1.0 (Linux; U; Android 6.0; ?????\\u009C?\\u0096? Build/MRA58K)";
    List<String> result = Splitter.on(" ").splitToList(string).subList(0, 5);
//    List<String> result = Lists.newArrayList(Splitter.on(" ").splitToList(string));
//    System.out.println(result.size());
//    result.remove(5);
    System.out.println(Joiner.on(" ").join(result));

  }
}
