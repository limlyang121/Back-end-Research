package com.myapp.restapi.researchconference.DAO.Interface;

import com.myapp.restapi.researchconference.entity.Review.BlacklistPaper;

public interface BlacklistDAO {
    boolean addBlackList(BlacklistPaper blacklistPaper);

    boolean deleteBlackList(BlacklistPaper blacklistPaper);

    boolean deleteBlackListAssociatedWithSpecifiedPaperID(int paperID);
}
