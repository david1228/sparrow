package com.zoo.sparrow.guava;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by David.Liu on 17/5/11.
 */
public class PreconditionsTest {

    public static void main(String[] args) {
        String id = "2111111111111111";
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(id), "专辑ID不能为空");
            Preconditions.checkArgument(id.matches("[0-9,]+")
                    && (!id.contains(",") && id.length() < 10), "输入id非法");
        } catch (IllegalStateException argEx) {
            System.out.println(argEx.getMessage());
        }
    }

}
