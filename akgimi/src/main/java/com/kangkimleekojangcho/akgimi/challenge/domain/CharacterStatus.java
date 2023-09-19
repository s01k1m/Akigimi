package com.kangkimleekojangcho.akgimi.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CharacterStatus {
    LEVEL0(0),
    LEVEL1(1),
    LEVEL2(2),
    LEVEL3(3),
    LEVEL4(4),
    LEVEL5(5);
    private Integer level;
}
