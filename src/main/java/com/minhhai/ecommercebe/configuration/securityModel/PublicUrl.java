package com.minhhai.ecommercebe.configuration.securityModel;

import java.util.List;

public class PublicUrl {
    public static final List<String> WHITE_LIST = List.of(
            "/auth/**", "/payment/vn-pay-callback"
    );
}
