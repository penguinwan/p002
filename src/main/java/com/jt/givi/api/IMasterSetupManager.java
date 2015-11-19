package com.jt.givi.api;

import com.jt.givi.model.Mold;

import java.io.IOException;
import java.util.List;

/**
 * Created by superman on 11/19/2015.
 */
public interface IMasterSetupManager {
    List<Mold> load() throws IOException, NumberFormatException;
    void save(List<Mold> moldList) throws IOException;
}
