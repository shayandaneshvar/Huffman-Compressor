package ir.shayandaneshvar.services.security;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class Security {
    private MD5Algorithm md5;

    public Security() {
        md5 = new MD5Algorithm();
    }
}
