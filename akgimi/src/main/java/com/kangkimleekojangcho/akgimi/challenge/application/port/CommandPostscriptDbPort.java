package com.kangkimleekojangcho.akgimi.challenge.application.port;

import com.kangkimleekojangcho.akgimi.challenge.domain.Postscript;

public interface CommandPostscriptDbPort {
    Postscript save(Postscript postscript);
}
