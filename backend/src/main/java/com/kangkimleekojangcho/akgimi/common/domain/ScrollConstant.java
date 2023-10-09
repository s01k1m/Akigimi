package com.kangkimleekojangcho.akgimi.common.domain;

import lombok.Getter;

@Getter
public enum ScrollConstant {
    FIRST_SCROLL(-1),
    LAST_SCROLL(-2);

    final int value;

    ScrollConstant(int value) {
        this.value = value;
    }
}
